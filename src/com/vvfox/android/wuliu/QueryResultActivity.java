package com.vvfox.android.wuliu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class QueryResultActivity extends Activity {

	private Button backBtn;
	WebView webView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.query_result);
		initView(savedInstanceState);

	}

	private void initView(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		backBtn = (Button) findViewById(R.id.button_query_back);
		backBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				backToQueryPage();
			}

		});

		webView = (WebView) findViewById(R.id.webView1);
		webView.getSettings().setJavaScriptEnabled(true);
		String result = getIntent().getStringExtra("display");
		if (result == null || "".equals(result)) {
			webView.loadData("somthing wrong!", "text/html", "utf-8");
		} else {
//			webView.loadData(result, "text/html", "utf-8");
			TextView t1 = (TextView)findViewById(R.id.textView1);
			t1.setText(result);
			webView.loadUrl(result);
			
		}
	}

	protected void backToQueryPage() {
		// TODO Auto-generated method stub
		startActivity(new Intent(QueryResultActivity.this, MainActivity.class));
	}
}
