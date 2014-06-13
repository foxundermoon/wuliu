package com.vvfox.android.wuliu;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.util.EncodingUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.vvfox.android.wuliu.core.Client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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
	private RadioGroup radioGroup;
	private View currentView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.page_1, container, false);
		currentView = view;
		this.container = container;
		initView(view);
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
		queryBtn = (Button) view.findViewById(R.id.button_query001);
		queryBtn.setOnClickListener(this);
		zncxCheckBox = (CheckBox) view.findViewById(R.id.checkBoxZncx);
		zncxCheckBox.setOnCheckedChangeListener(this);
		choseArea = (FrameLayout) view.findViewById(R.id.choseArea);
		wuliuNumberEditText = (EditText) view.findViewById(R.id.editTextNumber);
		radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup1);
		initRadiobox(view);
	}

	private void initRadiobox(View view) {
		// TODO Auto-generated method stub
		radioGroup
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						// TODO Auto-generated method stub
						RadioButton checkedRb = (RadioButton) group
								.findViewById(checkedId);
						wuliuNumberEditText.setHint("请输入" + checkedRb.getText()
								+ "的物流运单号");
					}

				});
		InputStream is = view.getContext().getResources()
				.openRawResource(R.raw.wuliu_gongsi);
		byte[] buf;
		try {
			buf = new byte[is.available()];
			is.read(buf);
			String config = EncodingUtils.getString(buf, "GBK");
			JSONArray ja = new JSONObject(config).getJSONArray("gongsis");
			for (int i = 0; i < ja.length(); i++) {
				JSONObject jo = (JSONObject) ja.get(i);
				RadioButton rb = new RadioButton(view.getContext());
				rb.setText(jo.getString("name"));
				rb.setTag(jo.getString("com"));
				radioGroup.addView(rb);

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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

		Intent intent = new Intent(mainActivity, QueryResultActivity.class);
		intent.putExtra("display", json);
		mainActivity.startActivity(intent);

	}

	private String executeQuery() {
		// TODO 查询物流
		String nu = wuliuNumberEditText.getText().toString();
		Map<String, String> recorde = new HashMap<String, String>();
		recorde.put("op", "record");
		recorde.put("userid", Client.getInstance().user.getUserid());
		String content = "";
		JSONObject jo = new JSONObject();
		if (zncxCheckBox.isChecked()) {
			try {
				jo.put("type", "0");
				jo.put("nu", nu);
				content = jo.toString();
				recorde.put("content", content);
				// MainActivity.client.insertRecorde(recorde);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return MainActivity.client.smartQuery(nu);
		} else {
			RadioButton rb = (RadioButton) currentView.findViewById(radioGroup
					.getCheckedRadioButtonId());
			String com = rb.getTag().toString();
			try {
				jo.put("type", "1");
				jo.put("nu", nu);
				jo.put("com", com);
				content = jo.toString();
				recorde.put("content", content);
				// MainActivity.client.insertRecorde(recorde);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return MainActivity.client.query(nu, com);
		}
	}

	private void choosedZhiNengChaXun() {
		// TODO 选择了智能查询
		// 隐藏物流选择区域
		wuliuNumberEditText.setHint("你选择了智能模式，只需输入快递运单号就可以！");
		choseArea.setVisibility(View.GONE);

	}

	private void unchoosedZhiNengChaXun() {
		// TODO 取消选择智能查询
		// 显示物流选择区域
		choseArea.setVisibility(View.VISIBLE);

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
