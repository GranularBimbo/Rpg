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
import com.game.Weapon;
import com.game.Dialogue;

import com.managers.*;
import com.game.displays.*;

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
	public boolean buyMenuVisible, launchingFireball;
	public boolean talkingToClerk, talkingToGuard, talkingToCitizen, selectingTarget;
	public int guardRespawn, fireballX, fireballY;
	public Guard target;
	public Enemy enemyTarget;
	
	
	public int clerkx, clerky, clerkw, clerkh;
	
	public int menuBox_x, menuBox_y, menuBox_w, menuBox_h;
	
	//enemies
	public Enemy chicken;
	
	//shop items
	public ShopItem leather_chest;
	
	//people
	public Person armorerClerk;
	public Person jim;
	
	Random random;	//creates the random game object
	
	Weapon playerWeapon;
	
	//dialogues
	Dialogue caughtDialogue;
	Dialogue armorerDialogue;
	Dialogue citizenDialogue;
	
	//Guards
	//Town1 guards
	public Guard guard;
	
	//weapons
	public ImageManager woodenSword;
	public ImageManager steelSword;
	public ImageManager wand;
	public ImageManager staff;
	public ImageManager shank;
	public ImageManager dagger;
	
	//enemy images
	public ImageManager chicken_i;
	
	//inventory slot position
	public int slot1x, slot1y, slot2x, slot2y, slot3x, slot3y, slot4x, slot4y, slot5x, slot5y, slot6x, slot6y;
	public int slot7x, slot7y, slot8x, slot8y, slot9x, slot9y, slot10x, slot10y;
	
	//locations
	public ImageManager town;
	public ImageManager town2;
	public ImageManager armorer;
	public ImageManager blacksmith;
	public ImageManager castle;
	public ImageManager woods;
	
	//spells
	public ImageManager fire_ball;
	
	//people images
	public ImageManager armorClerk;
	
	//body part images
	public ImageManager head;
	public ImageManager torso;
	public ImageManager legs;
	public ImageManager toolbar_orc;
	public ImageManager toolbar_human;
	public ImageManager toolbar_leather;
	public ImageManager toolbar_thief;
	public ImageManager toolbar_hood;
	
	//inventory items
	public ImageManager leatherChest_I;	// _I will be for inventory items
	public ImageManager mageRobe_I;
	public ImageManager thiefRobe_I;
	
	//armor images
	public int armorx, armory, armorw, armorh, armorRestock;
	public boolean armorVisible;
	
	//helmets
	public ImageManager hoodHelm;
	public ImageManager leatherHelm;
	public ImageManager thiefHelm;
	
	//chest plates
	public ImageManager leatherChest;
	public ImageManager thiefChest;
	public ImageManager robeChest;
	
	
	//button images
	public ImageManager newGame;
	public ImageManager newGameActive;
	public ImageManager left;
	public ImageManager leftActive;
	public ImageManager right;
	public ImageManager rightActive;
	public ImageManager done;
	public ImageManager doneActive;
	public ImageManager ok;
	public ImageManager okActive;
	public ImageManager southButton;
	public ImageManager southButtonActive;
	public ImageManager northButton;
	public ImageManager northButtonActive;
	
	//UI
	public ImageManager bigOrcHead;
	public ImageManager bigHumanHead;
	public ImageManager mediumOrc;
	public ImageManager mediumHuman;
	public ImageManager bigLeather;
	public ImageManager bigHood;
	public ImageManager bigThief;
	public ImageManager mediumLeather;
	public ImageManager mediumHood;
	public ImageManager mediumThief;
	public ImageManager cursor;
	
	//buttons
	public Button newGameButton;
	public Button raceLeft;
	public Button raceRight;
	public Button hpLeft;
	public Button hpRight;
	public Button strLeft;
	public Button strRight;
	public Button luckLeft;
	public Button luckRight;
	public Button IntLeft;
	public Button IntRight;
	public Button sneakLeft;
	public Button sneakRight;
	public Button speechLeft;
	public Button speechRight;
	public Button classLeft;
	public Button classRight;
	public Button doneButton;
	public Button playerMenuLeft;
	public Button playerMenuRight;
	public Button okButton;
	public Button south;
	public Button north;
	
	//player, window, and game state
	Player player;
	public String state;
	Display display;
	Console console;
	public String location;
	public boolean creatingCharacter;
	
	public Game(int w, int h) {
		display = new Display(w,h);
		Timer timer = new Timer(20, this);	//idk how this timer works i found it in a youtube video lol
		player = new Player();
		cursor = new ImageManager("assets/img/cursor.png");
		dialogueSlide = 0;
		
		fireballX = 32*16;
		fireballY = 32*10;
		
		//spells
		fire_ball = new ImageManager("assets/img/spells/fireball.png");
		
		//enemies
		chicken_i = new ImageManager("assets/img/enemies/chicken.png");
		
		//people
		armorClerk = new ImageManager("assets/img/people/armorClerk.png");
		
		//weapons
		woodenSword = new ImageManager("assets/img/weapons/woodenSword.png");
		steelSword = new ImageManager("assets/img/weapons/steelSword.png");
		wand = new ImageManager("assets/img/weapons/wand.png");
		staff = new ImageManager("assets/img/weapons/staff.png");
		shank = new ImageManager("assets/img/weapons/shank.png");
		dagger = new ImageManager("assets/img/weapons/dagger.png");
		
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
		
		//locations
		town = new ImageManager("assets/img/locations/town.jpg");
		town2 = new ImageManager("assets/img/locations/town2.png");
		armorer = new ImageManager("assets/img/locations/armorer.jpg");
		woods = new ImageManager("assets/img/locations/woods.jpg");
		blacksmith = new ImageManager("assets/img/locations/weapons.jpg");
		castle = new ImageManager("assets/img/locations/castle.jpg");
		
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
		
		this.WIDTH = w;
		this.HEIGHT = h;
		
		timer.start();
		run();
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
	
	public void init() {
		running = true;
		state = "main menu";
		playerMenuVisible = false;
		console = new Console(32*5,32*19); 
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
		okButton = new Button(false,32*8,32*20,50,50);
		south = new Button(false,32*29,32*21 - 15,50,50);
		north = new Button(false,32*29,32*20 - 45,50,50);
		creatingCharacter = false;
		wanted = false;
		hostile = false;
		turn = "you";
		location = "town";
		
		clerkx = 32*16;
		clerky = 32*2;
		clerkw = 32;
		clerkh = 64;
		
		//shop items
		leather_chest = new ShopItem(32*12,32*4,32,32);
		
		//enemies
		chicken = new Enemy(32*16,32*15,32,32,1,10);
		
		//people
		armorerClerk = new Person(clerkx,clerky,clerkw,clerkh,null);
		jim = new Person(32*6,32*10,32,64,"town");
		
		//dialogues
		caughtDialogue = new Dialogue(4);	//4 is how many "slides" are in the dialogue, or how many times
		armorerDialogue = new Dialogue(2);	//you would have to click to see it all
		citizenDialogue = new Dialogue(1);
		
		//might change where and how dialogue slides are added for more variety in the future
		//for instance i could use arrays of possible sentances and have a random one chosen
		caughtDialogue.addSlide(">> Guard: Stop!");
		caughtDialogue.addSlide(">> Guard: Pay your fine or serve your sentance.");
		caughtDialogue.addSlide(">> Guard: THEN DIE!");
		caughtDialogue.addSlide(">> Guard: I hope you learn from your mistakes.");
		
		armorerDialogue.addSlide(">> Merchant: Hey there!");
		armorerDialogue.addSlide(">> Merchant: Why not take a look at my wares?");
		
		citizenDialogue.addSlide(">> Citizen: Hey.");
		
		guard = new Guard(32*4,32*10,20);
		
		//inventory slot positions
		slot1x = 32*18 + 10;
		slot1y = 32*6 + 74;
		
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
		
		skillPoints = 10;
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
		
		bs.show();
		g.dispose();
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
			
			if(location == "armorer") {
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
						g.drawImage(woods.guy, 0, 0, null);
					}
					else {
						if(location == "castle") {
							g.drawImage(castle.guy, 0, 0, null);
						}
						else {
							if(location == "blacksmith") {
								g.drawImage(blacksmith.guy, 0, 0, null);
							}
							else {
								if(location == "town2") {
									g.drawImage(town2.guy, 0, 0, null);
								}
							}
						}
					}
				}
			}
			
			
			if(location == "armorer" && armorVisible) {	//draws the armor if armorVisible is true
				g.drawImage(leatherHelm.guy, armorx, armory, null);
			}
			
			if(location == "armorer" && leather_chest.restockTimer == 0) {
				g.drawImage(leatherChest_I.guy, leather_chest.x, leather_chest.y, null);
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
								dialogueSlide = -4;	//odio
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
								dialogueSlide = -4;	//odio
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
			if(playerWeapon == Weapon.woodenSword) {
				g.drawImage(woodenSword.guy, 32*16, 32*10, null);
			}
			else {
				if(playerWeapon == Weapon.steelSword) {
					g.drawImage(steelSword.guy, 32*16, 32*10, null);
				}
				else {
					if(playerWeapon == Weapon.wand) {
						g.drawImage(wand.guy, 32*16, 32*10, null);
					}
					else {
						if(playerWeapon == Weapon.staff) {
							g.drawImage(staff.guy, 32*16, 32*10, null);
						}
						else {
							if(playerWeapon == Weapon.shank) {
								g.drawImage(shank.guy, 32*16, 32*10, null);
							}
							else {
								if(playerWeapon == Weapon.dagger) {
									g.drawImage(dagger.guy, 32*16, 32*10, null);
								}
							}
						}
					}
				}
			}
			
			//draws the in-game UI
			g.setColor(Color.gray.darker());
			g.fillRect(2, HEIGHT - 149, WIDTH - 4, 148);
			
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
			
			if(dialogueSlide == 0 || dialogueSlide == 1 || dialogueSlide == 2) {
				if(wanted && location == "town" && guard.hp > 0) {
					g.drawImage(toolbar_human.guy, 2, HEIGHT - 149, null);
					g.drawImage(toolbar_leather.guy, 2, HEIGHT - 149, null);
				}
			}
			
			if(dialogueSlide == 0) {
				if(location == "armorer" || location == "town" || location == "blacksmith") {
					if(talkingToCitizen) {
						g.drawImage(toolbar_human.guy, 2, HEIGHT - 149, null);
						g.drawImage(toolbar_leather.guy, 2, HEIGHT - 149, null);
					}
				}
			}
			
			if(dialogueSlide == 0 || dialogueSlide == 1) {
				if(talkingToClerk && location == "armorer") {
					g.drawImage(toolbar_human.guy, 2, HEIGHT - 149, null);
					g.drawImage(toolbar_leather.guy, 2, HEIGHT - 149, null);
				}
			}
			else {
				if(location == "armorer" && pressDelay == 0) {
					dialogueSlide = 0;
					talkingToClerk = false;
				}
			}
		
			g.drawRect(2, HEIGHT - 149, WIDTH - 4, 148);
			g.drawRect(2, HEIGHT - 149, 32*5 - 10, 32*4);
			
			if(talkingToClerk == false && talkingToCitizen == false) {
				if(wanted && location != "town") {
					console.showText(">> Theft Failed: you've been caught", g);
				}
				else {
					if(caughtChecker >= 0 && caught > 9) {
						if(hostile == false) {
							console.showText(">> Theft Successful", g);
						}
					}
				}
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
			
			if(location == "armorer" && talkingToClerk) {
				if(dialogueSlide == 0) {
					console.showText(armorerDialogue.slides[0], g);
				}
				else {
					if(dialogueSlide == 1) {
						console.showText(armorerDialogue.slides[1], g);
					}
				}
			}
			
			if(talkingToCitizen) {
				if(dialogueSlide == 0) {
					console.showText(citizenDialogue.slides[0], g);
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
			
			if(dialogueSlide == 2 && location == "town") {
				turn = "guard";
				wanted = false;
				hostile = true;
			}
			
			if(location == "town" && guard.hp > 0) {
				g.drawImage(armorClerk.guy, guard.x, guard.y, null);
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
			
			if(talkingToCitizen && dialogueSlide > -1 && dialogueSlide < 1) {	//this if statement will change as more towns are added
				if(okButton.active) {
					g.drawImage(okActive.guy, okButton.x, okButton.y, null);
				}
				else {
					g.drawImage(ok.guy, okButton.x, okButton.y, null);
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
			
			//directional buttons
			if(mouseCollided(south.x,south.y,south.w,south.h)) {
				south.active = true;
				
				if(mousemanager.isLeftPressed() && pressDelay == 0) {
					if(location == "town") {
						location = "woods";
						talkingToCitizen = false;
						dialogueSlide = 0;
						woodsMonsterChance = random.nextInt(10);
						pressDelay = 15;
					}
					else {
						if(location == "woods") {
							location = "town2";
							pressDelay = 15;
						}
					}
				}
			}
			else {
				south.active = false;
			}
			
			
			if(location == "woods" && wanted) {
				hostile = true;
				wanted = false;
			}
			
			if(mouseCollided(north.x,north.y,north.w,north.h)) {
				north.active = true;
				
				if(mousemanager.isLeftPressed() && pressDelay == 0) {
					if(location == "woods") {
						location = "town";
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
					}
				}
			}
			else {
				north.active = false;
			}
			
			if(woodsMonsterChance > 4 && location == "woods" && dialogueSlide == 0) {
				console.showText(">> A chicken has appeared", g);
				caught = -3;
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
			
			if(location == "armorer") {
				if(talkingToClerk == false && talkingToCitizen == false && caught > 9) {
					console.showText(">> You see a variety of armor for sale on the counter", g);
				}
			}
			
			if(location == "town") {
				if(talkingToCitizen == false && wanted == false && hostile == false) {
					console.showText(">> You are in a town with 2 houses, a castle, armorer, and blacksmith", g);
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
				chicken.hp = -2;
			}

			
			//guard attacking
			if(mouseCollided(guard.x,guard.y,guard.w,guard.h)) {
				if(mousemanager.isLeftPressed() && pressDelay == 0) {
					if(selectingTarget == true) {
						target = guard;
						launchingFireball = true;
					}
					else {
						//algorythm that decides how much damage you deal
						damage = ((weaponDamage*player.str - (weaponDamage*player.str / player.luck)) / (random.nextInt(player.luck - random.nextInt(5)) + 1));
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
					guard.calcDamage();
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
						launchingFireball = true;
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
			
			g.setColor(Color.black);
			//the math here sets the maximum width of the bar based on your max hp
			g.drawRect(3, HEIGHT - 20, (player.maxHP * (player.maxHP / 15)), 15);
			
			g.setFont(new Font("Times New Roman",Font.BOLD,15));
			//sets text that displays your hp in the middle of the health bar
			g.drawString("Hp: " + player.hp + "/" + player.maxHP, ((player.maxHP*(player.maxHP/15))/2)-30, HEIGHT-8);
			
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
			if(location == "town") {
				if(mousemanager.mousex > 32 && mousemanager.mousex < 32*8 && mousemanager.mousey > 128 && mousemanager.mousey < 128 + 32*4) {
					g.setFont(new Font("Times New Roman",Font.BOLD,30));
					
					g.setColor(Color.black);
					g.drawRect(mousemanager.mousex + 20, mousemanager.mousey + 50, 300, 100);
					
					g.setColor(Color.gray.darker());
					g.fillRect(mousemanager.mousex + 21, mousemanager.mousey + 51, 299, 99);
					
					g.setColor(Color.black);
					g.drawString("Armorer", mousemanager.mousex + 120, mousemanager.mousey + 110);
					
					g.setFont(new Font("Times New Roman",Font.BOLD,16));
					g.drawString("click to enter", mousemanager.mousex + 130, mousemanager.mousey + 130);
					
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
				
				
				if(mousemanager.mousex > 32*13 && mousemanager.mousex < 32*20 && mousemanager.mousey > 128 && mousemanager.mousey < 128 + 32*4) {
					g.setFont(new Font("Times New Roman",Font.BOLD,30));
					
					g.setColor(Color.black);
					g.drawRect(mousemanager.mousex + 20, mousemanager.mousey + 50, 300, 100);
					
					g.setColor(Color.gray.darker());
					g.fillRect(mousemanager.mousex + 21, mousemanager.mousey + 51, 299, 99);
					
					g.setColor(Color.black);
					g.drawString("Castle", mousemanager.mousex + 130, mousemanager.mousey + 110);
					
					g.setFont(new Font("Times New Roman",Font.BOLD,16));
					g.drawString("click to enter", mousemanager.mousex + 125, mousemanager.mousey + 130);
					
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
				
				if(mousemanager.mousex > 32*26 && mousemanager.mousex < 32*33 && mousemanager.mousey > 128 && mousemanager.mousey < 128 + 32*4) {
					g.setFont(new Font("Times New Roman",Font.BOLD,30));
					
					g.setColor(Color.gray.darker());
					g.fillRect(mousemanager.mousex - 300, mousemanager.mousey + 50, 300, 100);
					
					g.setColor(Color.black);
					g.drawRect(mousemanager.mousex - 300, mousemanager.mousey + 50, 300, 100);
					
					g.setColor(Color.black);
					g.drawString("Blacksmith", mousemanager.mousex - 220, mousemanager.mousey + 110);
					
					g.setFont(new Font("Times New Roman",Font.BOLD,16));
					g.drawString("click to enter", mousemanager.mousex - 200, mousemanager.mousey + 130);
					
					if(mousemanager.isLeftPressed() && pressDelay == 0) {
						if(wanted) {
							hostile = true;
							wanted = false;
						}
						
						location = "blacksmith";
						talkingToCitizen = false;
						pressDelay = 15;
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
				System.out.println("Target: " + target);
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
				
				
				//shows the stats if game state is stats and inventory if it = inventory
				if(playerMenuState == "spells") {
					if(player.spells[0] == "fireball") {
						g.drawImage(fire_ball.guy, slot1x, slot1y, null);
						
						//equips the helmet and replaces it's slot with what you were originaly wearing
						if(mouseCollided(slot1x,slot1y,32,32)) {
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
				else {	//shows items in the inventory
					if(playerMenuState == "inventory") {
						//inventory slot 1
						if(player.inventory[0] == "leather helmet") {
							g.drawImage(leatherHelm.guy, slot1x, slot1y, null);
							
							//equips the helmet and replaces it's slot with what you were originaly wearing
							if(mouseCollided(slot1x,slot1y,32,32)) {
								if(mousemanager.isLeftPressed() && pressDelay == 0) {
									swapHelm = player.inventory[0];
									player.inventory[0] = player.helmet;
									player.helmet = swapHelm;
									pressDelay = 15;
								}
							}
						}
						else {
							if(player.inventory[0] == "mage hood") {
								g.drawImage(hoodHelm.guy, slot1x, slot1y, null);
								
								if(mouseCollided(slot1x,slot1y,32,32)) {
									if(mousemanager.isLeftPressed() && pressDelay == 0) {
										swapHelm = player.inventory[0];
										player.inventory[0] = player.helmet;
										player.helmet = swapHelm;
										pressDelay = 15;
									}
								}
							}
							else {
								if(player.inventory[0] == "thief hood") {
									g.drawImage(thiefHelm.guy, slot1x, slot1y, null);
									
									if(mouseCollided(slot1x,slot1y,32,32)) {
										if(mousemanager.isLeftPressed() && pressDelay == 0) {
											swapHelm = player.inventory[0];
											player.inventory[0] = player.helmet;
											player.helmet = swapHelm;
											pressDelay = 15;
										}
									}
								}
								else {
									if(player.inventory[0] == "leather chest") {
										g.drawImage(leatherChest_I.guy, slot1x, slot1y, null);
										
										if(mouseCollided(slot1x,slot1y,32,32)) {
											if(mousemanager.isLeftPressed() && pressDelay == 0) {
												swapHelm = player.inventory[0];
												player.inventory[0] = player.chest;
												player.chest = swapHelm;
												pressDelay = 15;
											}
										}
									}
									else {
										if(player.inventory[0] == "thief robe") {
											g.drawImage(thiefRobe_I.guy, slot1x, slot1y, null);
											
											if(mouseCollided(slot1x,slot1y,32,32)) {
												if(mousemanager.isLeftPressed() && pressDelay == 0) {
													swapHelm = player.inventory[0];
													player.inventory[0] = player.chest;
													player.chest = swapHelm;
													pressDelay = 15;
												}
											}
										}
										else {
											if(player.inventory[0] == "mage robe") {
												g.drawImage(mageRobe_I.guy, slot1x, slot1y, null);
												
												if(mouseCollided(slot1x,slot1y,32,32)) {
													if(mousemanager.isLeftPressed() && pressDelay == 0) {
														swapHelm = player.inventory[0];
														player.inventory[0] = player.chest;
														player.chest = swapHelm;
														pressDelay = 15;
													}
												}
											}
										}
									}
								}
							}
						}
						
						//inventory slot 2
						if(player.inventory[1] == "leather helmet") {
							g.drawImage(leatherHelm.guy, slot2x, slot2y, null);
							
							if(mouseCollided(slot2x,slot2y,32,32)) {
								if(mousemanager.isLeftPressed() && pressDelay == 0) {
									swapHelm = player.inventory[1];
									player.inventory[1] = player.helmet;
									player.helmet = swapHelm;
									pressDelay = 15;
								}
							}
						}
						else {
							if(player.inventory[1] == "mage hood") {
								g.drawImage(hoodHelm.guy, slot2x, slot2y, null);
								
								if(mouseCollided(slot2x,slot2y,32,32)) {
									if(mousemanager.isLeftPressed() && pressDelay == 0) {
										swapHelm = player.inventory[1];
										player.inventory[1] = player.helmet;
										player.helmet = swapHelm;
										pressDelay = 15;
									}
								}
							}
							else {
								if(player.inventory[1] == "thief hood") {
									g.drawImage(thiefHelm.guy, slot2x, slot2y, null);
									
									if(mouseCollided(slot2x,slot2y,32,32)) {
										if(mousemanager.isLeftPressed() && pressDelay == 0) {
											swapHelm = player.inventory[1];
											player.inventory[1] = player.helmet;
											player.helmet = swapHelm;
											pressDelay = 15;
										}
									}
								}
								else {
									if(player.inventory[1] == "leather chest") {
										g.drawImage(leatherChest_I.guy, slot2x, slot2y, null);
										
										if(mouseCollided(slot2x,slot2y,32,32)) {
											if(mousemanager.isLeftPressed() && pressDelay == 0) {
												swapHelm = player.inventory[1];
												player.inventory[1] = player.chest;
												player.chest = swapHelm;
												pressDelay = 15;
											}
										}
									}
									else {
										if(player.inventory[1] == "thief robe") {
											g.drawImage(thiefRobe_I.guy, slot2x, slot2y, null);
											
											if(mouseCollided(slot2x,slot2y,32,32)) {
												if(mousemanager.isLeftPressed() && pressDelay == 0) {
													swapHelm = player.inventory[1];
													player.inventory[1] = player.chest;
													player.chest = swapHelm;
													pressDelay = 15;
												}
											}
										}
										else {
											if(player.inventory[1] == "mage robe") {
												g.drawImage(mageRobe_I.guy, slot2x, slot2y, null);
												
												if(mouseCollided(slot2x,slot2y,32,32)) {
													if(mousemanager.isLeftPressed() && pressDelay == 0) {
														swapHelm = player.inventory[1];
														player.inventory[1] = player.chest;
														player.chest = swapHelm;
														pressDelay = 15;
													}
												}
											}
										}
									}
								}
							}
						}
						
						//inventory slot 3
						if(player.inventory[2] == "leather helmet") {
							g.drawImage(leatherHelm.guy, slot3x, slot3y, null);
							
							if(mouseCollided(slot3x,slot3y,32,32)) {
								if(mousemanager.isLeftPressed() && pressDelay == 0) {
									swapHelm = player.inventory[2];
									player.inventory[2] = player.helmet;
									player.helmet = swapHelm;
									pressDelay = 15;
								}
							}
						}
						else {
							if(player.inventory[2] == "mage hood") {
								g.drawImage(hoodHelm.guy, slot3x, slot3y, null);
								
								if(mouseCollided(slot3x,slot3y,32,32)) {
									if(mousemanager.isLeftPressed() && pressDelay == 0) {
										swapHelm = player.inventory[2];
										player.inventory[2] = player.helmet;
										player.helmet = swapHelm;
										pressDelay = 15;
									}
								}
							}
							else {
								if(player.inventory[2] == "thief hood") {
									g.drawImage(thiefHelm.guy, slot3x, slot3y, null);
									
									if(mouseCollided(slot3x,slot3y,32,32)) {
										if(mousemanager.isLeftPressed() && pressDelay == 0) {
											swapHelm = player.inventory[2];
											player.inventory[2] = player.helmet;
											player.helmet = swapHelm;
											pressDelay = 15;
										}
									}
								}
								else {
									if(player.inventory[2] == "leather chest") {
										g.drawImage(leatherChest_I.guy, slot3x, slot3y, null);
										
										if(mouseCollided(slot3x,slot3y,32,32)) {
											if(mousemanager.isLeftPressed() && pressDelay == 0) {
												swapHelm = player.inventory[2];
												player.inventory[2] = player.chest;
												player.chest = swapHelm;
												pressDelay = 15;
											}
										}
									}
									else {
										if(player.inventory[2] == "thief robe") {
											g.drawImage(thiefRobe_I.guy, slot3x, slot3y, null);
											
											if(mouseCollided(slot3x,slot3y,32,32)) {
												if(mousemanager.isLeftPressed() && pressDelay == 0) {
													swapHelm = player.inventory[2];
													player.inventory[2] = player.chest;
													player.chest = swapHelm;
													pressDelay = 15;
												}
											}
										}
										else {
											if(player.inventory[2] == "mage robe") {
												g.drawImage(mageRobe_I.guy, slot3x, slot3y, null);
												
												if(mouseCollided(slot3x,slot3y,32,32)) {
													if(mousemanager.isLeftPressed() && pressDelay == 0) {
														swapHelm = player.inventory[2];
														player.inventory[2] = player.chest;
														player.chest = swapHelm;
														pressDelay = 15;
													}
												}
											}
										}
									}
								}
							}
						}
						
						//inventory slot 4
						if(player.inventory[3] == "leather helmet") {
							g.drawImage(leatherHelm.guy, slot4x, slot4y, null);
							
							if(mouseCollided(slot4x,slot4y,32,32)) {
								if(mousemanager.isLeftPressed() && pressDelay == 0) {
									swapHelm = player.inventory[3];
									player.inventory[3] = player.helmet;
									player.helmet = swapHelm;
									pressDelay = 15;
								}
							}
						}
						else {
							if(player.inventory[3] == "mage hood") {
								g.drawImage(hoodHelm.guy, slot4x, slot4y, null);
								
								if(mouseCollided(slot4x,slot4y,32,32)) {
									if(mousemanager.isLeftPressed() && pressDelay == 0) {
										swapHelm = player.inventory[3];
										player.inventory[3] = player.helmet;
										player.helmet = swapHelm;
										pressDelay = 15;
									}
								}
							}
							else {
								if(player.inventory[3] == "thief hood") {
									g.drawImage(thiefHelm.guy, slot4x, slot4y, null);
									
									if(mouseCollided(slot4x,slot4y,32,32)) {
										if(mousemanager.isLeftPressed() && pressDelay == 0) {
											swapHelm = player.inventory[3];
											player.inventory[3] = player.helmet;
											player.helmet = swapHelm;
											pressDelay = 15;
										}
									}
								}
								else {
									if(player.inventory[3] == "leather chest") {
										g.drawImage(leatherChest_I.guy, slot4x, slot4y, null);
										
										if(mouseCollided(slot4x,slot4y,32,32)) {
											if(mousemanager.isLeftPressed() && pressDelay == 0) {
												swapHelm = player.inventory[3];
												player.inventory[3] = player.chest;
												player.chest = swapHelm;
												pressDelay = 15;
											}
										}
									}
									else {
										if(player.inventory[3] == "thief robe") {
											g.drawImage(thiefRobe_I.guy, slot4x, slot4y, null);
											
											if(mouseCollided(slot4x,slot4y,32,32)) {
												if(mousemanager.isLeftPressed() && pressDelay == 0) {
													swapHelm = player.inventory[3];
													player.inventory[3] = player.chest;
													player.chest = swapHelm;
													pressDelay = 15;
												}
											}
										}
										else {
											if(player.inventory[3] == "mage robe") {
												g.drawImage(mageRobe_I.guy, slot4x, slot4y, null);
												
												if(mouseCollided(slot4x,slot4y,32,32)) {
													if(mousemanager.isLeftPressed() && pressDelay == 0) {
														swapHelm = player.inventory[3];
														player.inventory[3] = player.chest;
														player.chest = swapHelm;
														pressDelay = 15;
													}
												}
											}
										}
									}
								}
							}
						}
						
						//inventory slot 5
						if(player.inventory[4] == "leather helmet") {
							g.drawImage(leatherHelm.guy, slot5x, slot5y, null);
							
							if(mouseCollided(slot5x,slot5y,32,32)) {
								if(mousemanager.isLeftPressed() && pressDelay == 0) {
									swapHelm = player.inventory[4];
									player.inventory[4] = player.helmet;
									player.helmet = swapHelm;
									pressDelay = 15;
								}
							}
						}
						else {
							if(player.inventory[4] == "mage hood") {
								g.drawImage(hoodHelm.guy, slot5x, slot5y, null);
								
								if(mouseCollided(slot5x,slot5y,32,32)) {
									if(mousemanager.isLeftPressed() && pressDelay == 0) {
										swapHelm = player.inventory[4];
										player.inventory[4] = player.helmet;
										player.helmet = swapHelm;
										pressDelay = 15;
									}
								}
							}
							else {
								if(player.inventory[4] == "thief hood") {
									g.drawImage(thiefHelm.guy, slot5x, slot5y, null);
									
									if(mouseCollided(slot5x,slot5y,32,32)) {
										if(mousemanager.isLeftPressed() && pressDelay == 0) {
											swapHelm = player.inventory[4];
											player.inventory[4] = player.helmet;
											player.helmet = swapHelm;
											pressDelay = 15;
										}
									}
								}
								else {
									if(player.inventory[4] == "leather chest") {
										g.drawImage(leatherChest_I.guy, slot5x, slot5y, null);
										
										if(mouseCollided(slot5x,slot5y,32,32)) {
											if(mousemanager.isLeftPressed() && pressDelay == 0) {
												swapHelm = player.inventory[4];
												player.inventory[4] = player.chest;
												player.chest = swapHelm;
												pressDelay = 15;
											}
										}
									}
									else {
										if(player.inventory[4] == "thief robe") {
											g.drawImage(thiefRobe_I.guy, slot5x, slot5y, null);
											
											if(mouseCollided(slot5x,slot5y,32,32)) {
												if(mousemanager.isLeftPressed() && pressDelay == 0) {
													swapHelm = player.inventory[4];
													player.inventory[4] = player.chest;
													player.chest = swapHelm;
													pressDelay = 15;
												}
											}
										}
										else {
											if(player.inventory[4] == "mage robe") {
												g.drawImage(mageRobe_I.guy, slot5x, slot5y, null);
												
												if(mouseCollided(slot5x,slot5y,32,32)) {
													if(mousemanager.isLeftPressed() && pressDelay == 0) {
														swapHelm = player.inventory[4];
														player.inventory[4] = player.chest;
														player.chest = swapHelm;
														pressDelay = 15;
													}
												}
											}
										}
									}
								}
							}
						}
						
						//inventory slot 6
						if(player.inventory[5] == "leather helmet") {
							g.drawImage(leatherHelm.guy, slot6x, slot6y, null);
							
							if(mouseCollided(slot6x,slot6y,32,32)) {
								if(mousemanager.isLeftPressed() && pressDelay == 0) {
									swapHelm = player.inventory[5];
									player.inventory[5] = player.helmet;
									player.helmet = swapHelm;
									pressDelay = 15;
								}
							}
						}
						else {
							if(player.inventory[5] == "mage hood") {
								g.drawImage(hoodHelm.guy, slot6x, slot6y, null);
								
								if(mouseCollided(slot6x,slot6y,32,32)) {
									if(mousemanager.isLeftPressed() && pressDelay == 0) {
										swapHelm = player.inventory[5];
										player.inventory[5] = player.helmet;
										player.helmet = swapHelm;
										pressDelay = 15;
									}
								}
							}
							else {
								if(player.inventory[5] == "thief hood") {
									g.drawImage(thiefHelm.guy, slot6x, slot6y, null);
									
									if(mouseCollided(slot6x,slot6y,32,32)) {
										if(mousemanager.isLeftPressed() && pressDelay == 0) {
											swapHelm = player.inventory[5];
											player.inventory[5] = player.helmet;
											player.helmet = swapHelm;
											pressDelay = 15;
										}
									}
								}
								else {
									if(player.inventory[5] == "leather chest") {
										g.drawImage(leatherChest_I.guy, slot6x, slot6y, null);
										
										if(mouseCollided(slot6x,slot6y,32,32)) {
											if(mousemanager.isLeftPressed() && pressDelay == 0) {
												swapHelm = player.inventory[5];
												player.inventory[5] = player.chest;
												player.chest = swapHelm;
												pressDelay = 15;
											}
										}
									}
									else {
										if(player.inventory[5] == "thief robe") {
											g.drawImage(thiefRobe_I.guy, slot6x, slot6y, null);
											
											if(mouseCollided(slot6x,slot6y,32,32)) {
												if(mousemanager.isLeftPressed() && pressDelay == 0) {
													swapHelm = player.inventory[5];
													player.inventory[5] = player.chest;
													player.chest = swapHelm;
													pressDelay = 15;
												}
											}
										}
										else {
											if(player.inventory[5] == "mage robe") {
												g.drawImage(mageRobe_I.guy, slot6x, slot6y, null);
												
												if(mouseCollided(slot6x,slot6y,32,32)) {
													if(mousemanager.isLeftPressed() && pressDelay == 0) {
														swapHelm = player.inventory[5];
														player.inventory[5] = player.chest;
														player.chest = swapHelm;
														pressDelay = 15;
													}
												}
											}
										}
									}
								}
							}
						}
						
						//inventory slot 7
						if(player.inventory[6] == "leather helmet") {
							g.drawImage(leatherHelm.guy, slot7x, slot7y, null);
							
							if(mouseCollided(slot7x,slot7y,32,32)) {
								if(mousemanager.isLeftPressed() && pressDelay == 0) {
									swapHelm = player.inventory[6];
									player.inventory[6] = player.helmet;
									player.helmet = swapHelm;
									pressDelay = 15;
								}
							}
						}
						else {
							if(player.inventory[6] == "mage hood") {
								g.drawImage(hoodHelm.guy, slot7x, slot7y, null);
								
								if(mouseCollided(slot7x,slot7y,32,32)) {
									if(mousemanager.isLeftPressed() && pressDelay == 0) {
										swapHelm = player.inventory[6];
										player.inventory[6] = player.helmet;
										player.helmet = swapHelm;
										pressDelay = 15;
									}
								}
							}
							else {
								if(player.inventory[6] == "thief hood") {
									g.drawImage(thiefHelm.guy, slot7x, slot7y, null);
									
									if(mouseCollided(slot7x,slot7y,32,32)) {
										if(mousemanager.isLeftPressed() && pressDelay == 0) {
											swapHelm = player.inventory[6];
											player.inventory[6] = player.helmet;
											player.helmet = swapHelm;
											pressDelay = 15;
										}
									}
								}
								else {
									if(player.inventory[6] == "leather chest") {
										g.drawImage(leatherChest_I.guy, slot7x, slot7y, null);
										
										if(mouseCollided(slot7x,slot7y,32,32)) {
											if(mousemanager.isLeftPressed() && pressDelay == 0) {
												swapHelm = player.inventory[6];
												player.inventory[6] = player.chest;
												player.chest = swapHelm;
												pressDelay = 15;
											}
										}
									}
									else {
										if(player.inventory[6] == "thief robe") {
											g.drawImage(thiefRobe_I.guy, slot7x, slot7y, null);
											
											if(mouseCollided(slot7x,slot7y,32,32)) {
												if(mousemanager.isLeftPressed() && pressDelay == 0) {
													swapHelm = player.inventory[6];
													player.inventory[6] = player.chest;
													player.chest = swapHelm;
													pressDelay = 15;
												}
											}
										}
										else {
											if(player.inventory[6] == "mage robe") {
												g.drawImage(mageRobe_I.guy, slot7x, slot7y, null);
												
												if(mouseCollided(slot7x,slot7y,32,32)) {
													if(mousemanager.isLeftPressed() && pressDelay == 0) {
														swapHelm = player.inventory[6];
														player.inventory[6] = player.chest;
														player.chest = swapHelm;
														pressDelay = 15;
													}
												}
											}
										}
									}
								}
							}
						}
						
						//inventory slot 8
						if(player.inventory[7] == "leather helmet") {
							g.drawImage(leatherHelm.guy, slot8x, slot8y, null);
							
							if(mouseCollided(slot8x,slot8y,32,32)) {
								if(mousemanager.isLeftPressed() && pressDelay == 0) {
									swapHelm = player.inventory[7];
									player.inventory[7] = player.helmet;
									player.helmet = swapHelm;
									pressDelay = 15;
								}
							}
						}
						else {
							if(player.inventory[7] == "mage hood") {
								g.drawImage(hoodHelm.guy, slot8x, slot8y, null);
								
								if(mouseCollided(slot8x,slot8y,32,32)) {
									if(mousemanager.isLeftPressed() && pressDelay == 0) {
										swapHelm = player.inventory[7];
										player.inventory[7] = player.helmet;
										player.helmet = swapHelm;
										pressDelay = 15;
									}
								}
							}
							else {
								if(player.inventory[7] == "thief hood") {
									g.drawImage(thiefHelm.guy, slot8x, slot8y, null);
									
									if(mouseCollided(slot8x,slot8y,32,32)) {
										if(mousemanager.isLeftPressed() && pressDelay == 0) {
											swapHelm = player.inventory[7];
											player.inventory[7] = player.helmet;
											player.helmet = swapHelm;
											pressDelay = 15;
										}
									}
								}
								else {
									if(player.inventory[7] == "leather chest") {
										g.drawImage(leatherChest_I.guy, slot8x, slot8y, null);
										
										if(mouseCollided(slot8x,slot8y,32,32)) {
											if(mousemanager.isLeftPressed() && pressDelay == 0) {
												swapHelm = player.inventory[7];
												player.inventory[7] = player.chest;
												player.chest = swapHelm;
												pressDelay = 15;
											}
										}
									}
									else {
										if(player.inventory[7] == "thief robe") {
											g.drawImage(thiefRobe_I.guy, slot8x, slot8y, null);
											
											if(mouseCollided(slot8x,slot8y,32,32)) {
												if(mousemanager.isLeftPressed() && pressDelay == 0) {
													swapHelm = player.inventory[7];
													player.inventory[7] = player.chest;
													player.chest = swapHelm;
													pressDelay = 15;
												}
											}
										}
										else {
											if(player.inventory[7] == "mage robe") {
												g.drawImage(mageRobe_I.guy, slot8x, slot8y, null);
												
												if(mouseCollided(slot8x,slot8y,32,32)) {
													if(mousemanager.isLeftPressed() && pressDelay == 0) {
														swapHelm = player.inventory[7];
														player.inventory[7] = player.chest;
														player.chest = swapHelm;
														pressDelay = 15;
													}
												}
											}
										}
									}
								}
							}
						}
						
						//inventory slot 9
						if(player.inventory[8] == "leather helmet") {
							g.drawImage(leatherHelm.guy, slot9x, slot9y, null);
							
							if(mouseCollided(slot9x,slot9y,32,32)) {
								if(mousemanager.isLeftPressed() && pressDelay == 0) {
									swapHelm = player.inventory[8];
									player.inventory[8] = player.helmet;
									player.helmet = swapHelm;
									pressDelay = 15;
								}
							}
						}
						else {
							if(player.inventory[8] == "mage hood") {
								g.drawImage(hoodHelm.guy, slot9x, slot9y, null);
								
								if(mouseCollided(slot9x,slot9y,32,32)) {
									if(mousemanager.isLeftPressed() && pressDelay == 0) {
										swapHelm = player.inventory[8];
										player.inventory[8] = player.helmet;
										player.helmet = swapHelm;
										pressDelay = 15;
									}
								}
							}
							else {
								if(player.inventory[8] == "thief hood") {
									g.drawImage(thiefHelm.guy, slot9x, slot9y, null);
									
									if(mouseCollided(slot9x,slot9y,32,32)) {
										if(mousemanager.isLeftPressed() && pressDelay == 0) {
											swapHelm = player.inventory[8];
											player.inventory[8] = player.helmet;
											player.helmet = swapHelm;
											pressDelay = 15;
										}
									}
								}
								else {
									if(player.inventory[8] == "leather chest") {
										g.drawImage(leatherChest_I.guy, slot9x, slot9y, null);
										
										if(mouseCollided(slot9x,slot9y,32,32)) {
											if(mousemanager.isLeftPressed() && pressDelay == 0) {
												swapHelm = player.inventory[8];
												player.inventory[8] = player.chest;
												player.chest = swapHelm;
												pressDelay = 15;
											}
										}
									}
									else {
										if(player.inventory[8] == "thief robe") {
											g.drawImage(thiefRobe_I.guy, slot9x, slot9y, null);
											
											if(mouseCollided(slot9x,slot9y,32,32)) {
												if(mousemanager.isLeftPressed() && pressDelay == 0) {
													swapHelm = player.inventory[8];
													player.inventory[8] = player.chest;
													player.chest = swapHelm;
													pressDelay = 15;
												}
											}
										}
										else {
											if(player.inventory[8] == "mage robe") {
												g.drawImage(mageRobe_I.guy, slot9x, slot9y, null);
												
												if(mouseCollided(slot9x,slot9y,32,32)) {
													if(mousemanager.isLeftPressed() && pressDelay == 0) {
														swapHelm = player.inventory[8];
														player.inventory[8] = player.chest;
														player.chest = swapHelm;
														pressDelay = 15;
													}
												}
											}
										}
									}
								}
							}
						}
						
						//inventory slot 10
						if(player.inventory[9] == "leather helmet") {
							g.drawImage(leatherHelm.guy, slot10x, slot10y, null);
							
							if(mouseCollided(slot10x,slot10y,32,32)) {
								if(mousemanager.isLeftPressed() && pressDelay == 0) {
									swapHelm = player.inventory[9];
									player.inventory[9] = player.helmet;
									player.helmet = swapHelm;
									pressDelay = 15;
								}
							}
						}
						else {
							if(player.inventory[9] == "mage hood") {
								g.drawImage(hoodHelm.guy, slot10x, slot10y, null);
								
								if(mouseCollided(slot10x,slot10y,32,32)) {
									if(mousemanager.isLeftPressed() && pressDelay == 0) {
										swapHelm = player.inventory[9];
										player.inventory[9] = player.helmet;
										player.helmet = swapHelm;
										pressDelay = 15;
									}
								}
							}
							else {
								if(player.inventory[9] == "thief hood") {
									g.drawImage(thiefHelm.guy, slot10x, slot10y, null);
									
									if(mouseCollided(slot10x,slot10y,32,32)) {
										if(mousemanager.isLeftPressed() && pressDelay == 0) {
											swapHelm = player.inventory[9];
											player.inventory[9] = player.helmet;
											player.helmet = swapHelm;
											pressDelay = 15;
										}
									}
								}
								else {
									if(player.inventory[9] == "leather chest") {
										g.drawImage(leatherChest_I.guy, slot10x, slot10y, null);
										
										if(mouseCollided(slot10x,slot10y,32,32)) {
											if(mousemanager.isLeftPressed() && pressDelay == 0) {
												swapHelm = player.inventory[9];
												player.inventory[9] = player.chest;
												player.chest = swapHelm;
												pressDelay = 15;
											}
										}
									}
									else {
										if(player.inventory[9] == "thief robe") {
											g.drawImage(thiefRobe_I.guy, slot10x, slot10y, null);
											
											if(mouseCollided(slot10x,slot10y,32,32)) {
												if(mousemanager.isLeftPressed() && pressDelay == 0) {
													swapHelm = player.inventory[9];
													player.inventory[9] = player.chest;
													player.chest = swapHelm;
													pressDelay = 15;
												}
											}
										}
										else {
											if(player.inventory[9] == "mage robe") {
												g.drawImage(mageRobe_I.guy, slot10x, slot10y, null);
												
												if(mouseCollided(slot10x,slot10y,32,32)) {
													if(mousemanager.isLeftPressed() && pressDelay == 0) {
														swapHelm = player.inventory[9];
														player.inventory[9] = player.chest;
														player.chest = swapHelm;
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
					playerWeapon = Weapon.woodenSword;
				}
				else {
					if(player.Class == "thief") {
						g.drawImage(bigThief.guy, 2, 30, null);
						playerWeapon = Weapon.shank;
					}
					else {
						g.drawImage(bigHood.guy, 2, 30, null);
						playerWeapon = Weapon.wand;
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
		//shows the custom cursor image at the mouse's x and y
		g.drawImage(cursor.guy, mousemanager.mousex, mousemanager.mousey, null);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {	//all game logic goes in here
		if(pressDelay > 0) {	//delay so buttons dont regester like 50 clicks instead of just 1
			pressDelay--;
		}
		
		if(leather_chest.restockTimer > 0) {
			leather_chest.restockTimer--;
		}
		
		if(playerWeapon == Weapon.woodenSword) {
			weaponDamage = 5;
		}
		else {
			if(playerWeapon == Weapon.wand) {
				weaponDamage = 1;
			}
			else {
				if(playerWeapon == Weapon.shank) {
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
						dialogueSlide++;
						pressDelay = 15;
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
						
						if(dialogueSlide > 0) {
							talkingToCitizen = false;
							dialogueSlide = 0;
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
			if(mousemanager.isLeftPressed() && armorVisible == true) {
				armorVisible = false;
				steal("leather helmet");
				armorRestock = 125; 
			}
			else {
				if(mousemanager.isRightPressed() && armorVisible) {
					buyMenuVisible = true;
				}
			}
		}
		else {
			if(mousemanager.isLeftPressed() || mousemanager.isRightPressed()) {
				buyMenuVisible = false;
			}
		}
		
		if(mouseCollided(leather_chest.x,leather_chest.y,leather_chest.w,leather_chest.h)) {
			if(mousemanager.isLeftPressed() && pressDelay == 0) {
				//leather_chest.restockTimer = 100000;
				steal("leather chest");
				leather_chest.restockTimer = 125;
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
