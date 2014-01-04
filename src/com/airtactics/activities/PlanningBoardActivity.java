package com.airtactics.activities;

import airtactics.com.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.airtactics.managers.GameManager;
import com.airtactics.pojos.Board;
import com.airtactics.pojos.Game;
import com.airtactics.pojos.Plane;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class PlanningBoardActivity extends Activity {

	private static final String TAG = "PlanningBoardActivity";
	private Button rotateButton;
	private Button randomizeButton;
	private Button startButton;
	private ImageView gridImageView;
	private FrameLayout gridLayout;
	private Plane selectedPlane;
	private ImageView planeImageView1;
	private ImageView planeImageView2;
	private ImageView planeImageView3;
	private ImageView selectedPlaneImageView;
	private Game game;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_planning_board);

		AdView adView = (AdView) this.findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().addTestDevice("1A96947585B930E5C32F8D7874E7F6A0").build();
		adView.loadAd(adRequest);

		setupGame();

		gridImageView = (ImageView) findViewById(R.id.imageViewGrid);

		gridLayout = (FrameLayout) findViewById(R.id.frameLayoutGrid);

		rotateButton = (Button) findViewById(R.id.buttonRotate);
		rotateButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{
				if (selectedPlane != null)
				{
					selectedPlane.rotateClockwise(PlanningBoardActivity.this, selectedPlaneImageView, gridImageView.getWidth(), R.drawable.plane);
				}
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
				String gameId = GameManager.getManager().addGame(game);
				Intent intent = new Intent(PlanningBoardActivity.this, PlayingBoardActivity.class);
				intent.putExtra(PlayingBoardActivity.GAME_ID, gameId);
				startActivity(intent);
				finish();
			}
		});
	}
	
	private void setupGame()
	{
		game = new Game();
		game.setYourBoard(new Board());

		planeImageView1 = (ImageView) findViewById(R.id.imageViewPlane1);
		final Plane plane1 = new Plane();
		game.getYourBoard().getPlanes().add(plane1);
		planeImageView1.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				selectedPlane = plane1;
				selectedPlaneImageView = planeImageView1;
				return false;
			}
		});

		planeImageView2 = (ImageView) findViewById(R.id.imageViewPlane2);
		final Plane plane2 = new Plane();
		game.getYourBoard().getPlanes().add(plane2);
		planeImageView2.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				selectedPlane = plane2;
				selectedPlaneImageView = planeImageView2;
				return false;
			}
		});

		planeImageView3 = (ImageView) findViewById(R.id.imageViewPlane3);
		final Plane plane3 = new Plane();
		game.getYourBoard().getPlanes().add(plane3);
		planeImageView3.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				selectedPlane = plane3;
				selectedPlaneImageView = planeImageView3;
				return false;
			}
		});
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
				this.selectedPlane = null;
				this.selectedPlaneImageView = null;
				break;
			}
			case MotionEvent.ACTION_MOVE:
			{
				if (selectedPlane != null)
				{
					selectedPlane.moveToCenteredCoordinates(this.selectedPlaneImageView, currentX, currentY, gridImageView.getWidth());
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
				if (selectedPlane != null)
				{
					if (event.getY(1) > pointerStartingPosY + 5)
					{
						selectedPlane.rotateClockwise(PlanningBoardActivity.this, this.selectedPlaneImageView, this.gridImageView.getWidth(), R.drawable.plane);
					}
				}
				break;
			}
		}
		return false;
	}
	
	private void updatePlaneImageViews()
	{
		game.getYourBoard().getPlanes().get(0).moveImageViewAfterPosition(this, planeImageView1, gridImageView.getWidth(), R.drawable.plane);
		game.getYourBoard().getPlanes().get(1).moveImageViewAfterPosition(this, planeImageView2, gridImageView.getWidth(), R.drawable.plane);
		game.getYourBoard().getPlanes().get(2).moveImageViewAfterPosition(this, planeImageView3, gridImageView.getWidth(), R.drawable.plane);
	}

}
