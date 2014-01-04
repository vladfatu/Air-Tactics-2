package com.airtactics.pojos;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
	
	public void setRandomDegrees()
	{
		Random r = new Random();
		int randomInt = r.nextInt(4);
		this.degrees = randomInt * 90;
	}
	
	public void setRandomPosition()
	{
		Random r = new Random();
		
		if (this.degrees == 0)
		{
			this.head.x = r.nextInt(8) + 1;
			this.head.y = r.nextInt(7);
		}
		else if (this.degrees == 90)
		{
			this.head.x = r.nextInt(7) + 3;
			this.head.y = r.nextInt(8) + 1;
		}
		else if (this.degrees == 180)
		{
			this.head.x = r.nextInt(8) + 1;
			this.head.y = r.nextInt(7) + 3;
		}
		else if (this.degrees == 270)
		{
			this.head.x = r.nextInt(7);
			this.head.y = r.nextInt(8) + 1;
		}
		
		setPositionsAfterHead();
	}
	
	public void moveImageViewAfterPosition(Context context, ImageView imageView, int gridSize, int imageId)
	{
		rotateImageView(context, imageView, gridSize, imageId);
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
	
	public void rotateClockwise(Context context, ImageView imageView, int gridSize, int imageId)
	{
		if (this.degrees + 90 >= 360)
		{
			this.degrees = 0;
		}
		else
		{
			this.degrees += 90;
		}
		rotateImageView(context, imageView, gridSize, imageId);
		LayoutParams params = (LayoutParams) imageView.getLayoutParams();
		moveToCoordinates(imageView, params.leftMargin, params.topMargin , gridSize, imageView.getHeight(), imageView.getWidth());
	}
	
	private void rotateImageView(Context context, ImageView imageView, int gridSize, int imageId)
	{
		ViewUtils.rotateImageView(context, imageView, this.degrees, imageId);
	}
	
	public Boolean hasCollisionsWithPlane(Plane plane)
	{
		if (containsPoint(plane.getHead()))
		{
			return true;
		}
		
		for (Point point : plane.getPoints())
		{
			if (containsPoint(point))
			{
				return true;
			}
		}
		return false;
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
	
	public List<Point> getPoints()
	{
		return points;
	}
	
	public float getDegrees()
	{
		return degrees;
	}

}
