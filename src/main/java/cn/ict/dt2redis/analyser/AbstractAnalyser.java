package cn.ict.dt2redis.analyser;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import cn.ict.dt2redis.utils.Config;
import cn.ict.dt2redis.utils.Log;
import cn.ict.dt2redis.utils.RedisUtil;

public abstract class AbstractAnalyser<T> implements Runnable{
	List<T> msgs;
	String groupName;
	
	public void setData(List<T> msgs,String groupName){
		this.msgs = msgs;
		this.groupName = groupName;
	}
	void pushOne(Map mp,String wid){
		RedisUtil.push(wid,mp);
	}
	abstract Map getReuslt2Map(T obj);
	abstract String getReuslt2Key(T obj);
	@Override
	public void run() {
		int idx = 0;
		for(T t : msgs){
			Map mp = getReuslt2Map(t);
			String wid = getReuslt2Key(t);
			if(mp == null|| wid == null) continue;
			pushOne(mp,wid);
		}
		Log.info(groupName+" finish push msgs "+msgs.size()+" to redis");
	}
}
