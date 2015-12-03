package cn.ict.dt2redis;

import cn.ict.dt2redis.analyser.UpdatedMidAnalyser;
import cn.ict.dt2redis.consumer.Comsumer;

import com.ict.dtube.client.exception.MQClientException;

public class TaskApp {
	public static void main(String[] args) throws MQClientException, InterruptedException{
		Comsumer sentiConsumer = new Comsumer(
				new UpdatedMidAnalyser());
		sentiConsumer.work();
	}
}
