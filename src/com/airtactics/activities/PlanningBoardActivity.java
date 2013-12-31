package com.airtactics.activities;

import airtactics.com.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.airtactics.pojos.Plane;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class PlanningBoardActivity extends Activity {
	
	private Button rotateButton;
	private Button randomizeButton;
	private ImageView gridImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_planning_board);

		AdView adView = (AdView)this.findViewById(R.id.adView);
	    AdRequest adRequest = new AdRequest.Builder()
	    	.addTestDevice("1A96947585B930E5C32F8D7874E7F6A0")
	    	.build();
	    adView.loadAd(adRequest);
	    
	    ImageView planeImageView = (ImageView) findViewById(R.id.imageViewPlane1);
	    final Plane plane = new Plane(planeImageView);
	    
	    gridImageView = (ImageView) findViewById(R.id.imageViewGrid);
	    
	    rotateButton = (Button) findViewById(R.id.buttonRotate);
	    rotateButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				plane.rotateClockwise(PlanningBoardActivity.this);
			}
		});
	    
	    randomizeButton = (Button) findViewById(R.id.buttonRanomize);
	    randomizeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				plane.shiftRight(gridImageView.getWidth());
			}
		});
	}

}
