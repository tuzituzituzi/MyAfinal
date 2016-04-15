package com.example.myafinal.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.view.View;
import android.view.View.OnClickListener;

public class MyClickListener implements OnClickListener {
	
	private Object obj;
	private String clickMethod;
	
	public MyClickListener(Object obj){
		this.obj = obj;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		invokeClickMethod(obj,clickMethod,v);
	}

	private static Object invokeClickMethod(Object obj, String clickMethod, Object... params) {
		// TODO Auto-generated method stub
		if(obj == null){
			return null;
		}
		Method method = null;
		
		try {
			method = obj.getClass().getDeclaredMethod(clickMethod, View.class);
			method.setAccessible(true);
			return method.invoke(obj, params);
		} catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}

	public MyClickListener click(String method) {
		this.clickMethod = method;
		return this;
	}
}
