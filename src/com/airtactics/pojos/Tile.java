package com.airtactics.pojos;

import com.airtactics.engine.Point;

/**
 * @author Vlad
 *
 */
public class Tile {

	private boolean hit;

	private boolean selected;

	private boolean visible;

	private Point position;

	public Tile(int x, int y) {
		this.position = new Point(x, y);
		this.visible = false;
		this.hit = false;
		this.selected = false;
	}

	public boolean isHit()
	{
		return hit;
	}

	public void setHit(boolean hit)
	{
		this.hit = hit;
	}

	public boolean isSelected()
	{
		return selected;
	}

	public void setSelected(boolean selected)
	{
		this.selected = selected;
	}

	public boolean isVisible()
	{
		return visible;
	}

	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

	public Point getPosition()
	{
		return position;
	}

	public void setPosition(Point position)
	{
		this.position = position;
	}
}
