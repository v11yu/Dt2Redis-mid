package cn.ict.dt2redis.test;

import org.json.JSONException;
import org.json.JSONObject;

public class TestJsonError {
	public void testNoKey(){
		JSONObject obj = new JSONObject();
		String s1 = null;
		try {
			s1 = obj.getString("key");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(s1);
	}
	public void testJsontype(){
		JSONObject obj = new JSONObject();
		
		String s1 = null;
		try {
			obj.put("hi", 5);
			s1 = (String) obj.get("hi");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(s1);
	}
	public static void main(String[] args) {
		TestJsonError t = new TestJsonError();
		t.testJsontype();
		
	}
}
