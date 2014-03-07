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
		
		while (getOpponentBoard().isPositionAlreayShot(new Point(x, y)))
		{
			x = r.nextInt(10);
			y = r.nextInt(10);
		}
		
		return new Point(x, y);
		
	}

}
