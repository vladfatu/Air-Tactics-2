package com.airtactics.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.widget.ImageView;


public class ViewUtils {
	
	public static void rotateImageView(Context context, ImageView imageView, float angle, int imageId)
	{
//		Matrix matrix=new Matrix();
//		imageView.setScaleType(ScaleType.MATRIX);   //required
//		matrix.postRotate(angle, imageView.getDrawable().getBounds().width()/2, imageView.getDrawable().getBounds().height()/2);
//		imageView.setImageMatrix(matrix);
		
		Bitmap myImg = BitmapFactory.decodeResource(context.getResources(), imageId);

		Matrix matrix = new Matrix();
		matrix.postRotate(angle);

		Bitmap rotated = Bitmap.createBitmap(myImg, 0, 0, myImg.getWidth(), myImg.getHeight(),
		        matrix, true);

		imageView.setImageBitmap(rotated);
	}
	
}