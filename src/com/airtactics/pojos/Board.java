package com.airtactics.pojos;


public class Board {
	
	private Tile[][] tileMatrix;
	
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
	}

}
