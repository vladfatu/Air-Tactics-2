package com.airtactics.pojos;

import java.util.ArrayList;
import java.util.List;


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

}
