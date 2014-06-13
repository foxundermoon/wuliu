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
 * Tabҳ�����ƻ����л��Լ�����Ч��
 * 
 * @author D.Winter
 * 
 */
public class MainActivity extends FragmentActivity implements OnLoginedListener {
	// ViewPager��google SDk���Դ���һ�����Ӱ���һ���࣬��������ʵ����Ļ����л���
	// android-support-v4.jar
	public static MainActivity instance;
	private ViewPager mPager;// ҳ������
	private List<Fragment> listFragments; // Tabҳ���б�
	private ImageView cursor;// ����ͼƬ
	private TextView t1, t2, t3;// ҳ��ͷ��
	private int offset = 0;// ����ͼƬƫ����
	private int currIndex = 0;// ��ǰҳ�����
	private int bmpW;// ����ͼƬ���
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
	 * ��ʼ��ͷ��
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
	 * ��ʼ��ViewPager
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
	 * ��ʼ������
	 */
	private void InitImageView() {
		cursor = (ImageView) findViewById(R.id.cursor);
		bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.a)
				.getWidth();// ��ȡͼƬ���
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// ��ȡ�ֱ��ʿ��
		offset = (screenW / 3 - bmpW) / 2;// ����ƫ����
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		cursor.setImageMatrix(matrix);// ���ö�����ʼλ��
	}

	/**
	 * ViewPager������
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
	 * ͷ��������
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
	 * ҳ���л�����
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {

		int one = offset * 2 + bmpW;// ҳ��1 -> ҳ��2 ƫ����
		int two = one * 2;// ҳ��1 -> ҳ��3 ƫ����

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
			animation.setFillAfter(true);// True:ͼƬͣ�ڶ�������λ��
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