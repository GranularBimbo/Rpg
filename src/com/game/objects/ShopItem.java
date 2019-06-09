package com.game.objects;

public class ShopItem {
	public int restockTimer, x, y, w, h, price;
	public String name;
	
	public ShopItem(int x, int y, int width, int height, int price,String name){
		this.restockTimer = 0;
		this.x = x;
		this.y = y;
		this.w = width;
		this.h= height;
		this.price = price;
		this.name = name;
	}
}
