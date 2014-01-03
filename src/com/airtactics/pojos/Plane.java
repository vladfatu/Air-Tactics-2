package com.airtactics.pojos;

import java.util.ArrayList;
import java.util.List;

import airtactics.com.R;
import android.content.Context;
import android.util.Log;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;

import com.airtactics.engine.Point;
import com.airtactics.utils.ViewUtils;

public class Plane {
	private List<Point> points = new ArrayList<Point>();
	private Point head;
	private float degrees;
	
	public Plane()
	{
		head = new Point();
		for (int i = 0; i < 7; i++)
		{
			points.add(new Point());
		}
		this.degrees = 0;
	}
	
	public boolean containsPoint(Point p)
	{
		if (head.equals(p)) 
		{
			return true;
		}
		for (int i=0;i<points.size();i++)
		{
			if (p.equals(points.get(i))) 
			{
				return true;
			}
		}
		return false;
	}
	
	public void moveToCenteredCoordinates(ImageView imageView, int x, int y, int gridSize)
	{
		moveToCoordinates(imageView, x - imageView.getWidth()/2, y - imageView.getHeight()/2 - 100, gridSize, imageView.getWidth(), imageView.getHeight());
	}
	
	public void moveToCoordinates(ImageView imageView, int x, int y, int gridSize, int width, int height)
	{
		int unit = gridSize/10;
		int left = x;
		int top = y;
		LayoutParams params = (LayoutParams) imageView.getLayoutParams();
		
		left -= (left % unit);
		top -= (top % unit);
		
		int previousLeftMargin = params.leftMargin;
		int previousTopMargin = params.topMargin;
		
		if (left < 0)
		{
			left = 0;
		}
		if (left + width > gridSize)
		{
			left = gridSize - width;
		}
		
		if (top < 0)
		{
			top = 0;
		}
		if (top + height > gridSize)
		{
			top = gridSize - height;
		}
		
		params.leftMargin = left;
		params.topMargin = top;
		
		if (previousLeftMargin != params.leftMargin || previousTopMargin != params.topMargin)
		{
			imageView.setLayoutParams(params);
			
			if (this.degrees == 0)
			{
				this.getHead().x = left/unit + 1;
				this.getHead().y = top/unit;
			}
			else if (this.degrees == 90)
			{
				this.getHead().x = left/unit + 3;
				this.getHead().y = top/unit + 1;
			}
			else if (this.degrees == 180)
			{
				this.getHead().x = left/unit + 1;
				this.getHead().y = top/unit + 3;
			}
			else if (this.degrees == 270)
			{
				this.getHead().x = left/unit;
				this.getHead().y = top/unit + 1;
			}
			
			
			setPositionsAfterHead();
			Log.d("TAG", "Head Position: " + this.getHead().x + ", " + this.getHead().y);
			
		}
		
	}
	
	public void moveImageViewAfterPosition(ImageView imageView, int gridSize)
	{
		int unit = gridSize/10;
		LayoutParams params = (LayoutParams) imageView.getLayoutParams();
		int previousLeftMargin = params.leftMargin;
		int previousTopMargin = params.topMargin;
		
		if (this.degrees == 0)
		{
			params.leftMargin = (this.getHead().x - 1) * unit;
			params.topMargin = this.getHead().y * unit;
		}
		else if (this.degrees == 90)
		{
			params.leftMargin = (this.getHead().x - 3) * unit;
			params.topMargin = (this.getHead().y - 1) * unit;
		}
		else if (this.degrees == 180)
		{
			params.leftMargin = (this.getHead().x - 1) * unit;
			params.topMargin = (this.getHead().y - 3) * unit;
		}
		else if (this.degrees == 270)
		{
			params.leftMargin = this.getHead().x * unit;
			params.topMargin = (this.getHead().y - 1) * unit;
		}
		
		if (previousLeftMargin != params.leftMargin || previousTopMargin != params.topMargin)
		{
			imageView.setLayoutParams(params);
		}
	}
	
