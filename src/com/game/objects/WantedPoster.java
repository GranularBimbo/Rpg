package com.game.objects;

import java.util.Random;

import com.managers.ImageManager;

public class WantedPoster {
	public String name,title,location,race;
	public int maxHP,attack,gold,xp,difficulty,oldHP,oldAttack;
	public String[] namePool,titlePool,locations;
	public ImageManager helmet,chest,legging;
	public Enemy enemy;
	Random random;
	
	//difficulty from 1 to 5
	public WantedPoster(int playerLevel) {
		random = new Random();
		int raceGenerator,helmet,chest,legging,loc;
		
		enemy = new Enemy(0,0,0,0,32,64,"","","",null,null,null);
		
		locations = new String[] {
				"camp","woods7","woods6","woods5","woods3","woods2"
		};
		
		namePool = new String[] {
				"Grubosh","Groble","Nate","Gaïrlan","Smelgish","Joe","Jenine","Anthony","Liam","Mason","Jarlog",
				"Putesk","Michael","Harper","Tablesh"
		};
		
		titlePool = new String[] {
				"the Gross","the Odio","the Smelly","Smith","Miller","the Rude","Williams","Jones","the Rancid",
				"the Poop Eater","Davis","Brown","the Foot Washer","Reed","the Hamburger"
		};
		
		name = namePool[random.nextInt(namePool.length)];
		title = titlePool[random.nextInt(titlePool.length)];
		
		raceGenerator = random.nextInt(2);
		helmet = random.nextInt(enemy.helmets.length);
		chest = random.nextInt(enemy.chests.length);
		legging = random.nextInt(enemy.leggings.length);
		loc = random.nextInt(locations.length);
		
		difficulty = random.nextInt(4)+1;
		
		gold = difficulty*10;
		xp = difficulty*8;
		
		this.helmet = enemy.helmets[helmet];
		this.chest = enemy.chests[chest];
		this.legging = enemy.leggings[legging];
		location = locations[loc];
		
		if(raceGenerator == 0) {
			race = "orc";
		}
		else {
			race = "human";
		}
		
		
		if(playerLevel > 1) {
			maxHP = (int) Math.floor((oldHP+(Math.abs(oldHP-((difficulty*30)/(playerLevel*playerLevel))))));
			attack = (int) Math.floor((oldAttack+(Math.abs(oldAttack-((difficulty*9)/(playerLevel*playerLevel))))));
		}
		else {
			maxHP = ((difficulty*30)/(playerLevel*playerLevel));
			attack = ((difficulty*9)/(playerLevel*playerLevel));
			
			oldHP = maxHP;
			oldAttack = attack;
		}
		
		
		enemy = new Enemy(32*20,32*10,((difficulty*30)/(playerLevel*playerLevel)),attack,32,64,"" + this.name + " " + this.title,race,location,this.helmet,this.chest,this.legging);
		
		if(enemy.race.equals("orc")) {
			for(int i = 0; i < enemy.orcBody.length; i++) {
				enemy.character[i] = enemy.orcBody[i];
			}
		}
		else {
			for(int c = 0; c < enemy.humanBody.length; c++) {
				enemy.character[c] = enemy.humanBody[c];
			}
		}
	}
}
