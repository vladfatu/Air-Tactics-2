package com.airtactics.interfaces;

import com.airtactics.engine.Point;

public interface GameListener {
	
	public void onGameStarted();
	
	public void onOpponentShot(Point point);
	
	public void onScoreUpated();

}
