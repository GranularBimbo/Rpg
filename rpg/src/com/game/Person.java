package com.game;

import java.util.Random;

public class Person {
	public int x, y, w, h, townLocationPicker;	//make more location pickers for each town so 										
	public String location;						//they can go to different shops
	Random random;
	
	public Person(int x, int y, int width, int height, String locationRange) {
		this.x = x;
		this.y = y;
		this.w = width;
		this.h = height;
		townLocationPicker = -1;
		random = new Random();
		
		setLocation(locationRange);
	}
	
	//sets the character's location inside of a town (inside a building or outside)
	public void setLocation(String locationRange) {
		if(locationRange == "town") {
			townLocationPicker = random.nextInt(3);
		}
					
		if(townLocationPicker == 0) {
			location = "town";
		}
		else {
			if(townLocationPicker == 1) {
				location = "armorer";
			}
			else {
				if(townLocationPicker == 2) {
					location = "blacksmith";
				}
			}
		}
	}
}
