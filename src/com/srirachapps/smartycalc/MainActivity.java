package com.srirachapps.smartycalc;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements OnPageChangeListener {

	MainPageAdapter pageAdapter;
	ActionBar actionBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		actionBar = getActionBar();
		
		List<Fragment> fragments = getFragments();
		pageAdapter = new MainPageAdapter(getSupportFragmentManager(), fragments);
		ViewPager pager = (ViewPager)findViewById(R.id.viewpager);
		pager.setOnPageChangeListener(this);
		pager.setAdapter(pageAdapter);
	}
	
	private List<Fragment> getFragments() {
		List<Fragment> fragmentList = new ArrayList<Fragment>();
		
		// List of fragments you can swipe to.
		fragmentList.add(new StandardFragment());
		fragmentList.add(new ScientificFragment());
		
		return fragmentList;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int position) {
		
		if(position == 0) {
			actionBar.setTitle(R.string.standard);
			actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.holo_orange_dark));
		}
		else if(position == 1) {
			actionBar.setTitle(R.string.scientific);
			actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.holo_purple));
		}
	}
}
