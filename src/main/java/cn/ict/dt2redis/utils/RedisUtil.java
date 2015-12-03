package cn.ict.dt2redis.utils;

import java.util.Map;

import redis.clients.jedis.Jedis;

/**
 * <code>Redis</code>单例模式
 * @author v11
 */
public class RedisUtil {
	private static RedisUtil unique;
	private Jedis jedis;
	private String host = Config.getValue("RedisIp");
	private Integer port = Config.getNum("RedisPort");
	private RedisUtil(){
		jedis = new Jedis(host, port);
	}
	public synchronized static Jedis getUniqueJedis(){
		if(unique == null){
			unique = new RedisUtil();
		}
		return unique.jedis;
	}
	public synchronized static void push(String key,Map hash){
		RedisUtil.getUniqueJedis().hmset(key, hash);
		RedisUtil.getUniqueJedis().expire(key, Config.getNum("expire"));
	}
}
