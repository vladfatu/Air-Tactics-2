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
import com.airtactics.pojos.PlaneView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * @author Vlad
 *
 */
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
		PlaneView planeView1 = new PlaneView(this, 
				game.getYourBoard().getPlanes().get(0),
				planeImageView1,
				this.gridSmallImageView.getWidth(),
				R.drawable.plane_small,
				R.drawable.redplane_small,
				game.getYourBoard());
		planeView1.updateImageView();

		ImageView planeImageView2 = (ImageView) findViewById(R.id.imageViewPlaneSmall2);
		PlaneView planeView2 = new PlaneView(this, 
				game.getYourBoard().getPlanes().get(1),
				planeImageView2,
				this.gridSmallImageView.getWidth(),
				R.drawable.plane_small,
				R.drawable.redplane_small,
				game.getYourBoard());
		planeView2.updateImageView();

		ImageView planeImageView3 = (ImageView) findViewById(R.id.imageViewPlaneSmall3);
		PlaneView planeView3 = new PlaneView(this, 
				game.getYourBoard().getPlanes().get(2),
				planeImageView3,
				this.gridSmallImageView.getWidth(),
				R.drawable.plane_small,
				R.drawable.redplane_small,
				game.getYourBoard());
		planeView3.updateImageView();
	}

}
