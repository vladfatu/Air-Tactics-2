package com.airtactics.interfaces;

import com.airtactics.views.Tile;

public interface GameListener {
	
	public void onOpponentShot(Tile tile);
	
	public void onScoreUpated();

}
