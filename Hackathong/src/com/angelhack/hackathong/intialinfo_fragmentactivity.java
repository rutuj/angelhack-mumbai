package com.angelhack.hackathong;

import com.angelhack.hackathong.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class intialinfo_fragmentactivity extends FragmentActivity {
	private MyAdapter mAdapter;
	private ViewPager mPager;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intialinfo_pager);
		mAdapter = new MyAdapter(getSupportFragmentManager());

		mPager = (ViewPager) findViewById(com.angelhack.hackathong.R.id.viewPager);
		mPager.setAdapter(mAdapter);
	}

	public static class MyAdapter extends FragmentPagerAdapter {
		public MyAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return 2 ;
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				return new FirstFragment();
			case 1:
				return new SecondFragment();
			
			default:
				return null;
			}
		}
	}
}