package com.example.myafinal.util;

import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.protocol.HttpContext;

import android.os.AsyncTask;

public class HttpHandlerTask<T> extends AsyncTask<Object, Object, Object> {
	
	private final AbstractHttpClient client;
	private final HttpContext context;
	private final AjaxCallBack<T> callback;
	private String charset;
	
	public HttpHandlerTask(AbstractHttpClient client, HttpContext context, AjaxCallBack<T> callBack, String charset){
		this.callback = callBack;
		this.client = client;
		this.context = context;
		this.charset = charset;
	}

	@Override
	protected Object doInBackground(Object... params) {
		// TODO Auto-generated method stub
		return null;
	}

}
