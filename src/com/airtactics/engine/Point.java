package com.airtactics.engine;

import java.io.Serializable;

/**
 * @author Vlad
 *
 */
public class Point implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8667229354336545438L;
	
	public int x,y;

	public Point()
	{
		x = 0;
		y = 0;
	}
	public Point(int x1, int y1)
	{
		x = x1;
		y = y1;
	}
	public Boolean equals(Point p)
	{
		if (x == p.x && y == p.y) return true;
		else return false;
	}
}
