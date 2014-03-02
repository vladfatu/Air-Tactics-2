package com.airtactics.pojos;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.airtactics.constants.Constants;

/**
 * @author Vlad
 *
 */
public class GameState implements Serializable{
	
	private static final long serialVersionUID = -883184545787519667L;
	
	private Map<String, Board> boards;
	private Move lastMove;
	
	public GameState()
	{
		boards = new HashMap<String, Board>();
	}
	
	public void addBoard(String username, Board board)
	{
		this.boards.put(username, board);
	}
	
	public Board getBoard(String username)
	{
		return this.boards.get(username);
	}

	public Move getLastMove()
	{
		return lastMove;
	}

	public void setLastMove(Move lastMove)
	{
		this.lastMove = lastMove;
	}

	public boolean isStarted()
	{
		return this.boards.size() == Constants.NUMBER_OF_PLAYERS;
	}

}
