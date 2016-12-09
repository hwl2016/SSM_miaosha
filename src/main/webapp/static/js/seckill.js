(function() {		//javascript模块化
	var seckill = {
			//封装秒杀相关ajax的URL
			URL: {
				now: '/seckill/time/now',	//获取服务器时间的url
				exposer: function(seckillId) {	//获取秒杀对象url
					return '/seckill/' + seckillId + '/exposer';
				},
				execution: function(seckillId, md5) {	//执行秒杀url
					return '/seckill/' + seckillId + '/' + md5 + '/execution';
				}
			},
			validatePhone: function(phone) {	//验证手机号
				if ((/^1[34578]\d{9}$/.test(phone))) {
					return true;
				}else {
					return false;
				}
			},
			//详情页秒杀逻辑
			detail: {
				//详情页初始化
				init: function(params) {
					//用户手机验证，计时交互
					//规划我们的交互流程
					//在cookie中查找手机号
					var killPhone = $.cookie('killPhone');
					
					if (!seckill.validatePhone(killPhone)) {	//手机号不存在、错误
						//绑定手机  控制输出
						var killPhoneModal = $("#killPhoneModal");
						killPhoneModal.modal({
							backdrop: 'static',	//禁止位置关闭
							keyboard: false,		//关闭键盘事件
							show: true
						});
						
						$("#killPhoneBtn").click(function() {	//绑定提交事件
							var inputPhone  = $("#killPhoneKey").val();
							if(seckill.validatePhone(inputPhone)) {
								//电话写入cookie
								$.cookie("killPhone", inputPhone, {expires: 1, path: "/seckill"});	//设置cookie有效期1天，在/seckill路径下有效
								//刷新页面
								window.location.reload();
							}else {	//用户输入的手机号不合法
								$("#killPhoneMessage").hide().html('<label class="label label-danger">手机号不合法！</label>').show(300);
							}
						});
					}
					
					//已经登录
					//计时交互
					var seckillId = params.seckillId;
					var startTime = params.startTime;
					var endTime = params.endTime;
					$.get(seckill.URL.now, {}, function(result) {
						if (result && result.success) {
							var nowTime = result.data;
							//时间判断  计时交互
							seckill.countdown(seckillId, nowTime, startTime, endTime);
						} else {
							console.log(result);
						}
					})
				}
			},
			countdown: function(seckillId, nowTime, startTime, endTime) {	//倒计时
				var seckillBox = $("#seckill-box");
				//时间判断
				if (nowTime > endTime) {		//秒杀结束
					seckillBox.text("秒杀结束");
				}else if(nowTime < startTime) { //秒杀未开始  计时事件绑定
					var killTime = new Date(startTime - 0 + 1000);
					seckillBox.countdown(killTime, function(event) {
						var format = event.strftime('秒杀倒计时：%D天 %H时 %M分 %S秒');	//时间格式
						$(this).html(format);
					}).on('finish.countdown', function() { //时间完成后回调事件
						//获取秒杀地址，控制实现逻辑，执行秒杀
						seckill.handlerSeckill(seckillId, seckillBox);
					});
				} else {	//秒杀开始
					seckill.handlerSeckill(seckillId, seckillBox);
				}
			},
			handlerSeckill : function(seckillId, node) {	//处理秒杀逻辑
				//获取秒杀地址，控制实现逻辑，执行秒杀
				node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');	//秒杀按钮
				$.post(seckill.URL.exposer(seckillId), {}, function(result) {
					//回调函数中，执行交互流程
					if (result && result.success) {
						var exposer = result.data;
						if(exposer.exposed) {	//开启秒杀
							//获取秒杀地址
							var md5 = exposer.md5;
							var killUrl = seckill.URL.execution(seckillId, md5);
							console.log(killUrl);
							$("#killBtn").one("click", function() {	//绑定一次点击事件
								//执行秒杀操作
								//1、禁用按钮
								$(this).addClass('disabled');
								//2、发送秒杀请求执行秒杀
								$.post(killUrl, {}, function(result) {
									if(result && result.success) {
										var killResult = result.data;
										var state = killResult.state;
										var stateInfo = killResult.stateInfo;
										//3、显示秒杀结果
										node.html('<span class="label label-success">' + stateInfo + '</span>');
									} else {
										console.log(result);
									}
								});
							});
							node.show();
						} else {	//未开启秒杀  客户端倒计时可能有偏差
							var now = exposer.now;
							var start = exposer.start;
							var end = exposer.end;
							seckill.countdown(seckillId, now, start, end);	//重新计算计时逻辑
						}
					} else {
						console.log(result);
					}
				});
			}
	}
	window.seckill = seckill;	//将seckill导出挂载到window对象上
})();
