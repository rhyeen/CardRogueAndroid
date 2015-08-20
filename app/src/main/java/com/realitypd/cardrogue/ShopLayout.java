package com.realitypd.cardrogue;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Ryan Saunders on 12/8/2014.
 */
public class ShopLayout extends LinearLayout implements ListAdapter
{
	Shop _shop = null;
	Context _context = null;
	boolean _isInShop = false;
	ListView _listView = null;


	public interface OnToTheParentListener {
		public void onToTheParent(ShopLayout inventoryLayout, Item item);
	}

	OnToTheParentListener _onToTheParentListener = null;

	public void setToTheParentListener(OnToTheParentListener onToTheParentListener) {
		_onToTheParentListener = onToTheParentListener;
	}

	public ShopLayout(Context context)
	{
		super(context);
		_context = context;

		ViewGroup.LayoutParams screenParams = new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT
		);
		_listView = new ListView(context);
		_listView.setAdapter(this);

		_listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int index, long id)
			{
				if(_shop == null)
					return;

				Item item = _shop.getItem(index);

				// buy an item
				if (_onToTheParentListener != null)
						_onToTheParentListener.onToTheParent(ShopLayout.this, item);
			}
		});

		addView(_listView, screenParams);
	}

	public void setShop(Shop shop)
	{
		_shop = shop;
		invalidate();
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup)
	{
		if(_shop == null)
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

		Item item = _shop.getItem(i);

		TextView itemName = new TextView(_context);
		TextView itemDescription = new TextView(_context);
		TextView itemGoldAmount = new TextView(_context);
		TextView itemAction = new TextView(_context);

		itemName.setText(item.getName().toUpperCase());

		itemAction.setTextColor(0xffcb3d2d);
		itemGoldAmount.setTextColor(0xfff4ce43);
		itemName.setTypeface(GameFont.getInstance(_context).getTypeface());

		itemDescription.setText(item.getDescription());
		// double the sell amount
		itemGoldAmount.setText("Buy amount: " + item.getGoldAmountAtShop());
		itemAction.setText("Click on this item to buy it.");

		vLayout.addView(itemName, viewParams);
		vLayout.addView(itemDescription, viewParams);

		vLayout2.addView(itemGoldAmount, viewParams);
		vLayout2.addView(itemAction, viewParams);

		return hLayout;
	}

	@Override
	public int getCount()
	{
		if(_shop == null)
			return 0;
		return _shop.getItemCount();
	}

	@Override
	public boolean isEmpty()
	{
		return getCount() <= 0;
	}

	@Override
	public Object getItem(int i)
	{
		if(_shop == null)
			return null;
		return _shop.getItem(i);
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
