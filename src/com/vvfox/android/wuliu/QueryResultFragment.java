package com.vvfox.android.wuliu;

import java.io.Serializable;
import java.util.Map;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class QueryResultFragment extends Fragment {
	private Button queryBackBtn;

	static public Fragment newInstance(Map<String, Object> resultMap) {
		QueryResultFragment instance = new QueryResultFragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable("result", (Serializable) resultMap);
		instance.setArguments(bundle);
		return instance;

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.query_result, container, false);
		initView(view);
		return view;

	}

	private void initView(View v) {
		// TODO Auto-generated method stub
		queryBackBtn = (Button) v.findViewById(R.id.button_query_back);
		queryBackBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				back();
			}
		});

	}

	protected void back() {
		// TODO Auto-generated method stub

	}

}
