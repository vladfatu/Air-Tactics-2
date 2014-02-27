package com.airtactics.ai;

import android.content.Context;

import com.airtactics.pojos.Board;
import com.airtactics.views.Tile;

public abstract class AI {
	
	private Board opponentBoard;

	public AI(Board opponentBoard)
	{
		this.opponentBoard = opponentBoard;
	}
	
	public abstract Tile shoot(Context context);
	
	public Board getOpponentBoard()
	{
		return opponentBoard;
	}

	public void setOpponentBoard(Board opponentBoard)
	{
		this.opponentBoard = opponentBoard;
	}
}
