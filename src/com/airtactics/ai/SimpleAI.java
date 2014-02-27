package com.airtactics.ai;

import java.util.Random;

import android.content.Context;

import com.airtactics.engine.Point;
import com.airtactics.pojos.Board;
import com.airtactics.views.Tile;
import com.airtactics.views.Tile.TileType;

public class SimpleAI extends AI{

	public SimpleAI(Board opponentBoard) {
		super(opponentBoard);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Tile shoot(Context context)
	{
		Random r = new Random();
		int x = r.nextInt(10);
		int y = r.nextInt(10);
		
		while (getOpponentBoard().isPositionAlreayShot(x, y))
		{
			x = r.nextInt(10);
			y = r.nextInt(10);
		}
		
		TileType tileType = getOpponentBoard().checkPoint(new Point(x, y));
		Point tilePosition = new Point(x, y);
		return new Tile(context, tilePosition, tileType);
		
	}

}
