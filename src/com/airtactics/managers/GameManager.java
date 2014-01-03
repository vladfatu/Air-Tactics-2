package com.airtactics.managers;

import java.util.HashMap;
import java.util.Map;

import com.airtactics.pojos.Game;
import com.airtactics.pojos.Game.GameType;

/**
 * @author Vlad
 *
 */
public class GameManager {
	
	private Map<String, Game> currentGames;
	
	private static GameManager instance = new GameManager();
	
	private GameManager()
	{
		this.currentGames = new HashMap<String, Game>();
	}
	
	public static GameManager getManager()
	{
		return instance;
	}

	public Game getGame(String gameId)
	{
		return currentGames.get(gameId);
	}
	
	public String addGame(Game game)
	{
		String gameId = "SINGLE_PLAYER";
		if (game.getGameType() == GameType.MULTI_PLAYER)
		{
			//TODO
		}
		currentGames.put(gameId, game);
		return gameId;
	}

	public Map<String, Game> getCurrentGames()
	{
		return currentGames;
	}

}
