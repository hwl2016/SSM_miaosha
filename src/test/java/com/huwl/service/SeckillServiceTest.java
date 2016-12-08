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
import com.huwl.exception.RepeatKillException;
import com.huwl.exception.SeckillCloseException;

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
		} catch (RepeatKillException e) {
			logger.info(e.getMessage());
		} catch (SeckillCloseException e) {
			logger.info(e.getMessage());
		}
	}
	
	/**
	 * 测试代码完整逻辑，注意可重复执行
	 * 将testExportSeckillUrl和testExecuteSeckill两个测试方法进行集成
	 */
	@Test
	public void testSeckillLogic() {
		long id = 1001;
		Exposer exposer = seckillService.exportSeckillUrl(id);
		if(exposer.isExposed()) {	//秒杀开始
			logger.info("exposer={}", exposer);
			
			long phone = 15201351133L;
			String md5 = exposer.getMd5();
			try {
				SeckillExecute seckillExecute = seckillService.executeSeckill(id, phone, md5);
				logger.info("result={}", seckillExecute);
			} catch (RepeatKillException e) {
				logger.info(e.getMessage());
			} catch (SeckillCloseException e) {
				logger.info(e.getMessage());
			}
		}else {
			//秒杀未开启
//			logger.warn("秒杀未开始");
			logger.warn("exposer={}", exposer);
		}
		
	}

}
