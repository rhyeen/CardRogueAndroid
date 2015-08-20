package com.realitypd.cardrogue;

/**
 * Created by Ryan Saunders on 11/2/2014.
 */
public class Effect
{
	private String _tooltip;
	private String _name;

	/**
	* Odds of being effected
	* Invariant, between 0.0f and 1.0f
	*/
	public float chanceOfAffection = 1.0f;
	/**
	 * How many turns effected
	 * -1 = constant effect, 0 = instant, 1+ = turns, starting next turn
	 */
	public int turnsEffected = 0;
	/**
	 * If heal +X, if damaging effect -X, else 0
	 */
	public int startTurnHealth = 0;
	/**
	 * If effected loses turn
	 */
	public boolean loseTurn = false;
	/**
	 * +/-X to attack roll, else 0
	 */
	public int attackRollModifier = 0;
	/**
	 * Multiply modify the attack roll
	 */
	public float multiplyAttack = 1.0f;
	/**
	 * +/-X to damage taken, else 0
	 */
	public int damageTakenModifier = 0;
	/**
	 * +/-X to moves while out of combat, else 0
	 */
	public int movementModifier = 0;

	/**
	 * Invariant: tooltip must be unique, but name does not need to be
	 * ex) Poison and Paralysis can be names for different length of turnsEffected
	 * @param tooltip
	 */
	public Effect(String tooltip, String name)
	{
		_tooltip = tooltip;
		_name = name;
	}

	/**
	 *
	 * @return
	 */
	public String getTooltip()
	{
		return _tooltip;
	}

	public String getName() { return _name; }
}
