package com.vvfox.android.wuliu.core;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Client {
	static boolean hasInstance = false;
	static Client instance;

	HttpClient hc;
	static final String key = "5315025d63bf35f4";
	static final String api = "http://api.kuaidi100.com/api";
	static String autoNuApi = "http://www.kuaidi100.com/autonumber/auto?num=";
	Map<String, Object> param;
	private String defaultCharset = "utf-8";

	private Client() {
		param = new HashMap<String, Object>();
		hc = new DefaultHttpClient();
	}

	public String query(String nu, String com) {
		param.put("id", key); // api key
		param.put("com", com); // 快递公司编号
		param.put("nu", nu);// 快递单
		param.put("show", "2");// 返回类型 0：json，1：xml，2：html，3：text
		param.put("mutil", "1"); // 1：多行完整信息
		// HttpGet get = new HttpGet();
		// HttpParams param1 = new HttpParams();
		return doGet(api, param, defaultCharset);

	}

	public String smartQuery(String nu) {
		String rtStr = "";
		String rspJson = doGet(autoNuApi + nu);

		try {
			JSONArray ja = new JSONArray(rspJson);
			if (ja.length() >= 1) {
				for (int i = 0; i < ja.length(); i++) {
					JSONObject tmp = ja.optJSONObject(i);
					if (tmp != null) {
						String com = tmp.optString("comCode");
						if (com == null || "".equals(com)) {
						} else {
							// return query(nu, com);
							return html5result(nu, com);
						}
						break;

					}
				}
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rtStr = "json格式错误";
		}

		return rtStr + rspJson;
	}

	private String html5result(String nu, String com) {
		// TODO Auto-generated method stub

		return "http://m.kuaidi100.com/index_all.html?type=" + com + "&postid="
				+ nu;
	}

	private String doGet(String url, Map<String, Object> param2, String charset) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, Object> entry : param2.entrySet()) {
			// key =value
			sb.append("&");
			sb.append(entry.getKey()).append("=")
					.append((String) entry.getValue());

		}
		String fullUrl = url + sb.toString().replaceFirst("&", "?");

		return doGet(fullUrl, charset);
	}

	private String doGet(String url, String charset) {
		HttpGet rst = new HttpGet(url);
		try {
			HttpResponse rsp = hc.execute(rst);
			if (rsp.getStatusLine().getStatusCode() == 200) {
				return EntityUtils.toString(rsp.getEntity(), charset);
			} else {
				return "error 网络连接错误!\n错误代码:"
						+ rsp.getStatusLine().getStatusCode();
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "some thing wrong:" + e.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.toString();
		}
	}

	private String doGet(String url) {
		HttpGet rst = new HttpGet(url);
		try {
			HttpResponse rsp = hc.execute(rst);
			if (rsp.getStatusLine().getStatusCode() == 200) {
				return EntityUtils.toString(rsp.getEntity());
			} else {
				return "error 网络连接错误!\n错误代码:"
						+ rsp.getStatusLine().getStatusCode();
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "some thing wrong!";
	}

	public static Client getInstance() {
		// TODO Auto-generated method stub
		if (hasInstance) {
			if (instance == null) {
				return new Client();
			} else {
				return instance;
			}
		} else {
			return new Client();
		}
	}
}
