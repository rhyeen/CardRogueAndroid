package com.realitypd.cardrogue;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by Ryan Saunders on 11/5/2014.
 */
public class TileView extends ImageView
{
	Boolean _centerToken = false;
	public TileView(Context context)
	{
		super(context);
	}

	public void putCenterToken()
	{
		_centerToken = true;
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);

		if(_centerToken)
		{
			RectF stroke = new RectF();
			Paint strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			strokePaint.setColor(0xff1e0d17);
			strokePaint.setStrokeWidth(20.0f);
			strokePaint.setStyle(Paint.Style.STROKE);
			stroke.left = 0;
			stroke.top = 0;
			stroke.right = getWidth();
			stroke.bottom = getHeight();
			canvas.drawRect(stroke, strokePaint);
		}
	}
}
