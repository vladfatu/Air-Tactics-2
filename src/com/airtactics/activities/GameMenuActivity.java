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

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_menu);
		
		AdView adView = (AdView)this.findViewById(R.id.adView);
	    AdRequest adRequest = new AdRequest.Builder()
	    	.addTestDevice("1A96947585B930E5C32F8D7874E7F6A0")
	    	.build();
	    adView.loadAd(adRequest);

		
		this.buttonSinglePlayer = (Button) findViewById(R.id.buttonSinglePlayer);
		this.buttonSinglePlayer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				startActivity(new Intent(GameMenuActivity.this, PlanningBoardActivity.class));
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
		                1, 7, true);
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

//            if (match != null) {
//                updateMatch(match);
//            }
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
                    GamesClient.EXTRA_MIN_AUTOMATCH_PLAYERS, 0);
            int maxAutoMatchPlayers = data.getIntExtra(
                    GamesClient.EXTRA_MAX_AUTOMATCH_PLAYERS, 0);

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
                	Intent intent = new Intent(GameMenuActivity.this, PlanningBoardActivity.class);
                	TurnBasedMatch match = result.getMatch();
					intent.putExtra(GamesClient.EXTRA_TURN_BASED_MATCH, match);
                	String playerId = Games.Players.getCurrentPlayerId(getApiClient());
                	String myParticipantId = match.getParticipantId(playerId);
                	intent.putExtra(PlanningBoardActivity.BUNDLE_YOUR_ID, myParticipantId);
                	intent.putExtra(PlanningBoardActivity.BUNDLE_OPP_ID, getNextParticipantId(match));
                	GameMenuActivity.this.startActivity(intent);
                	Toast.makeText(GameMenuActivity.this, "game created ", Toast.LENGTH_SHORT).show();
                }
            });
            //showSpinner();
        }
    }
	
//	public void startMatch(final TurnBasedMatch match) {
////        SkeletonTurn mTurnData = new SkeletonTurn();
////        // Some basic turn data
////        mTurnData.turnCounter += 1;
////        mTurnData.data = "First turn";
////
////        mMatch = match;
//
//        String playerId = Games.Players.getCurrentPlayerId(getApiClient());
//        final String myParticipantId = match.getParticipantId(playerId);
//
////        showSpinner();
//
//        Games.TurnBasedMultiplayer.takeTurn(getApiClient(), match.getMatchId(),
//        		null, getNextParticipantId(match)).setResultCallback(
//                new ResultCallback<TurnBasedMultiplayer.UpdateMatchResult>() {
//            @Override
//            public void onResult(TurnBasedMultiplayer.UpdateMatchResult result) {
//            	Toast.makeText(GameMenuActivity.this, "game result upated", Toast.LENGTH_SHORT).show();
////            	startTurn(match);
//            }
//        });
//    }
	
	public void startTurn(TurnBasedMatch match) {

        String nextParticipantId = getNextParticipantId(match);
        // Create the next turn
//        SkeletonTurn mTurnData = new SkeletonTurn();
//        mTurnData.turnCounter += 1;
//        mTurnData.data = "First turn";

//        showSpinner();

        Games.TurnBasedMultiplayer.takeTurn(getApiClient(), match.getMatchId(),
                null, nextParticipantId).setResultCallback(
                new ResultCallback<TurnBasedMultiplayer.UpdateMatchResult>() {
            @Override
            public void onResult(TurnBasedMultiplayer.UpdateMatchResult result) {
            	Toast.makeText(GameMenuActivity.this, "first turn taken", Toast.LENGTH_SHORT).show();
//                processResult(result);
            }
        });

//        mTurnData = null;
    }
	
	public String getNextParticipantId(TurnBasedMatch match) {

        String playerId = Games.Players.getCurrentPlayerId(getApiClient());
        String myParticipantId = match.getParticipantId(playerId);

        ArrayList<String> participantIds = match.getParticipantIds();

        int desiredIndex = -1;

        for (int i = 0; i < participantIds.size(); i++) {
            if (participantIds.get(i).equals(myParticipantId)) {
                desiredIndex = i + 1;
            }
        }

        if (desiredIndex < participantIds.size()) {
            return participantIds.get(desiredIndex);
        }

        if (match.getAvailableAutoMatchSlots() <= 0) {
            // You've run out of automatch slots, so we start over.
            return participantIds.get(0);
        } else {
            // You have not yet fully automatched, so null will find a new
            // person to play against.
            return null;
        }
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
		
	}
	
}
