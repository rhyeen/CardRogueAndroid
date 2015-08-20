package com.realitypd.cardrogue;

/**
 * Created by Ryan Saunders on 11/3/2014.
 */
public class Enemy extends Item
{
	private int _attackAmount, _maxHealth;
	private int _currentHealth;
	private int _victoryPoints;

	/**
	 * For Gson
	 * @param cardObject
	 */
	public Enemy(CardObject cardObject)
	{
		super(cardObject._imageId, cardObject._name, cardObject._description, null, cardObject._goldAmount, cardObject._isThrowable, cardObject._isSellable, cardObject._isTradeable, cardObject._isCursed);

		_attackAmount = cardObject._attackAmount;
		_maxHealth = cardObject._maxHealth;
		_currentHealth = cardObject._currentHealth;
		_victoryPoints = cardObject._victoryPoints;
	}


	/**
	 * No-args constructor
	 */
	public Enemy()
	{
		super(0, "", "", null, 0, false, false, false, false);
		setType("Enemy");
		_attackAmount = 0;
		_maxHealth = 0;
		_currentHealth = _maxHealth;
		_victoryPoints = 0;
	}

	public Enemy(int imageId, String name, String[] effectKeys, int goldAmount, int attackAmount, int maximumHealth)
	{
		super(imageId, name, "You can sell this item in the shop for gold.", effectKeys, goldAmount, false, true, true, false);
		setType("Enemy");

		_attackAmount = attackAmount;
		_maxHealth = maximumHealth;
		_currentHealth = _maxHealth;
		//_effects = EffectLibrary.getInstance().getEffects(effectKeys);
		_victoryPoints = 0;
	}

	/**
	 * For boss enemy
	 */
	public Enemy(int victoryPoints, int imageId, String name, String[] effectKeys, int attackAmount, int maximumHealth)
	{
		super(imageId, name, "A token of your victory! Bring it home for fame and glory.", effectKeys, 0, false, false, true, false);
		_attackAmount = attackAmount;
		_maxHealth = maximumHealth;
		_currentHealth = _maxHealth;
		//_effects = EffectLibrary.getInstance().getEffects(effectKeys);
		_victoryPoints = victoryPoints;
	}

	public void resetHealth()
	{
		_currentHealth = _maxHealth;
	}

	public int changeHealth(int changeToHealth)
	{
		if(changeToHealth + _currentHealth > _maxHealth)
			return _currentHealth = _maxHealth;
		return _currentHealth += changeToHealth;
	}

	public boolean isDead() { return _currentHealth <= 0; }

	public boolean isSellable() { return _victoryPoints != 0; }

	public int getAttackAmount()
	{
		return _attackAmount;
	}

	public int getMaxHealth()
	{
		return _maxHealth;
	}

	public int getVictoryPoints()
	{
		return _victoryPoints;
	}

	public int getCurrentHealth()
	{
		return _currentHealth;
	}
}
