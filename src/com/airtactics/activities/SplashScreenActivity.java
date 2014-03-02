package com.airtactics.activities;

import com.google.android.gms.games.GamesClient;

import airtactics.com.R;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

/**
 * @author Vlad
 *
 */
public class SplashScreenActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		if (extras != null)
		{
			if (extras.getParcelable(GamesClient.EXTRA_TURN_BASED_MATCH) != null)
			{
				Toast.makeText(this, "match extra", Toast.LENGTH_SHORT).show();;
			}
		}
		
		setContentView(R.layout.activity_splash_screen);
		new AsyncTask<String, String, String>() {

			@Override
			protected void onPostExecute(String result)
			{
				startActivity(new Intent(SplashScreenActivity.this, GameMenuActivity.class));
				finish();
				super.onPostExecute(result);
			}

			@Override
			protected String doInBackground(String... params)
			{
				try
				{
					Thread.sleep(500);
				} catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
		}.execute("");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash_screen, menu);
		return true;
	}

}
