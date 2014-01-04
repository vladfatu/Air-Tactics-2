package com.airtactics.pojos;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;


public class Board {
	
	private Tile[][] tileMatrix;
	private List<Plane> planes;
	
	public Board()
	{
		this.tileMatrix = new Tile[10][];
		
		for (int i=0;i<10;i++)
		{
			this.tileMatrix[i] = new Tile[10];
		}
		
		for (int i=0;i<10;i++)
    	{
    		for (int j=0;j<10;j++)
    			this.tileMatrix[i][j] = new Tile(i,j);
    	}
		
		this.setPlanes(new ArrayList<Plane>());
	}

	public List<Plane> getPlanes()
	{
		return planes;
	}

	public void setPlanes(List<Plane> planes)
	{
		this.planes = planes;
	}
	
	public void randomizePlanes()
	{
		this.planes.get(0).setRandomDegrees();
		this.planes.get(0).setRandomPosition();
		Log.d("TAG", "Plane1 : x : " + this.planes.get(0).getHead().x + " y : " + this.planes.get(0).getHead().y + " degrees : " + this.planes.get(0).getDegrees());
		
		boolean collisions = true;
		while (collisions)
		{
			this.planes.get(1).setRandomDegrees();
			this.planes.get(1).setRandomPosition();
			if (!this.planes.get(1).hasCollisionsWithPlane(this.planes.get(0)))
			{
				Log.d("TAG", "Plane2 : x : " + this.planes.get(1).getHead().x + " y : " + this.planes.get(1).getHead().y + " degrees : " + this.planes.get(1).getDegrees());
				collisions = false;
			}
		}
		
		collisions = true;
		while (collisions)
		{
			this.planes.get(2).setRandomDegrees();
			this.planes.get(2).setRandomPosition();
			if (!this.planes.get(2).hasCollisionsWithPlane(this.planes.get(0))
					&& !this.planes.get(2).hasCollisionsWithPlane(this.planes.get(1)))
			{
				Log.d("TAG", "Plane3 : x : " + this.planes.get(2).getHead().x + " y : " + this.planes.get(2).getHead().y + " degrees : " + this.planes.get(2).getDegrees());
				collisions = false;
			}
		}
	}

}
