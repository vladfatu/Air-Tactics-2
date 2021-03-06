package com.airtactics.views;

import airtactics.com.R;
import android.content.Context;
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
	
	public static int HIT_HEAD_WIDTH, HIT_BODY_WIDTH, NO_HIT_WIDTH;
	public static int HIT_HEAD_WIDTH_SMALL, HIT_BODY_WIDTH_SMALL, NO_HIT_WIDTH_SMALL;
	public static int EXPLOSION_WIDTH_SIZE, EXPLOSION_HEIGHT_SIZE, FIRE_WIDTH_SIZE, FIRE_HEIGHT_SIZE;
	
	private ImageView imageView;

	private boolean selected;

	private TileType type;

	private Point position;
	
	private boolean isPlaneSmall;
	
	public Tile(Point position, boolean isPlaneSmall) {
		this.isPlaneSmall = isPlaneSmall;
		this.position = position;
		this.setType(TileType.NONE);
		this.selected = false;
	}
	
	public Tile(Context context, boolean isPlaneSmall, Point position, TileType type)
	{
		this.isPlaneSmall = isPlaneSmall;
		this.position = position;
		this.setType(type);
		this.selected = false;
		ImageView imageView = new ImageView(context);
		imageView.setImageResource(getResourceId());
		setImageView(imageView);
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
	
	public Point getViewPosition(int gridSize, boolean isSmall)
	{
		int unit = gridSize/10;
		
		int imageSize = getImageSize(gridSize, isSmall);
		
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
					if (this.isPlaneSmall)
					{
						return R.drawable.hit_head_small;
					}
					else
					{
						return R.drawable.hit_head;
					}
				}
				case HIT_BODY:
				{
					if (this.isPlaneSmall)
					{
						return R.drawable.hit_body_small;
					}
					else
					{
						return R.drawable.hit_body;
					}
				}
				case MISSED:
				{
					if (this.isPlaneSmall)
					{
						return R.drawable.no_hit_small;
					}
					else
					{
						return R.drawable.no_hit;
					}
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
		this.imageView = newImageView;
	}
	
	private int getImageSize(int gridSize, boolean isSmall)
	{
		if (this.selected)
		{
			return gridSize/10;
		}
		else
		{
			switch (this.type)
			{
				case HIT_HEAD:
				{
					if (isSmall)
					{
						return HIT_HEAD_WIDTH_SMALL;
					}
					else
					{
						return HIT_HEAD_WIDTH;
					}
				}
				case HIT_BODY:
				{
					if (isSmall)
					{
						return HIT_BODY_WIDTH_SMALL;
					}
					else
					{
						return HIT_BODY_WIDTH;
					}
				}
				case MISSED:
				{
					if (isSmall)
					{
						return NO_HIT_WIDTH_SMALL;
					}
					else
					{
						return NO_HIT_WIDTH;
					}
				}
				case NONE:
				{
					return 0;
				}
			}
		}
		return 0;
	}
	
	public static Point getPosition(int left, int top, int gridSize)
	{
		int unit = gridSize/10;
		
		int x = left / unit;
		//for the case where left == griSize
		if (x > 9)
		{
			x = 9;
		}
		
		int y = top / unit;
		//for the case where top == griSize
		if (y > 9)
		{
			y = 9;
		}
		
		return new Point(x, y);
		
	}
}
