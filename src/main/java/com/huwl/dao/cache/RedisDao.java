package com.huwl.dao.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.huwl.entity.Seckill;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisDao {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final JedisPool jedisPool;	//类似于数据库连接池
	
	public RedisDao(String ip, int port) {	//传入redis的host和端口
		jedisPool = new JedisPool(ip, port);
	}
	
	private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);
	
	public Seckill getSeckill(long seckillId) {
		//redis操作逻辑
		try {
			Jedis jedis = jedisPool.getResource();		//相当于一个Connection
			try {
				String key = "seckill:" + seckillId;
				//redis并没有实现内部序列化操作
				//get -> byte[] ->反序列化 -> Object[Seckill]
				//采用自定义的序列化 采用protostuff-core来进行对象序列化
				//protostuff: pojo
				byte[] bytes = jedis.get(key.getBytes());
				if(bytes != null) {	//从缓存中获取到
					Seckill seckill = schema.newMessage();	//空对象
					ProtostuffIOUtil.mergeFrom(bytes, seckill, schema);	//seckill被反序列化了
					return seckill;
				}
			} finally {
				jedis.close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	public String putSeckill(Seckill seckill) {
		//redis的set过程：Object[Seckill] --->  序列化  --->  byte[]
		try {
			Jedis jedis = jedisPool.getResource();		//相当于一个Connection
			try {
				String key = "seckill:" + seckill.getSeckillId();
				byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema, 
						LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
				//超时缓存
				int time = 60*60;	//一小时
				String result = jedis.setex(key.getBytes(), time, bytes);	//成功返回 "OK"  失败返回错误信息字符串
				return result;
			} finally {
				jedis.close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
}
