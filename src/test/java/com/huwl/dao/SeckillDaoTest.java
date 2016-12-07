package com.huwl.dao;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.huwl.entity.Seckill;

/**
 * 配置spring和junit整合，junit启动时加载IOC容器
 * spring-test, junit
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})	//告诉junit spring配置文件
public class SeckillDaoTest {
	
	//注入Dao实现类依赖
	@Autowired
	private SeckillDao seckillDao;

	@Test
	public void testReduceNumber() throws Exception {
		Date killTime = new Date();
		int result = seckillDao.reduceNumber(1000l, killTime);
//		System.out.println("result: " + result);
		assertEquals(1, result);
	}
	
	@Test
	public void testQueryById() throws Exception {
		long id = 1000;
		Seckill seckill = seckillDao.queryById(id);
		System.out.println(seckill.getName());
		System.out.println(seckill);
	}
	
	@Test
	public void testQueryAll() throws Exception {
		List<Seckill> list = seckillDao.queryAll(0, 10);
		for (Seckill seckill : list) {
			System.out.println(seckill);
		}
	}

}
