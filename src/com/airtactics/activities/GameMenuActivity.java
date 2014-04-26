package com.airtactics.activities;

import java.util.ArrayList;

import airtactics.com.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.airtactics.backend.UpdateGameInfo;
import com.airtactics.constants.Constants;
import com.airtactics.managers.GameManager;
import com.airtactics.pojos.Game;
import com.airtactics.pojos.Game.GameType;
import com.airtactics.pojos.GameState;
import com.airtactics.utils.GooglePlayUtils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesClient;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatchConfig;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer;
import com.google.example.games.basegameutils.BaseGameActivity;

/**
 * @author Vlad
 *
 */
public class GameMenuActivity extends BaseGameActivity{
	
	protected static final int RC_UNUSED = 0;
	private static final int RC_SELECT_PLAYERS = 5000;
	private static final int RC_LOOK_AT_MATCHES = 5001;
	private Button buttonSinglePlayer;
	private Button buttonOnline;
	private Button buttonInviteFriends;
	private Button buttonCheckMatches;
	private boolean alreadyStartedMatch;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_menu);
		
		AdView adView = (AdView)this.findViewById(R.id.adView);
	    AdRequest adRequest = new AdRequest.Builder()
	    	.addTestDevice("1A96947585B930E5C32F8D7874E7F6A0")
	    	.addTestDevice("A6D0DF7C8962D83CCC2275E9333E0A8E")
	    	.addTestDevice("184695F6741124A281EF9F04133382A6")
	    	.build();
	    adView.loadAd(adRequest);

		this.buttonSinglePlayer = (Button) findViewById(R.id.buttonSinglePlayer);
		this.buttonSinglePlayer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				Game game = new Game(GameType.SINGLE_PLAYER, Constants.DEFAULT_SINGLE_PLAYER_USERNAME, Constants.DEFAULT_AI_USERNAME);
				game.initializeYourBoard();
				game.initializeOpponentBoard();
				String gameId = Constants.DEFAULT_ID_SINGLE_PLAYER;
				GameManager.getManager().addGame(gameId, game);
				Intent intent = new Intent(GameMenuActivity.this, PlanningBoardActivity.class);
				intent.putExtra(PlayingBoardActivity.GAME_ID, gameId);
				startActivity(intent);
