package com.realitypd.cardrogue;

/**
 * Created by Ryan Saunders on 11/3/2014.
 */
public class Equipment extends Item implements Card
{
	private String _equipmentSlot;

	/**
	 * For Gson
	 * @param cardObject
	 */
	public Equipment(CardObject cardObject)
	{
		super(cardObject._imageId, cardObject._name, cardObject._description, null, cardObject._goldAmount, cardObject._isThrowable, cardObject._isSellable, cardObject._isTradeable, cardObject._isCursed);

		_equipmentSlot = cardObject._equipmentSlot;
	}

	public Equipment()
	{
		super(0, "", "", null, 0, false, true, true, false);
		setType("Equipment");

		_equipmentSlot = "";
	}

	public Equipment(int imageId, String name, String description, String[] effectKeys, int goldAmount, String equipmentSlot)
	{
		super(imageId, name, description, effectKeys, goldAmount, false, true, true, false);
		setType("Equipment");

		_equipmentSlot = equipmentSlot;
	}

	public String getEquipmentSlot()
	{
		return _equipmentSlot;
	}
}
