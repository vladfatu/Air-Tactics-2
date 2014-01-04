package com.airtactics.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.widget.ImageView;


/**
 * @author Vlad
 *
 */
public class ViewUtils {
	
	public static void rotateImageView(Context context, ImageView imageView, float angle, int imageId)
	{
		Bitmap myImg = BitmapFactory.decodeResource(context.getResources(), imageId);

		Matrix matrix = new Matrix();
		matrix.postRotate(angle);

		Bitmap rotated = Bitmap.createBitmap(myImg, 0, 0, myImg.getWidth(), myImg.getHeight(),
		        matrix, true);

		imageView.setImageBitmap(rotated);
	}
	
}
