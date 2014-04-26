package com.vvfox.android.wuliu;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

public class Page1Fragment extends Fragment implements OnClickListener,
		OnCheckedChangeListener {

	private static final String QUERY_RESULT = "QUERY_RESULT";
	private Button queryBtn;
	private CheckBox zncxCheckBox;
	private FrameLayout choseArea;
	private EditText wuliuNumberEditText;
	private ViewGroup container;
	private Activity mainActivity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.page_1, container, false);
		initView(view);
		this.container = container;
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mainActivity = activity;

	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		queryBtn = (Button) view.findViewById(R.id.button_query);
		queryBtn.setOnClickListener(this);
		zncxCheckBox = (CheckBox) view.findViewById(R.id.checkBoxZncx);
		zncxCheckBox.setOnCheckedChangeListener(this);
		choseArea = (FrameLayout) view.findViewById(R.id.choseArea);
		wuliuNumberEditText = (EditText) view.findViewById(R.id.editTextNumber);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String number = wuliuNumberEditText.getText().toString();
		if ((number == null) || "".equals(number)) {
			Toast.makeText(getActivity(), "请先输入物流号再点击查询！", Toast.LENGTH_SHORT)
					.show();
		} else {
			Toast.makeText(getActivity(), "正在努力的联网查询，请稍等！", Toast.LENGTH_LONG)
					.show();

			displayResult(executeQuery());
		}

	}

	private void displayResult(String json) {
		// TODO 显示查询结果
		String result = null;
		if (null == json || "".equals(json)) {
			result = "未查询到结果！(●-●) ";
		} else {

		}
		Map<String, Object> resultMap = new HashMap<String, Object>();

		// FragmentManager fm = getActivity().getSupportFragmentManager();
		// FragmentTransaction ft = fm.beginTransaction();
		/*
		 * if(fm.findFragmentByTag(QUERY_RESULT)==null ){
		 * ft.add(QueryResultFragment.newInstance(resultMap), QUERY_RESULT); }
		 */
		mainActivity.startActivity(new Intent(mainActivity,
				QueryResultActivity.class));

	}

	private String executeQuery() {
		// TODO 查询物流

		return null;
	}

	private void choosedZhiNengChaXun() {
		// TODO 选择了智能查询
		// 隐藏物流选择区域
		choseArea.setVisibility(View.GONE);

	}

	private void unchoosedZhiNengChaXun() {
		// TODO 取消选择智能查询
		// 显示物流选择区域
		choseArea.setVisibility(View.INVISIBLE);

	}

	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		// TODO 智能查询选项框改变
		if (arg1) {
			choosedZhiNengChaXun();
		}
		if (!arg1) {
			unchoosedZhiNengChaXun();
		}

	}

}
