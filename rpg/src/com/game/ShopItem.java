package com.game;

public class ShopItem {
	int restockTimer, x, y, w, h;
	
	public ShopItem(int x, int y, int width, int height){
		this.restockTimer = 0;
		this.x = x;
		this.y = y;
		this.w = width;
		this.h= height;
	}
}
