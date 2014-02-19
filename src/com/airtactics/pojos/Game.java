package com.airtactics.pojos;

import android.content.Context;

import com.airtactics.ai.AI;
import com.airtactics.ai.SimpleAI;
import com.airtactics.pojos.Tile.TileType;

/**
 * @author Vlad
 *
 */
public class Game {
	
	public enum GameType
	{
		SINGLE_PLAYER,
		MULTI_PLAYER
	}
	
	private Board yourBoard;
	
	private Board opponentBoard;
	
	private boolean yourTurn;
	
	private GameType gameType;
	
	public Game()
	{
		this.gameType = GameType.SINGLE_PLAYER;
		this.yourTurn = true;
	}

	public Board getYourBoard()
	{
		return yourBoard;
	}

	public void setYourBoard(Board yourBoard)
	{
		this.yourBoard = yourBoard;
	}

	public Board getOpponentBoard()
	{
		return opponentBoard;
	}

	public void setOpponentBoard(Board opponentBoard)
	{
		this.opponentBoard = opponentBoard;
	}

	public GameType getGameType()
	{
		return gameType;
	}

	public void setGameType(GameType gameType)
	{
		this.gameType = gameType;
	}
	
	public Tile clickOpponentBoard(Context context, int left, int top, int gridSize)
	{
		Tile tile = this.opponentBoard.clickBoard(context, left, top, gridSize);
		if (tile != null && tile.getType() != TileType.NONE)
		{
			setYourTurn(false);
			AI ai = new SimpleAI(this.yourBoard);
			ai.shoot(context);
			setYourTurn(true);
		}
		return tile;
	}

	public boolean isYourTurn()
	{
		return yourTurn;
	}

	public void setYourTurn(boolean yourTurn)
	{
		this.yourTurn = yourTurn;
	}

}
