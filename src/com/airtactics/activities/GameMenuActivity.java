package com.airtactics.activities;

import airtactics.com.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.BaseGameActivity;

/**
 * @author Vlad
 *
 */
public class GameMenuActivity extends BaseGameActivity{
	
	protected static final int RC_UNUSED = 0;
	private Button buttonSinglePlayer;
	private Button buttonOnline;
	private Button buttonInviteFriends;

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
				startActivityForResult(Games.Achievements.getAchievementsIntent(getApiClient()),
	                    RC_UNUSED);
				
			}
		});
		
		this.buttonInviteFriends = (Button) findViewById(R.id.buttonInviteFriends);
		this.buttonInviteFriends.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				Intent intent = Games.TurnBasedMultiplayer.getSelectOpponentsIntent(getApiClient(),
		                1, 7, true);
		        startActivityForResult(intent, RC_UNUSED);
				
			}
		});
	}

	@Override
	public void onSignInFailed()
	{
		Toast.makeText(this, "Signed in failed", Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void onSignInSucceeded()
	{
		Toast.makeText(this, "Signed in", Toast.LENGTH_SHORT).show();
		
	}
	
}
