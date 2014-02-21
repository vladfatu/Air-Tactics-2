package com.airtactics.activities;

import airtactics.com.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airtactics.engine.Point;
import com.airtactics.interfaces.GameListener;
import com.airtactics.managers.GameManager;
import com.airtactics.pojos.Game;
import com.airtactics.pojos.PlaneView;
import com.airtactics.pojos.Tile;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * @author Vlad
 * 
 */
public class PlayingBoardActivity extends Activity implements GameListener{

	public final static String GAME_ID = "GAME_ID";

	private static final String TAG = "PlayingBoardActivity";

	private Game game;

	private ImageView gridSmallImageView;
	private ImageView gridLargeImageView;
	private FrameLayout gridSmallFrameLayout;
	private FrameLayout gridFrameLayout;
	private ImageView hitHeadImageView, hitBodyImageView, noHitImageView;
	private TextView yourScoreTextView, oppScoreTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_playing_board);

		if (getIntent().getExtras() != null)
		{
			String gameId = getIntent().getExtras().getString(GAME_ID);
			this.game = GameManager.getManager().getGame(gameId);
			this.game.addGameListener(this);
		} else
		{
			finish();
		}

		AdView adView = (AdView) this.findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().addTestDevice("1A96947585B930E5C32F8D7874E7F6A0").build();
		adView.loadAd(adRequest);

		gridSmallImageView = (ImageView) findViewById(R.id.imageViewGridSmall);
		gridLargeImageView = (ImageView) findViewById(R.id.imageViewGrid);
		gridFrameLayout = (FrameLayout) findViewById(R.id.frameLayoutGrid);
		gridSmallFrameLayout = (FrameLayout) findViewById(R.id.frameLayoutGridSmall);

		hitHeadImageView = (ImageView) findViewById(R.id.imageViewHitHead);
		hitBodyImageView = (ImageView) findViewById(R.id.imageViewHitBody);
		noHitImageView = (ImageView) findViewById(R.id.imageViewNoHit);
		
		yourScoreTextView = (TextView) findViewById(R.id.textViewYourScore);
		oppScoreTextView = (TextView) findViewById(R.id.textViewOppScore);

		final RelativeLayout layout = (RelativeLayout) findViewById(R.id.relativeLayout);
		ViewTreeObserver vto = layout.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@SuppressWarnings("deprecation")
			@SuppressLint("NewApi")
			@Override
			public void onGlobalLayout()
			{
				setupGame();
				Tile.HIT_HEAD_WIDTH = hitHeadImageView.getWidth();
				Tile.HIT_BODY_WIDTH = hitBodyImageView.getWidth();
				Tile.NO_HIT_WIDTH = noHitImageView.getWidth();
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
		PlaneView planeView1 = new PlaneView(this, game.getYourBoard().getPlanes().get(0), planeImageView1,
				this.gridSmallImageView.getWidth(), R.drawable.plane_small, R.drawable.redplane_small,
				game.getYourBoard());
		planeView1.updateImageView();

		ImageView planeImageView2 = (ImageView) findViewById(R.id.imageViewPlaneSmall2);
		PlaneView planeView2 = new PlaneView(this, game.getYourBoard().getPlanes().get(1), planeImageView2,
				this.gridSmallImageView.getWidth(), R.drawable.plane_small, R.drawable.redplane_small,
				game.getYourBoard());
		planeView2.updateImageView();

		ImageView planeImageView3 = (ImageView) findViewById(R.id.imageViewPlaneSmall3);
		PlaneView planeView3 = new PlaneView(this, game.getYourBoard().getPlanes().get(2), planeImageView3,
				this.gridSmallImageView.getWidth(), R.drawable.plane_small, R.drawable.redplane_small,
				game.getYourBoard());
		planeView3.updateImageView();
		
		updateScore();
	}
	
	private void updateScore()
	{
		if (yourScoreTextView != null)
		{
			yourScoreTextView.setText(game.getYourBoard().getNumberOfHitHeads() + "");
		}
		if (oppScoreTextView != null)
		{
			oppScoreTextView.setText(game.getOpponentBoard().getNumberOfHitHeads() + "");
		}
	}

	public boolean onTouchEvent(MotionEvent event)
	{
		int action = event.getAction();
		int currentX = (int) event.getX(0) - this.gridFrameLayout.getLeft();
		int currentY = (int) event.getY(0) - this.gridFrameLayout.getTop();

		Log.d(TAG, "Touch Event in Activity : X: " + currentX + "Y: " + currentY);

		if (currentX > 0 && currentX < this.gridLargeImageView.getWidth() && currentY > 0
				&& currentY < this.gridLargeImageView.getHeight())
		{

			switch (action & MotionEvent.ACTION_MASK)
			{
				case MotionEvent.ACTION_DOWN:
				{
					if (this.game.isYourTurn())
					{
						FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
								FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);

						Tile tile = this.game.clickOpponentBoard(this, currentX, currentY,
								this.gridLargeImageView.getWidth());

						if (tile != null)
						{
							Point viewPosition = tile.getViewPosition(this.gridLargeImageView.getWidth(), false);
							lp.setMargins(viewPosition.x, viewPosition.y, 0, 0);

							this.gridFrameLayout.addView(tile.getImageView(), lp);
						}
					} else
					{
						Toast.makeText(this, getString(R.string.not_your_turn), Toast.LENGTH_SHORT).show();
					}
					break;
				}
			}

		}
		return false;
	}

	@Override
	protected void onDestroy()
	{this.game.removeGameListeners(this);
		super.onDestroy();
	}
	
	@Override
	public void onOpponentShot(Tile tile)
	{
		FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
		if (tile != null)
		{
			Point viewPosition = tile.getViewPosition(this.gridSmallImageView.getWidth(), false);
			lp.setMargins(viewPosition.x, viewPosition.y, 0, 0);

			this.gridSmallFrameLayout.addView(tile.getImageView(), lp);
		}
	}

	@Override
	public void onScoreUpated()
	{
		updateScore();
		
	}

}
