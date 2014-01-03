package com.airtactics.pojos;

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
	
	private GameType gameType;
	
	public Game()
	{
		this.gameType = GameType.SINGLE_PLAYER;
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

}
