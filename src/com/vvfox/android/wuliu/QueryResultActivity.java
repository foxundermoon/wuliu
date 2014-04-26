package com.vvfox.android.wuliu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class QueryResultActivity extends Activity {

	private Button backBtn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.query_result_fragment);
		initView();

	}

	private void initView() {
		// TODO Auto-generated method stub
		backBtn = (Button) findViewById(R.id.button_query_back_in_fragment);
		backBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				backToQueryPage();
			}

		});
	}

	protected void backToQueryPage() {
		// TODO Auto-generated method stub
		startActivity(new Intent(QueryResultActivity.this, MainActivity.class));
	}
}
