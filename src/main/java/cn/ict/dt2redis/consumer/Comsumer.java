package cn.ict.dt2redis.consumer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import redis.clients.jedis.Jedis;
import cn.ict.dt2redis.analyser.AbstractAnalyser;
import cn.ict.dt2redis.utils.Config;
import cn.ict.dt2redis.utils.Log;

import com.ict.dtube.client.consumer.DtubeGetConsumer;
import com.ict.dtube.client.consumer.DtubePushConsumer;
import com.ict.dtube.client.consumer.GetResult;
import com.ict.dtube.client.exception.MQClientException;
import com.ict.dtube.common.consumer.ConsumeFromWhere;
import com.ict.dtube.common.message.MessageExt;
import com.ict.dtube.mqclient.GetMethod;
import com.mysql.fabric.xmlrpc.base.Array;

public class Comsumer {
	final Integer MsgNum = 10000;
	static volatile Integer t = 0;
	List<AbstractAnalyser> analysers;

	public Comsumer(AbstractAnalyser... analysers) {
		this.analysers = Arrays.asList(analysers);
	}

	public void work() throws MQClientException, InterruptedException {
		DtubeGetConsumer consumer = new DtubeGetConsumer(Config.getValue("DtConsumerName"));
		consumer.subscribe(Config.getValue("DtName"));
		consumer.setNamesrvAddr(Config.getValue("DtAddr"));
		consumer.start();
		Log.info("start consumer ....");
		List<JSONObject> msgs = new ArrayList<JSONObject>();
		while (true) {
			GetResult result = consumer.get(Config.getValue("DtName"), 256, GetMethod.SYNC);
			switch (result.getGetStatus()) {
			case FOUND:
				List<MessageExt> messageLists = result.getMessageList();
				for (MessageExt messageExt : messageLists) {
					String inputMsg = new String(messageExt.getBody());
					JSONObject jobj = null;
            		try {
						jobj = new JSONObject(inputMsg);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						Log.error("json转换出错"+e);
						continue;
					}
            		msgs.add(jobj);
            		//System.out.println(wids.size());
            		if(msgs.size() == MsgNum){
            			Thread[] ths = new Thread[analysers.size()];
            			int i = 0;
            			for(AbstractAnalyser analyser : analysers){
            				analyser.setData(msgs,analyser.getClass().getName());
            				ths[i] = new Thread(analyser);
            				ths[i++].start();
            			}
            			i=0;
            			for(AbstractAnalyser analyser : analysers) {
            				ths[i++].join();
            			}
            			msgs.clear();
            		}
				}
				break;
			case NO_NEW_MSG:
				break;
			case TOPIC_NOT_FOUND:
				break;
			case ARGS_ILLEGAL:
				break;
			default:
				break;
			}
		}
	}
	
}
