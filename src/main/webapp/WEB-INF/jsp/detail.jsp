<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="common/base.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>秒杀详情页</title>
    <%@include file="common/head.jsp" %>
</head>
<body>
	<div class="container">
		<div class="panel panel-default text-center">
			<div class="panel-heading">
				<h2>${seckill.name}</h2>
			</div>
			<div class="panel-body">
				<div class="text-danger">
					<span class="glyphicon glyphicon-time"></span>
					<span class="glyphicon" id="seckill-box"></span>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 登录弹出层，输入电话 -->
	<div id="killPhoneModal" class="modal fade">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	         <h3 class="modal-title text-center">
	        	<span class="glyphicon glyphicon-phone"></span>秒杀电话：
	        </h3>
	      </div>
	      <div class="modal-body">
	        <div class="row">
	        	<div class="col-xs-8 col-xs-offset-2">
	        		<input type="text" name="killPhone" id="killPhoneKey" placeholder="请输入手机号" class="form-control"/>
	        	</div>
	        </div>
	      </div>
	      <div class="modal-footer">
      		<span id="killPhoneMessage" class="glyphicon"></span>
	        <button id="killPhoneBtn" class="btn btn-success">
	        	<span class="glyphicon glyphicon-phone"></span> Submit
	        </button>
	        <!-- <button id="killPhoneBtn" class="btn btn-danger" data-dismiss="modal">
	        	<span class="glyphicon glyphicon-remove"></span> Cancel
	        </button> -->
	      </div>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	
    <script src="${ctx}/static/js/jQuery-2.1.4.min.js"></script>
    <script src="${ctx}/static/js/jquery.cookie.js"></script>
    <script src="${ctx}/static/js/jquery.countdown.min.js"></script>
    <script src="${ctx}/static/bootstrap/js/bootstrap.min.js"></script>
    <script src="${ctx}/static/js/seckill.js"> </script>
    <script>
    	$(function() {
    		//使用EL表达式传入参数
    		seckill.detail.init({
    			seckillId: '${seckill.seckillId}',
    			startTime: '${seckill.startTime.time}',	//使用EL表达式将日期转成毫秒时间
    			endTime: '${seckill.endTime.time}'
    		});
    	})
    </script>
</body>
</html>