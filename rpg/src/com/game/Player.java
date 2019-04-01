package com.game;

public class Player {
	public int hp,maxHP,str,luck,Int,sneak,xp,maxXP,level,gold,speech;
	public double xpChange;
	public String race,Class,helmet,chest,legs;	//helmet chest and legs are for what kind of armor you are wearing
	public String[] inventory;
	public String[] spells;
	
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
		xpChange = 50;
		race = "orc";
		Class = "warrior";
		helmet = "";	//this will decide which kind of helmet you have, so you can wear any kind
		
		inventory = new String[10];
		spells = new String[10];
	}
	
	//adds an item to your inventory
	public void addToInv(String item) {
		for(int i = 0; i < inventory.length; i++) {	//increases i by 1 while i isn't the length of the inventory
			if(inventory[i] == null) {	//adds item to that slot if the slot is empty
				inventory[i] = item;
				break;
			}
		}
	}
	
	//adds a spell to your spells list
	public void addToSpells(String spell) {
		for(int i = 0; i < spells.length; i++) {
			if(spells[i] == null) {
				spells[i] = spell;
				break;
			}
		}
	}
	
	//levels up the character
	public void levelUp() {
		if(xp >= maxXP) {
			if(xp > maxXP) {
				xp = (xp - maxXP);
			}
			else {
				xp = 0;
			}
			
			level++;
			gold += 30;
			maxHP += 10;
			hp = maxHP;
			maxXP += xpChange;
			
			xpChange = Math.floor(Math.pow(2, this.level));
		}
	}
}
