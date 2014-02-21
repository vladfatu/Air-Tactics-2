package com.airtactics.ai;

import android.content.Context;

import com.airtactics.pojos.Board;
import com.airtactics.pojos.Game;
import com.airtactics.pojos.Tile;

public abstract class AI {
	
	private Board opponentBoard;

	public AI(Board opponentBoard)
	{
		this.opponentBoard = opponentBoard;
	}
	
	public abstract Tile shoot(Context context, Game game);
	
	public Board getOpponentBoard()
	{
		return opponentBoard;
	}

	public void setOpponentBoard(Board opponentBoard)
	{
		this.opponentBoard = opponentBoard;
	}
}
