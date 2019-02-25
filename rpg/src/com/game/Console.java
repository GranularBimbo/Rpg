package com.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Console {	//this will control the text based part of the game
	public String text;
	public int x, y;
	
	public Console(int x,int y) {
		this.x = x;
		this.y = y;
	}
	
	public void showText(String txt,Graphics g) {
		this.text = txt;
		g.setColor(Color.black);
		g.setFont(new Font("Times New Roman",Font.BOLD,24));
		
		g.drawString(this.text, this.x, this.y);
	}
}
