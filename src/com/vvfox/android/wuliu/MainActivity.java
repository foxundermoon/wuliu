package com.vvfox.android.wuliu;

import java.util.ArrayList;
import java.util.List;

import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.vvfox.android.wuliu.core.Client;

/**
 * Tab页面手势滑动切换以及动画效果
 * 
 * @author D.Winter
 * 
 */
public class MainActivity extends FragmentActivity implements OnLoginedListener {
	// ViewPager是google SDk中自带的一个附加包的一个类，可以用来实现屏幕间的切换。
	// android-support-v4.jar
	public static MainActivity instance;
	private ViewPager mPager;// 页卡内容
	private List<Fragment> listFragments; // Tab页面列表
	private ImageView cursor;// 动画图片
	private TextView t1, t2, t3;// 页卡头标
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int bmpW;// 动画图片宽度
	private boolean loginedChanged = false;
	private Fragment page2;
	static Client client;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		InitImageView();
		InitTextView();
		InitViewPager();
		initClient();
		instance = this;

	}

	@Override
	public void onResume() {
		super.onResume();
		// if(Client.isLogined()) {
		// Log.v("vvfox","fromloginactivity:"+savedInstanceState.getBoolean("fromloginactivity"));
		// onLogined();
		// }
	}

	private void initClient() {
		// TODO Auto-generated method stub
		client = Client.getInstance();
	}

	/**
	 * 初始化头标
	 */
	private void InitTextView() {
		t1 = (TextView) findViewById(R.id.text1);
		t2 = (TextView) findViewById(R.id.text2);
		t3 = (TextView) findViewById(R.id.text3);

		t1.setOnClickListener(new MyOnClickListener(0));
		t2.setOnClickListener(new MyOnClickListener(1));
		t3.setOnClickListener(new MyOnClickListener(2));
	}

	/**
	 * 初始化ViewPager
	 */
	private void InitViewPager() {
		mPager = (ViewPager) findViewById(R.id.vPager);
		listFragments = new ArrayList<Fragment>();
		listFragments.add(new Page1Fragment());
		page2 = new Page2Fragment();
		listFragments.add(page2);
		listFragments.add(new Page3Fragment());
		mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(),
				listFragments));
		mPager.setCurrentItem(0);
		mPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	/**
	 * 初始化动画
	 */
	private void InitImageView() {
		cursor = (ImageView) findViewById(R.id.cursor);
		bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.a)
				.getWidth();// 获取图片宽度
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// 获取分辨率宽度
		offset = (screenW / 3 - bmpW) / 2;// 计算偏移量
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		cursor.setImageMatrix(matrix);// 设置动画初始位置
	}

	/**
	 * ViewPager适配器
	 */
	public class MyPagerAdapter extends FragmentPagerAdapter {
		public List<Fragment> mListFragments;
		private FragmentManager fragmentManager;

		public MyPagerAdapter(FragmentManager fm, List<Fragment> list) {
			super(fm);
			this.mListFragments = list;
			this.fragmentManager = fm;
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			return listFragments.get(arg0);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listFragments.size();
		}

		public void replace(int index, Fragment fragment) {
			mListFragments.remove(index);
			mListFragments.add(index, fragment);
			mPager.setCurrentItem(index);

		}

	}

	/**
	 * 头标点击监听
	 */
	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			mPager.setCurrentItem(index);

		}
	};

	/**
	 * 页卡切换监听
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {

		int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
		int two = one * 2;// 页卡1 -> 页卡3 偏移量

		@Override
		public void onPageSelected(int arg0) {
			Animation animation = null;
			switch (arg0) {
			case 0:
				if (currIndex == 1) {
					animation = new TranslateAnimation(one, 0, 0, 0);
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, 0, 0, 0);
				}
				break;
			case 1:
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, one, 0, 0);
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, one, 0, 0);
				}
				break;
			case 2:
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, two, 0, 0);
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, two, 0, 0);
				}
				break;
			}
			currIndex = arg0;
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			cursor.startAnimation(animation);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}

	@Override
	public void onLogined() {
		// TODO Auto-generated method stub
		/*
		 * MyPagerAdapter myPadp =(MyPagerAdapter) mPager.getAdapter();
		 * myPadp.replace(1,new Page2LoginedFragment());
		 * myPadp.notifyDataSetChanged();
		 */
		loginedChanged = true;
		Page2Fragment page = (Page2Fragment) page2;
		page.login();
		/*
		 * FragmentManager fm = getSupportFragmentManager(); FragmentTransaction
		 * transaction = fm.beginTransaction();
		 * transaction.replace(page2.getId(),new Page2LoginedFragment());
		 * transaction.commit();
		 */
		// findViewById(R.id.linearLayout_in_page_2_logined).setVisibility(
		// LinearLayout.VISIBLE);
		// findViewById(R.id.linearLayout_in_page_2_notlogined).setVisibility(
		// LinearLayout.GONE);

	}

	public boolean loginedChanged() {
		// TODO Auto-generated method stub
		return loginedChanged;
	}
}