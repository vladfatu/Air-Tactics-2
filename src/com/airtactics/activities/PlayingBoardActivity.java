package com.airtactics.activities;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import airtactics.com.R;
import android.app.Activity;
import android.os.Bundle;

public class PlayingBoardActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_playing_board);
		
		AdView adView = (AdView)this.findViewById(R.id.adView);
	    AdRequest adRequest = new AdRequest.Builder()
	    	.addTestDevice("1A96947585B930E5C32F8D7874E7F6A0")
	    	.build();
	    adView.loadAd(adRequest);

	}

}
