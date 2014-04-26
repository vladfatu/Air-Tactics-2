package com.airtactics.activities;

import airtactics.com.R;
import android.annotation.SuppressLint;
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

import com.airtactics.backend.UpdateGameInfo;
import com.airtactics.engine.Point;
import com.airtactics.interfaces.GameListener;
import com.airtactics.managers.GameManager;
import com.airtactics.pojos.Board;
import com.airtactics.pojos.Game;
import com.airtactics.pojos.Game.GameType;
import com.airtactics.views.PlaneView;
import com.airtactics.views.Tile;
import com.airtactics.views.Tile.TileType;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer;
import com.google.example.games.basegameutils.BaseGameActivity;

/**
 * @author Vlad
 * 
 */
public class PlayingBoardActivity extends BaseGameActivity implements GameListener{

	public final static String GAME_ID = "GAME_ID";

	private static final String TAG = "PlayingBoardActivity";

	private Game game;

	private ImageView gridSmallImageView;
	private ImageView gridLargeImageView;
	private FrameLayout gridSmallFrameLayout;
	private FrameLayout gridFrameLayout;
	private ImageView hitHeadImageView, hitBodyImageView, noHitImageView;
	private TextView yourScoreTextView, oppScoreTextView;
	private Tile selectedTile;
	private String gameId;
	private boolean hasLoaded;
	private boolean wasAlreadyFinished;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_playing_board);

		if (getIntent().getExtras() != null)
		{
			gameId = getIntent().getExtras().getString(GAME_ID);
			this.game = GameManager.getManager().getGame(gameId);
			this.game.addGameListener(this);
			this.wasAlreadyFinished = this.game.isFinished();
		} else
		{
			finish();
		}

		AdView adView = (AdView) this.findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder()
		.addTestDevice("1A96947585B930E5C32F8D7874E7F6A0")
		.addTestDevice("A6D0DF7C8962D83CCC2275E9333E0A8E")
		.addTestDevice("184695F6741124A281EF9F04133382A6")
		.build();
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

		this.hasLoaded = false;
		final RelativeLayout layout = (RelativeLayout) findViewById(R.id.relativeLayout);
		ViewTreeObserver vto = layout.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@SuppressWarnings("deprecation")
			@SuppressLint("NewApi")
			@Override
			public void onGlobalLayout()
			{
				hasLoaded = true;
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
	
	@Override
	protected void onResume()
	{
		if (this.hasLoaded && this.game != null && this.game.getGameType() == GameType.MULTI_PLAYER)
		{
			Games.TurnBasedMultiplayer.loadMatch(getApiClient(), this.gameId).setResultCallback(
					new ResultCallback<TurnBasedMultiplayer.LoadMatchResult>() {

						@Override
						public void onResult(TurnBasedMultiplayer.LoadMatchResult matchResult)
						{
							TurnBasedMatch match = matchResult.getMatch();
							game.update(match, getApiClient());

						}
					});
		}
		super.onResume();
	}
	
	private void setupTiles()
	{
		if (this.game != null)
		{
			Board yourBoard = game.getYourBoard();
			if (yourBoard != null)
			{
//				removeAllTiles(this.gridSmallFrameLayout);
				for (Tile tile : this.game.getYourBoard().getAlreadyHitTiles(this))
				{
					addTile(this.gridSmallFrameLayout, tile);
				}
			}
			Board opponentBoard = game.getOpponentBoard();
			if (opponentBoard != null)
			{
//				removeAllTiles(this.gridFrameLayout);
				for (Tile tile : this.game.getOpponentBoard().getAlreadyHitTiles(this))
				{
					addTile(this.gridFrameLayout, tile);
				}
			}
		}
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
		
		setupTiles();
		updateScore();
	}
	
	private void updateScore()
	{
		if (yourScoreTextView != null)
		{
			yourScoreTextView.setText(game.getYourBoard().getNumberOfHitHeads() + "");
		}
		if (oppScoreTextView != null && game.getOpponentBoard() != null)
		{
			oppScoreTextView.setText(game.getOpponentBoard().getNumberOfHitHeads() + "");
		}
		String winner = this.game.getWinner();
		if (winner != null)
		{
			if (winner.equals(this.game.getYourUsername()))
			{
				Toast.makeText(this, getResources().getString(R.string.you_won), Toast.LENGTH_SHORT).show();
				if (!wasAlreadyFinished)
				{
					UpdateGameInfo task = new UpdateGameInfo(Games.Players.getCurrentPlayerId(getApiClient()),
							1,
							0,
							1,
							this.game.getOpponentBoard().getAlreadyHitTiles(this).size());
				    task.execute();
				}
			}
			else
			{
				Toast.makeText(this, getResources().getString(R.string.you_lost), Toast.LENGTH_SHORT).show();
				if (!wasAlreadyFinished)
				{
					
					UpdateGameInfo task = new UpdateGameInfo(Games.Players.getCurrentPlayerId(getApiClient()),
							0,
							1,
							1,
							0);
				    task.execute();
				}
			}
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
					if (this.game.isStarted())
					{
						if (!this.game.isFinished())
						{
							if (this.game.isYourTurn())
							{
								FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
										FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
	
								Point currentTilePosition = Tile.getPosition(currentX, currentY,
										this.gridLargeImageView.getWidth());
	
								if (this.selectedTile != null
										&& this.selectedTile.getPosition().equals(currentTilePosition))
								{
									GoogleApiClient apiClient = null;
									String matchId = null;
									if (this.game.getGameType() == GameType.MULTI_PLAYER)
									{
										apiClient = getApiClient();
										matchId = this.gameId;
									}
									TileType tileType = this.game.clickOpponentBoard(this, currentTilePosition, matchId,
											apiClient);
									Tile tile = new Tile(this, currentTilePosition, tileType);
	
									addTile(this.gridFrameLayout, tile);
									if (tile != null)
									{
										this.gridFrameLayout.removeView(this.selectedTile.getImageView());
										this.selectedTile = null;
									}
								} else
								{
									if (this.selectedTile == null)
									{
										this.selectedTile = new Tile(currentTilePosition);
										this.selectedTile.setSelected(true);
										ImageView imageView = new ImageView(this);
										imageView.setImageResource(this.selectedTile.getResourceId());
										this.selectedTile.setImageView(imageView);
	
										Point viewPosition = this.selectedTile.getViewPosition(
												this.gridLargeImageView.getWidth(), false);
										lp.setMargins(viewPosition.x, viewPosition.y, 0, 0);
	
										this.gridFrameLayout.addView(this.selectedTile.getImageView(), lp);
									} else
									{
										this.selectedTile.setPosition(currentTilePosition);
										Point viewPosition = this.selectedTile.getViewPosition(
												this.gridLargeImageView.getWidth(), false);
										lp.setMargins(viewPosition.x, viewPosition.y, 0, 0);
	
										this.selectedTile.getImageView().setLayoutParams(lp);
									}
								}
							} else
							{
								Toast.makeText(this, getString(R.string.not_your_turn), Toast.LENGTH_SHORT).show();
							}
						}
						else
						{
							Toast.makeText(this, getString(R.string.finished), Toast.LENGTH_SHORT).show();
						}
					} else
					{
						Toast.makeText(this, getString(R.string.not_started), Toast.LENGTH_SHORT).show();
					}
					break;
				}
			}

		}
		return false;
	}

	@Override
	protected void onDestroy()
	{
		this.game.removeGameListeners(this);
		super.onDestroy();
	}
	
	public void removeAllTiles(FrameLayout gridLayout)
	{
		gridLayout.removeAllViews();
	}
	
	public void addTile(FrameLayout gridLayout, Tile tile)
	{
		FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
		if (tile != null)
		{
			Point viewPosition = tile.getViewPosition(gridLayout.getWidth(), false);
			lp.setMargins(viewPosition.x, viewPosition.y, 0, 0);

			gridLayout.addView(tile.getImageView(), lp);
		}
	}
	
	@Override
	public void onOpponentShot(Point point)
	{
		Tile tile = new Tile(this, point, this.game.getYourBoard().checkPoint(point));
		addTile(this.gridSmallFrameLayout, tile);
	}

	@Override
	public void onScoreUpated()
	{
		updateScore();
		
	}
	
	@Override
	public void onGameStarted()
	{
		Toast.makeText(this, "Game has started", Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void onSignInFailed()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSignInSucceeded()
	{
		GameManager.getManager().registerAsListener(getApiClient());
		
	}

}
