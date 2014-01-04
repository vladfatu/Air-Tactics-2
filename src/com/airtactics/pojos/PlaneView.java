package com.airtactics.pojos;

import android.widget.ImageView;

/**
 * @author Vlad
 *
 */
public class PlaneView {
	
	private Plane plane;
	
	private ImageView imageView;
	
	public PlaneView(Plane plane, ImageView imageView)
	{
		this.plane = plane;
		this.imageView = imageView;
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
