package com.game;

public class Guard{
	public int x,y,w,h,attack,damage,hp,maxHP;
	public String text;
	
	public Guard(int x, int y, int attack) {
		this.x = x;
		this.y = y;
		this.attack = attack;
		this.w = 32;
		this.h= 64;
		this.damage = 0;
		maxHP = 100;
		hp = maxHP;
	}
	
	public void calcDamage() {
		damage = 20;	//will be changed later
	}
	
	public void catchPlayer() {
		text = "Guard: Hey";
	}
}
