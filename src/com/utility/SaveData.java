package com.utility;

public class SaveData implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	public int hp,maxHP,str,luck,Int,sneak,xp,maxXP,level,gold,speech,mana,maxMana,manaGain,manaRegenTimer,chopped;
	public int fine;
	public String race,Class,helmet,chest,legs,weapon,location;
	public boolean weirdTreeDone,wanted,hostile;
	public String[] inventory;
	public String[] spells;
	public boolean[] quests;
	public double xpChange;
	
	public SaveData() {
		hp = 50;
		maxHP = 50;
		str = 10;
		luck = 10;
		Int = 10;
		sneak = 10;
		xp = 0;
		maxXP = 20;
		level = 1;
		gold = 15;
		speech = 10;
		xpChange = 50;
		maxMana = 10;
		manaGain = 2;
		chopped = 0;
		fine = 0;
		mana = maxMana;
		race = "orc";
		Class = "warrior";
		manaRegenTimer = -3;
		helmet = "";
		chest = "";
		legs = "";
		weapon = "";
		location = "";
		weirdTreeDone = true;
		wanted = false;
		hostile = false;
		
		inventory = new String[10];
		spells = new String[10];
		quests = new boolean[1];
	}
	
	public void updateData(int hp,int maxHP,int str,int luck,int Int,int sneak,int xp,int maxXP,int level,int gold,int speech,int mana,int maxMana,int manaGain,double xpChange,String race,String Class,String helmet,String chest,String legs,String[] inventory,String[] spells,int manaRegenTimer,String playerWeapon,boolean[] quests,boolean weirdTree,String location,int chopped,boolean wanted,boolean hostile,int fine) {
		this.hp = hp;
		this.maxHP = maxHP;
		this.str = str;
		this.luck = luck;
		this.Int = Int;
		this.sneak = sneak;
		this.xp = xp;
		this.maxXP = maxXP;
		this.level = level;
		this.gold = gold;
		this.speech = speech;
		this.xpChange = xpChange;
		this.maxMana = maxMana;
		this.manaGain = manaGain;
		this.mana = mana;
		this.race = race;
		this.Class = Class;
		this.helmet = helmet;
		this.chest = chest;
		this.legs = legs;
		this.inventory = inventory;
		this.spells = spells;
		this.manaRegenTimer = manaRegenTimer;
		weapon = playerWeapon;
		this.quests = quests;
		weirdTreeDone = weirdTree;
		this.location = location;
		this.chopped = chopped;
		this.wanted = wanted;
		this.hostile = hostile;
		this.fine = fine;
	}
}
