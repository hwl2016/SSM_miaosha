package com.huwl.dao;

import org.apache.ibatis.annotations.Param;

import com.huwl.entity.SuccessKilled;

public interface SuccessKilledDao {
	
	/**
	 * 插入购买明细 表中使用了联合主键可多虑重复  
	 * @param seckillId
	 * @param userPhone
	 * @return	插入的行数
	 */
	int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);
	
	/**
	 * 根据id查询SuccessKilled并携带秒杀产品的实体对象
	 * @param seckillId
	 * @param userPhone
	 * @return
	 */
	SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

}
