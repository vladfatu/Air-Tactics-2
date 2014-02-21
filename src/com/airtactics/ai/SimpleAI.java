package com.airtactics.ai;

import java.util.Random;

import android.content.Context;

import com.airtactics.pojos.Board;
import com.airtactics.pojos.Game;
import com.airtactics.pojos.Tile;

public class SimpleAI extends AI{

	public SimpleAI(Board opponentBoard) {
		super(opponentBoard);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Tile shoot(Context context, Game game)
	{
		Random r = new Random();
		int x = r.nextInt(10);
		int y = r.nextInt(10);
		
		while (!getOpponentBoard().isPositionAlreayShot(x, y))
		{
			x = r.nextInt(10);
			y = r.nextInt(10);
		}
		
		return getOpponentBoard().clickPosition(context, game, x, y, false);
		
	}

}
