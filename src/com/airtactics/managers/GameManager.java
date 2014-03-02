package com.airtactics.managers;

import java.util.HashMap;
import java.util.Map;

import com.airtactics.pojos.Game;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.multiplayer.turnbased.OnTurnBasedMatchUpdateReceivedListener;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch;

/**
 * @author Vlad
 *
 */
public class GameManager implements OnTurnBasedMatchUpdateReceivedListener{
	
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
	
	public String addGame(String gameId, Game game)
	{
		currentGames.put(gameId, game);
		return gameId;
	}

	public Map<String, Game> getCurrentGames()
	{
		return currentGames;
	}
	
	public void registerAsListener(GoogleApiClient apiClient)
	{
		Games.TurnBasedMultiplayer.registerMatchUpdateListener(apiClient, this);
	}

	@Override
	public void onTurnBasedMatchReceived(TurnBasedMatch match)
	{
		Game game = this.currentGames.get(match.getMatchId());
		if (game != null)
		{
			game.update(match);
		}
		
	}

	@Override
	public void onTurnBasedMatchRemoved(String arg0)
	{
		// TODO Auto-generated method stub
		
	}

}
