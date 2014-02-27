package com.airtactics.pojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.airtactics.engine.Point;

/**
 * @author Vlad
 *
 */
public class Plane implements Serializable{

	private static final long serialVersionUID = -5361965215542051550L;
	
	
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
	
	public void setPositionsAfterHead()
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
	
	public void rotateClockwise()
	{
		if (this.degrees + 90 >= 360)
		{
			this.degrees = 0;
		}
		else
		{
			this.degrees += 90;
		}
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
	
	public void setDegrees(float degrees)
	{
		this.degrees = degrees;
	}

}
