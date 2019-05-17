package com.game;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.artificial_neural_network.*;

import com.managers.*;
import com.prediction_ai.Predictor;
import com.utility.Building;
import com.utility.Console;
import com.utility.Dialogue;
import com.utility.Journal;
import com.utility.Quest;
import com.game.displays.*;
import com.game.objects.Enemy;
import com.game.objects.Guard;
import com.game.objects.Person;
import com.game.objects.Player;
import com.game.objects.ShopItem;

import javax.swing.Timer;

import com.window.Display;

public class Game implements ActionListener {
//caughtChecker will see if caught is under 10 for a certain amount of time(since it still catches you while above 9)
	public int WIDTH, HEIGHT, caught, caughtChecker, pressDelay, skillPoints, sneakBonus, intBonus, strBonus, speechBonus, luckBonus, hpBonus;
	public boolean running, playerMenuVisible, wanted, hostile;
	public KeyManager keymanager = new KeyManager();
	public MouseManager mousemanager = new MouseManager();
	public Graphics g;
	public String playerMenuState;
	public int dialogueSlide, weaponDamage, damage, woodsMonsterChance;
	public String swapHelm;
	public String turn;	//who's turn it is to attack
	public boolean buyMenuVisible, launchingFireball, inventoryVisible, statsVisible;
	public boolean talkingToClerk, talkingToGuard, talkingToCitizen, selectingTarget;
	public int guardRespawn, fireballX, fireballY;
	public Guard target;
	public Enemy enemyTarget;
	public Journal questJournal;
	public Predictor predictor;
	public int[] surroundingLocations;
	public int healthWidth,manaWidth,manaBonus;
	public int chopped;
	
	public int averageDamage, timesYouDeltDamage, lastDamageDelt, helm, ch, pa;
	
	//public Ann ann;
	
	public int clerkx, clerky, clerkw, clerkh;
	
	public int menuBox_x, menuBox_y, menuBox_w, menuBox_h;
	
	//enemies
	public Enemy chicken;
	
	//quests
	Quest weirdTree;
	
	//buildings
	public Building blackSmith,Armorer,Castle,blackSmith2,Armorer2,Castle2,magicShop;

	//shop items
	public ShopItem leather_chest,thief_hood,thief_robe,mage_hood,mage_robe,woodSword,SteelSword,Dagger,Shank;
	public ShopItem Wand,Staff,steel_chest,steel_helm,steel_leggings,Dagger2,SteelSword2;
	
	//people
	public Person armorerClerk,jim;
	
	Random random;	//creates the random game object
	
	String playerWeapon;
	String holdingSpot;
	
	//dialogues
	Dialogue caughtDialogue;
	Dialogue armorerDialogue;
	Dialogue citizenDialogue;
	
	//Guards
	//Town1 guards
	public Guard guard;
	
	//weapons
	public ImageManager woodenSword,steelSword,wand,staff,shank,dagger,woodenSword_I,steelSword_I,wand_I;
	public ImageManager staff_I,shank_I,dagger_I;
	
	//enemy images
	public ImageManager chicken_i;
	
	//inventory slot position
	public int slot1x, slot1y, slot2x, slot2y, slot3x, slot3y, slot4x, slot4y, slot5x, slot5y, slot6x, slot6y;
	public int slot7x, slot7y, slot8x, slot8y, slot9x, slot9y, slot10x, slot10y;
	
	//locations
	public ImageManager town,town2,armorer,blacksmith,castle,woods,woods2,woods3,woods4,woods5,woods6,woods7;
	public ImageManager camp,chop,woodsNormal;
	
	//spells
	public ImageManager fire_ball;
	
	//people images
	public ImageManager armorClerk, guardImage;
	
	//body part images
	public ImageManager head,torso,legs,toolbar_orc,toolbar_human,toolbar_leather,toolbar_thief,toolbar_hood;
	public ImageManager toolbar_steel;
	
	//inventory items (_I will be for inventory items)
	public ImageManager leatherChest_I,mageRobe_I,thiefRobe_I,steelChest_I,steelLeggings_I;	
	
	//armor images
	public int armorx, armory, armorw, armorh, armorRestock;
	public boolean armorVisible;
	
	//helmets
	public ImageManager hoodHelm,leatherHelm,thiefHelm,steelHelm;
	
	//chest plates
	public ImageManager leatherChest,thiefChest,robeChest,steelChest;
	
	//leggings
	public ImageManager steelLeggings;
	
	
	//button images
	public ImageManager newGame,newGameActive,left,leftActive,right,rightActive,done,doneActive,ok,okActive;
	public ImageManager southButton,southButtonActive,northButton,northButtonActive,compass,backpack;
	public ImageManager magic,diary,diaryActive,backpackActive,magicActive,character,characterActive,no;
	public ImageManager noActive;
	
	//UI
	public ImageManager bigOrcHead,bigHumanHead,mediumOrc,mediumHuman,bigLeather,bigHood,bigThief,mediumLeather;
	public ImageManager mediumHood,mediumThief,cursor,journal;
	
	//buttons
	public Button newGameButton,raceLeft,raceRight,hpLeft,hpRight,strLeft,strRight,luckLeft,luckRight,IntLeft;
	public Button IntRight,sneakLeft,sneakRight,speechLeft,speechRight,classLeft,classRight,doneButton;
	public Button playerMenuLeft,playerMenuRight,okButton,south,north,west,east,Diary,Inventory,stats,noButton;
	
	//player, window, and game state
	Player player;
	public String state;
	Display display;
	Console console;
	Console goldDisplay;
	public String location;
	public boolean creatingCharacter;
	
	public Game(int w, int h) {
		display = new Display(w,h);
		Timer timer = new Timer(20, this);	//idk how this timer works i found it in a youtube video lol
		player = new Player();
		cursor = new ImageManager("assets/img/cursor.png");
		dialogueSlide = 0;
		
		averageDamage = 0;
		timesYouDeltDamage = 0;
		lastDamageDelt = 0;
		
		inventoryVisible = true;
		
		
		//ann = new Ann();
		//ann.adjustLocationConnections(false, 0, 0, ann.loc1Connections);
		
		fireballX = 32*16;
		fireballY = 32*10;
		
		//spells
		fire_ball = new ImageManager("assets/img/spells/fireball.png");
		
		//enemies
		chicken_i = new ImageManager("assets/img/enemies/chicken.png");
		
		//people
		armorClerk = new ImageManager("assets/img/people/armorClerk.png");
		guardImage = new ImageManager("assets/img/people/guard.png");
		
		//weapons
		woodenSword = new ImageManager("assets/img/weapons/woodenSword.png");
		steelSword = new ImageManager("assets/img/weapons/steelSword.png");
		wand = new ImageManager("assets/img/weapons/wand.png");
		staff = new ImageManager("assets/img/weapons/staff.png");
		shank = new ImageManager("assets/img/weapons/shank.png");
		dagger = new ImageManager("assets/img/weapons/dagger.png");
		woodenSword_I = new ImageManager("assets/img/inventory/weapons/woodenSword.png");
		steelSword_I = new ImageManager("assets/img/inventory/weapons/steelSword.png");
		wand_I = new ImageManager("assets/img/inventory/weapons/wand.png");
		staff_I = new ImageManager("assets/img/inventory/weapons/staff.png");
		shank_I = new ImageManager("assets/img/inventory/weapons/shank.png");
		dagger_I = new ImageManager("assets/img/inventory/weapons/dagger.png");
		
		//big things
		bigOrcHead = new ImageManager("assets/img/heads/BigOrcHead.png");
		bigHumanHead = new ImageManager("assets/img/heads/BigHumanHead.png");
		mediumOrc = new ImageManager("assets/img/heads/mediumOrc.png");
		mediumHuman = new ImageManager("assets/img/heads/mediumHuman.png");
		bigLeather = new ImageManager("assets/img/armor/helmets/bighelmets/leather.png");
		bigThief = new ImageManager("assets/img/armor/helmets/bighelmets/thief.png");
		bigHood = new ImageManager("assets/img/armor/helmets/bighelmets/hood.png");
		mediumLeather = new ImageManager("assets/img/armor/helmets/mediums/leather.png");
		mediumThief = new ImageManager("assets/img/armor/helmets/mediums/thief.png");
		mediumHood = new ImageManager("assets/img/armor/helmets/mediums/hood.png");
		toolbar_orc = new ImageManager("assets/img/toolbar/heads/orc.png");
		toolbar_human = new ImageManager("assets/img/toolbar/heads/human.png");
		toolbar_leather = new ImageManager("assets/img/toolbar/helmets/leather.png");
		toolbar_thief = new ImageManager("assets/img/toolbar/helmets/thief.png");
		toolbar_hood = new ImageManager("assets/img/toolbar/helmets/hood.png");
		toolbar_steel = new ImageManager("assets/img/toolbar/helmets/steel.png");
		journal = new ImageManager("assets/img/toolbar/book.png");
		
		//locations
		town = new ImageManager("assets/img/locations/town.jpg");
		town2 = new ImageManager("assets/img/locations/town2.png");
		armorer = new ImageManager("assets/img/locations/armorer.jpg");
		woods = new ImageManager("assets/img/locations/woods.jpg");
		woods2 = new ImageManager("assets/img/locations/woods2.png");
		woods3 = new ImageManager("assets/img/locations/woods3.png");
		woods4 = new ImageManager("assets/img/locations/woods4.png");
		woods5 = new ImageManager("assets/img/locations/woods5.png");
		woods6 = new ImageManager("assets/img/locations/woods6.png");
		woods7 = new ImageManager("assets/img/locations/woods7.png");
		camp = new ImageManager("assets/img/locations/camp.png");
		blacksmith = new ImageManager("assets/img/locations/weapons.jpg");
		castle = new ImageManager("assets/img/locations/castle.jpg");
		chop = new ImageManager("assets/img/locations/chopped.png");
		woodsNormal = new ImageManager("assets/img/locations/woodsNormal.png");
		
		//armor
		leatherHelm = new ImageManager("assets/img/armor/helmets/leather.png");
		hoodHelm = new ImageManager("assets/img/armor/helmets/hood.png");
		thiefHelm = new ImageManager("assets/img/armor/helmets/thief.png");
		leatherChest = new ImageManager("assets/img/armor/chestplates/leather.png");
		thiefChest = new ImageManager("assets/img/armor/chestplates/thief.png");
		robeChest = new ImageManager("assets/img/armor/chestplates/robe.png");
		leatherChest_I = new ImageManager("assets/img/inventory/armor/leatherChest.png");
		mageRobe_I = new ImageManager("assets/img/inventory/armor/mageRobe.png");
		thiefRobe_I = new ImageManager("assets/img/inventory/armor/thiefRobe.png");
		steelChest_I = new ImageManager("assets/img/inventory/armor/steelChest.png");
		steelLeggings_I = new ImageManager("assets/img/inventory/armor/steelLeggings.png");
		steelChest= new ImageManager("assets/img/armor/chestplates/steel.png");
		steelHelm = new ImageManager("assets/img/armor/helmets/steel.png");
		steelLeggings = new ImageManager("assets/img/armor/leggings/steel.png");
		
		//buttons
		newGame = new ImageManager("assets/img/buttons/newGame.png");
		newGameActive = new ImageManager("assets/img/buttons/newGameActive.png");
		left = new ImageManager("assets/img/buttons/left.png");
		leftActive = new ImageManager("assets/img/buttons/leftActive.png");
		right = new ImageManager("assets/img/buttons/right.png");
		rightActive = new ImageManager("assets/img/buttons/rightActive.png");
		done = new ImageManager("assets/img/buttons/done.png");
		doneActive = new ImageManager("assets/img/buttons/doneActive.png");
		ok = new ImageManager("assets/img/buttons/ok.png");
		okActive = new ImageManager("assets/img/buttons/okActive.png");
		southButton = new ImageManager("assets/img/buttons/south.png");
		southButtonActive = new ImageManager("assets/img/buttons/southActive.png");
		northButton = new ImageManager("assets/img/buttons/north.png");
		northButtonActive = new ImageManager("assets/img/buttons/northActive.png");
		compass = new ImageManager("assets/img/buttons/compass.png");
		backpack = new ImageManager("assets/img/buttons/backpack.png");
		backpackActive = new ImageManager("assets/img/buttons/backpackActive.png");
		magic = new ImageManager("assets/img/buttons/magic.png");
		magicActive = new ImageManager("assets/img/buttons/magicActive.png");
		diary = new ImageManager("assets/img/buttons/diary.png");
		diaryActive = new ImageManager("assets/img/buttons/diaryActive.png");
		character = new ImageManager("assets/img/buttons/character.png");
		characterActive = new ImageManager("assets/img/buttons/characterActive.png");
		no = new ImageManager("assets/img/buttons/no.png");
		noActive = new ImageManager("assets/img/buttons/noActive.png");
		
		this.WIDTH = w;
		this.HEIGHT = h;
		
		timer.start();
		run();
	}
	
