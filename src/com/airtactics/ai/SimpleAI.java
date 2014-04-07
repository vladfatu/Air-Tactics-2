package com.airtactics.ai;

import java.util.Random;

import android.content.Context;

import com.airtactics.engine.Point;
import com.airtactics.pojos.Board;

public class SimpleAI extends AI{

	public SimpleAI(Board opponentBoard) {
		super(opponentBoard);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Point shoot(Context context)
	{
		Random r = new Random();
		int x = r.nextInt(10);
		int y = r.nextInt(10);
		
		while (getOpponentBoard().isPositionAlreayShot(new Point(x, y)) || isCorner(x, y))
		{
			x = r.nextInt(10);
			y = r.nextInt(10);
		}
		
		Point point = new Point(x, y);
		getOpponentBoard().markAsSeen(point);
		return point;
		
	}
	
	private boolean isCorner(int x, int y)
	{
		if ((x == 0 || x == 9) && (y == 0 || y == 9))
		{
			return true;
		}
		return false;
	}

}
