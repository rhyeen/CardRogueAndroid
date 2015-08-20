package com.realitypd.cardrogue;

import android.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Ryan Saunders on 12/6/2014.
 */
public class TitleScreenFragment extends Fragment
{

	public interface OnGameModeSelectedListener {
		public void onGameModeSelected(TitleScreenFragment titleScreenFragment, boolean newGameSelected);
	}

	OnGameModeSelectedListener _onGameModeSelectedListener = null;

	public void setOnGameModeSelectedListener(OnGameModeSelectedListener onGameModeSelectedListener) {
		_onGameModeSelectedListener = onGameModeSelectedListener;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		LinearLayout mainLayout = new LinearLayout(getActivity());
		LinearLayout.LayoutParams mainLayoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT
		);
		mainLayout.setOrientation(LinearLayout.VERTICAL);
		mainLayout.setBackgroundColor(0xff332925);

		View fillerView = new View(getActivity());
		View fillerView2 = new View(getActivity());

		TextView title = new TextView(getActivity());
		TextView version = new TextView(getActivity());

		LinearLayout.LayoutParams titleLayoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				0,
				7
		);

		LinearLayout.LayoutParams fillerLayoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				0,
				15
		);

		title.setText("CARD ROGUE");
		version.setText("v. 0.1");
		title.setTypeface(GameFont.getInstance(getActivity()).getTypeface());
		version.setTypeface(GameFont.getInstance(getActivity()).getTypeface());
		title.setTextSize(24.0f);
		version.setTextSize(12.0f);
		title.setGravity(Gravity.CENTER);
		version.setGravity(Gravity.CENTER);
		title.setTextColor(0xffcb3d2d);
		version.setTextColor(0xffd6d6d6);

		mainLayout.addView(fillerView, titleLayoutParams);
		mainLayout.addView(title, titleLayoutParams);
		mainLayout.addView(version, titleLayoutParams);
		mainLayout.addView(fillerView2, fillerLayoutParams);


		Button newGame = new Button(getActivity());
		Button continueGame = new Button(getActivity());

		LinearLayout.LayoutParams gameModeLayoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				0,
				20
		);

		newGame.setBackgroundResource(0);
		continueGame.setBackgroundResource(0);
		newGame.setText("NEW GAME");
		continueGame.setText("CONTINUE");
		newGame.setTypeface(GameFont.getInstance(getActivity()).getTypeface());
		continueGame.setTypeface(GameFont.getInstance(getActivity()).getTypeface());
		newGame.setTextSize(24.0f);
		continueGame.setTextSize(24.0f);
		newGame.setGravity(Gravity.CENTER);
		continueGame.setGravity(Gravity.CENTER);
		newGame.setTextColor(0xffd6d6d6);
		continueGame.setTextColor(0xffcb3d2d);
		continueGame.setPadding(0, 0, 0, 0);

		mainLayout.addView(newGame, gameModeLayoutParams);
		mainLayout.addView(continueGame, gameModeLayoutParams);

		newGame.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				if(_onGameModeSelectedListener != null)
					_onGameModeSelectedListener.onGameModeSelected(TitleScreenFragment.this, true);
			}
		});

		continueGame.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				if(_onGameModeSelectedListener != null)
					_onGameModeSelectedListener.onGameModeSelected(TitleScreenFragment.this, false);
			}
		});

		ImageView titleImage = new ImageView(getActivity());

		LinearLayout.LayoutParams titleImageLayoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				0,
				45
		);

		titleImage.setImageResource(R.drawable.title_ladder);

		mainLayout.addView(titleImage, titleImageLayoutParams);

		return mainLayout;
	}
}
