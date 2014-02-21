package com.airtactics.pojos;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.airtactics.engine.Point;
import com.airtactics.pojos.Tile.TileType;


/**
 * @author Vlad
 *
 */
public class Board {
	
	private Tile[][] tileMatrix;
	private List<Plane> planes;
	private Tile selectedTile;
	
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
		
		this.planes = new ArrayList<Plane>();
		
		this.planes.add(new Plane());
		this.planes.add(new Plane());
		this.planes.add(new Plane());
		
		this.planes.get(0).getHead().x = 1;
		this.planes.get(0).getHead().y = 0;
		this.planes.get(0).setPositionsAfterHead();
		
		this.planes.get(1).getHead().x = 4;
		this.planes.get(1).getHead().y = 1;
		this.planes.get(1).setPositionsAfterHead();
		
		this.planes.get(2).getHead().x = 7;
		this.planes.get(2).getHead().y = 2;
		this.planes.get(2).setPositionsAfterHead();
		
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
	
	public Tile clickBoard(Context context, Game game, int left, int top, int gridSize, boolean selectable)
	{
		int unit = gridSize/10;
		
		int x = left / unit;
		int y = top / unit;
		
		return clickPosition(context, game, x, y, selectable);
		
	}
	
	public boolean isPositionAlreayShot(int x, int y)
	{
		Tile tile = this.tileMatrix[x][y];
		if (tile.getType() == TileType.NONE)
		{
			return true;
		}
		else return false;
	}
	
	public Tile clickPosition(Context context, Game game, int x, int y, boolean selectable)
	{
		Tile tile = this.tileMatrix[x][y];
		if (tile.getType() == TileType.NONE)
		{
			if (!selectable || tile.isSelected())
			{
				clearSelectedTile();
				TileType tileType = checkPoint(new Point(x, y));
				tile.setType(tileType);
				if (tileType == TileType.HIT_HEAD)
				{
					game.updateScore();
				}
			}
			else
			{
				selectTile(tile);
			}
			ImageView imageView = new ImageView(context);
			imageView.setImageResource(tile.getResourceId());
			tile.setImageView(imageView);
			return tile;
		}
		else
		{
			return null;
		}
	}
	
	private void clearSelectedTile()
	{
		if (selectedTile != null)
		{
			this.selectedTile.setSelected(false);
		}
		this.selectedTile = null;
	}
	
	private void selectTile(Tile tile)
	{
		if (this.selectedTile != null)
		{
			this.selectedTile.setSelected(false);
			this.selectedTile.setImageView(null);
		}
		tile.setSelected(true);
		this.selectedTile = tile;
	}
	
	private TileType checkPoint(Point point)
	{
		for (Plane plane : this.planes)
		{
			if (point.equals(plane.getHead()))
			{
				return TileType.HIT_HEAD;
			}
			else if (plane.containsPoint(point))
			{
				return TileType.HIT_BODY;
			}
		}
		return TileType.MISSED;
	}
	
	public int getNumberOfHitHeads()
	{
		int count = 0;
		for (Plane plane : this.planes)
		{
			if (this.tileMatrix[plane.getHead().x][plane.getHead().y].getType() == TileType.HIT_HEAD)
			{
				count++;
			}
		}
		return count;
	}

}
