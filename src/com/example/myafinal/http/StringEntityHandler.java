package com.example.myafinal.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;

public class StringEntityHandler {

	public Object handleEntity(HttpEntity entity, EntityCallBack callBack,String charset) throws IOException{
		if(entity == null){
			return null;
		}
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		
		long count = entity.getContentLength();
		long curCount = 0;
		int len = -1;
		InputStream is = entity.getContent();
		//�ȹ涨һ�����鳤�ȣ���������е��ֽڻ��嵽����b�У����ص���������е��ֽڸ��������������û�����Ļ����򷵻���ʵ���ֽڸ�������δβʱ������-1
		while( (len = is.read(buffer)) != -1){
			outputStream.write(buffer,0,len);
			curCount += len;
			if(callBack != null){
				callBack.callBack(count, curCount, false);
			}
		}
		if(callBack != null){
			callBack.callBack(count, curCount, true);
		}
		byte[] data = outputStream.toByteArray();
		outputStream.close();
		is.close();
		return new String(data, charset);
		
	}
}
