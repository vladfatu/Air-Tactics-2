package com.airtactics.ai;

import android.content.Context;

import com.airtactics.engine.Point;
import com.airtactics.pojos.Board;

public abstract class AI {
	
	private Board opponentBoard;

	public AI(Board opponentBoard)
	{
		this.opponentBoard = opponentBoard;
	}
	
	public abstract Point shoot(Context context);
	
	public Board getOpponentBoard()
	{
		return opponentBoard;
	}

	public void setOpponentBoard(Board opponentBoard)
	{
		this.opponentBoard = opponentBoard;
	}
}
