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
import com.airtactics.ai.SmartAI;
import com.airtactics.constants.Constants;
import com.airtactics.engine.Point;
import com.airtactics.interfaces.GameListener;
import com.airtactics.utils.GooglePlayUtils;
import com.airtactics.views.Tile.TileType;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer;

/**
 * @author Vlad
 * 
 */
public class Game{

	public enum GameType{
		SINGLE_PLAYER, MULTI_PLAYER
	}

	private String yourUsername;

	private String opponentUsername;

	private GameType gameType;

	private GameState lastGameState;
	
	private AI ai;

	private ArrayList<GameListener> gameListeners;

	public Game(GameType type, String yourUsername, String opponentUsername) {
		this.yourUsername = yourUsername;
		this.opponentUsername = opponentUsername;
		this.gameType = type;
		this.gameListeners = new ArrayList<GameListener>();
		this.lastGameState = new GameState();
		this.lastGameState.setPlayerToMove(yourUsername);
		
	}
	
	public void initializeYourBoard()
	{
		Board board = new Board();
//		if (this.gameType == GameType.SINGLE_PLAYER)
//		{
			this.ai = new SmartAI(board);
//		}
		this.lastGameState.addBoard(yourUsername, board);
	}
	
	public void initializeOpponentBoard()
	{
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

	public TileType clickOpponentBoard(Context context, Point position, final String matchId, final GoogleApiClient apiClient)
	{
		getOpponentBoard().markAsSeen(position);
		TileType tileType = getOpponentBoard().checkPoint(position);
		if (tileType != TileType.NONE)
		{
			setPlayerToMove(opponentUsername);
			if (tileType == TileType.HIT_HEAD)
			{
				updateScore();
			}
			if (this.gameType == GameType.SINGLE_PLAYER)
			{
				Point opponentsShotPosition = this.ai.shoot(context);
				opponentShot(opponentsShotPosition);
			}
			else
			{
				this.lastGameState.setLastMove(new Move(yourUsername, position)); 
				if (isFinished())
				{
					Games.TurnBasedMultiplayer.finishMatch(apiClient, matchId, persist());
				}
				else
				{
					Games.TurnBasedMultiplayer.takeTurn(apiClient, matchId, persist(), getOpponentUsername()).setResultCallback(
						new ResultCallback<TurnBasedMultiplayer.UpdateMatchResult>() {
							@Override
							public void onResult(TurnBasedMultiplayer.UpdateMatchResult result)
							{
								
							}
						});
				}
			}
		}
		return tileType;
	}
	
	public void opponentShot(Point point)
	{
		TileType tileType = getYourBoard().checkPoint(point);
		if (tileType == TileType.HIT_HEAD)
		{
			updateScore();
		}
		for (GameListener gameListener : this.gameListeners)
		{
			gameListener.onOpponentShot(point);
		}
		setPlayerToMove(yourUsername);
	}

	public void updateScore()
	{
		for (GameListener gameListener : this.gameListeners)
		{
			gameListener.onScoreUpated();
		}
	}
	
	public void updateGameStarted()
	{
		for (GameListener gameListener : this.gameListeners)
		{
			gameListener.onGameStarted();
		}
	}

	public boolean isYourTurn()
	{
		return this.lastGameState.getPlayerToMove().equals(yourUsername);
	}

	public void setPlayerToMove(String playerToMove)
	{
		this.lastGameState.setPlayerToMove(playerToMove);
	}

	public void addGameListener(GameListener gameListener)
	{
		this.gameListeners.add(gameListener);
	}

	public void removeGameListeners(GameListener gameListener)
	{
		this.gameListeners.remove(gameListener);
	}
	
	public boolean isStarted()
	{
		return this.lastGameState.isStarted();
	}
	
	public void update(TurnBasedMatch match, GoogleApiClient apiClient)
	{
		if (this.opponentUsername == null)
		{
			this.opponentUsername = GooglePlayUtils.getNextParticipantId(match, apiClient);
		}
		GameState newGameState = unpersist(match.getData());
		boolean wasGameStarted = this.lastGameState.isStarted();
		this.lastGameState = newGameState;
		if (!wasGameStarted && newGameState.isStarted())
		{
			updateGameStarted();
		}
		else if (newGameState.getLastMove() != null)
		{
			opponentShot(newGameState.getLastMove().getPoint());
		}
	}
	
	public String getYourUsername()
	{
		return yourUsername;
	}
	
	public String getOpponentUsername()
	{
		return opponentUsername;
	}
	
	public void setLastGameState(GameState lastGameState)
	{
		this.lastGameState = lastGameState;
	}
	
	public boolean isFinished()
	{
		return getWinner() != null;
	}
	
	public boolean isCurrentUserTheWinner()
	{
		String winner = getWinner();
		if (winner != null && winner.equals(yourUsername))
		{
			return true;
		}
		return false;
	}
	
	public String getWinner()
	{
		Board yourBoard = this.lastGameState.getBoard(yourUsername);
		if (yourBoard != null && yourBoard.getNumberOfHitHeads() == Constants.NUMBER_OF_PLANES)
		{
			return opponentUsername;
		}
		if (opponentUsername != null)
		{
			Board opponentBoard = this.lastGameState.getBoard(opponentUsername);
			if (opponentBoard!= null && opponentBoard.getNumberOfHitHeads() == Constants.NUMBER_OF_PLANES)
			{
				return yourUsername;
			}
		}
		return null;
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

	public static GameState unpersist(byte[] byteArray)
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
