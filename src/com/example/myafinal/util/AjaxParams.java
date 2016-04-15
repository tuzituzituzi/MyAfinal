package com.example.myafinal.util;

import java.io.FileWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

/**
 * <p>
 * 使用方法:
 * <p>
 *
 * <pre>
 * AjaxParams params = new AjaxParams();
 * params.put(&quot;username&quot;, &quot;michael&quot;);
 * params.put(&quot;password&quot;, &quot;123456&quot;);
 * params.put(&quot;email&quot;, &quot;test@tsz.net&quot;);
 * params.put(&quot;profile_picture&quot;, new File(&quot;/mnt/sdcard/pic.jpg&quot;)); // 上传文件
 * params.put(&quot;profile_picture2&quot;, inputStream); // 上传数据流
 * params.put(&quot;profile_picture3&quot;, new ByteArrayInputStream(bytes)); // 提交字节流
 *
 * FinalHttp fh = new FinalHttp();
 * fh.post(&quot;http://www.yangfuhai.com&quot;, params, new AjaxCallBack&lt;String&gt;() {
 * 	&#064;Override
 * 	public void onLoading(long count, long current) {
 * 		textView.setText(current + &quot;/&quot; + count);
 * 	}
 *
 * 	&#064;Override
 * 	public void onSuccess(String t) {
 * 		textView.setText(t == null ? &quot;null&quot; : t);
 * 	}
 * });
 * </pre>
 */

public class AjaxParams {
	
	private static String ENCODING = "UTF-8";
	//ConcurrentHashMap可以保证线程安全
	protected ConcurrentHashMap<String,String> urlParams;
	
	public AjaxParams(){
		init();
	}
	
	public AjaxParams(Map<String, String> source){
		init();
		for(Map.Entry<String, String> entry : source.entrySet()){
			put(entry.getKey(), entry.getValue());
		}
	}
	
	public AjaxParams(Object... keyAndValues){
		init();
		int len = keyAndValues.length;
		if(len % 2 != 0){
			throw new IllegalArgumentException("Supplied arguments must be even");
		}
		for(int i = 0; i < len; i +=2 ){
			String key = String.valueOf(keyAndValues[i]);
			String value = String.valueOf(keyAndValues[i+1]);
			put(key, value);
		}
	}

	private void init() {
		// TODO Auto-generated method stub
		urlParams = new ConcurrentHashMap<String,String>();
	}

	private void put(String key, String value){
		if(key != null && value != null){
			urlParams.put(key, value);
		}
	}

	//将NameValuePair的List编码成字符串
	public String getParamString() {
		return URLEncodedUtils.format(getParamList(), ENCODING);
	}

	//把ConcurrentHashMap转换成NameValuePair以便编码
	private List<BasicNameValuePair> getParamList() {
		List<BasicNameValuePair> list = new LinkedList<BasicNameValuePair>();
		for(ConcurrentHashMap.Entry<String, String> entry : urlParams.entrySet()){
			list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		return list;
	}
}
