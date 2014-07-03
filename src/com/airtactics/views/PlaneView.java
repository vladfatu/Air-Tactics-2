package com.airtactics.views;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;

import com.airtactics.pojos.Board;
import com.airtactics.pojos.Plane;
import com.airtactics.utils.ViewUtils;

/**
 * @author Vlad
 *
 */
public class PlaneView{
	
	private Plane plane;
	
	private ImageView imageView;
	
	private int gridSize;
	
	private int goodPlaneId;
	
	private int badPlaneId;
	
	private Board board;
	
	private Context context;
	
	private float cachedDegrees;
	private int cachedHeadX;
	private int cachedHeadY;
	
	public PlaneView(Context context, Plane plane, ImageView imageView, int gridSize, int goodPlaneId, int badPlaneId, Board board)
	{
		this.plane = plane;
		this.imageView = imageView;
		this.gridSize = gridSize;
		this.goodPlaneId = goodPlaneId;
		this.badPlaneId = badPlaneId;
		this.board = board;
		this.context = context;
	}
	
	public void rotateClockwise()
	{
		this.plane.rotateClockwise();
		LayoutParams params = (LayoutParams) imageView.getLayoutParams();
		
		params.gravity = Gravity.NO_GRAVITY;
		
		moveToCoordinates(params.leftMargin, params.topMargin , imageView.getHeight(), imageView.getWidth(), true);
		updateImageView();
	}
	
	public boolean hasCollisions()
	{
		for (Plane plane : this.board.getPlanes())
		{
			if (this.plane != plane && this.plane.hasCollisionsWithPlane(plane))
			{
				return true;
			}
		}
		
		return false;
	}
	
	private void rotateImageView()
	{
		int planeId = this.goodPlaneId;
		
		if (hasCollisions())
		{
			planeId = this.badPlaneId;
		}
		
		ViewUtils.rotateImageView(this.context, imageView, this.plane.getDegrees(), planeId);
	}
	
	public void keepCachedPosition()
	{
		this.cachedDegrees = this.plane.getDegrees();
		this.cachedHeadX = this.plane.getHead().x;
		this.cachedHeadY = this.plane.getHead().y;
	}
	
	public void returnToCachedPosition()
	{
		this.plane.setDegrees(this.cachedDegrees);
		this.plane.getHead().x = this.cachedHeadX;
		this.plane.getHead().y = this.cachedHeadY;
		this.plane.setPositionsAfterHead();
		updateImageView();
	}
	
	public void moveToCenteredCoordinates(int x, int y)
	{
		moveToCoordinates(x - imageView.getWidth()/2, y - imageView.getHeight()/2 - 100, imageView.getWidth(), imageView.getHeight(), false);
	}
	
	private void moveToCoordinates(int x, int y, int width, int height, boolean forceUpdate)
	{
		int unit = gridSize/10;
		int left = x;
		int top = y;
		LayoutParams params = (LayoutParams) imageView.getLayoutParams();
		
		params.gravity = Gravity.NO_GRAVITY;
		
		left -= (left % unit);
		top -= (top % unit);
		
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
		
		if (forceUpdate || left != params.leftMargin || top != params.topMargin)
		{
			if (this.plane.getDegrees() == 0)
			{
				this.plane.getHead().x = left/unit + 1;
				this.plane.getHead().y = top/unit;
			}
			else if (this.plane.getDegrees() == 90)
			{
				this.plane.getHead().x = left/unit + 3;
				this.plane.getHead().y = top/unit + 1;
			}
			else if (this.plane.getDegrees() == 180)
			{
				this.plane.getHead().x = left/unit + 1;
				this.plane.getHead().y = top/unit + 3;
			}
			else if (this.plane.getDegrees() == 270)
			{
				this.plane.getHead().x = left/unit;
				this.plane.getHead().y = top/unit + 1;
			}
			
			this.plane.setPositionsAfterHead();
			
			updateImageView();
		}
		
	}
	
	public void updateImageView()
	{
		rotateImageView();
		int unit = gridSize/10;
		LayoutParams params = (LayoutParams) this.imageView.getLayoutParams();
		params.gravity = Gravity.NO_GRAVITY;
		int previousLeftMargin = params.leftMargin;
		int previousTopMargin = params.topMargin;
		
		if (this.getPlane().getDegrees() == 0)
		{
			params.leftMargin = (this.getPlane().getHead().x - 1) * unit;
			params.topMargin = this.getPlane().getHead().y * unit;
		}
		else if (this.getPlane().getDegrees() == 90)
		{
			params.leftMargin = (this.getPlane().getHead().x - 3) * unit;
			params.topMargin = (this.getPlane().getHead().y - 1) * unit;
		}
		else if (this.getPlane().getDegrees() == 180)
		{
			params.leftMargin = (this.getPlane().getHead().x - 1) * unit;
			params.topMargin = (this.getPlane().getHead().y - 3) * unit;
		}
		else if (this.getPlane().getDegrees() == 270)
		{
			params.leftMargin = this.getPlane().getHead().x * unit;
			params.topMargin = (this.getPlane().getHead().y - 1) * unit;
		}
		
		if (previousLeftMargin != params.leftMargin || previousTopMargin != params.topMargin)
		{
			this.imageView.setLayoutParams(params);
		}
	}

	public Plane getPlane()
	{
		return plane;
	}

	public void setPlane(Plane plane)
	{
		this.plane = plane;
	}

	public ImageView getImageView()
	{
		return imageView;
	}

	public void setImageView(ImageView imageView)
	{
		this.imageView = imageView;
	}

}
