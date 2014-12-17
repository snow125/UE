package com.yhd.avtivity;

import com.example.expressage08.R;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ImageView;

public class WelcomeActivity extends Activity{

	private ImageButton play;
	private ImageView word;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_activity);
		this.getActionBar().hide();
		initPlay();
		initAnimation();
	}
	
	private void initAnimation(){
		word = (ImageView) findViewById(R.id.word);
		
		AnimatorSet mSet = new AnimatorSet();
		ObjectAnimator wordAnimator = ObjectAnimator.ofFloat(word, "alpha", 1, 0);
		ObjectAnimator playAnimator = ObjectAnimator.ofFloat(play, "alpha", 0, 1);
		
		mSet.play(playAnimator).after(wordAnimator);
		mSet.setDuration(2000);
		mSet.start();
	}
	
	private void initPlay(){
		play = (ImageButton) findViewById(R.id.play);
		play.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(WelcomeActivity.this, MainActivity.class);
				startActivity(i);
				finish();
			}
		});
		play.setAlpha(0f);
	}
	
}


