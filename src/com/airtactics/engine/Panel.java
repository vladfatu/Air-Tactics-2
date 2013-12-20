package com.airtactics.engine;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class Panel extends View {

	private ArrayList<Sprite> spritesList = new ArrayList<Sprite>();
	
	private ArrayList<Label> labelsList = new ArrayList<Label>();
	
	private Paint paint;

	public Panel(Context context) {
		super(context);
		this.paint = new Paint();
	}
	
	public Panel(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.paint = new Paint();
	}

	public void addSprite(Sprite sprite)
	{
		spritesList.add(sprite);
		this.invalidate();
	}

	public void removeSprite(Sprite sprite)
	{
		spritesList.remove(sprite);
		this.invalidate();
	}

	public void addLabel(Label label)
	{
		labelsList.add(label);
		this.invalidate();
	}

	public void removeLabel(Label label)
	{
		labelsList.remove(label);
		this.invalidate();
	}

	public void removeAll()
	{
		labelsList.removeAll(labelsList);
		spritesList.removeAll(spritesList);
	}

	public void onDraw(Canvas canvas)
	{
		for (int i = 0; i < spritesList.size(); i++)
		{
			canvas.drawBitmap(spritesList.get(i).getBitmap(), spritesList.get(i).getLeft(), spritesList.get(i).getTop(), paint);
		}
		for (int i = 0; i < labelsList.size(); i++)
		{
			canvas.drawText(labelsList.get(i).getText(), labelsList.get(i).getPosition().x, labelsList.get(i).getPosition().y,
					labelsList.get(i).getPaint());
		}

		// background.draw(canvas);
		// tile.draw(canvas);
		// plane.draw(canvas);
	}
}
