package com.yhd.avtivity;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.expressage08.R;
import com.yhd.fragment.ExpressageFragment;
import com.yhd.fragment.MineFragment;
import com.yhd.fragment.SearchFragment;

import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View.OnClickListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.app.ActionBar;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends FragmentActivity implements OnClickListener{

	private final static int FIRST = 0; 
	private final static int SECOND = 1;
	private final static int THIRD = 2;
	private RelativeLayout mActionBarView3;
	private RelativeLayout mActionBarView2;
	private RelativeLayout mActionBarView1;
	private PullToRefreshAttacher mPullToRefreshAttacher;
	FragmentManager fragmentManager;
	ViewPager myViewPager;
	FragmentPagerAdapter myAdapter;
	List<Fragment> myFragments = new ArrayList<Fragment>();
	
	private RequestQueue mQueue;
	
	MineFragment mainTab01 = new MineFragment();
    SearchFragment mainTab02 = new SearchFragment();
    ExpressageFragment mainTab03 = new ExpressageFragment();
	
    ImageView mine;
    ImageView inquire;
    ImageView exprassage;
    
    /*private Color grey = new Color();
    private Color blue = new Color();
    
    private void initColor(){
    	grey.argb(alpha, red, green, blue);
    	blue.argb(alpha, red, green, blue);
    }*/
	
	public PullToRefreshAttacher getPullToRefreshAttacher() {
		return mPullToRefreshAttacher;
	}
	
	private static class MyAdapter extends FragmentPagerAdapter{

		private List<Fragment> myFragments;
		
		public MyAdapter(android.support.v4.app.FragmentManager fm,List<Fragment> myFragments) {
			super(fm);
			this.myFragments = myFragments;
		}

		@Override
		public android.support.v4.app.Fragment getItem(int arg0) {
			return myFragments.get(arg0);
		}

		@Override
		public int getCount() {
			return myFragments.size();
		}
		
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        mQueue = Volley.newRequestQueue(this);
        mainTab01.mainTab010(mQueue);
        mainTab02.mainTab020(mQueue);
        
        
        fragmentManager = getSupportFragmentManager();
        
        myViewPager = (ViewPager)findViewById(R.id.vp);  
        
        myFragments.add(mainTab02);  
        myFragments.add(mainTab01);  
        myFragments.add(mainTab03); 
        
        myAdapter = new MyAdapter(fragmentManager,myFragments); 
		
		myViewPager.setAdapter(myAdapter);
		
		myViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				initBottom();
				switch(arg0){
				case FIRST:
					//inquire.setBackgroundResource(R.drawable.inquire_on);
					inquire.setBackgroundColor(Color.argb(0xff, 0x82, 0xDC, 0xFA));
					customActionBar2();
					break;
				case SECOND:
					//mine.setBackgroundResource(R.drawable.mine_on);
					mine.setBackgroundColor(Color.argb(0xff, 0x82, 0xDC, 0xFA));
					customActionBar1();
					break;
				case THIRD:
					//exprassage.setBackgroundResource(R.drawable.exprassge_on);
					exprassage.setBackgroundColor(Color.argb(0xff, 0x82, 0xDC, 0xFA));
					customActionBar3();
					break;
				}
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
		
        inquire = (ImageView)findViewById(R.id.button1);
        mine = (ImageView)findViewById(R.id.button2);
        exprassage = (ImageView)findViewById(R.id.button3);
        mine.setOnClickListener(this);
        inquire.setOnClickListener(this);
        exprassage.setOnClickListener(this);
        
        initBottom();
        myViewPager.setCurrentItem(FIRST);
        inquire.setBackgroundColor(Color.argb(0xff, 0x82, 0xDC, 0xFA));
        customActionBar2();
        
        setPullToRefreshAttacher();
        
    }

	public ViewPager getMyViewPager() {
		return myViewPager;
	}

	private void customActionBar2(){
		ActionBar.LayoutParams params = new ActionBar.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		mActionBarView2 = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.tab2_actionbar,null);
		
		if(getActionBar()!=null){
			getActionBar().setCustomView(mActionBarView2,params);
			getActionBar().setDisplayShowHomeEnabled(false); 
			getActionBar().setDisplayShowCustomEnabled(true);
		}
	}
	
	private void customActionBar1(){
		ActionBar.LayoutParams params = new ActionBar.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		mActionBarView1 = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.tab1_actionbar,null);
		
		if(getActionBar()!=null){
			getActionBar().setCustomView(mActionBarView1,params);
			getActionBar().setDisplayShowHomeEnabled(false); 
			getActionBar().setDisplayShowCustomEnabled(true);
		}
	}
	
	private void customActionBar3(){
		ActionBar.LayoutParams params = new ActionBar.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		mActionBarView3 = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.tab3_actionar,null);
		
		if(getActionBar()!=null){
			getActionBar().setCustomView(mActionBarView3,params);
			getActionBar().setDisplayShowHomeEnabled(false); 
			getActionBar().setDisplayShowCustomEnabled(true);
		}
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.button1:
			initBottom();
			myViewPager.setCurrentItem(FIRST);
			//inquire.setBackgroundResource(R.drawable.inquire_on);
			inquire.setBackgroundColor(Color.argb(0xff, 0x82, 0xDC, 0xFA));
			customActionBar2();
			break;
		case R.id.button2:
			initBottom();
			myViewPager.setCurrentItem(SECOND);
			//mine.setBackgroundResource(R.drawable.mine_on);
			mine.setBackgroundColor(Color.argb(0xff, 0x82, 0xDC, 0xFA));
			customActionBar1();
			break;
		case R.id.button3:
			initBottom();
			myViewPager.setCurrentItem(THIRD);
			//exprassage.setBackgroundResource(R.drawable.exprassge_on);
			exprassage.setBackgroundColor(Color.argb(0xff, 0x82, 0xDC, 0xFA));
			customActionBar3();
			break;
		}
		
	}

	private void initBottom(){
		inquire.setBackgroundColor(Color.argb(0xff, 0xe3, 0xe1, 0xde));
		mine.setBackgroundColor(Color.argb(0xff, 0xe3, 0xe1, 0xde));
		exprassage.setBackgroundColor(Color.argb(0xff, 0xe3, 0xe1, 0xde));
	}
	
	private void setPullToRefreshAttacher(){
		
		PullToRefreshAttacher.Options options = new PullToRefreshAttacher.Options();
        options.headerInAnimation = R.anim.pulldown_fade_in;
        options.headerOutAnimation = R.anim.pulldown_fade_out;
        options.refreshScrollDistance = 0.3f;
        options.headerLayout = R.layout.pulldown_header;
        mPullToRefreshAttacher = new PullToRefreshAttacher(MainActivity.this,options);
        
	}
}
