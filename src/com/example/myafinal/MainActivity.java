package com.example.myafinal;


import com.example.myafinal.util.ViewInject;

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
	}
	
	public void clickIt(View v) {
		Toast.makeText(this, "lalalal", Toast.LENGTH_SHORT).show();
	}
	
}
