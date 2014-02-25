package com.airtactics.pojos;

import java.util.ArrayList;

import android.content.Context;

import com.airtactics.ai.AI;
import com.airtactics.ai.SimpleAI;
import com.airtactics.engine.Point;
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
	
	public TileType clickOpponentBoard(Context context, Point position)
	{
		TileType tileType = this.opponentBoard.checkPoint(position);
		if (tileType != TileType.NONE)
		{
			setYourTurn(false);
			if (tileType == TileType.HIT_HEAD)
			{
				updateScore();
			}
			AI ai = new SimpleAI(this.yourBoard);
			Tile opponentsShotTile = ai.shoot(context);
			if (opponentsShotTile.getType() == TileType.HIT_HEAD)
			{
				updateScore();
			}
			for (GameListener gameListener : this.gameListeners)
			{
				gameListener.onOpponentShot(opponentsShotTile);
			}
			setYourTurn(true);
		}
		return tileType;
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
