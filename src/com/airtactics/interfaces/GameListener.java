package com.airtactics.interfaces;

import com.airtactics.pojos.Tile;

public interface GameListener {
	
	public void onOpponentShot(Tile tile);
	
	public void onScoreUpated();

}
