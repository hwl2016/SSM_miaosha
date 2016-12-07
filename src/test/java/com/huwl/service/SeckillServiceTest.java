package com.huwl.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.huwl.dto.Exposer;
import com.huwl.dto.SeckillExecute;
import com.huwl.entity.Seckill;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml"})
public class SeckillServiceTest {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SeckillService seckillService;

	@Test
	public void testGetSeckillList() {
		List<Seckill> list = seckillService.getSeckillList();
		logger.info("list={}", list);
	}
	
	@Test
	public void testGetById() {
		long id = 1000L;
		Seckill seckill = seckillService.getById(id);
		logger.info("seckill={}", seckill);
	}
	
	@Test
	public void testExportSeckillUrl() {
		long id = 1000;
		Exposer exposer = seckillService.exportSeckillUrl(id);
		logger.info("exposer={}", exposer);
	}
	
	@Test
	public void testExecuteSeckill() {
		long id = 1000;
		long phone = 15201351122L;
		String md5 = "0e5ffc0d106cc253477fcc8c328ffd1c";
		try {
			SeckillExecute seckillExecute = seckillService.executeSeckill(id, phone, md5);
			logger.info("result={}", seckillExecute);
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
			
	}

}