	private void setPositionsAfterHead()
	{
		if (this.degrees == 0)
		{
			this.points.get(0).x = this.head.x - 1;
			this.points.get(0).y = this.head.y + 1;

			this.points.get(1).x = this.head.x;
			this.points.get(1).y = this.head.y + 1;

			this.points.get(2).x = this.head.x + 1;
			this.points.get(2).y = this.head.y + 1;

			this.points.get(3).x = this.head.x;
			this.points.get(3).y = this.head.y + 2;

			this.points.get(4).x = this.head.x - 1;
			this.points.get(4).y = this.head.y + 3;

			this.points.get(5).x = this.head.x;
			this.points.get(5).y = this.head.y + 3;

			this.points.get(6).x = this.head.x + 1;
			this.points.get(6).y = this.head.y + 3;
		}
		else if (this.degrees == 90)
		{
			this.points.get(0).x = this.head.x - 1;
			this.points.get(0).y = this.head.y - 1;

			this.points.get(1).x = this.head.x - 1;
			this.points.get(1).y = this.head.y;

			this.points.get(2).x = this.head.x - 1;
			this.points.get(2).y = this.head.y + 1;

			this.points.get(3).x = this.head.x - 2;
			this.points.get(3).y = this.head.y;

			this.points.get(4).x = this.head.x - 3;
			this.points.get(4).y = this.head.y - 1;

			this.points.get(5).x = this.head.x - 3;
			this.points.get(5).y = this.head.y;

			this.points.get(6).x = this.head.x - 3;
			this.points.get(6).y = this.head.y + 1;
		}
		else if (this.degrees == 180)
		{
			this.points.get(0).x = this.head.x + 1;
			this.points.get(0).y = this.head.y - 1;

			this.points.get(1).x = this.head.x;
			this.points.get(1).y = this.head.y - 1;

			this.points.get(2).x = this.head.x - 1;
			this.points.get(2).y = this.head.y - 1;

			this.points.get(3).x = this.head.x;
			this.points.get(3).y = this.head.y - 2;

			this.points.get(4).x = this.head.x + 1;
			this.points.get(4).y = this.head.y - 3;

			this.points.get(5).x = this.head.x;
			this.points.get(5).y = this.head.y - 3;

			this.points.get(6).x = this.head.x - 1;
			this.points.get(6).y = this.head.y - 3;
		}
		else if (this.degrees == 270)
		{
			this.points.get(0).x = this.head.x + 1;
			this.points.get(0).y = this.head.y + 1;

			this.points.get(1).x = this.head.x + 1;
			this.points.get(1).y = this.head.y;

			this.points.get(2).x = this.head.x + 1;
			this.points.get(2).y = this.head.y - 1;

			this.points.get(3).x = this.head.x + 2;
			this.points.get(3).y = this.head.y;

			this.points.get(4).x = this.head.x + 3;
			this.points.get(4).y = this.head.y + 1;

			this.points.get(5).x = this.head.x + 3;
			this.points.get(5).y = this.head.y;

			this.points.get(6).x = this.head.x + 3;
			this.points.get(6).y = this.head.y - 1;
		}
	}
	
	public void rotateClockwise(Context context, ImageView imageView, int gridSize)
	{
		if (this.degrees + 90 >= 360)
		{
			this.degrees = 0;
		}
		else
		{
			this.degrees += 90;
		}
		ViewUtils.rotateImageView(context, imageView, this.degrees, R.drawable.plane);
		LayoutParams params = (LayoutParams) imageView.getLayoutParams();
		moveToCoordinates(imageView, params.leftMargin, params.topMargin , gridSize, imageView.getHeight(), imageView.getWidth());
	}
	
	public Boolean checkPlane()
	{
		//TODO
//		if (head.x < 0 || head.x > 9 || head.y < 0 || head.y > 9 || Opponent.checkPoint(head))
//			return false;
//		for (int i=0;i<points.size();i++)
//			if (points.get(i).x < 0 || points.get(i).x > 9 || points.get(i).y < 0 || points.get(i).y > 9 || Opponent.checkPoint(points.get(i)))
//				return false;
		return true;
	}
	
	public Boolean equals(Plane p)
	{
		if (!head.equals(p.head)) 
		{
			return false;
		}
		for (int i=0;i<points.size();i++)
		{
			if (!p.containsPoint(points.get(i))) 
			{
				return false;
			}
		}
		return true;
	}
	
	public void setHead(Point p)
	{
		this.head = p;
	}
	
	public Point getHead()
	{
		return head;
	}

}
