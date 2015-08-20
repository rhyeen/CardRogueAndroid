package com.realitypd.cardrogue;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Ryan Saunders on 11/4/2014.
 */
public class Player
{
	private int _xPosition, _yPosition, _xPrevious, _yPrevious;
	private int _maxHealth, _currentHealth, _attackRoll;
	private int _totalGold;
	private int _totalClaimedVictoryPoints;
	private ArrayList<EffectCounter> _effectCounters;
	private Inventory _inventory;
	private EquipmentSlots _equipmentSlots;
	private boolean _inCombat;


	/**
	 * Tile listener
	 */
	public interface OnStatChangedListener {
		public void onStatChanged(Player player);
	}

	OnStatChangedListener _onStatChangedListener = null;

	public void setOnStatChangedListener(OnStatChangedListener onStatChangedListener) {
		_onStatChangedListener = onStatChangedListener;
	}

	/**
	 * No-args constructor
	 */
	public Player ()
	{
		_inCombat = false;
		_maxHealth = _currentHealth = 10;
		_attackRoll = 0;
		_totalGold = 0;
		_totalClaimedVictoryPoints = 0;
		_effectCounters = new ArrayList<EffectCounter>();

		// for now, we don't care about the capacity
		_inventory = new Inventory(-1);
	}

	public Player (int maxHealth, Effect[] startingEffects)
	{
		_inCombat = false;
		_maxHealth = _currentHealth = maxHealth;
		_attackRoll = 0;
		_totalGold = 0;
		_totalClaimedVictoryPoints = 0;
		_effectCounters = new ArrayList<EffectCounter>();
		if(startingEffects != null)
			for(Effect effect : startingEffects)
				_effectCounters.add(new EffectCounter(effect));

		// for now, we don't care about the capacity
		_inventory = new Inventory(-1);
	}

	public void setStartingPosition(int startingX, int startingY)
	{
		_xPosition = _xPrevious = startingX;
		_yPosition = _yPrevious = startingY;
	}

	/**
	 * Reduce the counter of current effects and remove all that need to be removed.
	 */
	public void newTurn()
	{
		ArrayList<EffectCounter> toRemove = new ArrayList<EffectCounter>();
		for(EffectCounter effectCounter : _effectCounters)
		{
			if(effectCounter.effect.turnsEffected != -1)
			{
				effectCounter.counter--;
				if (effectCounter.counter <= 0)
					toRemove.add(effectCounter);
			}
		}
		for(EffectCounter effectCounter : toRemove)
			_effectCounters.remove(effectCounter);
	}

	public Effect[] getEffects()
	{
		return _effectCounters.toArray(new Effect[_effectCounters.size()]);
	}

	public boolean isInCombat() { return _inCombat; }
	public void setInCombat(boolean inCombat) { _inCombat = inCombat; }

	public int rollAttack()
	{
		return new Random().nextInt(6)+1+_attackRoll;
	}

	public boolean attemptFlee()
	{
		if(new Random().nextBoolean())
		{
			_inCombat = false;
			return true;
		}
		return false;
	}

	public int getxPosition()
	{
		return _xPosition;
	}

	public int getyPosition()
	{
		return _yPosition;
	}

	public int getxPrevious()
	{
		return _xPrevious;
	}

	public int getyPrevious()
	{
		return _yPrevious;
	}

	public int getMaxHealth()
	{
		return _maxHealth;
	}

	public int getCurrentHealth()
	{
		return _currentHealth;
	}

	public int getAttackRoll()
	{
		return _attackRoll;
	}

	public void changeAttackRoll(int changeAmount)
	{
		if(changeAmount + _attackRoll < 0)
			_attackRoll = 0;
		else
			_attackRoll += changeAmount;
	}

	public int getTotalGold()
	{
		return _totalGold;
	}

	public int getTotalClaimedVictoryPoints()
	{
		return _totalClaimedVictoryPoints;
	}

	/**
	 * Updates player's gold with the change amount, if gold would be negative,
	 * the gold is not modified and the method returns false.
	 * @param changeAmount
	 * @return
	 */
	public boolean changeGold(int changeAmount)
	{
		if(_totalGold + changeAmount < 0)
			return false;
		_totalGold += changeAmount;
		sendChange();
		return true;
	}

	/**
	 * Updates player's current health with the change amount, if health would be negative or 0,
	 * the health is set to zero and method returns false.
	 * @param changeAmount
	 * @return
	 */
	public boolean changeCurrentHealth(int changeAmount)
	{
		if(_currentHealth + changeAmount <= 0)
		{
			_currentHealth = 0;
			sendChange();
			return false;
		}

		_currentHealth += changeAmount;
		if(_currentHealth > _maxHealth)
			_currentHealth = _maxHealth;
		sendChange();
		return true;
	}

	private void sendChange()
	{
		if(_onStatChangedListener != null)
			_onStatChangedListener.onStatChanged(Player.this);
	}

	/**
	 * Updates player's max health with the change amount.  If the change is positive,
	 * health is added to the current health.
	 * @param changeAmount
	 * @return
	 */
	public void changeMaxHealth(int changeAmount)
	{
		_maxHealth += changeAmount;

		if(changeAmount > 0)
			changeCurrentHealth(changeAmount);
		//sendChange();
	}

	/**
	 * Updates player's vp with the change amount, vp cannot be negative.
	 * @param changeAmount
	 * @return
	 */
	public void changeVictoryPoints(int changeAmount)
	{
		if(_totalClaimedVictoryPoints + changeAmount <= 0)
			_totalClaimedVictoryPoints = 0;
		_totalClaimedVictoryPoints += changeAmount;
		sendChange();
	}


	/**
	 * Invariant: caller is responsible for correctly modifying Inventory outside of this method.
	 * @return
	 */
	public Inventory getInventory()
	{
		return _inventory;
	}

	public void setInventory(Inventory inventory) { inventory = _inventory; }

	/**
	 * Invariant: caller is responsible for correctly modifying EquipmentSlot outside of this method.
	 * @return
	 */
	public EquipmentSlots getEquipmentSlots()
	{
		return _equipmentSlots;
	}

	/**
	 * Invariant: call movePlayer on game board instead.
	 * this method
	 * @param xPosition
	 * @param yPosition
	 * @return
	 */
	public void movePlayer(int xPosition, int yPosition)
	{
		_xPrevious = _xPosition;
		_yPrevious = _yPosition;
		_xPosition = xPosition;
		_yPosition = yPosition;
	}

	public void addEffect(Effect effect)
	{
		boolean notAdded = true;
		for(EffectCounter effectCounter : _effectCounters)
			if(effectCounter.effect.getName().equals(effect.getName()))
			{
				notAdded = false;
				// if the new effect makes an old effect last longer, increase the turn counter
				if (effectCounter.counter < effect.turnsEffected)
					effectCounter.counter = effect.turnsEffected;
			}
		if(notAdded)
			_effectCounters.add(new EffectCounter(effect));
	}

	public boolean removeEffect(Effect effect)
	{
		for(int i=0; i< _effectCounters.size(); i++)
			if(_effectCounters.get(i).effect.getName().equals(effect.getName()))
			{
				_effectCounters.remove(i);
				return true;
			}
		return false;
	}
}
