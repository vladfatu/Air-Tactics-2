package com.airtactics.pojos;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

import android.content.Context;

import com.airtactics.ai.AI;
import com.airtactics.ai.SimpleAI;
import com.airtactics.engine.Point;
import com.airtactics.interfaces.GameListener;
import com.airtactics.views.Tile;
import com.airtactics.views.Tile.TileType;

/**
 * @author Vlad
 * 
 */
public class Game {

	public enum GameType {
		SINGLE_PLAYER, MULTI_PLAYER
	}

	private String playerToMove;

	private String yourUsername;

	private String opponentUsername;

	private GameType gameType;

	private GameState lastGameState;

	private ArrayList<GameListener> gameListeners;

	public Game(GameType type, String yourUsername, String opponentUsername) {
		this.yourUsername = yourUsername;
		this.opponentUsername = opponentUsername;
		this.gameType = type;
		this.playerToMove = yourUsername;
		this.gameListeners = new ArrayList<GameListener>();
		this.lastGameState = new GameState();
		this.lastGameState.addBoard(yourUsername, new Board());
		this.lastGameState.addBoard(opponentUsername, new Board());
		
	}

	public Board getYourBoard()
	{
		return lastGameState.getBoard(yourUsername);
	}

	public Board getOpponentBoard()
	{
		return lastGameState.getBoard(opponentUsername);
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
		TileType tileType = getOpponentBoard().checkPoint(position);
		if (tileType != TileType.NONE)
		{
			setPlayerToMove(opponentUsername);
			if (tileType == TileType.HIT_HEAD)
			{
				updateScore();
			}
			AI ai = new SimpleAI(getYourBoard());
			Tile opponentsShotTile = ai.shoot(context);
			if (opponentsShotTile.getType() == TileType.HIT_HEAD)
			{
				updateScore();
			}
			for (GameListener gameListener : this.gameListeners)
			{
				gameListener.onOpponentShot(opponentsShotTile);
			}
			setPlayerToMove(yourUsername);
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
		return playerToMove.equals(yourUsername);
	}

	public void setPlayerToMove(String playerToMove)
	{
		this.playerToMove = playerToMove;
	}

	public void addGameListener(GameListener gameListener)
	{
		this.gameListeners.add(gameListener);
	}

	public void removeGameListeners(GameListener gameListener)
	{
		this.gameListeners.remove(gameListener);
	}

	public byte[] persist()
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = null;
		try
		{
			out = new ObjectOutputStream(bos);
			out.writeObject(this.lastGameState);
			byte[] yourBytes = bos.toByteArray();
			return yourBytes;
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally
		{
			try
			{
				if (out != null)
				{
					out.close();
				}
			} catch (IOException ex)
			{
				// ignore close exception
			}
			try
			{
				bos.close();
			} catch (IOException ex)
			{
				// ignore close exception
			}
		}
		return null;
	}

	public GameState unpersist(byte[] byteArray)
	{
		GameState gameState = null;
		ByteArrayInputStream bis = new ByteArrayInputStream(byteArray);
		ObjectInput in = null;
		try {
		  in = new ObjectInputStream(bis);
		  gameState = (GameState) in.readObject(); 
		} catch (StreamCorruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		  try {
		    bis.close();
		  } catch (IOException ex) {
		    // ignore close exception
		  }
		  try {
		    if (in != null) {
		      in.close();
		    }
		  } catch (IOException ex) {
		    // ignore close exception
		  }
		}
		return gameState;
	}

}
