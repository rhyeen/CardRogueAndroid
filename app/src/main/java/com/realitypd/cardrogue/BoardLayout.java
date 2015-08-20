package com.realitypd.cardrogue;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by Ryan Saunders on 11/5/2014.
 */
public class BoardLayout extends LinearLayout
{
	TileView[] _tileViews;
	int TILE_PADDING = 10;
	/**
	 * Tile listener
	 */
	public interface OnTileSelectedListener {
		public void onTileSelected(BoardLayout boardLayout, int tileAdditionalX, int tileAdditionalY, int tileIndex);
	}

	OnTileSelectedListener _onTileSelectedListener = null;

	public void setOnTileSelectedListener(OnTileSelectedListener onTileSelectedListener) {
		_onTileSelectedListener = onTileSelectedListener;
	}

	public BoardLayout(Context context)
	{
		super(context);

		_tileViews = new TileView[9];

		// want to have matching padding to tiles so that outer padding matches inner padding
		setPadding(TILE_PADDING, TILE_PADDING, TILE_PADDING, TILE_PADDING);
		setOrientation(VERTICAL);

		LayoutParams vLayoutParams = new LayoutParams(
				LayoutParams.MATCH_PARENT, 0, 1
		);

		LayoutParams tileParams = new LayoutParams(
				0, LayoutParams.MATCH_PARENT, 1
		);

		for(int x = 0; x < 3; x++)
		{
			LinearLayout vLayout = new LinearLayout(context);
			vLayout.setOrientation(HORIZONTAL);
			for (int y = 0; y < 3; y++)
			{
				TileView tileView = new TileView(context);
				tileView.setPadding(TILE_PADDING, TILE_PADDING, TILE_PADDING, TILE_PADDING);
				vLayout.addView(tileView, tileParams);
				_tileViews[x*3 + y] = tileView;
			}
			addView(vLayout, vLayoutParams);
		}

		//// click listeners for tiles that are adjacent to center tile
		// top
		_tileViews[1].setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				if(_onTileSelectedListener != null)
					_onTileSelectedListener.onTileSelected(BoardLayout.this, 0, -1, 1);
			}
		});

		// left
		_tileViews[3].setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				if (_onTileSelectedListener != null)
					_onTileSelectedListener.onTileSelected(BoardLayout.this, -1, 0, 3);
			}
		});

		// right
		_tileViews[5].setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				if (_onTileSelectedListener != null)
					_onTileSelectedListener.onTileSelected(BoardLayout.this, 1, 0, 5);
			}
		});

		// bottom
		_tileViews[7].setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				if(_onTileSelectedListener != null)
					_onTileSelectedListener.onTileSelected(BoardLayout.this, 0, 1, 7);
			}
		});

		_tileViews[4].putCenterToken();
	}

	public void updateTile(int tileNumber, int imageId)
	{
		if(tileNumber < 0 || tileNumber > 8)
			return;

		// put token on center tile
		if(tileNumber == 4)
		{
			_tileViews[4].setImageResource(R.drawable.token);
			_tileViews[4].setBackgroundResource(imageId);
			return;
		}

		if(imageId == -1)
		{
			//_tileViews[tileNumber].setBackgroundColor(Color.RED);

			_tileViews[tileNumber].setImageResource(R.drawable.blank);
			_tileViews[tileNumber].setBackgroundColor(Color.TRANSPARENT);
		}
		else if(imageId == R.drawable.off_board)
		{
			_tileViews[tileNumber].setImageResource(R.drawable.blank);
			_tileViews[tileNumber].setBackgroundColor(Color.BLACK);
		}
		else
		{
			_tileViews[tileNumber].setImageResource(imageId);
			_tileViews[tileNumber].setBackgroundColor(Color.TRANSPARENT);
		}

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		// keep it square
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);

		int shorterSpec = width < height ? widthMeasureSpec : heightMeasureSpec;

		super.onMeasure(shorterSpec, shorterSpec);
	}
}
