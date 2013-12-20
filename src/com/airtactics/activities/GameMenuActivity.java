package com.airtactics.activities;

import airtactics.com.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class GameMenuActivity extends Activity{
	
	private Button buttonSinglePlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_menu);
		
		this.buttonSinglePlayer = (Button) findViewById(R.id.buttonSinglePlayer);
		this.buttonSinglePlayer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				startActivity(new Intent(GameMenuActivity.this, PlanningBoardActivity.class));
				finish();
				
			}
		});
	}
	
}