	public void slotCheck() {
		int[] slotXs = new int[] {slot1x,slot2x,slot3x,slot4x,slot5x,slot6x,slot7x,slot8x,slot9x,slot10x};
		int[] slotYs = new int[] {slot1y,slot2y,slot3y,slot4y,slot5y,slot6y,slot7y,slot8y,slot9y,slot10y};
		
		for(int i = 0; i < player.inventory.length; i++) {
			
			if(inventoryVisible) {
				if(player.inventory[i] == "leather helmet") {
					g.drawImage(leatherHelm.guy, slotXs[i], slotYs[i], null);
					
					//equips the helmet and replaces it's slot with what you were originaly wearing
					if(mouseCollided(slotXs[i],slotYs[i],32,32)) {
						g.setFont(new Font("Times New Roman",Font.BOLD,30));
						
						g.setColor(Color.gray.darker());
						g.fillRect(mousemanager.mousex - 300, mousemanager.mousey + 50, 300, 100);
						
						g.setColor(Color.black);
						g.drawRect(mousemanager.mousex - 300, mousemanager.mousey + 50, 300, 100);
						
						g.setColor(Color.black);
						g.drawString(player.inventory[i], mousemanager.mousex - 220, mousemanager.mousey + 110);
						
						g.setFont(new Font("Times New Roman",Font.BOLD,16));
						g.drawString("click to equip", mousemanager.mousex - 200, mousemanager.mousey + 130);
						
						if(mousemanager.isLeftPressed() && pressDelay == 0) {
							swapHelm = player.inventory[0];
							player.inventory[i] = player.helmet;
							player.helmet = swapHelm;
							pressDelay = 15;
						}
					}
				}
				else {
					if(player.inventory[i] == "mage hood") {
						g.drawImage(hoodHelm.guy, slotXs[i], slotYs[i], null);
						
						if(mouseCollided(slotXs[i],slotYs[i],32,32)) {
							g.setFont(new Font("Times New Roman",Font.BOLD,30));
							
							g.setColor(Color.gray.darker());
							g.fillRect(mousemanager.mousex - 300, mousemanager.mousey + 50, 300, 100);
							
							g.setColor(Color.black);
							g.drawRect(mousemanager.mousex - 300, mousemanager.mousey + 50, 300, 100);
							
							g.setColor(Color.black);
							g.drawString(player.inventory[i], mousemanager.mousex - 220, mousemanager.mousey + 110);
							
							g.setFont(new Font("Times New Roman",Font.BOLD,16));
							g.drawString("click to equip", mousemanager.mousex - 200, mousemanager.mousey + 130);
							
							if(mousemanager.isLeftPressed() && pressDelay == 0) {
								swapHelm = player.inventory[i];
								player.inventory[i] = player.helmet;
								player.helmet = swapHelm;
								pressDelay = 15;
							}
						}
					}
					else {
						if(player.inventory[i] == "thief hood") {
							g.drawImage(thiefHelm.guy, slotXs[i], slotYs[i], null);
							
							if(mouseCollided(slotXs[i],slotYs[i],32,32)) {
								g.setFont(new Font("Times New Roman",Font.BOLD,30));
								
								g.setColor(Color.gray.darker());
								g.fillRect(mousemanager.mousex - 300, mousemanager.mousey + 50, 300, 100);
								
								g.setColor(Color.black);
								g.drawRect(mousemanager.mousex - 300, mousemanager.mousey + 50, 300, 100);
								
								g.setColor(Color.black);
								g.drawString(player.inventory[i], mousemanager.mousex - 220, mousemanager.mousey + 110);
								
								g.setFont(new Font("Times New Roman",Font.BOLD,16));
								g.drawString("click to equip", mousemanager.mousex - 200, mousemanager.mousey + 130);
								
								if(mousemanager.isLeftPressed() && pressDelay == 0) {
									swapHelm = player.inventory[i];
									player.inventory[i] = player.helmet;
									player.helmet = swapHelm;
									pressDelay = 15;
								}
							}
						}
						else {
							if(player.inventory[i] == "leather chest") {
								g.drawImage(leatherChest_I.guy, slotXs[i], slotYs[i], null);
								
								if(mouseCollided(slotXs[i],slotYs[i],32,32)) {
									g.setFont(new Font("Times New Roman",Font.BOLD,30));
									
									g.setColor(Color.gray.darker());
									g.fillRect(mousemanager.mousex - 300, mousemanager.mousey + 50, 300, 100);
									
									g.setColor(Color.black);
									g.drawRect(mousemanager.mousex - 300, mousemanager.mousey + 50, 300, 100);
									
									g.setColor(Color.black);
									g.drawString(player.inventory[i], mousemanager.mousex - 220, mousemanager.mousey + 110);
									
									g.setFont(new Font("Times New Roman",Font.BOLD,16));
									g.drawString("click to equip", mousemanager.mousex - 200, mousemanager.mousey + 130);
									
									if(mousemanager.isLeftPressed() && pressDelay == 0) {
										swapHelm = player.inventory[i];
										player.inventory[i] = player.chest;
										player.chest = swapHelm;
										pressDelay = 15;
									}
								}
							}
							else {
								if(player.inventory[i] == "thief robe") {
									g.drawImage(thiefRobe_I.guy, slotXs[i], slotYs[i], null);
									
									if(mouseCollided(slotXs[i],slotYs[i],32,32)) {
										g.setFont(new Font("Times New Roman",Font.BOLD,30));
										
										g.setColor(Color.gray.darker());
										g.fillRect(mousemanager.mousex - 300, mousemanager.mousey + 50, 300, 100);
										
										g.setColor(Color.black);
										g.drawRect(mousemanager.mousex - 300, mousemanager.mousey + 50, 300, 100);
										
										g.setColor(Color.black);
										g.drawString(player.inventory[i], mousemanager.mousex - 220, mousemanager.mousey + 110);
										
										g.setFont(new Font("Times New Roman",Font.BOLD,16));
										g.drawString("click to equip", mousemanager.mousex - 200, mousemanager.mousey + 130);
										
										if(mousemanager.isLeftPressed() && pressDelay == 0) {
											swapHelm = player.inventory[i];
											player.inventory[i] = player.chest;
											player.chest = swapHelm;
											pressDelay = 15;
										}
									}
								}
								else {
									if(player.inventory[i] == "mage robe") {
										g.drawImage(mageRobe_I.guy, slotXs[i], slotYs[i], null);
										
										if(mouseCollided(slotXs[i],slotYs[i],32,32)) {
											g.setFont(new Font("Times New Roman",Font.BOLD,30));
											
											g.setColor(Color.gray.darker());
											g.fillRect(mousemanager.mousex - 300, mousemanager.mousey + 50, 300, 100);
											
											g.setColor(Color.black);
											g.drawRect(mousemanager.mousex - 300, mousemanager.mousey + 50, 300, 100);
											
											g.setColor(Color.black);
											g.drawString(player.inventory[i], mousemanager.mousex - 220, mousemanager.mousey + 110);
											
											g.setFont(new Font("Times New Roman",Font.BOLD,16));
											g.drawString("click to equip", mousemanager.mousex - 200, mousemanager.mousey + 130);
											
											if(mousemanager.isLeftPressed() && pressDelay == 0) {
												swapHelm = player.inventory[i];
												player.inventory[i] = player.chest;
												player.chest = swapHelm;
												pressDelay = 15;
											}
										}
									}
									else {
										if(player.inventory[i] == "woodenSword") {
											g.drawImage(woodenSword_I.guy, slotXs[i], slotYs[i], null);
											
											//equips the helmet and replaces it's slot with what you were originaly wearing
											if(mouseCollided(slotXs[i],slotYs[i],32,32)) {
												g.setFont(new Font("Times New Roman",Font.BOLD,30));
												
												g.setColor(Color.gray.darker());
												g.fillRect(mousemanager.mousex - 300, mousemanager.mousey + 50, 300, 100);
												
												g.setColor(Color.black);
												g.drawRect(mousemanager.mousex - 300, mousemanager.mousey + 50, 300, 100);
												
												g.setColor(Color.black);
												g.drawString(player.inventory[i], mousemanager.mousex - 220, mousemanager.mousey + 110);
												
												g.setFont(new Font("Times New Roman",Font.BOLD,16));
												g.drawString("click to equip", mousemanager.mousex - 200, mousemanager.mousey + 130);
												
												if(mousemanager.isLeftPressed() && pressDelay == 0) {
													holdingSpot = "woodenSword";
													player.inventory[i] = playerWeapon;
													playerWeapon = holdingSpot;
													pressDelay = 15;
												}
											}
										}
										else {
											if(player.inventory[i] == "dagger") {
												g.drawImage(dagger_I.guy, slotXs[i], slotYs[i], null);
												
												//equips the helmet and replaces it's slot with what you were originaly wearing
												if(mouseCollided(slotXs[i],slotYs[i],32,32)) {
													g.setFont(new Font("Times New Roman",Font.BOLD,30));
													
													g.setColor(Color.gray.darker());
													g.fillRect(mousemanager.mousex - 300, mousemanager.mousey + 50, 300, 100);
													
													g.setColor(Color.black);
													g.drawRect(mousemanager.mousex - 300, mousemanager.mousey + 50, 300, 100);
													
													g.setColor(Color.black);
													g.drawString(player.inventory[i], mousemanager.mousex - 220, mousemanager.mousey + 110);
													
													g.setFont(new Font("Times New Roman",Font.BOLD,16));
													g.drawString("click to equip", mousemanager.mousex - 200, mousemanager.mousey + 130);
													if(mousemanager.isLeftPressed() && pressDelay == 0) {
														holdingSpot = "dagger";
														player.inventory[i] = playerWeapon;
														playerWeapon = holdingSpot;
														pressDelay = 15;
													}
												}
											}
											else {
												if(player.inventory[i] == "wand") {
													g.drawImage(wand_I.guy, slotXs[i], slotYs[i], null);
													
													//equips the helmet and replaces it's slot with what you were originaly wearing
													if(mouseCollided(slotXs[i],slotYs[i],32,32)) {
														g.setFont(new Font("Times New Roman",Font.BOLD,30));
														
														g.setColor(Color.gray.darker());
														g.fillRect(mousemanager.mousex - 300, mousemanager.mousey + 50, 300, 100);
														
														g.setColor(Color.black);
														g.drawRect(mousemanager.mousex - 300, mousemanager.mousey + 50, 300, 100);
														
														g.setColor(Color.black);
														g.drawString(player.inventory[i], mousemanager.mousex - 220, mousemanager.mousey + 110);
														
														g.setFont(new Font("Times New Roman",Font.BOLD,16));
														g.drawString("click to equip", mousemanager.mousex - 200, mousemanager.mousey + 130);
														
														if(mousemanager.isLeftPressed() && pressDelay == 0) {
															holdingSpot = "wand";
															player.inventory[i] = playerWeapon;
															playerWeapon = holdingSpot;
															pressDelay = 15;
														}
													}
												}
												else {
													if(player.inventory[i] == "shank") {
														g.drawImage(shank_I.guy, slotXs[i], slotYs[i], null);
														
														//equips the helmet and replaces it's slot with what you were originaly wearing
														if(mouseCollided(slotXs[i],slotYs[i],32,32)) {
															g.setFont(new Font("Times New Roman",Font.BOLD,30));
															
															g.setColor(Color.gray.darker());
															g.fillRect(mousemanager.mousex - 300, mousemanager.mousey + 50, 300, 100);
															
															g.setColor(Color.black);
															g.drawRect(mousemanager.mousex - 300, mousemanager.mousey + 50, 300, 100);
															
															g.setColor(Color.black);
															g.drawString(player.inventory[i], mousemanager.mousex - 220, mousemanager.mousey + 110);
															
															g.setFont(new Font("Times New Roman",Font.BOLD,16));
															g.drawString("click to equip", mousemanager.mousex - 200, mousemanager.mousey + 130);
															
															if(mousemanager.isLeftPressed() && pressDelay == 0) {
																holdingSpot = "shank";
																player.inventory[i] = playerWeapon;
																playerWeapon = holdingSpot;
																pressDelay = 15;
															}
														}
													}
													else {
														if(player.inventory[i] == "staff") {
															g.drawImage(staff_I.guy, slotXs[i], slotYs[i], null);
															
															//equips the helmet and replaces it's slot with what you were originaly wearing
															if(mouseCollided(slotXs[i],slotYs[i],32,32)) {
																g.setFont(new Font("Times New Roman",Font.BOLD,30));
																
																g.setColor(Color.gray.darker());
																g.fillRect(mousemanager.mousex - 300, mousemanager.mousey + 50, 300, 100);
																
																g.setColor(Color.black);
																g.drawRect(mousemanager.mousex - 300, mousemanager.mousey + 50, 300, 100);
																
																g.setColor(Color.black);
																g.drawString(player.inventory[i], mousemanager.mousex - 220, mousemanager.mousey + 110);
																
																g.setFont(new Font("Times New Roman",Font.BOLD,16));
																g.drawString("click to equip", mousemanager.mousex - 200, mousemanager.mousey + 130);
																
																if(mousemanager.isLeftPressed() && pressDelay == 0) {
																	holdingSpot = "staff";
																	player.inventory[i] = playerWeapon;
																	playerWeapon = holdingSpot;
																	pressDelay = 15;
																}
															}
														}
														else {
															if(player.inventory[i] == "steelSword") {
																g.drawImage(steelSword_I.guy, slotXs[i], slotYs[i], null);
																
																//equips the helmet and replaces it's slot with what you were originaly wearing
																if(mouseCollided(slotXs[i],slotYs[i],32,32)) {
																	g.setFont(new Font("Times New Roman",Font.BOLD,30));
																	
																	g.setColor(Color.gray.darker());
																	g.fillRect(mousemanager.mousex - 300, mousemanager.mousey + 50, 300, 100);
																	
																	g.setColor(Color.black);
																	g.drawRect(mousemanager.mousex - 300, mousemanager.mousey + 50, 300, 100);
																	
																	g.setColor(Color.black);
																	g.drawString(player.inventory[i], mousemanager.mousex - 220, mousemanager.mousey + 110);
																	
																	g.setFont(new Font("Times New Roman",Font.BOLD,16));
																	g.drawString("click to equip", mousemanager.mousex - 200, mousemanager.mousey + 130);
																	
																	if(mousemanager.isLeftPressed() && pressDelay == 0) {
																		holdingSpot = "steelSword";
																		player.inventory[i] = playerWeapon;
																		playerWeapon = holdingSpot;
																		pressDelay = 15;
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				} //4
			}
			
			if(inventoryVisible == false) {
				if(player.spells[i] == "fireball") {
					g.drawImage(fire_ball.guy, slotXs[i], slotYs[i], null);
					
					//equips the helmet and replaces it's slot with what you were originaly wearing
					if(mouseCollided(slotXs[i],slotYs[i],32,32)) {
						if(mousemanager.isLeftPressed() && pressDelay == 0) {
							selectingTarget = true;
							
							if(selectingTarget) {
								castSpell_Guards(targetSelectionGuard());
								castSpell_Enemies(targetSelectionEnemy());
							}
						}
					}
				}
			}
		}
	}
	
	public void castSpell_Guards(Guard target) {
		selectingTarget = true;
		this.target = target;
	}
	
	public void castSpell_Enemies(Enemy target) {
		selectingTarget = true;
		enemyTarget = target;
	}
	
	public Guard targetSelectionGuard() {
		if(location == "town") {
			if(mouseCollided(guard.x,guard.y,guard.w,guard.h)) {
				if(mousemanager.isLeftPressed() && pressDelay == 0) {
					return guard;
				}
				else {
					return null;
				}
			}
			else {
				return null;
			}
		}
		else {
			return null;
		}
	}
	
	public Enemy targetSelectionEnemy() {
		if(location == "woods") {
			if(mouseCollided(chicken.x,chicken.y,chicken.w,chicken.h)) {
				if(mousemanager.isLeftPressed() && pressDelay  == 0) {
					return chicken;
				}
				else {
					return null;
				}
			}
			else {
				return null;
			}
		}
		else {
			return null;
		}
	}
	
	//a function so i dont have to rewrite the collision code for each button
	public boolean mouseCollided(int obj_x,int obj_y,int obj_width,int obj_height) {
		if(mousemanager.mousex > obj_x && mousemanager.mousex < obj_x + obj_width && mousemanager.mousey > obj_y && mousemanager.mousey < obj_y + obj_height) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void steal(String item) {
		player.addToInv(item);
		caughtCalculator();
		caughtChecker = 25;
		pressDelay = 25;
	}
	
	public void buy(ShopItem itemObject,String itemName) {
		if(player.gold >= itemObject.price) {
			player.gold -= itemObject.price;
			player.addToInv(itemName);
			pressDelay = 25;
			itemObject.restockTimer = 125;
		}
		else {
			System.out.println("Insufficient funds");
		}
	}
	
	public void armorCheck() {
		if(player.helmet == "leather helmet") {
			helm = 5;
		}
		else {
			if(player.helmet == "mage hood") {
				helm = 3;
			}
			else {
				if(player.helmet == "thief hood") {
					helm = 3;
				}
			}
		}
		
		if(player.chest == "leather chest") {
			ch = 7;
		}
		else {
			if(player.chest == "mage robe") {
				ch = 4;
			}
			else {
				if(player.chest == "thief robe") {
					ch = 4;
				}
			}
		}
		
		pa = 2;
	}
	
	public void init() {
		running = true;
		state = "main menu";
		playerMenuVisible = false;
		console = new Console(32*5,32*19,24); 
		goldDisplay = new Console((32*34) + 5,(32*18) - 10,20);
		newGameButton = new Button(false,WIDTH/2-150,HEIGHT/2-200,300,100); //false is if it's lit up or not
		raceLeft = new Button(false,200,215,50,50);
		raceRight = new Button(false,260,215,50,50);
		hpLeft = new Button(false,120,280,50,50);
		hpRight = new Button(false,180,280,50,50);
		strLeft = new Button(false,120,345,50,50);
		strRight = new Button(false,180,345,50,50);
		luckLeft = new Button(false,160,420,50,50);
		luckRight = new Button(false,220,420,50,50);
		IntLeft = new Button(false,120,495,50,50);
		IntRight = new Button(false,180,495,50,50);
		sneakLeft = new Button(false,170,560,50,50);
		sneakRight = new Button(false,230,560,50,50);
		speechLeft = new Button(false,190,635,50,50);
		speechRight = new Button(false,250,635,50,50);
		classLeft = new Button(false,420,470,50,50);
		classRight = new Button(false,480,470,50,50);
		doneButton = new Button(false,780,620,300,100);
		playerMenuLeft = new Button(false,32*20 + 10,32*6 + 10,50,50);
		playerMenuRight = new Button(false,32*20 + 70,32*6 + 10,50,50);
		okButton = new Button(false,32*5,32*20,50,50);
		south = new Button(false,32*35,32*21 - 1,50,50);
		north = new Button(false,32*35,32*20 - 66,50,50);
		west = new Button(false,32*34-20,north.y+50,50,50);
		east = new Button(false,32*36+20,north.y+50,50,50);
		Diary = new Button(false,32*5,32*20,50,50);
		Inventory = new Button(false,32*7,32*20,50,50);
		stats = new Button(false,32*9,32*20,50,50);
		noButton = new Button(false,32*7,32*20,50,50);
		
		creatingCharacter = false;
		wanted = false;
		hostile = false;
		turn = "you";
		location = "town";
		
		chopped = -3;
		
		surroundingLocations = new int[4];
		
		clerkx = 32*16;
		clerky = 32*2;
		clerkw = 32;
		clerkh = 64;
		
		predictor = new Predictor();
		
		predictor.randomize(surroundingLocations);
		
		//buildings
		
		//town 1
		Armorer = new Building(32,32*4,"Armorer");
		Castle = new Building(32*13,32*4,"Castle");
		blackSmith = new Building(32*26,32*4,"Blacksmith");
		
		//town 2
		blackSmith2 = new Building(32*3,-32,"Blacksmith");
		Armorer2 = new Building(32*3,32*5,"Armorer");
		magicShop = new Building(32*23,-32,"Magic Shop");
		Castle2 = new Building(32*23,32*5,"Castle");
		
		
		//shop items
		leather_chest = new ShopItem(32*12,32*4,32,32,30);
		thief_hood = new ShopItem(32*14,32*4,32,32,5);
		thief_robe = new ShopItem(32*16,32*4,32,32,15);
		mage_hood = new ShopItem(32*18,32*4,32,32,5);
		mage_robe = new ShopItem(32*20,32*4,32,32,15);
		woodSword = new ShopItem(32*12,32*4,32,32,20);
		SteelSword = new ShopItem(32*14,32*4,32,32,50);
		Shank = new ShopItem(32*16,32*4,32,32,3);
		Dagger = new ShopItem(32*18,32*4,32,32,20);
		Wand = new ShopItem(32*10,32*4,32,32,20);
		Staff = new ShopItem(32*12,32*4,32,64,40);
		steel_helm = new ShopItem(32*10,32*4,32,32,40);
		steel_chest = new ShopItem(32*12,32*4,32,32,100);
		steel_leggings = new ShopItem(32*14,32*4,32,32,75);
		Dagger2 = new ShopItem(32*10,32*4,32,32,20);
		SteelSword2 = new ShopItem(32*12,32*4,32,32,50);
		
		//enemies
		chicken = new Enemy(32*16,32*15,32,32,1,10);
		
		//people
		armorerClerk = new Person(clerkx,clerky,clerkw,clerkh,null);
		jim = new Person(32*6,32*10,32,64,"town");
		
		//quests
		weirdTree = new Quest("Weird Tree","Find and chop down weird tree",10,10);
		
		questJournal = new Journal();
		
		//dialogues
		caughtDialogue = new Dialogue(4);	//4 is how many "slides" are in the dialogue, or how many times
		armorerDialogue = new Dialogue(2);	//you would have to click to see it all
		
		if(weirdTree.finished) {
			citizenDialogue = new Dialogue(1);
		}
		else {
			citizenDialogue = new Dialogue(5);
		}
		
		//might change where and how dialogue slides are added for more variety in the future
		//for instance i could use arrays of possible sentances and have a random one chosen
		if(weirdTree.finished) {
			citizenDialogue.addSlide(">> Citizen: Hey.");
		}
		else {
			citizenDialogue.addSlide(">> Citizen: Hey, can you do me a favor?");
			citizenDialogue.addSlide(">> Citizen: Can you cut down weird tree for me?");
			citizenDialogue.addSlide(">> Citizen: Ill pay you generously for your service.");
			citizenDialogue.addSlide(">> Citizen: Not cool dog.");
			citizenDialogue.addSlide(">> Citizen: Thanks odio. Here's your reward.");
		}
		
		
		caughtDialogue.addSlide(">> Guard: Stop!");
		caughtDialogue.addSlide(">> Guard: Pay your fine or DIE odio eater!");
		caughtDialogue.addSlide(">> Guard: THEN DIE!");
		caughtDialogue.addSlide(">> Guard: I hope you learn from your mistakes.");
		
		armorerDialogue.addSlide(">> Merchant: Hey there!");
		armorerDialogue.addSlide(">> Merchant: Why not take a look at my wares?");
		
		
		guard = new Guard(32*4,32*10,20);
		
		//inventory slot positions
		slot1x = 32*34 + 20;
		slot1y = 12 + 74;
		
		slot2x = slot1x + 42;	//42 is the image's width + 10 so there is some space between them
		slot2y = slot1y;
		
		slot3x = slot2x + 42;
		slot3y = slot2y;
		
		slot4x = slot3x + 42;
		slot4y = slot3y;
		
		slot5x = slot1x;
		slot5y = slot1y + 42;
		
		slot6x = slot2x;
		slot6y = slot5y;
		
		slot7x = slot3x;
		slot7y = slot6y;
		
		slot8x = slot4x;
		slot8y = slot7y;
		
		slot9x = slot5x;
		slot9y = slot5y + 42;
		
		slot10x = slot6x;
		slot10y = slot9y;
		
		
		//player menu state and armor position
		playerMenuState = "stats";
		
		armorx = 32*10;
		armory = 32*4;
		armorw = 32;
		armorh = 32;
		
		caught = 20;	//its above 10 so it doesn't trigger the code for getting caught if you haven't done anything
		caughtChecker = -3;	//i set it to -3 because it sets off the code for getting caught when it = 0
		
		random = new Random();
		
		location = "town";	//placeholder, eventually spawning location will be in the character creation menu
		
		//also got this cursor stuff from youtube
		// Transparent 16 x 16 pixel cursor image.
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

		// Create a new blank cursor.
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
		    cursorImg, new Point(0, 0), "blank cursor");

		// Set the blank cursor to the JFrame.
		display.getJFrame().getContentPane().setCursor(blankCursor);
		
		skillPoints = 3;
		strBonus = 0;
		intBonus = 0;
		sneakBonus = 0;
		speechBonus = 0;
		luckBonus = 0;
		hpBonus = 0;
		
		//adds the keymanager and mousemanager to the window so it can be used in-game
		display.getJFrame().addKeyListener(keymanager);
		display.getJFrame().addMouseListener(mousemanager);
		display.getJFrame().addMouseMotionListener(mousemanager);
		display.getCanvas().addMouseListener(mousemanager);
		display.getCanvas().addMouseMotionListener(mousemanager);
	}
	
	public void caughtCalculator() {
		caught = (((random.nextInt(player.sneak) - (player.sneak / ((random.nextInt(player.luck) + random.nextInt(3)) + 1))) + Math.abs(player.sneak - player.luck)) + 1);
	}
	
	public void render() {
		BufferStrategy bs = display.getCanvas().getBufferStrategy();
		//checks if there is a buffer strategy
		if(bs == null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		
		update(g);
		update2(g);
		update3(g);
		
		bs.show();
		g.dispose();
	}
	
	public void update3(Graphics graphics) {
		healthWidth = (player.maxHP * (player.maxHP / 15));
		manaWidth = (player.maxMana * (player.maxMana / 15));
		
		if(state == "game" && talkingToCitizen == false && talkingToClerk == false && talkingToGuard == false) {
			if(Diary.active) {
				g.drawImage(diaryActive.guy, Diary.x, Diary.y, null);
			}
			else {
				g.drawImage(diary.guy, Diary.x, Diary.y, null);
			}
			
			if(inventoryVisible) {
				if(Inventory.active) {
					g.drawImage(magicActive.guy, Inventory.x, Inventory.y, null);
				}
				else {
					g.drawImage(magic.guy, Inventory.x, Inventory.y, null);
				}
			}
			else {
				if(Inventory.active) {
					g.drawImage(backpackActive.guy, Inventory.x, Inventory.y, null);
				}
				else {
					g.drawImage(backpack.guy, Inventory.x, Inventory.y, null);
				}
			}
			
			if(stats.active) {
				g.drawImage(characterActive.guy, stats.x, stats.y, null);
			}
			else {
				g.drawImage(character.guy, stats.x, stats.y, null);
			}
		}
		
		if(mouseCollided(Inventory.x,Inventory.y,Inventory.w,Inventory.h)) {
			Inventory.active = true;
			
			if(mousemanager.isLeftPressed() && pressDelay == 0 && state == "game" && talkingToCitizen == false && talkingToClerk == false && talkingToGuard == false) {
				if(inventoryVisible) {
					inventoryVisible = false;
					pressDelay = 15;
				}
				else {
					inventoryVisible = true;
					pressDelay = 15;
				}
			}
		}
		else {
			Inventory.active = false;
		}
		
		if(mouseCollided(Diary.x,Diary.y,Diary.w,Diary.h)) {
			Diary.active = true;
			
			if(mousemanager.isLeftPressed() && pressDelay == 0 && state == "game" && talkingToCitizen == false && talkingToClerk == false && talkingToGuard == false) {
				if(questJournal.visible) {
					questJournal.visible = false;
					pressDelay = 15;
				}
				else {
					questJournal.visible = true;
					pressDelay = 15;
				}
			}
		}
		else {
			Diary.active = false;
		}
		
		if(mouseCollided(stats.x,stats.y,stats.w,stats.h)) {
			stats.active = true;
			
			if(mousemanager.isLeftPressed() && pressDelay == 0) {
				//show stats
			}
		}
		else {
			stats.active = false;
		}
		
		
		if(mouseCollided(noButton.x,noButton.y,noButton.w,noButton.h)) {
			noButton.active = true;
			
			if(mousemanager.isLeftPressed() && pressDelay == 0) {
				if(talkingToCitizen) {
					dialogueSlide = 3;
				}
				
				if(talkingToGuard && wanted) {
					pressDelay = 15;
					wanted = false;
					hostile = true;
					turn = "guard";
				}
			}
		}
		else {
			noButton.active = false;
		}
		
		if(dialogueSlide == 15 && weirdTree.finished == false) {
			questJournal.removeQuest(weirdTree);
		}
		
		if(mouseCollided(steel_helm.x,steel_helm.y,steel_helm.w,steel_helm.h) && location == "armorer2" && steel_helm.restockTimer < 1) {
			g.setFont(new Font("Times New Roman",Font.BOLD,30));
			
			g.setColor(Color.black);
			g.drawRect(mousemanager.mousex + 20, mousemanager.mousey + 50, 300, 100);
			
			g.setColor(Color.gray.darker());
			g.fillRect(mousemanager.mousex + 21, mousemanager.mousey + 51, 299, 99);
			
			g.setColor(Color.black);
			g.drawString("Steel helm: " + steel_helm.price + " gold", mousemanager.mousex + 57, mousemanager.mousey + 110);
			
			g.setFont(new Font("Times New Roman",Font.BOLD,16));
			g.drawString("Left click to buy", mousemanager.mousex + 125, mousemanager.mousey + 130);
			g.drawString("Right click to steal", mousemanager.mousex + 118, mousemanager.mousey + 145);
		}
		
		if(mousemanager.mousex > armorx && mousemanager.mousex < armorx + armorw && mousemanager.mousey > armory && mousemanager.mousey < armory + armorh && location == "armorer" && armorVisible) {
			g.setFont(new Font("Times New Roman",Font.BOLD,30));
			
			g.setColor(Color.black);
			g.drawRect(mousemanager.mousex + 20, mousemanager.mousey + 50, 300, 100);
			
			g.setColor(Color.gray.darker());
			g.fillRect(mousemanager.mousex + 21, mousemanager.mousey + 51, 299, 99);
			
			g.setColor(Color.black);
			g.drawString("Leather helm: 10 gold", mousemanager.mousex + 32, mousemanager.mousey + 110);
			
			g.setFont(new Font("Times New Roman",Font.BOLD,16));
			g.drawString("Left click to buy", mousemanager.mousex + 125, mousemanager.mousey + 130);
			g.drawString("Right click to steal", mousemanager.mousex + 118, mousemanager.mousey + 145);
		}
		
		if(mouseCollided(leather_chest.x,leather_chest.y,leather_chest.w,leather_chest.h) && location == "armorer" && leather_chest.restockTimer < 1) {
			g.setFont(new Font("Times New Roman",Font.BOLD,30));
			
			g.setColor(Color.black);
			g.drawRect(mousemanager.mousex + 20, mousemanager.mousey + 50, 300, 100);
			
			g.setColor(Color.gray.darker());
			g.fillRect(mousemanager.mousex + 21, mousemanager.mousey + 51, 299, 99);
			
			g.setColor(Color.black);
			g.drawString("Leather chest: " + leather_chest.price + " gold", mousemanager.mousex + 31, mousemanager.mousey + 110);
			
			g.setFont(new Font("Times New Roman",Font.BOLD,16));
			g.drawString("Left click to buy", mousemanager.mousex + 125, mousemanager.mousey + 130);
			g.drawString("Right click to steal", mousemanager.mousex + 118, mousemanager.mousey + 145);
		}
		
		if(mouseCollided(thief_hood.x,thief_hood.y,thief_hood.w,thief_hood.h) && location == "armorer" && thief_hood.restockTimer < 1) {
			g.setFont(new Font("Times New Roman",Font.BOLD,30));
			
			g.setColor(Color.black);
			g.drawRect(mousemanager.mousex + 20, mousemanager.mousey + 50, 300, 100);
			
			g.setColor(Color.gray.darker());
			g.fillRect(mousemanager.mousex + 21, mousemanager.mousey + 51, 299, 99);
			
			g.setColor(Color.black);
			g.drawString("Thief hood: " + thief_hood.price + " gold", mousemanager.mousex + 57, mousemanager.mousey + 110);
			
			g.setFont(new Font("Times New Roman",Font.BOLD,16));
			g.drawString("Left click to buy", mousemanager.mousex + 125, mousemanager.mousey + 130);
			g.drawString("Right click to steal", mousemanager.mousex + 118, mousemanager.mousey + 145);
		}
		
		if(mouseCollided(thief_robe.x,thief_robe.y,thief_robe.w,thief_robe.h) && location == "armorer" && thief_robe.restockTimer < 1) {
			g.setFont(new Font("Times New Roman",Font.BOLD,30));
			
			g.setColor(Color.black);
			g.drawRect(mousemanager.mousex + 20, mousemanager.mousey + 50, 300, 100);
			
			g.setColor(Color.gray.darker());
			g.fillRect(mousemanager.mousex + 21, mousemanager.mousey + 51, 299, 99);
			
			g.setColor(Color.black);
			g.drawString("Thief robe: " + thief_robe.price + " gold", mousemanager.mousex + 57, mousemanager.mousey + 110);
			
			g.setFont(new Font("Times New Roman",Font.BOLD,16));
			g.drawString("Left click to buy", mousemanager.mousex + 125, mousemanager.mousey + 130);
			g.drawString("Right click to steal", mousemanager.mousex + 118, mousemanager.mousey + 145);
		}
		
		if(mouseCollided(mage_hood.x,mage_hood.y,mage_hood.w,mage_hood.h) && location == "armorer" && mage_hood.restockTimer < 1) {
			g.setFont(new Font("Times New Roman",Font.BOLD,30));
			
			g.setColor(Color.black);
			g.drawRect(mousemanager.mousex + 20, mousemanager.mousey + 50, 300, 100);
			
			g.setColor(Color.gray.darker());
			g.fillRect(mousemanager.mousex + 21, mousemanager.mousey + 51, 299, 99);
			
			g.setColor(Color.black);
			g.drawString("Mage hood: " + mage_hood.price + " gold", mousemanager.mousex + 60, mousemanager.mousey + 110);
			
			g.setFont(new Font("Times New Roman",Font.BOLD,16));
			g.drawString("Left click to buy", mousemanager.mousex + 125, mousemanager.mousey + 130);
			g.drawString("Right click to steal", mousemanager.mousex + 118, mousemanager.mousey + 145);
		}
		
		if(mouseCollided(mage_robe.x,mage_robe.y,mage_robe.w,mage_robe.h) && location == "armorer" && mage_robe.restockTimer < 1) {
			g.setFont(new Font("Times New Roman",Font.BOLD,30));
			
			g.setColor(Color.black);
			g.drawRect(mousemanager.mousex + 20, mousemanager.mousey + 50, 300, 100);
			
			g.setColor(Color.gray.darker());
			g.fillRect(mousemanager.mousex + 21, mousemanager.mousey + 51, 299, 99);
			
			g.setColor(Color.black);
			g.drawString("Mage robe: " + mage_robe.price + " gold", mousemanager.mousex + 60, mousemanager.mousey + 110);
			
			g.setFont(new Font("Times New Roman",Font.BOLD,16));
			g.drawString("Left click to buy", mousemanager.mousex + 125, mousemanager.mousey + 130);
			g.drawString("Right click to steal", mousemanager.mousex + 118, mousemanager.mousey + 145);
		}
		
		if(mouseCollided(woodSword.x,woodSword.y,woodSword.w,woodSword.h) && location == "blacksmith" && woodSword.restockTimer < 1) {
			g.setFont(new Font("Times New Roman",Font.BOLD,30));
			
			g.setColor(Color.black);
			g.drawRect(mousemanager.mousex + 20, mousemanager.mousey + 50, 300, 100);
			
			g.setColor(Color.gray.darker());
			g.fillRect(mousemanager.mousex + 21, mousemanager.mousey + 51, 299, 99);
			
			g.setColor(Color.black);
			g.drawString("Wood sword: " + woodSword.price + " gold", mousemanager.mousex + 40, mousemanager.mousey + 110);
			
			g.setFont(new Font("Times New Roman",Font.BOLD,16));
			g.drawString("Left click to buy", mousemanager.mousex + 125, mousemanager.mousey + 130);
			g.drawString("Right click to steal", mousemanager.mousex + 118, mousemanager.mousey + 145);
		}
		
		if(mouseCollided(SteelSword.x,SteelSword.y,SteelSword.w,SteelSword.h) && location == "blacksmith" && SteelSword.restockTimer < 1) {
			g.setFont(new Font("Times New Roman",Font.BOLD,30));
			
			g.setColor(Color.black);
			g.drawRect(mousemanager.mousex + 20, mousemanager.mousey + 50, 300, 100);
			
			g.setColor(Color.gray.darker());
			g.fillRect(mousemanager.mousex + 21, mousemanager.mousey + 51, 299, 99);
			
			g.setColor(Color.black);
			g.drawString("Steel sword: " + SteelSword.price + " gold", mousemanager.mousex + 47, mousemanager.mousey + 110);
			
			g.setFont(new Font("Times New Roman",Font.BOLD,16));
			g.drawString("Left click to buy", mousemanager.mousex + 125, mousemanager.mousey + 130);
			g.drawString("Right click to steal", mousemanager.mousex + 118, mousemanager.mousey + 145);
		}
		
		if(mouseCollided(Shank.x,Shank.y,Shank.w,Shank.h) && location == "blacksmith" && Shank.restockTimer < 1) {
			g.setFont(new Font("Times New Roman",Font.BOLD,30));
			
			g.setColor(Color.black);
			g.drawRect(mousemanager.mousex + 20, mousemanager.mousey + 50, 300, 100);
			
			g.setColor(Color.gray.darker());
			g.fillRect(mousemanager.mousex + 21, mousemanager.mousey + 51, 299, 99);
			
			g.setColor(Color.black);
			g.drawString("Shank: " + Shank.price + " gold", mousemanager.mousex + 90, mousemanager.mousey + 110);
			
			g.setFont(new Font("Times New Roman",Font.BOLD,16));
			g.drawString("Left click to buy", mousemanager.mousex + 125, mousemanager.mousey + 130);
			g.drawString("Right click to steal", mousemanager.mousex + 118, mousemanager.mousey + 145);
		}
		
		if(mouseCollided(Dagger.x,Dagger.y,Dagger.w,Dagger.h) && location == "blacksmith" && Dagger.restockTimer < 1) {
			g.setFont(new Font("Times New Roman",Font.BOLD,30));
			
			g.setColor(Color.black);
			g.drawRect(mousemanager.mousex + 20, mousemanager.mousey + 50, 300, 100);
			
			g.setColor(Color.gray.darker());
			g.fillRect(mousemanager.mousex + 21, mousemanager.mousey + 51, 299, 99);
			
			g.setColor(Color.black);
			g.drawString("Dagger: " + Dagger.price + " gold", mousemanager.mousex + 85, mousemanager.mousey + 110);
			
			g.setFont(new Font("Times New Roman",Font.BOLD,16));
			g.drawString("Left click to buy", mousemanager.mousex + 125, mousemanager.mousey + 130);
			g.drawString("Right click to steal", mousemanager.mousex + 118, mousemanager.mousey + 145);
		}
		
		if(state == "game") {
			goldDisplay.showText("Gold: " + player.gold, g);
		}
		
		//quest journal
		if(questJournal.visible == true) {
			g.drawImage(journal.guy,questJournal.x,questJournal.y,null);
			
			if(questJournal.quests[0] != null) {
				questJournal.slot1.showText("" + questJournal.quests[0].name + ": " + questJournal.quests[0].objective, g);
				questJournal.slot1L2.showText("Exp: " + questJournal.quests[0].xp + " Gold: " + questJournal.quests[0].gold, g);
			}
		}
		
		//shows the custom cursor image at the mouse's x and y
		g.drawImage(cursor.guy, mousemanager.mousex, mousemanager.mousey, null);
	}
	
	public void update2(Graphics graphics) {
		slotCheck();
	}
	
	public void update(Graphics graphics) {
		if(state == "game") {	//graphics that show while you are in the game
			//constantly checks if the player should level up
			player.levelUp();
			
			if(player.hp < 0) {
				player.hp = 0;
			}
			
			if(guardRespawn > 0) {
				guardRespawn--;
				System.out.println("" + guardRespawn);
			}
			
			if(guard.hp < -1) {
				guard.hp = 0;
			}
			
			
			if(guard.hp == 0) {
				guardRespawn = 12000;
				player.xp += 15;
				guard.hp = -1;
			}
			
			if(guardRespawn == 0) {
				guard.hp = guard.maxHP;
				guardRespawn = -1;
			}
			
			if(location == "armorer" || location == "blacksmith") {
				if(mouseCollided(armorerClerk.x,armorerClerk.y,armorerClerk.w,armorerClerk.h)) {
					if(mousemanager.isLeftPressed() && pressDelay == 0) {
						talkingToClerk = true;
						talkingToCitizen = false;
					}
				}
			}
			
			if(mouseCollided(jim.x,jim.y,jim.w,jim.h)) {
				if(mousemanager.isLeftPressed() && pressDelay == 0 && location == jim.location) {
					talkingToCitizen = true;
					dialogueSlide = 0;
				}
			}
			
			//draws the location
			if(location == "town") {
				g.drawImage(town.guy,0,0,null);
			}
			else {	//draws the armor store
				if(location == "armorer") {
					g.drawImage(armorer.guy,0,0,null);
					if(armorRestock == 0) {	//this is a timer for how long it disappears
						armorVisible = true;	//while this is true it shows the armor
					}
					
					g.drawImage(armorClerk.guy, armorerClerk.x, armorerClerk.y, null);
				}
				else {
					if(location == "woods") {
						if(chopped == -3) {
							g.drawImage(woods.guy, 0, 0, null);
						}
						else {
							if(chopped > 0) {
								g.drawImage(chop.guy,0,0,null);
							}
							else {
								if(chopped == 0) {
									g.drawImage(woodsNormal.guy, 0, 0, null);
								}
							}
						}
					}
					else {
						if(location == "castle" || location == "castle2") {
							g.drawImage(castle.guy, 0, 0, null);
						}
						else {
							if(location == "blacksmith" || location == "blacksmith2") {
								g.drawImage(blacksmith.guy, 0, 0, null);
								g.drawImage(armorClerk.guy, armorerClerk.x, armorerClerk.y, null);
							}
							else {
								if(location == "town2") {
									g.drawImage(town2.guy, 0, 0, null);
								}
								else {
									if(location == "woods2") {
										g.drawImage(woods2.guy, 0, 0, null);
									}
									else {
										if(location == "woods3") {
											g.drawImage(woods3.guy, 0, 0, null);
										}
										else {
											if(location == "woods4") {
												g.drawImage(woods4.guy, 0, 0, null);
											}
											else {
												if(location == "woods5") {
													g.drawImage(woods5.guy, 0, 0, null);
												}
												else {
													if(location == "woods6") {
														g.drawImage(woods6.guy, 0, 0, null);
													}
													else {
														if(location == "woods7") {
															g.drawImage(woods7.guy, 0, 0, null);
														}
														else {
															if(location == "camp") {
																g.drawImage(camp.guy, 0, 0, null);
															}
															else {
																if(location == "armorer2") {
																	g.drawImage(armorer.guy,0,0,null);
																	g.drawImage(armorClerk.guy, armorerClerk.x, armorerClerk.y, null);
																}
																else {
																	if(location == "magic shop") {
																		g.drawImage(armorer.guy,0,0,null);
																		g.drawImage(armorClerk.guy, armorerClerk.x, armorerClerk.y, null);
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
			
			//armor in the shop
			if(location == "armorer" && armorVisible) {	//draws the armor if armorVisible is true
				g.drawImage(leatherHelm.guy, armorx, armory, null);
			}
			
			if(location == "armorer" && leather_chest.restockTimer == 0) {
				g.drawImage(leatherChest_I.guy, leather_chest.x, leather_chest.y, null);
			}
			
			if(location == "armorer" && thief_hood.restockTimer == 0) {
				g.drawImage(thiefHelm.guy, thief_hood.x, thief_hood.y, null);
			}
			
			if(location == "armorer" && thief_robe.restockTimer == 0) {
				g.drawImage(thiefRobe_I.guy, thief_robe.x, thief_robe.y, null);
			}
			
			if(location == "armorer" && mage_hood.restockTimer == 0) {
				g.drawImage(hoodHelm.guy, mage_hood.x, mage_hood.y, null);
			}
			
			if(location == "armorer" && mage_robe.restockTimer == 0) {
				g.drawImage(mageRobe_I.guy, mage_robe.x, mage_robe.y, null);
			}
			
			//weapons in the shop
			if(location == "blacksmith" && woodSword.restockTimer == 0) {
				g.drawImage(woodenSword_I.guy, woodSword.x, woodSword.y, null);
			}
			
			if(location == "blacksmith" && SteelSword.restockTimer == 0) {
				g.drawImage(steelSword_I.guy, SteelSword.x, SteelSword.y, null);
			}
			
			if(location == "blacksmith" && Dagger.restockTimer == 0) {
				g.drawImage(dagger_I.guy, Dagger.x, Dagger.y, null);
			}
			
			if(location == "blacksmith" && Shank.restockTimer == 0) {
				g.drawImage(shank_I.guy, Shank.x, Shank.y, null);
			}
			
			if(location == "armorer2" && steel_chest.restockTimer == 0) {
				g.drawImage(steelChest_I.guy,steel_chest.x,steel_chest.y,null);
			}
			
			if(location == "armorer2" && steel_helm.restockTimer == 0) {
				g.drawImage(steelHelm.guy,steel_helm.x,steel_helm.y,null);
			}
			
			if(location == "armorer2" && steel_leggings.restockTimer == 0) {
				g.drawImage(steelLeggings_I.guy,steel_leggings.x,steel_leggings.y,null);
			}
			
			
			//draws the character
			g.drawImage(head.guy,32*16,32*10,null);	//32 is the tile size, so this chooses how many tiles over it is
			g.drawImage(torso.guy,32*16,32*10, null);
			g.drawImage(legs.guy,32*16,32*10,null);
			
			if(launchingFireball) {
				g.drawImage(fire_ball.guy, fireballX, fireballY, null);
				
				if(target != null) {
					if(fireballX + 32 > target.x && fireballX < target.x + target.w && fireballY + 32 > target.y && fireballY < target.y + target.h) {
						launchingFireball = false;
					}
					else {
						if(fireballX > target.x + target.w) {
							fireballX--;
						}
						else {
							if(fireballX + 32 < target.x) {
								fireballX++;
							}
							else {
								target.hp -= 20;
								dialogueSlide = -4;
								pressDelay = 40;
								hostile = true;
								turn = "guard";
								launchingFireball = false;
							}
						}
						
						if(fireballY > target.y + target.h) {
							fireballY--;
						}
						else {
							if(fireballY + 32 < target.y) {
								fireballY++;
							}
						}
					}
				}
				
				if(enemyTarget != null) {
					g.drawImage(fire_ball.guy, fireballX, fireballY, null);
						
					if(fireballX + 32 > enemyTarget.x && fireballX < enemyTarget.x + enemyTarget.w && fireballY + 32 > enemyTarget.y && fireballY < enemyTarget.y + enemyTarget.h) {
						launchingFireball = false;
					}
					else {
						if(fireballX > enemyTarget.x + enemyTarget.w) {
							fireballX--;
						}
						else {
							if(fireballX + 32 < enemyTarget.x) {
								fireballX++;
							}
						}
							
						if(fireballY > enemyTarget.y + enemyTarget.h) {
							fireballY--;
						}
						else {
							if(fireballY + 32 < enemyTarget.y) {
								fireballY++;
							}
							else {
								enemyTarget.hp -= 20;
								dialogueSlide = -4;
								pressDelay = 40;
								enemyTarget.hostile = true;
								turn = "chicken";	//use toString() so the turn isnt just chicken
								System.out.println(turn);
								launchingFireball = false;
							}
						}
					}
				}
				
			}
			
			
			if(launchingFireball == false) {
				fireballX = 32*16;
				fireballY = 32*10;
			}
			
			//draws equipment
			//helmets
			if(player.helmet == "leather helmet") {
				g.drawImage(leatherHelm.guy, 32*16, 32*10, null);
			}
			else {
				if(player.helmet == "mage hood") {
					g.drawImage(hoodHelm.guy, 32*16, 32*10, null);
				}
				else {
					if(player.helmet == "thief hood") {
						g.drawImage(thiefHelm.guy, 32*16, 32*10, null);
					}
				}
			}
			
			//chest
			if(player.chest == "leather chest") {
				g.drawImage(leatherChest.guy, 32*16, 32*10, null);
			}
			else {
				if(player.chest == "mage robe") {
					g.drawImage(robeChest.guy, 32*16, 32*10, null);
				}
				else {
					if(player.chest == "thief robe") {
						g.drawImage(thiefChest.guy, 32*16, 32*10, null);
					}
				}
			}
			
			//legs
			
			
			//weapon
			if(playerWeapon == "woodenSword") {
				g.drawImage(woodenSword.guy, 32*16, 32*10, null);
				manaBonus = 0;
			}
			else {
				if(playerWeapon == "steelSword") {
					g.drawImage(steelSword.guy, 32*16, 32*10, null);
					manaBonus = 0;
				}
				else {
					if(playerWeapon == "wand") {
						g.drawImage(wand.guy, 32*16, 32*10, null);
						manaBonus = 10;
					}
					else {
						if(playerWeapon == "staff") {
							g.drawImage(staff.guy, 32*16, 32*10, null);
							manaBonus = 25;
						}
						else {
							if(playerWeapon == "shank") {
								g.drawImage(shank.guy, 32*16, 32*10, null);
								manaBonus = 0;
							}
							else {
								if(playerWeapon == "dagger") {
									g.drawImage(dagger.guy, 32*16, 32*10, null);
									manaBonus = 0;
								}
							}
						}
					}
				}
			}
			
			//draws the in-game UI
			g.setColor(Color.gray.darker());
			g.fillRect(1085, 0, WIDTH - 1080, HEIGHT);
			
			g.setColor(Color.black);
			g.drawRect(1085, 0, WIDTH - 1080, HEIGHT);
			
			g.setColor(Color.gray.darker());
			g.fillRect(2, HEIGHT - 149, WIDTH - 4, 148);
			
			if(inventoryVisible) {
				g.drawImage(backpack.guy, 1085, 0, null);
			}
			else {
				g.drawImage(magic.guy, 1085, 0, null);
			}
			
			g.setColor(Color.black);
			
			//shows the head in the corner (in game) while dialogue is closed
			if(talkingToClerk == false && talkingToGuard == false && talkingToCitizen == false) {
				if(player.race == "orc") {
					g.drawImage(toolbar_orc.guy, 2, HEIGHT - 149, null);
				}
				else {
					g.drawImage(toolbar_human.guy, 2, HEIGHT - 149, null);
				}
				
				if(player.helmet == "leather helmet") {
					g.drawImage(toolbar_leather.guy, 2, HEIGHT - 149, null);
				}
				else {
					if(player.helmet == "thief hood") {
						g.drawImage(toolbar_thief.guy, 2, HEIGHT - 149, null);
					}
					else {
						if(player.helmet == "mage hood") {
							g.drawImage(toolbar_hood.guy, 2, HEIGHT - 149, null);
						}
					}
				}
			}
			
			if(talkingToGuard) {
				if(wanted && location == "town" && guard.hp > 0) {
					g.drawImage(toolbar_human.guy, 2, HEIGHT - 149, null);
					g.drawImage(toolbar_steel.guy, 2, HEIGHT - 145, null);
				}
			}
			
			
			if(dialogueSlide < 5) {
				if(location == "armorer" || location == "town" || location == "blacksmith") {
					if(talkingToCitizen) {
						g.drawImage(toolbar_human.guy, 2, HEIGHT - 149, null);
						g.drawImage(toolbar_leather.guy, 2, HEIGHT - 149, null);
					}
				}
			}
			
			if(dialogueSlide == 0 || dialogueSlide == 1) {
				if(talkingToClerk) {
					if(location == "armorer" || location == "blacksmith") {
						g.drawImage(toolbar_human.guy, 2, HEIGHT - 149, null);
						g.drawImage(toolbar_leather.guy, 2, HEIGHT - 149, null);
					}
				}
			}
			else {
				if(location == "armorer" || location == "blacksmith") {
					if(pressDelay == 0 && talkingToCitizen == false && talkingToClerk == false) {
						dialogueSlide = 0;
						talkingToClerk = false;
					}
				}
			}
		
			g.drawRect(2, HEIGHT - 149, WIDTH - 4, 148);
			g.drawRect(2, HEIGHT - 149, 32*5 - 10, 32*4);
			
			if(talkingToClerk == false && talkingToCitizen == false) {
				if(wanted && location != "town") {
					console.showText(">> Theft Failed: you've been caught", g);
				}
			}
			
			if(hostile) {
				talkingToGuard = false;
			}

			if(wanted && location == "town" && guard.hp > 0) {
				talkingToGuard = true;
				
				if(dialogueSlide == 0 && talkingToGuard) {
					console.showText(caughtDialogue.slides[0], g);
				}
				else {
					if(dialogueSlide == 1 && talkingToGuard) {
						console.showText(caughtDialogue.slides[1], g);
					}
				}
			}
			
			if(hostile) {
				talkingToGuard = false;
			}
			
			if(talkingToClerk && talkingToCitizen == false) {
				if(location == "armorer" || location == "blacksmith") {
					if(dialogueSlide == 0) {
						console.showText(armorerDialogue.slides[0], g);
					}
					else {
						if(dialogueSlide == 1) {
							console.showText(armorerDialogue.slides[1], g);
						}
					}
				}
			}
			
			if(talkingToCitizen) {
				if(weirdTree.finished) {
					console.showText(">> Citizen: Hey!", g);
				}
				else {
					if(dialogueSlide < 5 && dialogueSlide > -1) {
						if(chopped == -3) {
							console.showText(citizenDialogue.slides[dialogueSlide], g);
						}
						else {
							dialogueSlide = 2;
							console.showText(">> Citizen: Thanks odio. Here's your reward.", g);
						}
					}
				}
			}
			
			if(dialogueSlide == -1) {
				console.showText(">> Health lost: " + guard.damage, g);
			}
			
			if(dialogueSlide == -3) {
				console.showText(">> Health lost: " + chicken.damage, g);
			}
			
			if(dialogueSlide == -2) {
				console.showText(">> Damage delt: " + damage, g);;
			}
			
			if(dialogueSlide == -4) {
				console.showText(">> Damage delt: 20", g);;
			}
			
			if(dialogueSlide == 2 && location == "town" && talkingToCitizen == false) {
				turn = "guard";
				wanted = false;
				hostile = true;
			}
			
			if(location == "town" && guard.hp > 0) {
				g.drawImage(guardImage.guy, guard.x, guard.y, null);
			}
			
			if(location == jim.location) {
				g.drawImage(armorClerk.guy, jim.x, jim.y, null);
			}
			
			if(wanted && location == "town" && dialogueSlide > -1 && guard.hp > 0) {	//this if statement will change as more towns are added
				if(okButton.active) {
					g.drawImage(okActive.guy, okButton.x, okButton.y, null);
				}
				else {
					g.drawImage(ok.guy, okButton.x, okButton.y, null);
				}
			}
			
			if(talkingToClerk && location == "armorer" && dialogueSlide > -1 && dialogueSlide < 2) {	//this if statement will change as more towns are added
				if(okButton.active) {
					g.drawImage(okActive.guy, okButton.x, okButton.y, null);
				}
				else {
					g.drawImage(ok.guy, okButton.x, okButton.y, null);
				}
			}
			
			if(talkingToClerk && location == "blacksmith" && dialogueSlide > -1 && dialogueSlide < 2) {	//this if statement will change as more towns are added
				if(okButton.active) {
					g.drawImage(okActive.guy, okButton.x, okButton.y, null);
				}
				else {
					g.drawImage(ok.guy, okButton.x, okButton.y, null);
				}
			}
			
			if(talkingToGuard && wanted) {
				if(chopped == -3 && dialogueSlide != 3) {
					if(noButton.active) {
						g.drawImage(noActive.guy, noButton.x, noButton.y, null);
					}
					else {
						g.drawImage(no.guy, noButton.x, noButton.y, null);
					}
				}
			}
			
			if(weirdTree.finished) {
				if(talkingToCitizen && dialogueSlide > -1 && dialogueSlide < 1) {	//this if statement will change as more towns are added
					if(okButton.active) {
						g.drawImage(okActive.guy, okButton.x, okButton.y, null);
					}
					else {
						g.drawImage(ok.guy, okButton.x, okButton.y, null);
					}
				}
			}
			else {
				if(talkingToCitizen && dialogueSlide > -1 && dialogueSlide < 5) {	//this if statement will change as more towns are added
					if(okButton.active) {
						g.drawImage(okActive.guy, okButton.x, okButton.y, null);
					}
					else {
						g.drawImage(ok.guy, okButton.x, okButton.y, null);
					}
				}
				
				if(chopped == -3 && dialogueSlide != 3) {
					if(noButton.active) {
						g.drawImage(noActive.guy, noButton.x, noButton.y, null);
					}
					else {
						g.drawImage(no.guy, noButton.x, noButton.y, null);
					}
				}
			}
			
			
			if(south.active) {
				g.drawImage(southButtonActive.guy, south.x, south.y, null);
			}
			else {
				g.drawImage(southButton.guy, south.x, south.y, null);
			}
			
			if(north.active) {
				g.drawImage(northButtonActive.guy, north.x, north.y, null);
			}
			else {
				g.drawImage(northButton.guy, north.x, north.y, null);
			}
			
			g.drawImage(compass.guy, north.x, north.y+50, null);
			
			if(west.active) {
				g.drawImage(leftActive.guy, west.x, west.y, null);
			}
			else {
				g.drawImage(left.guy, west.x, west.y, null);
			}
			
			if(east.active) {
				g.drawImage(rightActive.guy, east.x, east.y, null);
			}
			else {
				g.drawImage(right.guy, east.x, east.y, null);
			}
			
			
			if(location == "woods2") {
				predictor.calcPercentError(13, surroundingLocations);
				predictor.predict(13,surroundingLocations);
			}
			
			
			if(location == "woods" && chopped == -3) {
				if(mouseCollided(32*3,32*3,32,32) && weirdTree.finished == false) {	//chopping down weird tree
					g.setFont(new Font("Times New Roman",Font.BOLD,30));
					
					g.setColor(Color.black);
					g.drawRect(mousemanager.mousex + 20, mousemanager.mousey + 50, 300, 100);
					
					g.setColor(Color.gray.darker());
					g.fillRect(mousemanager.mousex + 21, mousemanager.mousey + 51, 299, 99);
					
					g.setColor(Color.black);
					g.drawString("Weird tree", mousemanager.mousex + 100, mousemanager.mousey + 110);
					
					g.setFont(new Font("Times New Roman",Font.BOLD,16));
					g.drawString("click to chop", mousemanager.mousex + 125, mousemanager.mousey + 130);
					
					if(mousemanager.isLeftPressed() && pressDelay == 0) {
						chopped = 10000;
						pressDelay = 15;
					}
				}
			}
			
			//directional buttons
			if(mouseCollided(south.x,south.y,south.w,south.h)) {
				south.active = true;
				
				if(mousemanager.isLeftPressed() && pressDelay == 0) {
					if(location == "town") {
						location = "woods";
						selectingTarget = false;
						talkingToCitizen = false;
						dialogueSlide = 0;
						//ann.adjustLocationConnections(true, 1, 0, ann.loc2Connections);
						//ann.assignInputs(1);
						//ann.calculateOutput(0.54);
						woodsMonsterChance = random.nextInt(10);
						pressDelay = 15;
					}
					else {
						if(location == "woods") {
							location = "town2";
							pressDelay = 15;
						}
						else {
							if(location == "woods2") {
								location = "woods3";
								//ann.adjustLocationConnections(true, 3, 3,ann.loc2Connections);
								//ann.adjustLocationConnections(true, 1, 2, ann.loc3Connections);
								//ann.assignInputs(3);
								//ann.calculateOutput(0.1);
								pressDelay = 15;
							}
							else {
								if(location == "woods4") {
									location = "woods2";
									//ann.adjustLocationConnections(true, 1, 4,ann.loc2Connections);
									//ann.adjustLocationConnections(true, 3, 2, ann.loc4Connections);
									//ann.assignInputs(2);
									//ann.calculateOutput(0.70);
									pressDelay = 15;
								}
								else {
									if(location == "woods5") {
										location = "woods6";
										//ann.adjustLocationConnections(true, 3, 6,ann.loc5Connections);
										//ann.adjustLocationConnections(true, 1, 5, ann.loc6Connections);
										//ann.assignInputs(6);
										//ann.calculateOutput(0.5);
										pressDelay = 15;
									}
									else {
										if(location == "camp") {
											location = "woods7";
											pressDelay = 15;
										}
									}
								}
							}
						}
					}
				}
			}
			else {
				south.active = false;
			}
		
			
			if(mouseCollided(east.x,east.y,east.w,east.h)) {
				east.active = true;
				
				if(mousemanager.isLeftPressed() && pressDelay == 0) {
					if(location == "woods2") {
						location = "woods";
						selectingTarget = false;
						talkingToCitizen = false;
						dialogueSlide = 0;
						//ann.adjustLocationConnections(true, 0, 2,ann.loc1Connections);
						//ann.adjustLocationConnections(true, 2, 1, ann.loc2Connections);
						//ann.assignInputs(1);
						//ann.calculateOutput(0.54);
						//System.out.println("" + ann.decisionNode.outputValue);
						woodsMonsterChance = random.nextInt(10);
						pressDelay = 15;
					}
					else {
						if(location == "woods3") {
							location = "town2";
							pressDelay = 15;
						}
						else {
							if(location == "woods5") {
								location = "woods2";
								//ann.adjustLocationConnections(true, 2, 2,ann.loc5Connections);
								//ann.adjustLocationConnections(true, 0, 5, ann.loc2Connections);
								//ann.assignInputs(2);
								//ann.calculateOutput(0.70);
								pressDelay = 15;
							}
							else {
								if(location == "woods6") {
									location = "woods3";
									pressDelay = 15;
								}
								else {
									if(location == "woods7") {
										location = "woods6";
										pressDelay = 15;
									}
								}
							}
						}
					}
				}
			}
			else {
				east.active = false;
			}
			
			if(mouseCollided(west.x,west.y,west.w,west.h)) {
				west.active = true;
				
				if(mousemanager.isLeftPressed() && pressDelay == 0) {
					if(location == "woods") {
						location = "woods2";
						woodsMonsterChance = 0;
						selectingTarget = false;
						talkingToCitizen = false;
						dialogueSlide = 0;
						pressDelay = 15;
					}
					else {
						if(location == "town2") {
							woodsMonsterChance = 0;
							location = "woods3";
							pressDelay = 15;
						}
						else {
							if(location == "woods2") {
								location = "woods5";
								//ann.adjustLocationConnections(true, 0, 5,ann.loc2Connections);
								//ann.adjustLocationConnections(true, 2, 2, ann.loc5Connections);
								//ann.assignInputs(5);
								//ann.calculateOutput(0.3);
								//System.out.println("" + ann.decisionNode.outputValue);
								pressDelay = 15;
							}
							else {
								if(location == "woods3") {
									location = "woods6";
									pressDelay = 15;
								}
								else {
									if(location == "woods6") {
										location = "woods7";
										pressDelay = 15;
									}
								}
							}
						}
					}
				}
			}
			else {
				west.active = false;
			}
			
			
			if(location == "woods" && wanted) {
				hostile = true;
				wanted = false;
			}
			
			if(mouseCollided(north.x,north.y,north.w,north.h)) {
				north.active = true;
				
				if(mousemanager.isLeftPressed() && pressDelay == 0) {
					if(location == "woods") {
						chicken.hostile = false;
						location = "town";
						selectingTarget = false;
						if(wanted) {
							hostile = true;;
							wanted = false;
						}
						
						if(hostile) {
							turn = "guard";
						}
						
						pressDelay = 15;
					}
					else {
						if(location == "town2") {
							location = "woods";
							woodsMonsterChance = random.nextInt(10);
							pressDelay = 15;
						}
						else {
							if(location == "woods3") {
								location = "woods2";
								//ann.adjustLocationConnections(true, 1, 2,ann.loc3Connections);
								//ann.adjustLocationConnections(true, 3, 3, ann.loc2Connections);
								//ann.assignInputs(2);
								//ann.calculateOutput(0.71);
								//System.out.println("" + ann.decisionNode.outputValue);
								pressDelay = 15;
							}
							else {
								if(location == "woods2") {
									location = "woods4";
									//ann.adjustLocationConnections(true, 3, 2,ann.loc4Connections);
									//ann.adjustLocationConnections(true, 1, 4, ann.loc2Connections);
									//ann.assignInputs(4);
									//ann.calculateOutput(0.1);
									//System.out.println("" + ann.decisionNode.outputValue);
									pressDelay = 15;
								}
								else {
									if(location == "woods6") {
										location = "woods5";
										pressDelay = 15;
									}
									else {
										if(location == "woods7") {
											location = "camp";
											pressDelay = 15;
										}
									}
								}
							}
						}
					}
				}
			}
			else {
				north.active = false;
			}
			
			if(woodsMonsterChance > 4 && location == "woods" && dialogueSlide == 0) {
				console.showText(">> A chicken has appeared", g);
				caught = 20;
			}
			
			if(woodsMonsterChance > 4 && location == "woods" && chicken.hp > 0) {
				g.drawImage(chicken_i.guy, chicken.x, chicken.y,null);
			}
			
			if(location != "woods") {
				chicken.hp = chicken.maxHP;
			}
			
			if(location == "woods") {
				if(chicken.hp < 1 || woodsMonsterChance < 5) {
					dialogueSlide = 0;
					g.setColor(Color.gray.darker());
					g.fillRect(console.x, console.y - 30, 500, 50);
					console.showText(">> You see a town to the north and a town to the south", g);
				}
			}
			
			if(location == "woods2") {
				if(chicken.hp < 1 || woodsMonsterChance < 5) {
					dialogueSlide = 0;
					g.setColor(Color.gray.darker());
					g.fillRect(console.x, console.y - 30, 500, 50);
					console.showText(">> You can't see any nearby exit from the woods", g);
				}
			}
			
			if(location == "camp") {
				if(chicken.hp < 1 || woodsMonsterChance < 5) {
					dialogueSlide = 0;
					g.setColor(Color.gray.darker());
					g.fillRect(console.x, console.y - 30, 500, 50);
					console.showText(">> You can't see any nearby exit from the woods", g);
				}
			}
			
			if(location == "woods7") {
				if(chicken.hp < 1 || woodsMonsterChance < 5) {
					dialogueSlide = 0;
					g.setColor(Color.gray.darker());
					g.fillRect(console.x, console.y - 30, 500, 50);
					console.showText(">> You see a small camp to the north", g);
				}
			}
			
			if(location == "woods4") {
				if(chicken.hp < 1 || woodsMonsterChance < 5) {
					dialogueSlide = 0;
					g.setColor(Color.gray.darker());
					g.fillRect(console.x, console.y - 30, 500, 50);
					console.showText(">> You can't see any nearby exit from the woods", g);
				}
			}
			
			if(location == "woods6") {
				if(chicken.hp < 1 || woodsMonsterChance < 5) {
					dialogueSlide = 0;
					g.setColor(Color.gray.darker());
					g.fillRect(console.x, console.y - 30, 500, 50);
					console.showText(">> You see a dungeon off to the side... and it's, unnervingly clean...", g);
				}
			}
			
			if(location == "woods5") {
				if(chicken.hp < 1 || woodsMonsterChance < 5) {
					dialogueSlide = 0;
					g.setColor(Color.gray.darker());
					g.fillRect(console.x, console.y - 30, 500, 50);
					console.showText(">> You can't see any nearby exit from the woods", g);
				}
			}
			
			if(location == "woods3") {
				if(chicken.hp < 1 || woodsMonsterChance < 5) {
					dialogueSlide = 0;
					g.setColor(Color.gray.darker());
					g.fillRect(console.x, console.y - 30, 500, 50);
					console.showText(">> You see a town to the east", g);
				}
			}
			
			if(location == "armorer") {
				if(talkingToClerk == false && talkingToCitizen == false && caught > 9) {
					console.showText(">> You see a variety of armor for sale on the counter", g);
				}
			}
			
			if(location == "castle") {
				if(talkingToClerk == false && talkingToCitizen == false && caught > 9) {
					console.showText(">> The castle is very fancy, with just the king inside", g);
				}
			}
			
			if(location == "blacksmith") {
				if(talkingToClerk == false && talkingToCitizen == false && caught > 9) {
					console.showText(">> You see a variety of weapons for sale on the counter", g);
				}
			}
			
			if(location == "town") {
				if(talkingToCitizen == false && wanted == false && dialogueSlide == 0) {
					console.showText(">> You are in a town with 2 houses, a castle, armorer, and a blacksmith", g);
				}
			}
			
			if(location == "town2") {
				if(talkingToCitizen == false && wanted == false && dialogueSlide == 0) {
					console.showText(">> You are in a town with 3 houses, a castle, armorer, blacksmith, and a magic shop", g);
				}
			}
			
			
			//xp and rewards
			if(chicken.hp <= 0 && location == "woods") {
				if(chicken.hp != -2) {
					chicken.hp = -1;
				}
			}
			
			
			if(chicken.hp == -1) {
				player.xp += 5;
				chicken.hostile = false;
				woodsMonsterChance = 0;
				chicken.hp = -2;
			}

			
			//guard attacking
			if(mouseCollided(guard.x,guard.y,guard.w,guard.h) && location == "town") {
				if(mousemanager.isLeftPressed() && pressDelay == 0) {
					if(selectingTarget == true) {
						target = guard;
						
						if(player.mana + manaBonus >= 25) {
							player.mana -= 25;
							player.manaRegenTimer = 50;
							launchingFireball = true;
						}
						else {
							selectingTarget = false;
						}
					}
					else {
						//algorythm that decides how much damage you deal
						
						if(lastDamageDelt == 0) {
							damage = ((weaponDamage*player.str - (weaponDamage*player.str / player.luck)) / (random.nextInt(player.luck - random.nextInt(5)) + 1));
							lastDamageDelt = damage;
						}
						else {
							lastDamageDelt = damage;
							damage = ((weaponDamage*player.str - (weaponDamage*player.str / player.luck)) / (random.nextInt(player.luck - random.nextInt(5)) + 1));
						}
						
						guard.hp -= damage;
						dialogueSlide = -2;
						hostile = true;
						turn = "guard";
						pressDelay = 40;
					}
				}
			}
			
			if(hostile && location == "town") {
				if(turn == "guard" && guard.hp > 0 && pressDelay == 0) {
					armorCheck();
					guard.calcDamage(helm, ch, pa);
					player.hp -= guard.damage;
					dialogueSlide = -1;
					turn = "you";
				}
			}
			
			if(location == "woods" && chicken.hostile) {
				if(turn == "chicken" && chicken.hp > 0 && pressDelay == 0) {
					chicken.calcDamage();
					player.hp -= chicken.damage;
					dialogueSlide = -3;
					turn = "you";
				}
			}
			
			if(pressDelay < 1 && hostile && dialogueSlide == -2) {
				turn = "guard";
			}
			
			if(pressDelay < 1 && chicken.hostile && dialogueSlide == -2) {
				turn = "chicken";
			}
			
			if(mouseCollided(chicken.x,chicken.y,chicken.w,chicken.h)) {
				if(mousemanager.isLeftPressed() && pressDelay == 0) {
					if(selectingTarget == true) {
						enemyTarget = chicken;
						
						if(player.mana + manaBonus >= 25) {
							player.mana -= 25;
							player.manaRegenTimer = 50;
							launchingFireball = true;
							selectingTarget = false;
						}
						else {
							selectingTarget = false;
						}
					}
					else {
						if(mousemanager.isLeftPressed() && pressDelay == 0 && location == "woods") {
							damage = ((weaponDamage*player.str - (weaponDamage*player.str / player.luck)) / (random.nextInt(player.luck - random.nextInt(5)) + 1));
							chicken.hp -= damage;
							dialogueSlide = -2;
							chicken.hostile = true;
							pressDelay = 40;
						}
					}
				}
			}
			
			
			//health bar
			g.setColor(Color.red);
			//fills the bar based with red on your hp, the math extends the bar so it's not too small
			g.fillRect(3, HEIGHT - 20, (player.hp * (player.hp / 15)), 15);
			
			g.setColor(Color.CYAN);
			g.fillRect(3+healthWidth, HEIGHT-20, (player.mana * (player.mana / 15)), 15);
			
			g.setColor(Color.black);
			//the math here sets the maximum width of the bar based on your max hp
			g.drawRect(3, HEIGHT - 20, healthWidth, 15);
			
			
			g.setColor(Color.black);
			g.drawRect(3+healthWidth, HEIGHT-20, manaWidth, 15);
			
			g.setFont(new Font("Times New Roman",Font.BOLD,15));
			//sets text that displays your hp in the middle of the health bar
			g.drawString("Hp: " + player.hp + "/" + player.maxHP, (healthWidth/2)-30, HEIGHT-8);
			g.drawString("Mana: " + (player.mana+manaBonus) + "/" + (player.maxMana+manaBonus), healthWidth+((manaWidth/2)-30), HEIGHT-8);
			
			if(buyMenuVisible) {
				g.setColor(Color.gray.darker());
				menuBox_x = armorx + 20;
				menuBox_y = armory + 30;
				menuBox_w = 200;
				menuBox_h = 100;
				
				g.fillRect(menuBox_x, menuBox_y, menuBox_w, menuBox_h);
				
				g.setColor(Color.black);
				g.drawRect(menuBox_x, menuBox_y, menuBox_w, menuBox_h);
			}
			
			
			//shows the box with the description of each building when the mouse is over it
			if(location == "town2") {
				if(mouseCollided(Armorer2.x,Armorer2.y,Armorer2.w,Armorer2.h)) {
					Armorer2.showDesc(g, mousemanager, 120, 130);
					
					if(mousemanager.isLeftPressed()) {
						location = "armorer2";
						caught = 20;
						talkingToCitizen = false;	//change later according to person in second town
						dialogueSlide = 0;
						
						if(hostile) {
							turn = "guard";
						}
					}
				}
				
				
				if(mouseCollided(blackSmith2.x,blackSmith2.y,blackSmith2.w,blackSmith2.h)) {
					blackSmith2.showDesc(g, mousemanager, 110, 130);
					
					if(mousemanager.isLeftPressed()) {
						location = "blacksmith2";
						caught = 20;
						talkingToCitizen = false;
						dialogueSlide = 0;
					}
				}
				
				if(mouseCollided(Castle2.x,Castle2.y,Castle2.w,Castle2.h)) {
					Castle2.showDeskAlt(g, mousemanager, 190, 195);
					
					if(mousemanager.isLeftPressed()) {
						location = "castle2";
						caught = 20;
						talkingToCitizen = false;
						dialogueSlide = 0;
					}
				}
				
				if(mouseCollided(magicShop.x,magicShop.y,magicShop.w,magicShop.h)) {
					magicShop.showDeskAlt(g, mousemanager, 220, 195);
					
					if(mousemanager.isLeftPressed()) {
						location = "magic shop";
						caught = 20;
						talkingToCitizen = false;
						dialogueSlide = 0;
					}
				}
			}
			
			if(location == "town") {
				if(mouseCollided(Armorer.x,Armorer.y,Armorer.w,Armorer.h)) {
					Armorer.showDesc(g, mousemanager, 120, 130);
					
					if(mousemanager.isLeftPressed()) {
						if(wanted) {
							hostile = true;
							wanted = false;
						}
						
						location = "armorer";
						caught = 20;
						talkingToCitizen = false;
						dialogueSlide = 0;
						
						if(hostile) {
							turn = "guard";
						}
					}
					
				}
				
				
				if(mouseCollided(Castle.x,Castle.y,Castle.w,Castle.h)) {
					Castle.showDesc(g, mousemanager, 135, 130);
					
					if(mousemanager.isLeftPressed() && pressDelay == 0) {
						if(wanted) {
							hostile = true;
							wanted = false;
						}
						
						location = "castle";
						talkingToCitizen = false;
						pressDelay = 15;
						dialogueSlide = 0;
						
						if(hostile) {
							turn = "guard";
						}
					}
				}
				
				if(mouseCollided(blackSmith.x,blackSmith.y,blackSmith.w,blackSmith.h)) {
					blackSmith.showDeskAlt(g, mousemanager, 220, 195);
					
					if(mousemanager.isLeftPressed() && pressDelay == 0) {
						if(wanted) {
							hostile = true;
							wanted = false;
							turn = "guard";
						}
						
						location = "blacksmith";
						caught = 20;
						talkingToCitizen = false;
						dialogueSlide = 0;
						
						if(hostile) {
							turn = "guard";
						}
					}
				}
			}
			else {
				if(location == "armorer") {
					if(mousemanager.mousex > 32*16 && mousemanager.mousex < 32*17 && mousemanager.mousey > HEIGHT-181 && mousemanager.mousey < HEIGHT-149) {
						g.setFont(new Font("Times New Roman",Font.BOLD,30));
						
						g.setColor(Color.black);
						g.drawRect(mousemanager.mousex + 20, mousemanager.mousey - 120, 300, 100);
						
						g.setColor(Color.gray.darker());
						g.fillRect(mousemanager.mousex + 21, mousemanager.mousey - 121, 299, 99);
						
						g.setColor(Color.black);
						g.drawString("Town", mousemanager.mousex + 130, mousemanager.mousey - 65);
						
						g.setFont(new Font("Times New Roman",Font.BOLD,16));
						g.drawString("click to enter", mousemanager.mousex + 125, mousemanager.mousey - 45);
					}
				}
				else {
					if(location == "castle") {
						if(mousemanager.mousex > 32*16 && mousemanager.mousex < 32*17 && mousemanager.mousey > HEIGHT-181 && mousemanager.mousey < HEIGHT-149) {
							g.setFont(new Font("Times New Roman",Font.BOLD,30));
							
							g.setColor(Color.black);
							g.drawRect(mousemanager.mousex + 20, mousemanager.mousey - 120, 300, 100);
							
							g.setColor(Color.gray.darker());
							g.fillRect(mousemanager.mousex + 21, mousemanager.mousey - 121, 299, 99);
							
							g.setColor(Color.black);
							g.drawString("Town", mousemanager.mousex + 130, mousemanager.mousey - 65);
							
							g.setFont(new Font("Times New Roman",Font.BOLD,16));
							g.drawString("click to enter", mousemanager.mousex + 125, mousemanager.mousey - 45);
							
							if(mousemanager.isLeftPressed()) {
								location = "town";
								talkingToCitizen = false;
								pressDelay = 15;
								dialogueSlide = 0;
							}
						}
					}
					else {
						if(location == "blacksmith") {
							if(mousemanager.mousex > 32*16 && mousemanager.mousex < 32*17 && mousemanager.mousey > HEIGHT-181 && mousemanager.mousey < HEIGHT-149) {
								g.setFont(new Font("Times New Roman",Font.BOLD,30));
								
								g.setColor(Color.black);
								g.drawRect(mousemanager.mousex + 20, mousemanager.mousey - 120, 300, 100);
								
								g.setColor(Color.gray.darker());
								g.fillRect(mousemanager.mousex + 21, mousemanager.mousey - 121, 299, 99);
								
								g.setColor(Color.black);
								g.drawString("Town", mousemanager.mousex + 130, mousemanager.mousey - 65);
								
								g.setFont(new Font("Times New Roman",Font.BOLD,16));
								g.drawString("click to enter", mousemanager.mousex + 125, mousemanager.mousey - 45);
								
								if(mousemanager.isLeftPressed()) {
									location = "town";
									talkingToCitizen = false;
									pressDelay = 15;
									dialogueSlide = 0;
								}
							}
						}
						else {
							if(location == "castle2" || location == "blacksmith2" || location == "armorer2" || location == "magic shop") {
								if(mousemanager.mousex > 32*16 && mousemanager.mousex < 32*17 && mousemanager.mousey > HEIGHT-181 && mousemanager.mousey < HEIGHT-149) {
									g.setFont(new Font("Times New Roman",Font.BOLD,30));
									
									g.setColor(Color.black);
									g.drawRect(mousemanager.mousex + 20, mousemanager.mousey - 120, 300, 100);
									
									g.setColor(Color.gray.darker());
									g.fillRect(mousemanager.mousex + 21, mousemanager.mousey - 121, 299, 99);
									
									g.setColor(Color.black);
									g.drawString("Town", mousemanager.mousex + 130, mousemanager.mousey - 65);
									
									g.setFont(new Font("Times New Roman",Font.BOLD,16));
									g.drawString("click to enter", mousemanager.mousex + 125, mousemanager.mousey - 45);
									
									if(mousemanager.isLeftPressed()) {
										location = "town2";
										talkingToCitizen = false;
										pressDelay = 15;
										dialogueSlide = 0;
									}
								}
							}
						}
					}
				}
			}
			
			if(mousemanager.mousex > 32*16 && mousemanager.mousex < 32*17 && mousemanager.mousey > 32*10 && mousemanager.mousey < 32*12) {
				if(mousemanager.isRightPressed()) {
					playerMenuVisible = true;
				}
			}
			else {
				if(mousemanager.isLeftPressed()) {
					if(mousemanager.mousex > 32*18 && mousemanager.mousex < 32*18 + 200 && mousemanager.mousey > 32*6 && mousemanager.mousey < 32*6 + 300) {
						
					}
					else {
						playerMenuVisible = false;
					}
				}
			}
			
			//player menu state arrow buttons
			if(mouseCollided(playerMenuLeft.x,playerMenuLeft.y,playerMenuLeft.w,playerMenuLeft.h)) {
				playerMenuLeft.active = true;
				
				if(mousemanager.isLeftPressed() && pressDelay == 0) {
					if(playerMenuState == "stats") {
						playerMenuState = "spells";
						pressDelay = 15;
					}
					else {
						if(playerMenuState == "inventory") {
							playerMenuState = "stats";
							pressDelay = 15;
						}
						else {
							if(playerMenuState == "spells") {
								playerMenuState = "inventory";
								pressDelay = 15;
							}
						}
					}
				}
			}
			else {
				playerMenuLeft.active = false;
			}
			
			if(mouseCollided(playerMenuRight.x,playerMenuRight.y,playerMenuRight.w,playerMenuRight.h)) {
				playerMenuRight.active = true;
				
				if(mousemanager.isLeftPressed() && pressDelay == 0) {
					if(playerMenuState == "inventory") {
						playerMenuState = "spells";
						pressDelay = 15;
					}
					else {
						if(playerMenuState == "spells") {
							playerMenuState = "stats";
							pressDelay = 15;
						}
						else {
							if(playerMenuState == "stats") {
								playerMenuState = "inventory";
								pressDelay = 15;
							}
						}
					}
				}
			}
			else {
				playerMenuRight.active = false;
			}
			
			//target selecting
			if(target == guard && enemyTarget == null && selectingTarget == true) {	//maybe not if it equals null
				pressDelay = 15;
				selectingTarget = false;
			}
			
			
			//shows the player stat menu
			if(playerMenuVisible) {
				g.setColor(Color.gray.darker());
				g.fillRect(32*18, 32*6, 200, 300);
				g.fillRect(32*18, 32*6, 64, 64);
				
				g.setColor(Color.black);
				g.drawRect(32*18, 32*6, 200, 300);
				g.drawRect(32*18, 32*6, 64, 64);
				
				
				//displays the player menu state arrow buttons
				if(playerMenuLeft.active == false) {
					g.drawImage(left.guy, playerMenuLeft.x, playerMenuLeft.y, null);
				}
				else {
					g.drawImage(leftActive.guy, playerMenuLeft.x, playerMenuLeft.y, null);
				}
				
				if(playerMenuRight.active == false) {
					g.drawImage(right.guy, playerMenuRight.x, playerMenuRight.y, null);
				}
				else {
					g.drawImage(rightActive.guy, playerMenuRight.x, playerMenuRight.y, null);
				}
				
				
				if(playerMenuState == "stats") {
					g.setFont(new Font("Times New Roman",Font.BOLD,18));
					g.drawString("Hp: " + player.maxHP, 32*18 + 3, 32*6 + 80);
					g.drawString("Level: " + player.level, 32*18 + 3, 32*6 + 110);
					g.drawString("Exp: " + player.xp + "/" + player.maxXP, 32*18 + 3, 32*6 + 140);
					g.drawString("Int: " + player.Int, 32*18 + 3, 32*6 + 170);
					g.drawString("Sneak: " + player.sneak, 32*18 + 3, 32*6 + 200);
					g.drawString("Speech: " + player.speech, 32*18 + 3, 32*6 + 230);
					g.drawString("Luck: " + player.luck, 32*18 + 3, 32*6 + 260);
					g.drawString("Str: " + player.str, 32*18 + 3, 32*6 + 290);
				}
				
				//shows the face
				if(player.race == "orc") {
					g.drawImage(mediumOrc.guy, 32*18, 32*6 + 15, null);
				}
				else {
					g.drawImage(mediumHuman.guy, 32*18, 32*6 + 15, null);
				}
				
				//shows your helmet
				if(player.helmet == "leather helmet") {
					g.drawImage(mediumLeather.guy, 32*18, 32*6 + 15, null);
				}
				else {
					if(player.helmet == "mage hood") {
						g.drawImage(mediumHood.guy, 32*18, 32*6 + 15, null);
					}
					else {
						if(player.helmet == "thief hood") {
							g.drawImage(mediumThief.guy, 32*18, 32*6 + 15, null);
						}
					}
				}
				
				if(talkingToCitizen) {
					g.drawImage(toolbar_human.guy, 32*18, 32*6 + 15, null);
					g.drawImage(toolbar_leather.guy, 32*18, 32*6 + 15, null);
				}
			}
		}
		
		
		if(state == "main menu") {	//graphics you see while in the main menu
			//gray background until i have a main menu image
			g.setColor(Color.gray);
			g.fillRect(0, 0, WIDTH, HEIGHT);
			
			//new game button
			if(newGameButton.active == false) {
				g.drawImage(newGame.guy,newGameButton.x,newGameButton.y,null);
			}
			else {
				g.drawImage(newGameActive.guy,newGameButton.x,newGameButton.y,null);
			}
			
			//draws the character creation menu
			if(creatingCharacter) {	//sets up the character creation screen
				//shows the head in the box
				g.setColor(Color.gray);
				g.fillRect(0, 0, WIDTH, HEIGHT);
				
				g.setColor(Color.black);
				g.drawRect(2, 2, 200, 200);
				
				g.setColor(Color.gray.darker());
				g.fillRect(3, 3, 199, 199);
				
				if(player.race == "orc") {
					g.drawImage(bigOrcHead.guy, 2, 30, null);
				}
				else {
					g.drawImage(bigHumanHead.guy, 2, 30, null);
				}
				
				//shows starter equipment on the head and sets the player's starter weapon
				if(player.Class == "warrior") {
					g.drawImage(bigLeather.guy, 3, 30, null);
					playerWeapon = "woodenSword";
				}
				else {
					if(player.Class == "thief") {
						g.drawImage(bigThief.guy, 2, 30, null);
						playerWeapon = "shank";
					}
					else {
						g.drawImage(bigHood.guy, 2, 30, null);
						playerWeapon = "wand";
					}
				}
				
				//text
				g.setColor(Color.black);
				g.setFont(new Font("Times New Roman",Font.BOLD,40));
				g.drawString("Race: " + player.race, 0, 250);
				
				g.drawString("Hp: " + (player.maxHP + hpBonus), 0, 320);
				g.drawString("Str: " + (player.str + strBonus), 0, 390);
				g.drawString("Luck: " + (player.luck + luckBonus), 0, 460);
				g.drawString("Int: " + (player.Int + intBonus), 0, 530);
				g.drawString("Sneak: " + (player.sneak + sneakBonus), 0, 600);
				g.drawString("Speech: " + (player.speech + speechBonus), 0, 670);
				g.drawString("Class: " + player.Class, 350, 460);
				
				if(skillPoints > 0) {
					g.drawString("Skill points: " + skillPoints, 420, 700);
				}
				
				//displays the done button
				if(skillPoints == 0) {
					if(doneButton.active == false) {
						g.drawImage(done.guy, doneButton.x, doneButton.y, null);
					}
					else {
						g.drawImage(doneActive.guy, doneButton.x, doneButton.y, null);
					}
				}

				
				//changes the arrow buttons' x based on the race(so it doesn't sit on top of the text)
				if(player.race == "orc") {
					raceLeft.x = 170;
					raceRight.x = 230;
				}
				else {
					raceLeft.x = 230;
					raceRight.x = 290;
				}
				
				//displays the race arrow buttons
				if(raceLeft.active == false) {
					g.drawImage(left.guy, raceLeft.x, raceLeft.y, null);
				}
				else {
					g.drawImage(leftActive.guy, raceLeft.x, raceLeft.y, null);
				}
				
				if(raceRight.active == false) {
					g.drawImage(right.guy, raceRight.x, raceRight.y, null);
				}
				else {
					g.drawImage(rightActive.guy, raceRight.x, raceRight.y, null);
				}
				
				
				//only shows the increasing arrow if your hp is 50
				if(player.maxHP ==  50) {
					hpRight.x = 120;
					hpLeft.x = -50;	//moves the left arrow so both aren't clicked at the same time
				}
				else {
					if(player.hp == 55 && hpBonus == 5) {	//fixes the arrows moving when 55 isn't the minimum
						hpRight.x = 120;
						hpLeft.x = -50;
					}
					else {
						//puts both in their original positions
						hpLeft.x = 120;
						hpRight.x = 180;
					}
				}

				
				//only shows the increasing arrow if your str is 10
				if(player.str == 10) {
					strRight.x = 120;
					strLeft.x = -50;	//moves the left arrow so both aren't clicked at the same time
				}
				else {
					if(player.str == 15 && strBonus == 5) {
						strRight.x = 120;
						strLeft.x = -50;
					}
					else {
						//puts both in theirr original positions
						strLeft.x = 120;
						strRight.x = 180;
					}
				}
				
				
				//only shows the increasing arrow if your luck is 10
				if(player.luck ==  10) {
					luckRight.x = 160;
					luckLeft.x = -50;	//moves the left arrow so both aren't clicked at the same time
				}
				else {
					if(player.luck == 15 && luckBonus == 5) {
						luckRight.x = 160;
						luckLeft.x = -50;
					}
					else {
						//puts both in their original positions
						luckLeft.x = 160;
						luckRight.x = 220;
					}
				}
				
				
				//only shows the increasing arrow if your Int is 10
				if(player.Int == 10) {
					IntRight.x = 120;
					IntLeft.x = -50;	//moves the left arrow so both aren't clicked at the same time
				}
				else {
					if(player.Int == 15 && intBonus == 5) {
						IntRight.x = 120;
						IntLeft.x = -50;
					}
					else {
						//puts both in their original positions
						IntLeft.x = 120;
						IntRight.x = 180;
					}
				}
				
				
				//only shows the increasing arrow if your sneak is 10
				if(player.sneak == 10) {
						sneakRight.x = 170;
						sneakLeft.x = -50;	//moves the left arrow so both aren't clicked at the same time
				}
				else {
					if(player.sneak == 15 && sneakBonus == 5) {
						sneakRight.x = 170;
						sneakLeft.x = -50;
					}
					else {
						//puts both in their original positions
						sneakLeft.x = 170;
						sneakRight.x = 230;
					}
				}
				
				
				//only shows the increasing arrow if your speech is 10
				if(player.speech == 10) {
					speechRight.x = 190;
					speechLeft.x = -50;	//moves the left arrow so both aren't clicked at the same time
				}
				else {
					if(player.speech == 15 && speechBonus == 5) {
						speechRight.x = 190;
						speechLeft.x = -50;	//moves the left arrow so both aren't clicked at the same time
					}
					else {
						//puts both in their original positions
						speechLeft.x = 190;
						speechRight.x = 250;
					}
				}
				
				
				//displays the hp arrow buttons
				if(hpLeft.active == false) {
					g.drawImage(left.guy, hpLeft.x, hpLeft.y, null);
				}
				else {
					g.drawImage(leftActive.guy, hpLeft.x, hpLeft.y, null);
				}
				
				if(hpRight.active == false) {
					g.drawImage(right.guy, hpRight.x, hpRight.y, null);
				}
				else {
					g.drawImage(rightActive.guy, hpRight.x, hpRight.y, null);
				}
				
				
				//displays the str arrow buttons
				if(strLeft.active == false) {
					g.drawImage(left.guy, strLeft.x, strLeft.y, null);
				}
				else {
					g.drawImage(leftActive.guy, strLeft.x, strLeft.y, null);
				}
				
				if(strRight.active == false) {
					g.drawImage(right.guy, strRight.x, strRight.y, null);
				}
				else {
					g.drawImage(rightActive.guy, strRight.x, strRight.y, null);
				}
				
				
				//displays the luck arrow buttons
				if(luckLeft.active == false) {
					g.drawImage(left.guy, luckLeft.x, luckLeft.y, null);
				}
				else {
					g.drawImage(leftActive.guy, luckLeft.x, luckLeft.y, null);
				}
				
				if(luckRight.active == false) {
					g.drawImage(right.guy, luckRight.x, luckRight.y, null);
				}
				else {
					g.drawImage(rightActive.guy, luckRight.x, luckRight.y, null);
				}
				
				
				//displays the Int arrow buttons
				if(IntLeft.active == false) {
					g.drawImage(left.guy, IntLeft.x, IntLeft.y, null);
				}
				else {
					g.drawImage(leftActive.guy, IntLeft.x, IntLeft.y, null);
				}
				
				if(IntRight.active == false) {
					g.drawImage(right.guy, IntRight.x, IntRight.y, null);
				}
				else {
					g.drawImage(rightActive.guy, IntRight.x, IntRight.y, null);
				}
				
				
				//displays the sneak arrow buttons
				if(sneakLeft.active == false) {
					g.drawImage(left.guy, sneakLeft.x, sneakLeft.y, null);
				}
				else {
					g.drawImage(leftActive.guy, sneakLeft.x, sneakLeft.y, null);
				}
				
				if(sneakRight.active == false) {
					g.drawImage(right.guy, sneakRight.x, sneakRight.y, null);
				}
				else {
					g.drawImage(rightActive.guy, sneakRight.x, sneakRight.y, null);
				}
				
				
				//displays the speech arrow buttons
				if(speechLeft.active == false) {
					g.drawImage(left.guy, speechLeft.x, speechLeft.y, null);
				}
				else {
					g.drawImage(leftActive.guy, speechLeft.x, speechLeft.y, null);
				}
				
				if(speechRight.active == false) {
					g.drawImage(right.guy, speechRight.x, speechRight.y, null);
				}
				else {
					g.drawImage(rightActive.guy, speechRight.x, speechRight.y, null);
				}
				
				
				//displays the class arrow buttons
				if(classLeft.active == false) {
					g.drawImage(left.guy, classLeft.x, classLeft.y, null);
				}
				else {
					g.drawImage(leftActive.guy, classLeft.x, classLeft.y, null);
				}
				
				if(classRight.active == false) {
					g.drawImage(right.guy, classRight.x, classRight.y, null);
				}
				else {
					g.drawImage(rightActive.guy, classRight.x, classRight.y, null);
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {	//all game logic goes in here
		//change this later to loading previous save game
		if(player.hp <= 0) {	//player death
			player = new Player();
			questJournal = new Journal();
			location = "town";
			hostile = false;
			wanted = false;
			chopped = -3;
			weirdTree.finished = false;
			skillPoints = 3;
			dialogueSlide = 0;
			inventoryVisible = true;
			creatingCharacter = false;
			state = "main menu";
		}
		
		if(pressDelay > 0) {	//delay so buttons dont regester like 50 clicks instead of just 1
			pressDelay--;
		}
		
		if(jim.moveTimer > 0) {
			jim.moveTimer--;
		}
		
		if(jim.moveTimer == 0 && location != jim.location) {
			jim.setLocation("town");
			jim.moveTimer = 2000;
		}
		
		if(chopped > 0) {
			chopped--;
		}
		
		if(player.manaRegenTimer > 0) {
			player.manaRegenTimer--;
		}
		
		if(player.manaRegenTimer == 0) {
			player.mana += Math.floor(player.manaGain);
			player.manaRegenTimer = 50;
		}
		
		if(player.mana > player.maxMana) {
			player.mana = player.maxMana;
		}
		
		if(leather_chest.restockTimer > 0) {
			leather_chest.restockTimer--;
		}
		
		if(thief_hood.restockTimer > 0) {
			thief_hood.restockTimer--;
		}
		
		if(thief_robe.restockTimer > 0) {
			thief_robe.restockTimer--;
		}
		
		if(mage_hood.restockTimer > 0) {
			mage_hood.restockTimer--;
		}
		
		if(mage_robe.restockTimer > 0) {
			mage_robe.restockTimer--;
		}
		
		if(playerWeapon == "woodenSword") {
			weaponDamage = 5;
		}
		else {
			if(playerWeapon == "wand") {
				weaponDamage = 1;
			}
			else {
				if(playerWeapon == "shank") {
					weaponDamage = 4;
				}
			}
		}
		
		//armor restock timer and caught checking timer
		if(armorRestock > 0) {
			armorRestock--;
		}
		
		//it is used to see if caught is below 10 for about 1 second
		if(caughtChecker > 0) {
			caughtChecker--;
		}
		
		if(wanted) {
			if(mouseCollided(okButton.x,okButton.y,okButton.w,okButton.h)) {
				okButton.active = true;
				
				if(mousemanager.isLeftPressed() && pressDelay == 0) {
					if(dialogueSlide > -1 && dialogueSlide != 2) {	//change back to 1 later when i add decisions
						if(dialogueSlide == 3 && chopped == -3 && talkingToCitizen) {
							dialogueSlide = 0;
							talkingToCitizen = false;
							pressDelay = 15;
						}
						else {
							dialogueSlide++;
							pressDelay = 15;
						}
					}
				}
			}
			else {
				okButton.active = false;
			}
		}
		
		if(talkingToClerk) {
			if(mouseCollided(okButton.x,okButton.y,okButton.w,okButton.h)) {
				okButton.active = true;
				
				if(mousemanager.isLeftPressed() && pressDelay == 0) {
					if(dialogueSlide > -1) {	//change back to 1 later when i add decisions
						dialogueSlide++;
						pressDelay = 15;
						
						if(dialogueSlide > 1) {
							talkingToClerk = false;
							dialogueSlide = 0;
						}	
					}
				}
			}
			else {
				okButton.active = false;
			}
		}
		
		
		if(talkingToCitizen) {
			if(mouseCollided(okButton.x,okButton.y,okButton.w,okButton.h)) {
				okButton.active = true;
				
				if(mousemanager.isLeftPressed() && pressDelay == 0) {
					if(dialogueSlide > -1) {	//change back to 1 later when i add decisions
						dialogueSlide++;
						pressDelay = 15;
						
						if(dialogueSlide == 4) {
							dialogueSlide = 15;
						}
						
						if(weirdTree.finished) {
							if(dialogueSlide > 0) {
								talkingToCitizen = false;
								talkingToClerk = false;
								dialogueSlide = 0;
							}
						}
						else {
							if(dialogueSlide >= 3 && dialogueSlide < 15) {
								talkingToCitizen = false;
								talkingToClerk = false;
								questJournal.addQuest(weirdTree);
								dialogueSlide = 0;
								
								if(chopped > -3) {
									weirdTree.finished = true;
									player.xp += weirdTree.xp;
									player.gold += weirdTree.gold;
									questJournal.removeQuest(weirdTree);
								}
							}
							else {
								if(dialogueSlide == 15) {
									talkingToCitizen = false;
									talkingToClerk = false;
									questJournal.removeQuest(weirdTree);
									dialogueSlide = 0;
								}
							}
						}
					}
				}
			}
			else {
				okButton.active = false;
			}
		}
		
		
		//caught is chosen randomly based on the player's sneak stat, this catches you if it is below 10
		//so if you have 10 sneak you have a 10% or 1/10 chance to get away with it
		if(caught < 10 && caughtChecker == 0) {
			caughtChecker = -3;
			wanted = true;
		}
		
		//makes the armor dissapear after it's clicked
		if(mousemanager.mousex > armorx && mousemanager.mousex < armorx + armorw && mousemanager.mousey > armory && mousemanager.mousey < armory + armorh) {
			if(mousemanager.isLeftPressed() && armorVisible == true && location == "armorer") {
				if(player.gold >= 10) {
					player.gold -= 10;
					armorVisible = false;
				}
				else {
					armorVisible = false;
				}
			}
			else {
				if(mousemanager.isRightPressed() && armorVisible) {
					armorVisible = false;
					steal("leather helmet");
					armorRestock = 125; 
				}
			}
		}
		
		if(mouseCollided(leather_chest.x,leather_chest.y,leather_chest.w,leather_chest.h)) {
			if(mousemanager.isLeftPressed() && pressDelay == 0 && location == "armorer") {
				buy(leather_chest,"leather chest");
			}
			else {
				if(mousemanager.isRightPressed() && pressDelay == 0 & location == "armorer") {
					//leather_chest.restockTimer = 100000;
					steal("leather chest");
					leather_chest.restockTimer = 125;
				}
			}
		}
		
		if(mouseCollided(thief_hood.x,thief_hood.y,thief_hood.w,thief_hood.h)) {
			if(mousemanager.isLeftPressed() && pressDelay == 0 && location == "armorer") {
				buy(thief_hood,"thief hood");
			}
			else {
				if(mousemanager.isRightPressed() && pressDelay == 0 & location == "armorer") {
					//leather_chest.restockTimer = 100000;
					steal("thief hood");
					thief_hood.restockTimer = 125;
				}
			}
		}
		
		if(mouseCollided(thief_robe.x,thief_robe.y,thief_robe.w,thief_robe.h)) {
			if(mousemanager.isLeftPressed() && pressDelay == 0 && location == "armorer") {
				buy(thief_robe,"thief robe");
			}
			else {
				if(mousemanager.isRightPressed() && pressDelay == 0 & location == "armorer") {
					//leather_chest.restockTimer = 100000;
					steal("thief robe");
					thief_robe.restockTimer = 125;
				}
			}
		}
		
		if(mouseCollided(mage_hood.x,mage_hood.y,mage_hood.w,mage_hood.h)) {
			if(mousemanager.isLeftPressed() && pressDelay == 0 && location == "armorer") {
				buy(mage_hood,"mage hood");
			}
			else {
				if(mousemanager.isRightPressed() && pressDelay == 0 & location == "armorer") {
					//leather_chest.restockTimer = 100000;
					steal("mage hood");
					mage_hood.restockTimer = 125;
				}
			}
		}
		
		if(mouseCollided(mage_robe.x,mage_robe.y,mage_robe.w,mage_robe.h)) {
			if(mousemanager.isLeftPressed() && pressDelay == 0 && location == "armorer") {
				buy(mage_robe,"mage robe");
			}
			else {
				if(mousemanager.isRightPressed() && pressDelay == 0 & location == "armorer") {
					//leather_chest.restockTimer = 100000;
					steal("mage robe");
					mage_robe.restockTimer = 125;
				}
			}
		}
		
		
		//weapon stealing
		if(mouseCollided(woodSword.x,woodSword.y,woodSword.w,woodSword.h)) {
			if(mousemanager.isLeftPressed() && pressDelay == 0 && location == "blacksmith") {
				buy(woodSword,"woodenSword");
			}
			else {
				if(mousemanager.isRightPressed() && pressDelay == 0 & location == "blacksmith") {
					//leather_chest.restockTimer = 100000;
					steal("woodenSword");
					woodSword.restockTimer = 125;
				}
			}
		}
		
		if(mouseCollided(SteelSword.x,SteelSword.y,SteelSword.w,SteelSword.h)) {
			if(mousemanager.isLeftPressed() && pressDelay == 0 && location == "blacksmith") {
				buy(SteelSword,"steelSword");
			}
			else {
				if(mousemanager.isRightPressed() && pressDelay == 0 & location == "blacksmith") {
					//leather_chest.restockTimer = 100000;
					steal("steelSword");
					SteelSword.restockTimer = 125;
				}
			}
		}
		
		if(mouseCollided(Dagger.x,Dagger.y,Dagger.w,Dagger.h)) {
			if(mousemanager.isLeftPressed() && pressDelay == 0 && location == "blacksmith") {
				buy(Dagger,"dagger");
			}
			else {
				if(mousemanager.isRightPressed() && pressDelay == 0 & location == "blacksmith") {
					//leather_chest.restockTimer = 100000;
					steal("dagger");
					Dagger.restockTimer = 125;
				}
			}
		}
		
		if(mouseCollided(Shank.x,Shank.y,Shank.w,Shank.h)) {
			if(mousemanager.isLeftPressed() && pressDelay == 0 && location == "blacksmith") {
				buy(Shank,"shank");
			}
			else {
				if(mousemanager.isRightPressed() && pressDelay == 0 & location == "blacksmith") {
					//leather_chest.restockTimer = 100000;
					steal("shank");
					Shank.restockTimer = 125;
				}
			}
		}
		
		if(location == "armorer") {
			if(mousemanager.mousex > 32*16 && mousemanager.mousex < 32*17 && mousemanager.mousey > HEIGHT-181 && mousemanager.mousey < HEIGHT-149) {
				if(mousemanager.isLeftPressed()) {
					location = "town";
					talkingToCitizen = false;
				}
			}
		}
		
		if(state == "main menu") {	//main menu logic
			//adds class bonuses to stats and sets starter gear
			if(player.Class == "thief") {
				sneakBonus = 5;
				speechBonus = 5;
				intBonus = 0;
				hpBonus = 0;
				luckBonus = 0;
				strBonus = 0;
				
				player.helmet = "thief hood";
				player.chest = "thief robe";
				player.legs = "thief pants";
			}
			else {
				if(player.Class == "mage") {
					intBonus = 5;
					luckBonus = 5;
					sneakBonus = 0;
					speechBonus = 0;
					hpBonus = 0;
					strBonus = 0;
					
					player.helmet = "mage hood";
					player.chest = "mage robe";
					player.legs = "thief pants";
				}
				else {
					strBonus = 5;
					hpBonus = 5;
					intBonus = 0;
					luckBonus = 0;
					sneakBonus = 0;
					speechBonus = 0;
					
					player.helmet = "leather helmet";
					player.chest = "leather chest";
					player.legs = "leather pants";
				}
			}
		}
		
		if(state == "main menu") {
			//decides whether to show orc or human in-game
			if(player.race == "orc") {
				head = new ImageManager("assets/img/heads/orcHead.png");
				torso = new ImageManager("assets/img/torsos/orcBody.png");
				legs = new ImageManager("assets/img/legs/orcLegs.png");
				bigOrcHead = new ImageManager("assets/img/heads/BigOrcHead.png");
				bigHumanHead = new ImageManager("assets/img/heads/BigHumanHead.png");
			}
			else {
				head = new ImageManager("assets/img/heads/humanHead.png");
				torso = new ImageManager("assets/img/torsos/humanBody.png");
				legs = new ImageManager("assets/img/legs/humanLegs.png");
			}
			
			//button logic
			//checks if the mouse is touching the button
			if(mousemanager.mousex >= newGameButton.x && mousemanager.mousex <= newGameButton.x + newGameButton.w && mousemanager.mousey >= newGameButton.y && mousemanager.mousey <= newGameButton.y + newGameButton.h) {
				newGameButton.active = true;
				
				if(mousemanager.isLeftPressed()) {
					creatingCharacter = true;	//goes to character creation when button is pressed
				}
			}
			else {
				newGameButton.active = false;	//makes the button darker when the mouse is not touching it
			}
			
			if(mouseCollided(hpLeft.x,hpLeft.y,hpLeft.w,hpLeft.h)) {
				hpLeft.active = true;
				
				if(pressDelay == 0) {
					if(mousemanager.isLeftPressed()) {
						skillPoints++;
						player.maxHP--;
						pressDelay = 15;
					}
				}
			}
			else {
				hpLeft.active = false;
			}
			
			if(mouseCollided(hpRight.x,hpRight.y,hpRight.w,hpRight.h)) {
				hpRight.active = true;
				
				if(pressDelay == 0) {
					if(mousemanager.isLeftPressed() && skillPoints > 0) {
						player.maxHP++;
						skillPoints--;
						pressDelay = 15;
					}
				}
			}
			else {
				hpRight.active = false;
			}
			
			if(mouseCollided(raceLeft.x,raceLeft.y,raceLeft.w,raceLeft.h)) {
				raceLeft.active = true;
				
				//cycles between races when the arrows are pressed
				if(mousemanager.isLeftPressed() && pressDelay == 0) {
					if(player.race == "human") {
						player.race = "orc";
						pressDelay = 15;
					}
					else {
						player.race = "human";
						pressDelay = 15;
					}
				}
			}
			else {
				raceLeft.active = false;
			}
			
			if(mouseCollided(raceRight.x,raceRight.y,raceRight.w,raceRight.h)) {
				raceRight.active = true;
				
				if(mousemanager.isLeftPressed() && pressDelay == 0) {
					if(player.race == "human") {
						player.race = "orc";
						pressDelay = 15;
					}
					else {
						player.race = "human";
						pressDelay = 15;
					}
				}
			}
			else {
				raceRight.active = false;
			}
			
			
			if(mouseCollided(strLeft.x,strLeft.y,strLeft.w,strLeft.h)) {
				strLeft.active = true;
				
				if(mousemanager.isLeftPressed() && pressDelay == 0) {
					player.str--;
					skillPoints++;
					pressDelay = 15;
				}
			}
			else {
				strLeft.active = false;
			}
			
			if(mouseCollided(strRight.x,strRight.y,strRight.w,strRight.h)) {
				strRight.active = true;
				
				if(mousemanager.isLeftPressed() && pressDelay == 0) {
					if(skillPoints > 0) {
						player.str++;
						skillPoints--;
						pressDelay = 15;
					}
				}
			}
			else {
				strRight.active = false;
			}
			
			
			if(mouseCollided(luckLeft.x,luckLeft.y,luckLeft.w,luckLeft.h)) {
				luckLeft.active = true;
				
				if(mousemanager.isLeftPressed() && pressDelay == 0) {
					player.luck--;
					skillPoints++;
					pressDelay = 15;
				}
			}
			else {
				luckLeft.active = false;
			}
			
			if(mouseCollided(luckRight.x,luckRight.y,luckRight.w,luckRight.h)) {
				luckRight.active = true;
				
				if(mousemanager.isLeftPressed() && pressDelay == 0) {
					if(skillPoints > 0) {
						player.luck++;
						skillPoints--;
						pressDelay = 15;
					}
				}
			}
			else {
				luckRight.active = false;
			}
			
			
			if(mouseCollided(IntLeft.x,IntLeft.y,IntLeft.w,IntLeft.h)) {
				IntLeft.active = true;
				
				if(mousemanager.isLeftPressed() && pressDelay == 0) {
					player.Int--;
					skillPoints++;
					pressDelay = 15;
				}
			}
			else {
				IntLeft.active = false;
			}
			
			if(mouseCollided(IntRight.x,IntRight.y,IntRight.w,IntRight.h)) {
				IntRight.active = true;
				
				if(mousemanager.isLeftPressed() && pressDelay == 0) {
					if(skillPoints > 0) {
						player.Int++;
						skillPoints--;
						pressDelay = 15;
					}
				}
			}
			else {
				IntRight.active = false;
			}
			
			
			if(mouseCollided(sneakLeft.x,sneakLeft.y,sneakLeft.w,sneakLeft.h)) {
				sneakLeft.active = true;
				
				if(mousemanager.isLeftPressed() && pressDelay == 0) {
					player.sneak--;
					skillPoints++;
					pressDelay = 15;
				}
			}
			else {
				sneakLeft.active = false;
			}
			
			if(mouseCollided(sneakRight.x,sneakRight.y,sneakRight.w,sneakRight.h)) {
				sneakRight.active = true;
				
				if(mousemanager.isLeftPressed() && pressDelay == 0) {
					if(skillPoints > 0) {
						player.sneak++;
						skillPoints--;
						pressDelay = 15;
					}
				}
			}
			else {
				sneakRight.active = false;
			}
			
			
			if(mouseCollided(speechLeft.x,speechLeft.y,speechLeft.w,speechLeft.h)) {
				speechLeft.active = true;
				
				if(mousemanager.isLeftPressed() && pressDelay == 0) {
					player.speech--;
					skillPoints++;
					pressDelay = 15;
				}
			}
			else {
				speechLeft.active = false;
			}
			
			if(mouseCollided(speechRight.x,speechRight.y,speechRight.w,speechRight.h)) {
				speechRight.active = true;
				
				if(mousemanager.isLeftPressed() && pressDelay == 0) {
					if(skillPoints > 0) {
						player.speech++;
						skillPoints--;
						pressDelay = 15;
					}
				}
			}
			else {
				speechRight.active = false;
			}
			
			
			if(mouseCollided(classLeft.x,classLeft.y,classLeft.w,classLeft.h)) {
				classLeft.active = true;
				
				if(mousemanager.isLeftPressed() && pressDelay == 0) {
					if(player.Class == "warrior") {
						player.Class = "mage";
						pressDelay = 15;
					}
					else {
						if(player.Class == "mage") {
							player.Class = "thief";
							pressDelay = 15;
						}
						else {
							player.Class = "warrior";
							pressDelay = 15;
						}
					}
				}
			}
			else {
				classLeft.active = false;
			}
			
			if(mouseCollided(classRight.x,classRight.y,classRight.w,classRight.h)) {
				classRight.active = true;
				
				if(mousemanager.isLeftPressed() && pressDelay == 0) {
					if(player.Class == "warrior") {
						player.Class = "thief";
						pressDelay = 15;
					}
					else {
						if(player.Class == "thief") {
							player.Class = "mage";
							pressDelay = 15;
						}
						else {
							player.Class = "warrior";
							pressDelay = 15;
						}
					}
				}
			}
			else {
				classRight.active = false;
			}
			
			
			if(mouseCollided(doneButton.x,doneButton.y,doneButton.w,doneButton.h)) {
				doneButton.active = true;
				
				if(mousemanager.isLeftPressed() && pressDelay == 0) {
					if(skillPoints == 0) {
						player.maxHP += hpBonus;
						player.hp = player.maxHP;
						player.str += strBonus;
						player.luck += luckBonus;
						player.Int += intBonus;
						player.sneak += sneakBonus;
						player.speech += speechBonus;
						player.maxMana = player.Int + 45;
						player.manaGain = player.maxMana/30;
						player.mana = player.maxMana;
						state = "game";
						pressDelay = 15;
						
						if(player.Class == "mage") {
							player.addToSpells("fireball");
						}
					}
				}
			}
			else {
				doneButton.active = false;
			}
			
		}
		else {
			if(state == "game") {
				//gameplay controls and logic will go here
			}
		}
	}
	
	public void run() {
		init();
		
		while(running) {
			render();
		}
	}

	
	public MouseManager getMouseManager() {
		return mousemanager;
	}
	
	public KeyManager getKeyManager() {
		return keymanager;
	}
}
