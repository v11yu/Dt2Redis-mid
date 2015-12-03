package cn.ict.dt2redis.analyser;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import cn.ict.dt2redis.utils.Log;

public class UpdatedMidAnalyser extends AbstractAnalyser<JSONObject>{

	@Override
	Map getReuslt2Map(JSONObject obj) {
		// TODO Auto-generated method stub
		Map<String,String> mp = new HashMap<String, String>();
		try {
			Integer commentnum = (Integer) obj.get("root_commentnum");
			Integer retweetnum = (Integer) obj.get("root_retweetnum");
			mp.put("mid_retweetnum", retweetnum+"");
			mp.put("mid_commentnum", commentnum+"");
		} catch (JSONException e) {
			Log.error("json转换出错"+e);
			return null;
		}
		return mp;
	}

	@Override
	String getReuslt2Key(JSONObject obj) {
		// TODO Auto-generated method stub
		String key = null;
		try {
			Integer type = (Integer) obj.get("msg_type");
			if(type ==1) return null;
			key = "tsina_"+(String) obj.get("root_mid");
		} catch (JSONException e) {
			Log.error("json转换出错"+e);
		}
		return key;
	}

	

}
