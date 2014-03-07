package com.airtactics.utils;

import com.airtactics.constants.Constants;
import com.airtactics.engine.Point;
import com.airtactics.pojos.Plane;
import com.airtactics.views.Tile.TileType;

/**
 * @author Vlad
 *
 */
public class MatrixProbabilitiesGenerator {

	public static TileType[][] tileMatrix;
	private static int[][] probabilityMatrix;

	public static void main(String[] args)
	{
		tileMatrix = initTileMatrix();
		tileMatrix[1][1] = TileType.MISSED;
		probabilityMatrix = initMatrix();
		generateProbabilityMatrix(probabilityMatrix, tileMatrix);
		printMatrix(probabilityMatrix);
	}
	
	/**
	 * This removes all the tiles from the matrix that are not maximal.
	 * 
	 * this seems to work well in practice, 
	 * it makes sure to always check the tiles that are the best at the moment.
	 * 
	 */
	public static void removeOtherThanMax(int[][] probabilityMatrix)
	{
		int max = 0;
		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				if (probabilityMatrix[i][j] > max)
				{
					max = probabilityMatrix[i][j];
				}
			}
		}
		
		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				if (probabilityMatrix[i][j] < max)
				{
					probabilityMatrix[i][j] = 0;
				}
			}
		}
	}
	
	public static int[][] initMatrix()
	{
		int[][] probabilityMatrix = new int[Constants.GRID_SIZE][];

		for (int i = 0; i < 10; i++)
		{
			probabilityMatrix[i] = new int[Constants.GRID_SIZE];
		}
		
		return probabilityMatrix;
	}
	
	public static TileType[][] initTileMatrix()
	{
		TileType[][] tileMatrix = new TileType[Constants.GRID_SIZE][];

		for (int i = 0; i < Constants.GRID_SIZE; i++)
		{
			tileMatrix[i] = new TileType[Constants.GRID_SIZE];
			for (int j = 0;j < Constants.GRID_SIZE; j++)
			{
				tileMatrix[i][j] = TileType.NONE;
			}
		}
		
		return tileMatrix;
	}

	/**
	 * Generates a probability matrix based on the tileMatrix
	 * 
	 * For each value in the matrix it generates the 4 possible planes
	 * 
	 * - checks if they are valid:
	 * 		- can actually exist in the matrix
	 * 		- there are no already tried positions in the tileMatrix that don't fit their type 
	 * 				(for instance a plane has a body part in a tile that was already revealed 
	 * 				to have a Head type or a missed type, then it is not a valid plane)
	 * 
	 * - if the planes are also Body Hit, then we increment this tile with the number of hits (TODO this should be parameterized)
	 */
	public static void generateProbabilityMatrix(int[][] probabilityMatrix, TileType[][] tileMatrix)
	{
		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				probabilityMatrix[i][j] = 0;
				if (tileMatrix[i][j] == TileType.NONE)
				{
					Point head = new Point(i, j);
					if (isPlaneValid(head, 0, tileMatrix))
					{
						probabilityMatrix[i][j]++;
						probabilityMatrix[i][j] += getPlaneBodyHits(head, 0, tileMatrix);
					}
					if (isPlaneValid(head, 90, tileMatrix))
					{
						probabilityMatrix[i][j]++;
						probabilityMatrix[i][j] += getPlaneBodyHits(head, 90, tileMatrix);
					}
					if (isPlaneValid(head, 180, tileMatrix))
					{
						probabilityMatrix[i][j]++;
						probabilityMatrix[i][j] += getPlaneBodyHits(head, 180, tileMatrix);
					}
					if (isPlaneValid(head, 270, tileMatrix))
					{
						probabilityMatrix[i][j]++;
						probabilityMatrix[i][j] += getPlaneBodyHits(head, 270, tileMatrix);
					}
				}

			}
		}
	}
	
	
	/**
	 * If the type was already hit it checks if the type in tileMatrix is the same as the parameter 
	 * @type (the type it occupies in the plane).
	 */
	public static boolean isPointValid(Point point, TileType[][] tileMatrix, TileType type)
	{
		return tileMatrix[point.x][point.y] == TileType.NONE || tileMatrix[point.x][point.y] == type;
	}
	
	/**
	 * We check if the plane generated from @head and @degrees is already body hit in 
	 * tileMatrix and return the number of hits
	 */
	public static int getPlaneBodyHits(Point head, int degrees, TileType[][] tileMatrix)
	{
		int hits = 0;
		if (isHeadValid(head, degrees))
		{
			Plane plane = new Plane();
			plane.setHead(head);
			plane.setDegrees(degrees);
			plane.setPositionsAfterHead();
			for (Point point : plane.getPoints())
			{
				if (tileMatrix[point.x][point.y] == TileType.HIT_BODY)
				{
					hits++;
				}
			}
		}
		return hits;
	}
	
	/**
	 * We check if the plane generated from @head and @degrees is valid:
	 * 
	 * - can actually exist in the matrix
	 * - there are no already tried positions in the tileMatrix that don't fit their type 
	 * 			(for instance a plane has a body part in a tile that was already revealed 
	 * 			to have a Head type or a missed type, then it is not a valid plane)
	 */
	public static boolean isPlaneValid(Point head, int degrees, TileType[][] tileMatrix)
	{
		if (isHeadValid(head, degrees))
		{
			if (!isPointValid(head, tileMatrix, TileType.HIT_HEAD))
			{
				return false;
			}
			Plane plane = new Plane();
			plane.setHead(head);
			plane.setDegrees(degrees);
			plane.setPositionsAfterHead();
			for (Point point : plane.getPoints())
			{
				if (!isPointValid(point, tileMatrix, TileType.HIT_BODY))
				{
					return false;
				}
			}
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * We check if the plane generated from @head and @degrees is valid:
	 * 
	 * - can actually exist in the matrix
	 */
	public static boolean isHeadValid(Point head, int degrees)
	{
		if (degrees == 0)
		{
			if (head.x > 0 && head.x < Constants.GRID_SIZE - 1 && head.y >= 0 && head.y < Constants.GRID_SIZE - 3)
			{
				return true;
			}
		} else if (degrees == 90)
		{
			if (head.x > 2 && head.x < Constants.GRID_SIZE && head.y > 0 && head.y < Constants.GRID_SIZE - 1)
			{
				return true;
			}
		} else if (degrees == 180)
		{
			if (head.x > 0 && head.x < Constants.GRID_SIZE - 1 && head.y > 2 && head.y < Constants.GRID_SIZE)
			{
				return true;
			}
		} else if (degrees == 270)
		{
			if (head.x >= 0 && head.x < Constants.GRID_SIZE - 3 && head.y > 0 && head.y < Constants.GRID_SIZE - 1)
			{
				return true;
			}
		}
		return false;
	}
	
	public static void printMatrix(TileType[][] tileMatrix)
	{
		for (int i = 0; i < Constants.GRID_SIZE; i++)
		{
			for (int j = 0; j < Constants.GRID_SIZE; j++)
			{
				System.out.print(tileMatrix[i][j] + " ");
			}
			System.out.println();
		}
	}

	public static void printMatrix(int[][] probabilityMatrix)
	{
		for (int i = 0; i < Constants.GRID_SIZE; i++)
		{
			for (int j = 0; j < Constants.GRID_SIZE; j++)
			{
				System.out.print(probabilityMatrix[i][j] + " ");
			}
			System.out.println();
		}
	}

}
