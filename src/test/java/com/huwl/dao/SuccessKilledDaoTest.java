package com.huwl.dao;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.huwl.entity.SuccessKilled;

/**
 * 配置spring和junit整合，junit启动时加载IOC容器
 * spring-test, junit
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})	//告诉junit spring配置文件
public class SuccessKilledDaoTest {
	
	@Autowired
	private SuccessKilledDao successKilledDao;

	@Test
	public void testInsertSuccessKilled() throws Exception {
		int result = successKilledDao.insertSuccessKilled(1001L, 15201351234L);
		assertEquals(1, result);	//采用了联合主键 不能重复插入
	}
	
	@Test
	public void testQueryByIdWithSeckill() throws Exception {
		SuccessKilled sk = successKilledDao.queryByIdWithSeckill(1002L, 15201358461L);
		System.out.println(sk);
	}

}
