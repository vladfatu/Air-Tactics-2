package com.airtactics.utils;

import java.util.Random;

import com.airtactics.constants.Constants;
import com.airtactics.engine.Point;

/**
 * @author Vlad
 *
 */
public class RandomUtils {

	/**
	 * Returns a random tile from a weighted matrix
	 */
	public static Point getRandomPoint(int[][] probabilityMatrix)
	{
		int x=0, y=0;
		Random r = new Random();
		int[] rows = new int[Constants.GRID_SIZE];
		int sum = 0;
		for (int i=0;i<Constants.GRID_SIZE;i++)
		{
			for (int j=0;j<Constants.GRID_SIZE;j++)
			{
				rows[i] += probabilityMatrix[i][j];
			}
			sum += rows[i];
		}
		int randomValue = r.nextInt(sum)+1;
		x = getRandomFromRow(rows, randomValue);
		
		randomValue = r.nextInt(rows[x])+1;
		
		y = getRandomFromRow(probabilityMatrix[x], randomValue);
		
		return new Point(x, y);
	}
	
	/**
	 * Returns a random index from a weighted row.
	 */
	public static int getRandomFromRow(int[] row, int randomValue)
	{
		int tempSum = 0;
		for (int i=0;i<Constants.GRID_SIZE;i++)
		{
			tempSum += row[i];
			if (tempSum >= randomValue)
			{
				return i;
			}
		}
		return 0;
	}
	
}
