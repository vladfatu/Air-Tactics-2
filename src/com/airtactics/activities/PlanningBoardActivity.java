package com.airtactics.activities;

import airtactics.com.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.airtactics.pojos.Plane;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class PlanningBoardActivity extends Activity {
	
	private static final String TAG = "PlanningBoardActivity";
	private Button rotateButton;
	private Button randomizeButton;
	private ImageView gridImageView;
	private FrameLayout gridLayout;
	private Plane plane;

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
	    plane = new Plane(planeImageView);
	    
	    gridImageView = (ImageView) findViewById(R.id.imageViewGrid);
	    
	    gridLayout = (FrameLayout) findViewById(R.id.frameLayoutGrid);
	    
	    rotateButton = (Button) findViewById(R.id.buttonRotate);
	    rotateButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				plane.rotateClockwise(PlanningBoardActivity.this, gridImageView.getWidth());
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
	
	public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int currentX = (int)event.getX(0) - this.gridLayout.getLeft(); 
        int currentY = (int)event.getY(0) - this.gridLayout.getTop(); 
        
        float pointerStartingPosY = 0;

        Log.d(TAG, "Touch Event in Activity : X: " + currentX + "Y: " + currentY);
        
        switch(action & MotionEvent.ACTION_MASK)
        {
	        case MotionEvent.ACTION_DOWN: 
	        { 
	        	break;
	        }
	        case MotionEvent.ACTION_MOVE:
	        {
	        	plane.moveToCenteredCoordinates(currentX, currentY, gridImageView.getWidth());
	        	break;
	        }
	        case MotionEvent.ACTION_POINTER_DOWN:
	        {
	        	pointerStartingPosY = event.getY(1);
	        	break;
	        }
	        case MotionEvent.ACTION_POINTER_UP:
	        {
	        		if (event.getY(1)>pointerStartingPosY+5)
	        		{
	        			plane.rotateClockwise(PlanningBoardActivity.this, this.gridImageView.getWidth());
	        		}
	        	break;
	        }
        }
		return false;
	}

}
