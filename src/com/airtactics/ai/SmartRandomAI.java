package com.airtactics.ai;

import android.content.Context;

import com.airtactics.engine.Point;
import com.airtactics.pojos.Board;
import com.airtactics.utils.MatrixProbabilitiesGenerator;
import com.airtactics.utils.RandomUtils;
import com.airtactics.views.Tile.TileType;

/**
 * @author Vlad
 *
 */
public class SmartRandomAI extends AI{

	private int[][] probabilityMatrix;
	public TileType[][] tileMatrix;

	public SmartRandomAI(Board opponentBoard) {
		super(opponentBoard);
		tileMatrix = MatrixProbabilitiesGenerator.initTileMatrix();
		probabilityMatrix = MatrixProbabilitiesGenerator.initMatrix();
		MatrixProbabilitiesGenerator.generateProbabilityMatrix(probabilityMatrix, tileMatrix);
	}

	@Override
	public Point shoot(Context context)
	{
		Point point = RandomUtils.getRandomPoint(probabilityMatrix);

		while (getOpponentBoard().isPositionAlreayShot(point))
		{
			System.out.println("already shot: " + point.x + ", " + point.y);
			point = RandomUtils.getRandomPoint(probabilityMatrix);
		}

		probabilityMatrix[point.x][point.y] = 0;
		getOpponentBoard().markAsSeen(point);
		return point;

	}

}