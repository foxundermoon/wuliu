package com.vvfox.android.wuliu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vvfox.android.wuliu.core.Client;

public class Page2Fragment extends Fragment {
	private LayoutInflater inflater;
	private View view;
	private ViewGroup container;
	LinearLayout linearLayoutLogined;
	LinearLayout linearLayoutNotlogined;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.inflater = inflater;
		this.container = container;
		View view = inflater
				.inflate(R.layout.page_2_notlogin, container, false);
		initView(view);
		this.view = view;
		return view;

	}

	private void initView(View view2) {
		// TODO Auto-generated method stub
		Button btn1 = (Button) view2.findViewById(R.id.button1_in_page_2);

		btn1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getActivity().startActivity(
						new Intent(getActivity(), LoginActivity.class));
			}
		});

		Button btn2 = (Button) view2.findViewById(R.id.button2_in_page_2);
		btn2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getActivity().startActivity(
						new Intent(getActivity(), RegisterActivity.class));
			}
		});

	}

	public void login() {
		hideLayout();
		displayRecode();
		showMsg("logined");

	}

	private void hideLayout() {
		// TODO Auto-generated method stub
		view.findViewById(R.id.page2_notlogined).setVisibility(
				LinearLayout.GONE);
	}

	private void displayRecode() {
		// TODO 显示查询记录
		View view2 = view;
		LinearLayout layout = (LinearLayout) view
				.findViewById(R.id.page2_logined);
		// LinearLayout layout = (LinearLayout) view2
		// .findViewById(R.id.linearLayout_in_page2_logined);
		// RecordeView recordeView = new RecordeView(view2.getContext());
		// recordeView.initView();
		// layout.addView(recordeView);
		JSONArray records = Client.getInstance().getJsonRecord();
		Context context = view2.getContext();
		if (records == null) {
			TextView t1 = new TextView(context);
			t1.setText("该用户还无任何查询纪录");
			layout.addView(t1);

		} else {
			for (int i = 0; i < records.length(); i++) {
				try {
					JSONObject record = records.getJSONObject(i);
					JSONObject content = record.getJSONObject("content");
					LinearLayout linearLayout = new LinearLayout(context);
					linearLayout.setOrientation(LinearLayout.HORIZONTAL);
					linearLayout.setPadding(10, 5, 10, 5);
					TextView t1 = new TextView(context);
					TextView t2 = new TextView(context);
					TextView t3 = new TextView(context);
					String type = content.optString("type");
					if ("0".equals(type)) {
						t1.setText("智能查询");
					}
					if ("1".equals(type)) {
						t1.setText(content.optString("com"));
					}
					t2.setText("运单号：" + content.optString("nu"));
					t3.setText(record.optString("time"));
					linearLayout.addView(t1);
					linearLayout.addView(t2);
					linearLayout.addView(t3);
					layout.addView(linearLayout);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private void showMsg(String msg) {
		Toast.makeText(getActivity().getApplicationContext(), msg,
				Toast.LENGTH_SHORT).show();
	}

}
