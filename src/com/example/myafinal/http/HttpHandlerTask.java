package com.example.myafinal.http;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.protocol.HttpContext;

import android.os.AsyncTask;
import android.util.Log;

public class HttpHandlerTask<T> extends AsyncTask<Object, Object, Object>
		implements EntityCallBack {

	private final AbstractHttpClient client;
	private final HttpContext context;
	private final AjaxCallBack<T> callback;
	private String charset;

	private StringEntityHandler mStringEntityHandler = new StringEntityHandler();

	public HttpHandlerTask(AbstractHttpClient client, HttpContext context,
			AjaxCallBack<T> callBack, String charset) {
		this.callback = callBack;
		this.client = client;
		this.context = context;
		this.charset = charset;
	}

	@Override
	protected Object doInBackground(Object... params) {
		if (params != null) {
			try {
				makeRequestWithRetries((HttpUriRequest) params[0]);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	private void makeRequestWithRetries(HttpUriRequest request)
			throws IOException {
		// œ»≤ª–¥÷ÿ ‘

		try {
			if (!isCancelled()) {
				HttpResponse response = client.execute(request, context);
				if (!isCancelled())
					handleResponse(response);
			}
		} catch (Exception e) {
			Log.i("request exception", e.toString());
			e.printStackTrace();
		}

	}

	private void handleResponse(HttpResponse response) {
		HttpEntity entity = response.getEntity();
		Object responseBody = null;

		try {
			if (entity != null) {
				responseBody = mStringEntityHandler.handleEntity(entity, this,
						charset);
			}
			publishProgress(UPDATE_SUCCESS, responseBody);
			
		} catch (IOException e) {
			publishProgress(UPDATE_FAILURE, e, 0, e.getMessage());
			e.printStackTrace();
		}

	}
	
	private final static int UPDATE_START = 1;
	private final static int UPDATE_LOADING = 2;
	private final static int UPDATE_FAILURE = 3;
	private final static int UPDATE_SUCCESS = 4;
	
	@Override
	protected void onProgressUpdate(Object... values) {
		int update = Integer.valueOf(String.valueOf(values[0]));
		switch (update) {
		case UPDATE_SUCCESS:
			if(callback != null){
				callback.onSuccess((T)values[1]);
			}
			break;
			
		case UPDATE_FAILURE:
			if(callback != null){
				callback.onFailure((Throwable) values[1], (Integer) values[2], (String)values[3]);
			}
		default:
			break;
		}
		super.onProgressUpdate(values);
	}

	@Override
	public void callBack(long count, long current, boolean mustNoticeUI) {
		// TODO Auto-generated method stub
		
	}

}
