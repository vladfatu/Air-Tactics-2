package com.airtactics.pojos;

import java.io.Serializable;

import com.airtactics.engine.Point;

/**
 * @author Vlad
 *
 */
public class Move implements Serializable{

	private static final long serialVersionUID = 247134488219161681L;
	
	private String username;
	private Point point;
	
	public Move(String username, Point point)
	{
		this.username = username;
		this.point = point;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public Point getPoint()
	{
		return point;
	}

	public void setPoint(Point point)
	{
		this.point = point;
	}

}
