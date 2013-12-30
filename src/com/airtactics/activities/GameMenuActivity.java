package com.airtactics.activities;

import airtactics.com.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class GameMenuActivity extends Activity{
	
	private Button buttonSinglePlayer;
	private Button buttonOnline;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_menu);
		
		AdView adView = (AdView)this.findViewById(R.id.adView);
	    AdRequest adRequest = new AdRequest.Builder()
	    	.addTestDevice("1A96947585B930E5C32F8D7874E7F6A0")
	    	.build();
	    adView.loadAd(adRequest);

		
		this.buttonSinglePlayer = (Button) findViewById(R.id.buttonSinglePlayer);
		this.buttonSinglePlayer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				startActivity(new Intent(GameMenuActivity.this, PlanningBoardActivity.class));
//				finish();
				
			}
		});
		
		this.buttonOnline = (Button) findViewById(R.id.buttonOnline);
		this.buttonOnline.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				startActivity(new Intent(GameMenuActivity.this, PlayingBoardActivity.class));
//				finish();
				
			}
		});
	}
	
}
