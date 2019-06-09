package com.game.objects;

import java.util.Random;

import com.managers.ImageManager;

public class Enemy{
	public int x,y,w,h,maxHP,hp,attack,damage;
	public String location,race,name;
	public boolean hostile;
	public ImageManager orcHead,orcTorso,orcLegs,humanHead,humanTorso,humanLegs;
	public ImageManager leatherHelm,hoodHelm,thiefHelm,leatherChest,thiefChest,robeChest,steelHelm,steelChest;
	public ImageManager steelLeggings,helmet,chest,legging;
	public ImageManager[] character,orcBody,humanBody,helmets,chests,leggings; 
	Random random;
	
	public Enemy() {
		
	}
	
	public Enemy(int x,int y,int maxHP,int attack,int width,int height,String name,String race,String location,ImageManager helmet,ImageManager chest,ImageManager legging) {
		this.x = x;
		this.y = y;
		this.maxHP = maxHP;
		this.hp = maxHP;
		this.attack = attack;
		this.race = race;
		this.location = location;
		this.helmet = helmet;
		this.legging = legging;
		this.chest = chest;
		this.name = name;
		w = width;
		h = height;
		damage = 0;
		random = new Random();
		
		hostile = false;
		
		orcBody = new ImageManager[] {
				orcHead = new ImageManager("assets/img/heads/orcHead.png"),
				orcTorso = new ImageManager("assets/img/torsos/orcBody.png"),
				orcLegs = new ImageManager("assets/img/legs/orcLegs.png")
		};
		
		humanBody = new ImageManager[] {
				humanHead = new ImageManager("assets/img/heads/humanHead.png"),
				humanTorso = new ImageManager("assets/img/torsos/humanBody.png"),
				humanLegs = new ImageManager("assets/img/legs/humanLegs.png")
		};
		
		helmets = new ImageManager[] {
				leatherHelm = new ImageManager("assets/img/armor/helmets/leather.png"),
				hoodHelm = new ImageManager("assets/img/armor/helmets/hood.png"),
				thiefHelm = new ImageManager("assets/img/armor/helmets/thief.png"),
				steelHelm = new ImageManager("assets/img/armor/helmets/steel.png")
		};
		
		chests = new ImageManager[] {
				leatherChest = new ImageManager("assets/img/armor/chestplates/leather.png"),
				thiefChest = new ImageManager("assets/img/armor/chestplates/thief.png"),
				robeChest = new ImageManager("assets/img/armor/chestplates/robe.png"),
				steelChest= new ImageManager("assets/img/armor/chestplates/steel.png")
		};
		
		leggings = new ImageManager[] {
				steelLeggings = new ImageManager("assets/img/armor/leggings/steel.png")	
		};
		
		character = new ImageManager[3];
	}
	
	public void calcDamage(int helmet, int chest, int legs,int luck) {
		damage = ((((attack*(random.nextInt(3)+1))+(random.nextInt(9)))/((helmet+chest+legs)+1))*((random.nextInt(luck)+1)+10));
	}
}

