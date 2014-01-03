package com.airtactics.activities;

import airtactics.com.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.airtactics.managers.GameManager;
import com.airtactics.pojos.Game;
import com.airtactics.pojos.Plane;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class PlayingBoardActivity extends Activity {

	public final static String GAME_ID = "GAME_ID";

	private Game game;

	private ImageView gridSmallImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_playing_board);
		
		if (getIntent().getExtras() != null)
		{
			String gameId = getIntent().getExtras().getString(GAME_ID);
			this.game = GameManager.getManager().getGame(gameId);
		}
		else
		{
			finish();
		}

		AdView adView = (AdView) this.findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().addTestDevice("1A96947585B930E5C32F8D7874E7F6A0").build();
		adView.loadAd(adRequest);

		gridSmallImageView = (ImageView) findViewById(R.id.imageViewGridSmall);

		final RelativeLayout layout = (RelativeLayout) findViewById(R.id.relativeLayout);
		ViewTreeObserver vto = layout.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@SuppressWarnings("deprecation")
			@SuppressLint("NewApi")
			@Override
			public void onGlobalLayout()
			{
				setupGame();
				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
				{
					layout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				} else
				{
					layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
				}
			}
		});
		
	}

	private void setupGame()
	{
		ImageView planeImageView1 = (ImageView) findViewById(R.id.imageViewPlaneSmall1);
		Plane plane1 = game.getYourBoard().getPlanes().get(0);
		plane1.moveImageViewAfterPosition(planeImageView1, gridSmallImageView.getWidth());

		ImageView planeImageView2 = (ImageView) findViewById(R.id.imageViewPlaneSmall2);
		Plane plane2 = game.getYourBoard().getPlanes().get(1);
		plane2.moveImageViewAfterPosition(planeImageView2, gridSmallImageView.getWidth());

		ImageView planeImageView3 = (ImageView) findViewById(R.id.imageViewPlaneSmall3);
		Plane plane3 = game.getYourBoard().getPlanes().get(2);
		plane3.moveImageViewAfterPosition(planeImageView3, gridSmallImageView.getWidth());
	}

}
