package com.utility;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.managers.MouseManager;

public class Building{
	public int x,y,w,h;
	public String name;
	
	public Building(int x,int y,String name) {
		this.x = x;
		this.y = y;
		w = (32*7);
		h = 32*4;
		this.name = name;
	}
	
	public void showDesc(Graphics g,MouseManager mouse,int topX,int bottomX) {
		g.setFont(new Font("Times New Roman",Font.BOLD,30));
		
		g.setColor(Color.black);
		g.drawRect(mouse.mousex + 20, mouse.mousey + 50, 300, 100);
		
		g.setColor(Color.gray.darker());
		g.fillRect(mouse.mousex + 21, mouse.mousey + 51, 299, 99);
		
		g.setColor(Color.black);
		g.drawString(name, mouse.mousex + topX, mouse.mousey + 110);
		
		g.setFont(new Font("Times New Roman",Font.BOLD,16));
		g.drawString("click to enter", mouse.mousex + bottomX, mouse.mousey + 130);
	}
	
	public void showDeskAlt(Graphics g,MouseManager mouse,int topX,int bottomX) {
		g.setFont(new Font("Times New Roman",Font.BOLD,30));
		
		g.setColor(Color.gray.darker());
		g.fillRect(mouse.mousex - 300, mouse.mousey + 50, 300, 100);
		
		g.setColor(Color.black);
		g.drawRect(mouse.mousex - 300, mouse.mousey + 50, 300, 100);
		
		g.setColor(Color.black);
		g.drawString(name, mouse.mousex - topX, mouse.mousey + 110);
		
		g.setFont(new Font("Times New Roman",Font.BOLD,16));
		g.drawString("click to enter", mouse.mousex - bottomX, mouse.mousey + 130);
	}
}
