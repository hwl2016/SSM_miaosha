package com.huwl.service;

import java.util.List;

import com.huwl.dto.Exposer;
import com.huwl.dto.SeckillExecute;
import com.huwl.entity.Seckill;
import com.huwl.exception.RepeatKillException;
import com.huwl.exception.SeckillCloseException;
import com.huwl.exception.SeckillException;

/**
 * 业务接口：站在使用者的角度设计接口
 * 三个方面：方法定义粒度，参数，返回类型（return类型/异常)
 * @author zzh
 *
 */
public interface SeckillService {
	
	/**
	 * 查询所有秒杀记录
	 * @return
	 */
	List<Seckill> getSeckillList();
	
	/**
	 * 查询单个秒杀记录
	 * @param seckillId
	 * @return
	 */
	Seckill getById(long seckillId);
	
	/**
	 * 秒杀开启时输出秒杀接口地址
	 * 否则输出系统时间和秒杀时间
	 * 这样做是为了防止别人提前拿到秒杀接口地址后 用js脚本进行刷单
	 * @param seckillId
	 */
	Exposer exportSeckillUrl(long seckillId);
	
	/**
	 * 执行秒杀操作
	 * @param seckillId
	 * @param userPhone
	 * @param md5	从Exposer中获取md5 然后通过算法来进行比对前后是否一致，从而判断url地址是否被篡改
	 * @return
	 * @throws SeckillException	秒杀业务异常
	 * @throws RepeatKillException	重复秒杀异常
	 * @throws SeckillCloseException	秒杀关闭异常
	 */
	SeckillExecute executeSeckill(long seckillId, long userPhone, String md5) 
			throws SeckillException, RepeatKillException, SeckillCloseException;
	
	/**
	 * 执行秒杀操作 by 存储过程
	 * @param seckillId
	 * @param userPhone
	 * @param md5	从Exposer中获取md5 然后通过算法来进行比对前后是否一致，从而判断url地址是否被篡改
	 * @return
	 * @throws SeckillException	秒杀业务异常
	 * @throws RepeatKillException	重复秒杀异常
	 * @throws SeckillCloseException	秒杀关闭异常
	 */
	SeckillExecute executeSeckillByProcedure(long seckillId, long userPhone, String md5);
	
}
