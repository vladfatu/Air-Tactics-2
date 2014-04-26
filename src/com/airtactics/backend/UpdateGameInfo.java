package com.airtactics.backend;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import android.os.AsyncTask;
import android.util.Log;

public class UpdateGameInfo extends AsyncTask<String, String, String>{
	
	private String userId;
	private int newWonGames;
	private int newLostGames;
	private int gamesForAverage;
	private float averageShots;
	
	public UpdateGameInfo(String userId, int newWonGames, int newLostGames, int gamesForAverage, float averageShots)
	{
		this.userId = userId;
		this.newWonGames = newWonGames;
		this.newLostGames = newLostGames;
		this.gamesForAverage = gamesForAverage;
		this.averageShots = averageShots;
	}

	@Override
	protected String doInBackground(String... params)
	{
		String url = "http://work-tracker.appspot.com/resources/gameinfo";
		String charset = "UTF-8";
		
		String query;
		try
		{
			query = String.format("userId=%s"
					+ "&newWonGames=%s"
					+ "&newLostGames=%s"
					+ "&gamesForAverage=%s"
					+ "&averageShots=%s", 
				     URLEncoder.encode(getUserId(), charset), 
				     URLEncoder.encode(Integer.toString(getNewWonGames()), charset),
				     URLEncoder.encode(Integer.toString(getNewLostGames()), charset),
				     URLEncoder.encode(Integer.toString(getGamesForAverage()), charset),
				     URLEncoder.encode(Float.toString(getAverageShots()), charset));
			
			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setDoOutput(true); // Triggers POST.
			connection.setRequestProperty("Accept-Charset", charset);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
			OutputStream output = connection.getOutputStream();
			try {
			     output.write(query.getBytes(charset));
			} finally {
			     try { output.close(); } catch (IOException logOrIgnore) {}
			}
//			InputStream response = connection.getInputStream();
			Log.d("UpdateGameInfo", "Update status code: " + connection.getResponseCode() + " and message: " + connection.getResponseMessage());
			
		} catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public int getNewWonGames()
	{
		return newWonGames;
	}

	public void setNewWonGames(int newWonGames)
	{
		this.newWonGames = newWonGames;
	}

	public int getNewLostGames()
	{
		return newLostGames;
	}

	public void setNewLostGames(int newLostGames)
	{
		this.newLostGames = newLostGames;
	}

	public int getGamesForAverage()
	{
		return gamesForAverage;
	}

	public void setGamesForAverage(int gamesForAverage)
	{
		this.gamesForAverage = gamesForAverage;
	}

	public float getAverageShots()
	{
		return averageShots;
	}

	public void setAverageShots(float averageShots)
	{
		this.averageShots = averageShots;
	}

}
