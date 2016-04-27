package com.example.myafinal;


import com.example.myafinal.http.AjaxCallBack;
import com.example.myafinal.ioc.ViewInject;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FinalActivity {
	
	@ViewInject(id=R.id.tv,click="clickIt")
	private TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		textView = (TextView) findViewById(R.id.tv);
		textView.setText("hhahaha");
		FinalHttp http = FinalHttp.create();
		http.get("http://192.168.2.202/web/store/service/201/node-tair-web/owner/servertime", new AjaxCallBack<String>() {
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);
//				textView.setText(strMsg);
				System.out.println(strMsg);
				Toast.makeText(MainActivity.this, "failure", Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onSuccess(String t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);
				Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
				textView.setText(t);
				
			}
		});
	}
	
	public void clickIt(View v) {
		Toast.makeText(this, "lalalal", Toast.LENGTH_SHORT).show();
	}
	
}
