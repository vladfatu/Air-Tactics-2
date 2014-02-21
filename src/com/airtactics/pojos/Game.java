package com.airtactics.pojos;

import java.util.ArrayList;

import android.content.Context;

import com.airtactics.ai.AI;
import com.airtactics.ai.SimpleAI;
import com.airtactics.interfaces.GameListener;
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
	
	private ArrayList<GameListener> gameListeners;
	
	public Game()
	{
		this.gameType = GameType.SINGLE_PLAYER;
		this.yourTurn = true;
		this.gameListeners = new ArrayList<GameListener>();
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
		Tile tile = this.opponentBoard.clickBoard(context, this, left, top, gridSize, true);
		if (tile != null && tile.getType() != TileType.NONE)
		{
			setYourTurn(false);
			AI ai = new SimpleAI(this.yourBoard);
			Tile opponentsShotTile = ai.shoot(context, this);
			for (GameListener gameListener : this.gameListeners)
			{
				gameListener.onOpponentShot(opponentsShotTile);
			}
			setYourTurn(true);
		}
		return tile;
	}
	
	public void updateScore()
	{
		for (GameListener gameListener : this.gameListeners)
		{
			gameListener.onScoreUpated();
		}
	}

	public boolean isYourTurn()
	{
		return yourTurn;
	}

	public void setYourTurn(boolean yourTurn)
	{
		this.yourTurn = yourTurn;
	}

	public void addGameListener(GameListener gameListener)
	{
		this.gameListeners.add(gameListener);
	}

	public void removeGameListeners(GameListener gameListener)
	{
		this.gameListeners.remove(gameListener);
	}

}
