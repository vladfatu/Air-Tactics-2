package com.airtactics.pojos;

import com.airtactics.interfaces.PlaneListener;

import android.widget.ImageView;

/**
 * @author Vlad
 *
 */
public class PlaneView implements PlaneListener{
	
	private Plane plane;
	
	private ImageView imageView;
	
	public PlaneView(Plane plane, ImageView imageView)
	{
		this.plane = plane;
		this.imageView = imageView;
	}
	
	@Override
	public void onChange()
	{
		// TODO Auto-generated method stub
		
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
