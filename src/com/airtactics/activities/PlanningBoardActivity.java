package com.airtactics.activities;

import airtactics.com.R;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.airtactics.constants.Constants;
import com.airtactics.managers.GameManager;
import com.airtactics.pojos.Game;
import com.airtactics.pojos.Game.GameType;
import com.airtactics.views.PlaneView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesClient;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer;
import com.google.example.games.basegameutils.BaseGameActivity;

/**
 * @author Vlad
 *
 */
public class PlanningBoardActivity extends BaseGameActivity {

	private static final String TAG = "PlanningBoardActivity";
	public static final String BUNDLE_OPP_ID = "BUNDLE_OPP_ID";
	public static final String BUNDLE_YOUR_ID = "BUNDLE_YOUR_ID";
	
	private Button rotateButton;
	private Button randomizeButton;
	private Button startButton;
	private ImageView gridImageView;
	private FrameLayout gridLayout;
	private PlaneView selectedPlaneView;
	private PlaneView planeView1;
	private PlaneView planeView2;
	private PlaneView planeView3;
	private Game game;
	private String opponentUsername, yourUsername;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_planning_board);

		AdView adView = (AdView) this.findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().addTestDevice("1A96947585B930E5C32F8D7874E7F6A0").build();
		adView.loadAd(adRequest);

		gridImageView = (ImageView) findViewById(R.id.imageViewGrid);

		gridLayout = (FrameLayout) findViewById(R.id.frameLayoutGrid);

		rotateButton = (Button) findViewById(R.id.buttonRotate);
		rotateButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{
				planeView1.rotateClockwise();
//				if (selectedPlaneView != null)
//				{
//					selectedPlaneView.rotateClockwise();
//				}
			}
		});

		randomizeButton = (Button) findViewById(R.id.buttonRanomize);
		randomizeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{
				game.getYourBoard().randomizePlanes();
				updatePlaneImageViews();
			}
		});
		
		startButton = (Button) findViewById(R.id.buttonContinue);
		startButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{
				game.getOpponentBoard().randomizePlanes();
				String gameId = GameManager.getManager().addGame(game);
				Intent intent = new Intent(PlanningBoardActivity.this, PlayingBoardActivity.class);
				intent.putExtra(PlayingBoardActivity.GAME_ID, gameId);
				if (game.getGameType() == GameType.MULTI_PLAYER)
				{
					startMatch();
				}
				startActivity(intent);
				finish();
			}
		});
		
		Bundle extras = getIntent().getExtras();
		if (extras != null)
		{
			this.opponentUsername = extras.getString(BUNDLE_OPP_ID);
			this.yourUsername = extras.getString(BUNDLE_YOUR_ID);
		}
		
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
		if (this.yourUsername!= null && this.opponentUsername != null)
		{
			game = new Game(GameType.MULTI_PLAYER, this.yourUsername, this.opponentUsername);
		}
		else
		{
			game = new Game(GameType.SINGLE_PLAYER, Constants.DEFAULT_SINGLE_PLAYER_USERNAME, Constants.DEFAULT_AI_USERNAME);
		}
		final ImageView planeImageView1 = (ImageView) findViewById(R.id.imageViewPlane1);
		planeView1 = new PlaneView(this, 
				game.getYourBoard().getPlanes().get(0),
				planeImageView1,
				this.gridImageView.getWidth(),
				R.drawable.plane,
				R.drawable.redplane,
				game.getYourBoard());
		planeImageView1.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				selectedPlaneView = planeView1;
				selectedPlaneView.keepCachedPosition();
				return false;
			}
		});

		final ImageView planeImageView2 = (ImageView) findViewById(R.id.imageViewPlane2);
		planeView2 = new PlaneView(this, 
				game.getYourBoard().getPlanes().get(1),
				planeImageView2,
				this.gridImageView.getWidth(),
				R.drawable.plane,
				R.drawable.redplane,
				game.getYourBoard());
		planeImageView2.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				selectedPlaneView = planeView2;
				selectedPlaneView.keepCachedPosition();
				return false;
			}
		});

		final ImageView planeImageView3 = (ImageView) findViewById(R.id.imageViewPlane3);
		planeView3 = new PlaneView(this, 
				game.getYourBoard().getPlanes().get(2),
				planeImageView3,
				this.gridImageView.getWidth(),
				R.drawable.plane,
				R.drawable.redplane,
				game.getYourBoard());
		planeImageView3.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				selectedPlaneView = planeView3;
				selectedPlaneView.keepCachedPosition();
				return false;
			}
		});
		
		updatePlaneImageViews();
	}

	public boolean onTouchEvent(MotionEvent event)
	{
		int action = event.getAction();
		int currentX = (int) event.getX(0) - this.gridLayout.getLeft();
		int currentY = (int) event.getY(0) - this.gridLayout.getTop();

		float pointerStartingPosY = 0;

		Log.d(TAG, "Touch Event in Activity : X: " + currentX + "Y: " + currentY);

		switch (action & MotionEvent.ACTION_MASK)
		{
			case MotionEvent.ACTION_UP:
			{
				if (this.selectedPlaneView != null)
				{
					if (this.selectedPlaneView.hasCollisions())
					{
						this.selectedPlaneView.returnToCachedPosition();
					}
					this.selectedPlaneView = null;
				}
				break;
			}
			case MotionEvent.ACTION_MOVE:
			{
				if (selectedPlaneView != null)
				{
					selectedPlaneView.moveToCenteredCoordinates(currentX, currentY);
				}
				break;
			}
			case MotionEvent.ACTION_POINTER_DOWN:
			{
				pointerStartingPosY = event.getY(1);
				break;
			}
			case MotionEvent.ACTION_POINTER_UP:
			{
				if (selectedPlaneView != null)
				{
					if (event.getY(1) > pointerStartingPosY + 5)
					{
						selectedPlaneView.rotateClockwise();
					}
				}
				break;
			}
		}
		return false;
	}
	
	private void updatePlaneImageViews()
	{
		planeView1.updateImageView();
		planeView2.updateImageView();
		planeView3.updateImageView();
	}
	
	public void startMatch()
	{

		TurnBasedMatch match = getIntent().getParcelableExtra(GamesClient.EXTRA_TURN_BASED_MATCH);
		Games.TurnBasedMultiplayer.takeTurn(getApiClient(), match.getMatchId(), this.game.persist(), this.opponentUsername).setResultCallback(
				new ResultCallback<TurnBasedMultiplayer.UpdateMatchResult>() {
					@Override
					public void onResult(TurnBasedMultiplayer.UpdateMatchResult result)
					{
						Toast.makeText(PlanningBoardActivity.this, "game result upated", Toast.LENGTH_SHORT).show();
						// startTurn(match);
					}
				});
	}

	@Override
	public void onSignInFailed()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSignInSucceeded()
	{
		// TODO Auto-generated method stub
		
	}

}
