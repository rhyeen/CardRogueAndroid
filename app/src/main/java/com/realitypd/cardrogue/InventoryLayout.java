package com.realitypd.cardrogue;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Ryan Saunders on 12/8/2014.
 */
public class InventoryLayout extends LinearLayout implements ListAdapter
{
	ListView _listView = null;
	Player _player = null;
	Context _context = null;
	boolean _isInShop = false;

	public interface OnToTheParentListener {
		public void onToTheParent(InventoryLayout inventoryLayout, boolean isInShop, Item item);
	}

	OnToTheParentListener _onToTheParentListener = null;

	public void setToTheParentListener(OnToTheParentListener onToTheParentListener) {
		_onToTheParentListener = onToTheParentListener;
	}

	public void refreshScreen()
	{
		if (_listView != null)
		{
			// doesn't work.  Doesn't work if we make a ListAdapter either.
			// maybe needs to be an ArrayAdapter
			//((ArrayAdapter) _listView.getAdapter()).notifyDataSetChanged();
		}
	}

	public InventoryLayout(Context context)
	{
		super(context);
		_context = context;
		ViewGroup.LayoutParams screenParams = new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT
		);
		_listView = new ListView(context);
		_listView.setAdapter(this);
		addView(_listView, screenParams);

		_listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int index, long id)
			{
				if(_player == null)
					return;

				Item item = _player.getInventory().getItem(index);

				// use an item
				if(item.isThrowable() && !_isInShop)
				{
					if (_onToTheParentListener != null)
						_onToTheParentListener.onToTheParent(InventoryLayout.this, _isInShop, item);
				}
				// sell an item
				else if(_isInShop && item.getGoldAmount() > 0)
				{
					if (_onToTheParentListener != null)
						_onToTheParentListener.onToTheParent(InventoryLayout.this, _isInShop, item);
				}
			}
		});
	}

	public void setPlayer(Player player)
	{
		_player = player;
		invalidate();
	}

	public void setInShop(boolean inShop)
	{
		_isInShop = inShop;
		invalidate();
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup)
	{
		if(_player == null)
			return null;

		LinearLayout hLayout = new LinearLayout(_context);
		hLayout.setOrientation(LinearLayout.HORIZONTAL);

		LinearLayout vLayout = new LinearLayout(_context);
		LinearLayout vLayout2 = new LinearLayout(_context);

		vLayout.setOrientation(VERTICAL);
		vLayout2.setOrientation(VERTICAL);
		LayoutParams viewParams = new LayoutParams(LayoutParams.MATCH_PARENT, 0, 1);

		LayoutParams vLayoutParams = new LayoutParams(0, LayoutParams.MATCH_PARENT, 1);

		hLayout.addView(vLayout, vLayoutParams);
		hLayout.addView(vLayout2, vLayoutParams);

		Item item = _player.getInventory().getItem(i);

		TextView itemName = new TextView(_context);
		TextView itemDescription = new TextView(_context);
		TextView itemGoldAmount = new TextView(_context);
		TextView itemAction = new TextView(_context);

		itemAction.setTextColor(0xffcb3d2d);
		itemGoldAmount.setTextColor(0xfff4ce43);
		itemName.setTypeface(GameFont.getInstance(_context).getTypeface());


		if(item instanceof Enemy)
		{
			itemName.setText(("loot from\n" + item.getName()).toUpperCase());
		}
		else
		{
			if(!_isInShop)
				itemAction.setText("Click on this item to use it.");
			itemName.setText(item.getName().toUpperCase());
		}

		itemDescription.setText(item.getDescription());
		if(item.getGoldAmount() > 0)
			itemGoldAmount.setText("Sell amount: " + item.getGoldAmount());
		else
			itemGoldAmount.setText("Cannot sell this item.");

		if(_isInShop && item.getGoldAmount() > 0)
			itemAction.setText("Click on this item to sell it.");


		vLayout.addView(itemName, viewParams);
		vLayout.addView(itemDescription, viewParams);

		vLayout2.addView(itemGoldAmount, viewParams);
		vLayout2.addView(itemAction, viewParams);

		return hLayout;
	}

	@Override
	public int getCount()
	{
		if(_player == null)
			return 0;
		return _player.getInventory().getNumberOfItems();
	}

	@Override
	public boolean isEmpty()
	{
		return getCount() <= 0;
	}

	@Override
	public Object getItem(int i)
	{
		if(_player == null)
			return null;
		return _player.getInventory().getItem(i);
	}

	@Override
	public long getItemId(int i)
	{
		return i;
	}

	@Override
	public boolean hasStableIds()
	{
		return true;
	}


	@Override
	public int getItemViewType(int i)
	{
		return 0;
	}

	@Override
	public int getViewTypeCount()
	{
		return 1;
	}

	@Override
	public boolean areAllItemsEnabled()
	{
		return true;
	}

	@Override
	public boolean isEnabled(int i)
	{
		return true;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver dataSetObserver)
	{

	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver dataSetObserver)
	{

	}
}
