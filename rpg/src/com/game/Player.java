package com.game;

public class Player {
	public int hp,maxHP,str,luck,Int,sneak,xp,maxXP,level,gold,speech;
	public String race,Class,helmet;
	
	public Player() {
		hp = 50;
		maxHP = 50;
		str = 10;
		luck = 10;
		Int = 10;
		sneak = 10;
		xp = 0;
		maxXP = 20;
		level = 1;
		gold = 0;
		speech = 10;
		race = "orc";
		Class = "warrior";
		helmet = "";	//this will decide which kind of helmet you have, so you can wear any kind
	}
	
	public void levelUp() {
		if(xp >= maxXP) {
			level++;
			gold += 30;
			hp = maxHP;
			xp = 0;
			maxXP += 50;
		}
	}
}
