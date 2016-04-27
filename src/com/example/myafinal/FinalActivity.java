package com.example.myafinal;

import java.lang.reflect.Field;

import com.example.myafinal.ioc.MyClickListener;
import com.example.myafinal.ioc.ViewInject;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

public class FinalActivity extends Activity{

	public void setContentView(int layoutResID){
		super.setContentView(layoutResID);
		//到了吗？？
		System.out.println("到了吗？？");
		initInjectedView(this);
	}

	private void initInjectedView(Activity activity) {
		// TODO Auto-generated method stub
		initInjectedView(activity,activity.getWindow().getDecorView());
	}

	private void initInjectedView(Object injectedSource, View sourceView) {
		/***
		 * getFields()获得某个类的所有的公共（public）的字段，包括父类。 
		 * getDeclaredFields()获得某个类的所有申明的字段，即包括public、private和proteced，但是不包括父类的申明字段。 
		 */
		Field[] fields = injectedSource.getClass().getDeclaredFields();
		if(fields != null && fields.length > 0){
			for(Field field : fields){
				try {
					field.setAccessible(true);
					if(field.get(injectedSource) != null){
						continue;
					}
					
					ViewInject viewInject = field.getAnnotation(ViewInject.class);
					if(viewInject != null){
						System.out.println("viewId= "+viewInject.id());
						int viewId = viewInject.id();
						field.set(injectedSource, sourceView.findViewById(viewId));
						setListener(injectedSource,field,viewInject.click(),Method.Click);
					}
					
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private void setListener(Object injectedSource, Field field, String methodName,
			Method method) throws Exception {
		if(methodName == null || methodName.trim().length() == 0){
			return;
		}
		
		Object object = field.get(injectedSource);
		
		switch (method) {
		case Click:
			if(object instanceof View){
				((View) object).setOnClickListener(new MyClickListener(injectedSource).click(methodName));
			}
			break;

		default:
			break;
		}
	}

	public enum Method {
		Click, LongClick, ItemClick, ItemLongClick
	}

}
