package com.airtactics.pojos;

import java.util.ArrayList;
import java.util.List;

import airtactics.com.R;
import android.content.Context;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;

import com.airtactics.engine.Point;
import com.airtactics.utils.ViewUtils;

public class Plane {
	private List<Point> points = new ArrayList<Point>();
	private Point head;
	private int numberOfPoints;
	private ImageView imageView;
	private float degrees;
	
	public Plane(ImageView imageView)
	{
		numberOfPoints = 0;
		head = new Point();
		this.imageView = imageView;
		this.degrees = 0;
	}
	public void setPoints(List<Point> p)
	{
		points = p;
	}
	public boolean addPoint(Point p)
	{
		Point temp = new Point(p.x,p.y);
		if (numberOfPoints < 7)
		{
			points.add(temp);
			return true;
		}
		else
			return false;
	}
	public boolean checkPoint(Point p)
	{
		if (head.equals(p)) return true;
		for (int i=0;i<points.size();i++)
			if (p.equals(points.get(i))) return true;
		return false;
	}
	
	
	//See if this is necessary
	//rotate the plane 180 degrees around the point p if the plane is directed to the right
//	public Plane rotate1to3(Point p)
//	{
//		Plane temp = new Plane();
//		Point tempPoint = new Point();
//		for (int i=0; i<points.size();i++)
//		{
//			tempPoint.x = 2 * p.x - points.get(i).x;
//			tempPoint.y = points.get(i).y;
//			temp.addPoint(tempPoint);
//		}
//		tempPoint.x = 2 * p.x - head.x;
//		tempPoint.y = head.y;
//		temp.setHead(tempPoint);
//		return temp;
//	}
	//rotate the plane 180 degrees around the point p if the plane is directed up
//	public Plane rotate0to2(Point p)
//	{
//		Plane temp = new Plane();
//		Point tempPoint = new Point();
//		for (int i=0; i<points.size();i++)
//		{
//			tempPoint.y = 2 * p.y - points.get(i).y;
//			tempPoint.x = points.get(i).x;
//			temp.addPoint(tempPoint);
//		}
//		tempPoint.y = 2 * p.y - head.y;
//		tempPoint.x = head.x;
//		temp.setHead(tempPoint);
//		return temp;
//	}
	
	public void moveToCenteredCoordinates(int x, int y, int gridSize)
	{
		moveToCoordinates(x - this.imageView.getWidth()/2, y - this.imageView.getHeight()/2 - 100, gridSize, this.imageView.getWidth(), this.imageView.getHeight());
	}
	
	public void moveToCoordinates(int x, int y, int gridSize, int width, int height)
	{
		int unit = gridSize/10;
		int left = x;
		int top = y;
		LayoutParams params = (LayoutParams) this.imageView.getLayoutParams();
		
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
			this.imageView.setLayoutParams(params);
		}
	}
	
	public void shiftUp(int gridSize)
	{
		LayoutParams params = (LayoutParams) this.imageView.getLayoutParams();
		if (params.topMargin - gridSize/10 >= 0)
		{
			params.topMargin -= gridSize/10;
			this.imageView.setLayoutParams(params);
		
			//TODO
//			Plane temp = new Plane();
//			Point tempPoint = new Point();
//			for (int i=0; i<points.size();i++)
//			{
//				tempPoint.y = points.get(i).y-1;
//				tempPoint.x = points.get(i).x;
//				temp.addPoint(tempPoint);
//			}
//			tempPoint.y = head.y-1;
//			tempPoint.x = head.x;
//			temp.setHead(tempPoint);
//			return temp;
		}
	}
	
	public void shiftDown(int gridSize)
	{
		LayoutParams params = (LayoutParams) this.imageView.getLayoutParams();
		if (params.topMargin + this.imageView.getHeight() + gridSize/10 <= gridSize)
		{
			params.topMargin += gridSize/10;
			this.imageView.setLayoutParams(params);
			
			//TODO
//			Plane temp = new Plane();
//			Point tempPoint = new Point();
//			for (int i=0; i<points.size();i++)
//			{
//				tempPoint.y = points.get(i).y+1;
//				tempPoint.x = points.get(i).x;
//				temp.addPoint(tempPoint);
//			}
//			tempPoint.y = head.y+1;
//			tempPoint.x = head.x;
//			temp.setHead(tempPoint);
//			return temp;
		}
	}
	
	public void shiftLeft(int gridSize)
	{
		LayoutParams params = (LayoutParams) this.imageView.getLayoutParams();
		if (params.leftMargin - gridSize/10 >= 0)
		{
			params.leftMargin -= gridSize/10;
			this.imageView.setLayoutParams(params);
			
			//TODO
//			Plane temp = new Plane();
//			Point tempPoint = new Point();
//			for (int i=0; i<points.size();i++)
//			{
//				tempPoint.y = points.get(i).y;
//				tempPoint.x = points.get(i).x-1;
//				temp.addPoint(tempPoint);
//			}
//			tempPoint.y = head.y;
//			tempPoint.x = head.x-1;
//			temp.setHead(tempPoint);
//			return temp;
		}
	}
	
	public void shiftRight(int gridSize)
	{
		LayoutParams params = (LayoutParams) this.imageView.getLayoutParams();
		if (params.leftMargin + this.imageView.getWidth() + gridSize/10 <= gridSize)
		{
			params.leftMargin += gridSize/10;
			this.imageView.setLayoutParams(params);
			
			//TODO
//			Plane temp = new Plane();
//			Point tempPoint = new Point();
//			for (int i=0; i<points.size();i++)
//			{
//				tempPoint.y = points.get(i).y;
//				tempPoint.x = points.get(i).x+1;
//				temp.addPoint(tempPoint);
//			}
//			tempPoint.y = head.y;
//			tempPoint.x = head.x+1;
//			temp.setHead(tempPoint);
//			return temp;
		}
	}
	
	public void rotateClockwise(Context context, int gridSize)
	{
		if (this.degrees + 90 >= 360)
		{
			this.degrees = 0;
		}
		else
		{
			this.degrees += 90;
		}
		ViewUtils.rotateImageView(context, this.imageView, this.degrees, R.drawable.plane);
		LayoutParams params = (LayoutParams) this.imageView.getLayoutParams();
		moveToCoordinates(params.leftMargin, params.topMargin , gridSize, this.imageView.getHeight(), imageView.getWidth());
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
		if (!head.equals(p.head)) return false;
		for (int i=0;i<points.size();i++)
			if (!p.checkPoint(points.get(i))) return false;
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