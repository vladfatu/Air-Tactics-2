package com.airtactics.activities;

import airtactics.com.R;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;

import com.airtactics.engine.Label;
import com.airtactics.engine.Panel;
import com.airtactics.engine.Sprite;

public class PlanningBoardActivity extends Activity {

	private Panel panelPlanning;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_planning_board);

		this.panelPlanning = (Panel) findViewById(R.id.panelPlanning);

		// test
		Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
		int screenWidth = display.getWidth();
		int screenHeight = display.getHeight();
		
		Label scoreLabel = new Label("You 0-0 Opp", 25, this.panelPlanning, Color.WHITE);
		scoreLabel.setPosition(160, 25);
		this.panelPlanning.addLabel(scoreLabel);
		Sprite grid = new Sprite(getResources(), R.drawable.grid, this.panelPlanning);
		grid.setPosition(screenWidth/2, 350);
		this.panelPlanning.addSprite(grid);
		
		Sprite selected = new Sprite(getResources(), R.drawable.selected, this.panelPlanning);
		selected.setPosition(grid.getLeft() + selected.getBitmap().getWidth()/2-1, grid.getTop() + selected.getBitmap().getHeight()/2-1);
		this.panelPlanning.addSprite(selected);
	}

}
