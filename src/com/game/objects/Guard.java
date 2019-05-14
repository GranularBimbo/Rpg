package com.game.objects;

import java.util.Random;

public class Guard{
	public int x,y,w,h,attack,damage,hp,maxHP;
	public String text;
	public Random random;
	
	public Guard(int x, int y, int attack) {
		this.x = x;
		this.y = y;
		this.attack = attack;
		this.w = 32;
		this.h= 64;
		this.damage = 0;
		maxHP = 100;
		hp = maxHP;
		
		this.random = new Random();
	}
	
	public void calcDamage(int helmet, int chest, int legs) {
		damage = (((attack*(random.nextInt(3)+1))+(random.nextInt(9)))-(helmet+chest+legs));
	}
}
