package com.airtactics.pojos;

import airtactics.com.R;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.airtactics.engine.Point;

/**
 * @author Vlad
 *
 */
public class Tile {
	
	public enum TileType{
		HIT_HEAD,
		HIT_BODY,
		MISSED,
		NONE
	}
	
	private ImageView imageView;

	private boolean selected;

	private TileType type;

	private Point position;

	public Tile(int x, int y) {
		this.position = new Point(x, y);
		this.setType(TileType.NONE);
		this.selected = false;
	}

	public boolean isSelected()
	{
		return selected;
	}

	public void setSelected(boolean selected)
	{
		this.selected = selected;
	}

	public Point getPosition()
	{
		return position;
	}

	public void setPosition(Point position)
	{
		this.position = position;
	}
	
	public Point getViewPosition(int gridSize)
	{
		int unit = gridSize/10;
		
		int imageSize = 2;
		
		if (this.selected)
		{
			imageSize = unit;
		}
		
		int left = this.position.x * unit + unit/2 - imageSize/2;
		int top = this.position.y * unit + unit/2 - imageSize/2;
		
		return new Point(left, top);
	}

	public TileType getType()
	{
		return type;
	}

	public void setType(TileType type)
	{
		this.type = type;
	}
	
	public int getResourceId()
	{
		if (this.selected)
		{
			return R.drawable.selected;
		}
		else
		{
			switch (this.type)
			{
				case HIT_HEAD:
				{
					return R.drawable.hit_head;
				}
				case HIT_BODY:
				{
					return R.drawable.hit_body;
				}
				case MISSED:
				{
					return R.drawable.no_hit;
				}
				case NONE:
				{
					return 0;
				}
			}
		}
		return 0;
	}

	public ImageView getImageView()
	{
		return imageView;
	}

	public void setImageView(ImageView newImageView)
	{
		if (this.imageView != null)
		{
			ViewGroup parent = (ViewGroup) this.imageView.getParent();
			parent.removeView(this.imageView);
			
		}
		this.imageView = newImageView;
	}
}
