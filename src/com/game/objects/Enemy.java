package com.game.objects;

public class Enemy {
	public int maxHP, hp, damage, x, y, w, h, level;
	public boolean hostile;
	
	public Enemy(int x, int y, int w, int h, int level, int maxHP) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h= h;
		this.level = level;
		this.damage = 0;
		this.maxHP = maxHP;
		this.hp = this.maxHP;
		hostile = false;
	}
	
	public void calcDamage() {
		damage = 5;
	}
}
