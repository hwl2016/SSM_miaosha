package com.huwl.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.huwl.dao.SeckillDao;
import com.huwl.dao.SuccessKilledDao;
import com.huwl.dto.Exposer;
import com.huwl.dto.SeckillExecute;
import com.huwl.entity.Seckill;
import com.huwl.entity.SuccessKilled;
import com.huwl.enums.SeckillStatEnum;
import com.huwl.exception.RepeatKillException;
import com.huwl.exception.SeckillCloseException;
import com.huwl.exception.SeckillException;
import com.huwl.service.SeckillService;

@Service
public class SeckillServiceImpl implements SeckillService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//注入Service依赖
	@Autowired
	private SeckillDao seckillDao;
	
	@Autowired
	private SuccessKilledDao successKilledDao;
	
	private final String slat = "vhurhg458721uiaoij89FHUI^$*$()&@^&*#HBFBHUjio56";	//md5盐值字符串，用于混淆md5  越复杂越好
	
	public List<Seckill> getSeckillList() {
		return seckillDao.queryAll(0, 4);
	}

	public Seckill getById(long seckillId) {
		return seckillDao.queryById(seckillId);
	}

	public Exposer exportSeckillUrl(long seckillId) {
		Seckill seckill = seckillDao.queryById(seckillId);
		if(seckill == null) {
			return new Exposer(false, seckillId);
		}
		Date startTime = seckill.getStartTime();
		Date endTime = seckill.getEndTime();
		Date nowTime = new Date();	//系统当前时间
		if (nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()) {	//秒杀未开始或者已结束
			return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
		}
		String md5 = this.getMD5(seckillId);
		return new Exposer(true, md5, seckillId);
	}
	
	
	/**
	 * 使用注解控制事物方法的优点：
	 * 1、开发团队达成一致约定，明确标注事物方法的编程风格
	 * 2、保证事物方法的执行时间尽可能短，不要穿插其他网络操作RPC/HTTP请求 或者剥离到事物方法外部
	 * 3、不是所有的方法都需要事物 如只有一条修改操作，只读操作不需要事物控制
	 */
	@Transactional
	public SeckillExecute executeSeckill(long seckillId, long userPhone,
			String md5) throws SeckillException, RepeatKillException,
			SeckillCloseException {
		if (md5 == null || !md5.equals(getMD5(seckillId))) {
			throw new SeckillException("seckill data rewrite!");
		}
		//执行秒杀业务逻辑：减库存 + 记录购买行为
		Date nowTime = new Date();
		try {
			int updateCount = seckillDao.reduceNumber(seckillId, nowTime);	//减库存
			if (updateCount <= 0) {		//没有更新到记录，秒杀结束
				throw new SeckillCloseException("seckill is close");
			}else {
				// 记录购买行为
				int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
				//唯一：seckillId, userPhone
				if (insertCount <= 0) {
					throw new RepeatKillException("重复秒杀");
				}else {
					//秒杀成功
					SuccessKilled sk = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
					return new SeckillExecute(seckillId, SeckillStatEnum.SUCCESS, sk);
				}
			}
		} catch (SeckillCloseException e1) {
			throw e1;
		}  catch (RepeatKillException e2) {
			throw e2;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			//所有编译期异常都转化为运行期异常  spring的声明式事物都会进行rollback
			throw new SeckillException("seckill inner error: " + e.getMessage());
		}
	}
	
	/**
	 * 生成md5
	 * @param seckillId
	 * @return
	 */
	private String getMD5(long seckillId) {
		String base = seckillId  + "/" + slat;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());	//spring提供的工具类用来生成md5
		return md5;
	}

}
