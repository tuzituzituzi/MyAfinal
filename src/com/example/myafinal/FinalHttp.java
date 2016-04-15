package com.example.myafinal;


import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;










import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRoute;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.SyncBasicHttpContext;

import com.example.myafinal.util.AjaxCallBack;
import com.example.myafinal.util.AjaxParams;
import com.example.myafinal.util.HttpHandlerTask;

public class FinalHttp {
	
	 private static final int DEFAULT_SOCKET_BUFFER_SIZE = 8 * 1024; // 8KB
	 private static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
	 private static final String ENCODING_GZIP = "gzip";
	
	private static int maxConnections = 10; // http������󲢷�������
    private static int socketTimeout = 10 * 1000; // ��ʱʱ�䣬Ĭ��10��
    private static int maxRetries = 5;// �����Դ����������쳣������RetryHandler���
    private static int httpThreadCount = 3;// http�̳߳�����
    
    private final HttpContext httpContext;
    private final DefaultHttpClient httpClient;
    
    private final Map<String, String> clientHeaderMap;
    
    
	private FinalHttp(){
		BasicHttpParams httpParams = new BasicHttpParams();
		
		ConnManagerParams.setTimeout(httpParams, socketTimeout);  //��ConnectionManager��������ӳ���ȡ�����ӵĳ�ʱʱ��
		ConnManagerParams.setMaxConnectionsPerRoute(httpParams,new ConnPerRouteBean(maxConnections));
		ConnManagerParams.setMaxTotalConnections(httpParams, 10);  //���������
		
		HttpConnectionParams.setConnectionTimeout(httpParams, socketTimeout);//���ӳ�ʱ
		HttpConnectionParams.setSoTimeout(httpParams, socketTimeout); //����ʱ
		HttpConnectionParams.setTcpNoDelay(httpParams, true);
		HttpConnectionParams.setSocketBufferSize(httpParams, DEFAULT_SOCKET_BUFFER_SIZE);
		HttpConnectionParams.setStaleCheckingEnabled(httpParams, true);
		
		HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
		
		//Scheme�������һ��Э��ģʽ,SchemeRegistry������ά��һ��Scheme
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
		
		ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(httpParams, schemeRegistry);
		
		httpContext = new SyncBasicHttpContext(new BasicHttpContext());
		httpClient = new DefaultHttpClient();
		
		//�Ȳ�д�������������
//		//������ص�ѹ���ļ���ʾ��������ͷ����Ϣ��
//		httpClient.addRequestInterceptor(new HttpRequestInterceptor() {
//			
//			@Override
//			public void process(HttpRequest arg0, HttpContext arg1)
//					throws HttpException, IOException {
//				// TODO Auto-generated method stub
//				
//			}
//		});
		//http��������
		httpClient.setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler());
		
		//httpclient������ͷ��Ϣ
		clientHeaderMap = new HashMap<String,String>();
		
	}
	
	public void addHeader(String header, String value){
		clientHeaderMap.put(header, value);
	}
	
	//--------------------get����-------------------
	public void get(String url, AjaxCallBack<? extends Object> callBack) {
		get(url,null,callBack);
	}

	public void get(String url, AjaxParams params,
			AjaxCallBack<? extends Object> callBack) {
		sendRequest(httpClient, httpContext, new HttpGet(getUrlWithQueryString(url,params)), null, callBack);
		
	}


	public static String getUrlWithQueryString(String url, AjaxParams params) {
		// TODO Auto-generated method stub
		if(params != null){
			String paramString = params.getParamString();
			url += "?" + paramString;
		}
		return null;
	}
	
	
	protected void sendRequest(DefaultHttpClient client,
			HttpContext httpContext, HttpUriRequest uriRequest, String contentType,
			AjaxCallBack<? extends Object> callBack) {
		if(contentType != null){
			uriRequest.addHeader("Content-Type",contentType);
		}
		
		HttpHandlerTask<T> handlerTask = new HttpHandlerTask<>(client, httpContext, callBack, charset);
		handlerTask.executeOnExecutor(executor, uriRequest);
	}

}