//				finish();
				
			}
		});
		
		this.buttonOnline = (Button) findViewById(R.id.buttonOnline);
		this.buttonOnline.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				startActivityForResult(Games.Achievements.getAchievementsIntent(getApiClient()),
	                    RC_UNUSED);
				
			}
		});
		
		this.buttonInviteFriends = (Button) findViewById(R.id.buttonInviteFriends);
		this.buttonInviteFriends.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				Intent intent = Games.TurnBasedMultiplayer.getSelectOpponentsIntent(getApiClient(),
						Constants.NUMBER_OF_PLAYERS -1, Constants.NUMBER_OF_PLAYERS -1, true);
		        startActivityForResult(intent, RC_SELECT_PLAYERS);
				
			}
		});
		
		this.buttonCheckMatches = (Button) findViewById(R.id.buttonCheckMatches);
		this.buttonCheckMatches.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				Intent intent = Games.TurnBasedMultiplayer.getInboxIntent(getApiClient());
		        startActivityForResult(intent, RC_LOOK_AT_MATCHES);
				
			}
		});
	}
	
	public void onActivityResult(int request, int response, Intent data) {
        super.onActivityResult(request, response, data);

       if (request == RC_LOOK_AT_MATCHES) {
            // Returning from the 'Select Match' dialog

            if (response != Activity.RESULT_OK) {
                // user canceled
                return;
            }

            TurnBasedMatch match = data
                    .getParcelableExtra(GamesClient.EXTRA_TURN_BASED_MATCH);

            if (match != null) {
                handleMatch(match);
            }
//
//            Log.d(TAG, "Match = " + match);
        } else if (request == RC_SELECT_PLAYERS) {
            // Returned from 'Select players to Invite' dialog

            if (response != Activity.RESULT_OK) {
                // user canceled
                return;
            }

            // get the invitee list
            final ArrayList<String> invitees = data
                    .getStringArrayListExtra(GamesClient.EXTRA_PLAYERS);

            // get automatch criteria
            Bundle autoMatchCriteria = null;

            int minAutoMatchPlayers = data.getIntExtra(
                    GamesClient.EXTRA_MIN_AUTOMATCH_PLAYERS, Constants.NUMBER_OF_PLAYERS -1);
            int maxAutoMatchPlayers = data.getIntExtra(
                    GamesClient.EXTRA_MAX_AUTOMATCH_PLAYERS, Constants.NUMBER_OF_PLAYERS -1);

            if (minAutoMatchPlayers > 0) {
                autoMatchCriteria = RoomConfig.createAutoMatchCriteria(
                        minAutoMatchPlayers, maxAutoMatchPlayers, 0);
            } else {
                autoMatchCriteria = null;
            }

            TurnBasedMatchConfig tbmc = TurnBasedMatchConfig.builder()
                    .addInvitedPlayers(invitees)
                    .setAutoMatchCriteria(autoMatchCriteria).build();

            // Start the match
            Games.TurnBasedMultiplayer.createMatch(getApiClient(), tbmc).setResultCallback(
                    new ResultCallback<TurnBasedMultiplayer.InitiateMatchResult>() {
                @Override
                public void onResult(TurnBasedMultiplayer.InitiateMatchResult result) {
                    //processResult(result);
                	TurnBasedMatch match = result.getMatch();
                	handleMatch(match);
                }
            });
            //showSpinner();
        }
    }
	
	private void handleMatch(TurnBasedMatch match)
	{
		GameState gameState = null;
		if (match.getData() != null)
		{
			gameState = Game.unpersist(match.getData());
		}
		if (gameState != null && gameState.isStarted())
		{
			String playerId = Games.Players.getCurrentPlayerId(getApiClient());
	    	String myParticipantId = match.getParticipantId(playerId);
			Game game = new Game(GameType.MULTI_PLAYER, myParticipantId, GooglePlayUtils.getNextParticipantId(match, getApiClient()));
			game.setLastGameState(gameState);
			String gameId = GameManager.getManager().addGame(match.getMatchId(), game);
			Intent intent = new Intent(GameMenuActivity.this, PlayingBoardActivity.class);
			intent.putExtra(PlayingBoardActivity.GAME_ID, gameId);
			startActivity(intent);
		}
		else
		{
			startMultiPlayerGame(match, gameState);
		}
	}
	
	private void startMultiPlayerGame(TurnBasedMatch match, GameState gameState)
	{
		Intent intent = new Intent(GameMenuActivity.this, PlanningBoardActivity.class);
		intent.putExtra(GamesClient.EXTRA_TURN_BASED_MATCH, match);
    	String playerId = Games.Players.getCurrentPlayerId(getApiClient());
    	String myParticipantId = match.getParticipantId(playerId);
    	Game game = new Game(GameType.MULTI_PLAYER, myParticipantId, GooglePlayUtils.getNextParticipantId(match, getApiClient()));
    	GameManager.getManager().addGame(match.getMatchId(), game);
    	intent.putExtra(PlayingBoardActivity.GAME_ID, match.getMatchId());
    	if (gameState != null)
    	{
    		game.setLastGameState(gameState);
    	}
    	game.initializeYourBoard();
    	GameMenuActivity.this.startActivity(intent);
    	Toast.makeText(GameMenuActivity.this, "game created ", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void onSignInFailed()
	{
		Toast.makeText(this, "Signed in failed", Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void onSignInSucceeded()
	{
		Toast.makeText(this, "Signed in", Toast.LENGTH_SHORT).show();
		GameManager.getManager().registerAsListener(getApiClient());
		if (!alreadyStartedMatch)
		{
			TurnBasedMatch match = mHelper.getTurnBasedMatch();
			if (match != null) {
				alreadyStartedMatch = true;
				handleMatch(match);
			}
		}
	}
	
}
