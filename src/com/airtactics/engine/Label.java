package com.airtactics.engine;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

/**
 * @author Vlad
 *
 */
public class Label {
	private String text;
	private Point position;
	private Paint paint;
	private Panel panel;
	
	public Label(String tempText, int textSize, Panel tempPanel, int color)
	{
		position = new Point(0,0);
		paint = new Paint();
    	paint.setColor(color);
    	paint.setTextSize(textSize);
    	paint.setTextAlign(Paint.Align.CENTER);
    	//paint.setTypeface(font);
    	paint.setFlags(Paint.ANTI_ALIAS_FLAG);
    	//paint.setStyle(Paint.Style.FILL);
		text = tempText;
		panel = tempPanel;
	}
	
	public Label(String tempText, int textSize, Typeface font, Panel tempPanel)
	{
		position = new Point(0,0);
		paint = new Paint();
    	paint.setColor(Color.BLACK);
    	paint.setTextSize(textSize);
    	paint.setTextAlign(Paint.Align.CENTER);
    	paint.setTypeface(font);
    	paint.setFlags(Paint.ANTI_ALIAS_FLAG);
    	//paint.setStyle(Paint.Style.FILL);
		text = tempText;
		panel = tempPanel;
	}
	
	public void setPosition(int x, int y)
	{
		position.x = x;
		position.y = y;
		panel.invalidate();
	}
	
	public void setPosition(Point p)
	{
		position.x = p.x;
		position.y = p.y;
		panel.invalidate();
	}
	
	public void setText(String tempText)
	{
		text = tempText;
		panel.invalidate();
	}
	
	public String getText()
	{
		return text;
	}

	public Point getPosition()
	{
		return position;
	}

	public Paint getPaint()
	{
		return paint;
	}
	
}
