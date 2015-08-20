package com.realitypd.cardrogue;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by Ryan Saunders on 11/3/2014.
 */
public class EquipmentSlots
{
	private Hashtable<String, Equipment> _equipmentSlots;

	public EquipmentSlots()
	{
		_equipmentSlots = new Hashtable<String, Equipment>();
		_equipmentSlots.put("primary hand", null);
		_equipmentSlots.put("secondary hand", null);
		_equipmentSlots.put("head", null);
		_equipmentSlots.put("chest", null);
		_equipmentSlots.put("feet", null);
	}

	public boolean equip(Equipment equipment)
	{
		if(!_equipmentSlots.containsKey(equipment.getEquipmentSlot()))
			return false;
		_equipmentSlots.put(equipment.getEquipmentSlot(), equipment);
		return true;
	}

	public boolean unequip(String equipmentSlot)
	{
		if(!_equipmentSlots.containsKey(equipmentSlot))
			return false;
		_equipmentSlots.put(equipmentSlot, null);
		return true;
	}

	public Equipment getEquipmentInSlot(String equipmentSlot)
	{
		if(!_equipmentSlots.containsKey(equipmentSlot))
			return null;
		return _equipmentSlots.get(equipmentSlot);
	}
}
