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

import com.managers.*;
import com.game.displays.*;

import javax.swing.Timer;

import com.window.Display;

public class Game implements ActionListener {
//caughtChecker will see if caught is under 10 for a certain amount of time(since it still catches you while above 9)
	public int WIDTH, HEIGHT, caught, caughtChecker, pressDelay, skillPoints, sneakBonus, intBonus, strBonus, speechBonus, luckBonus, hpBonus;
	public boolean running, playerMenuVisible, wanted;
	public KeyManager keymanager = new KeyManager();
	public MouseManager mousemanager = new MouseManager();
	public Graphics g;
	
	Random random;	//creates the random game object
	
	//locations
	public ImageManager town;
	public ImageManager armorer;
	
	//people images
	public ImageManager armorClerk;
	
	//body part images
	public ImageManager head;
	public ImageManager torso;
	public ImageManager legs;
	
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
		
		//people
		armorClerk = new ImageManager("assets/img/people/armorClerk.png");
		
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
		
		//locations
		town = new ImageManager("assets/img/locations/town.jpg");
		armorer = new ImageManager("assets/img/locations/armorer.jpg");
		//armor
		leatherHelm = new ImageManager("assets/img/armor/helmets/leather.png");
		hoodHelm = new ImageManager("assets/img/armor/helmets/hood.png");
		thiefHelm = new ImageManager("assets/img/armor/helmets/thief.png");
		leatherChest = new ImageManager("assets/img/armor/chestplates/leather.png");
		thiefChest = new ImageManager("assets/img/armor/chestplates/thief.png");
		robeChest = new ImageManager("assets/img/armor/chestplates/robe.png");
		
		//buttons
		newGame = new ImageManager("assets/img/buttons/newGame.png");
		newGameActive = new ImageManager("assets/img/buttons/newGameActive.png");
		left = new ImageManager("assets/img/buttons/left.png");
		leftActive = new ImageManager("assets/img/buttons/leftActive.png");
		right = new ImageManager("assets/img/buttons/right.png");
		rightActive = new ImageManager("assets/img/buttons/rightActive.png");
		done = new ImageManager("assets/img/buttons/done.png");
		doneActive = new ImageManager("assets/img/buttons/doneActive.png");
		
		this.WIDTH = w;
		this.HEIGHT = h;
		
		timer.start();
		run();
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
		creatingCharacter = false;
		wanted = false;
		
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
					
					g.drawImage(armorClerk.guy, 32*16, 32*2, null);
				}
			}
			if(location == "armorer" && armorVisible) {	//draws the armor if armorVisible is true
				g.drawImage(leatherHelm.guy, armorx, armory, null);
			}
			
			
			//draws the character
			g.drawImage(head.guy,32*16,32*10,null);	//32 is the tile size, so this chooses how many tiles over it is
			g.drawImage(torso.guy,32*16,32*10, null);
			g.drawImage(legs.guy,32*16,32*10,null);
			
			//draws equipment
			if(player.Class == "thief") {
				g.drawImage(thiefHelm.guy, 32*16, 32*10, null);
				g.drawImage(thiefChest.guy, 32*16, 32*10, null);
			}
			else {
				if(player.Class == "mage") {
					g.drawImage(hoodHelm.guy, 32*16, 32*10, null);
					g.drawImage(robeChest.guy, 32*16, 32*10, null);
				}
				else {
					g.drawImage(leatherHelm.guy, 32*16, 32*10, null);
					g.drawImage(leatherChest.guy, 32*16, 32*10, null);
				}
			}
			
			//draws the in-game UI
			g.setColor(Color.gray.darker());
			g.fillRect(2, HEIGHT - 149, WIDTH - 4, 148);
			
			g.setColor(Color.black);
			g.drawRect(2, HEIGHT - 149, WIDTH - 4, 148);
			g.drawRect(2, HEIGHT - 149, 32*5 - 10, 32*4);
			
			if(wanted) {
				console.showText(">> Theft Failed", g);
			}
			else {
				if(caughtChecker >= 0 && caught > 9) {
					console.showText(">> Theft Succeeded", g);
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
						location = "armorer";
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
			}
			
			if(mousemanager.mousex > 32*16 && mousemanager.mousex < 32*17 && mousemanager.mousey > 32*10 && mousemanager.mousey < 32*12) {
				if(mousemanager.isRightPressed()) {
					playerMenuVisible = true;
				}
			}
			else {
				if(mousemanager.isLeftPressed()) {
					playerMenuVisible = false;
				}
			}
			
			//shows the player stat menu
			if(playerMenuVisible) {
				g.setColor(Color.gray.darker());
				g.fillRect(32*18, 32*6, 200, 300);
				g.fillRect(32*18, 32*6, 64, 64);
				
				g.setColor(Color.black);
				g.drawRect(32*18, 32*6, 200, 300);
				g.drawRect(32*18, 32*6, 64, 64);
				
				g.setFont(new Font("Times New Roman",Font.BOLD,18));
				g.drawString("Hp: " + player.maxHP, 32*18 + 3, 32*6 + 80);
				g.drawString("Str: " + player.str, 32*18 + 3, 32*6 + 110);
				g.drawString("Luck: " + player.luck, 32*18 + 3, 32*6 + 140);
				g.drawString("Int: " + player.Int, 32*18 + 3, 32*6 + 170);
				g.drawString("Sneak: " + player.sneak, 32*18 + 3, 32*6 + 200);
				g.drawString("Speech: " + player.speech, 32*18 + 3, 32*6 + 230);
				
				//shows the face
				if(player.race == "orc") {
					g.drawImage(mediumOrc.guy, 32*18, 32*6 + 15, null);
				}
				else {
					g.drawImage(mediumHuman.guy, 32*18, 32*6 + 15, null);
				}
				
				//shows your helmet
				if(player.Class == "thief") {	//this system will eventually be modified when more armor is added
					g.drawImage(mediumThief.guy, 32*18, 32*6 + 15, null);
				}
				else {
					if(player.Class == "mage") {
						g.drawImage(mediumHood.guy, 32*18, 32*6 + 15, null);
					}
					else {
						g.drawImage(mediumLeather.guy, 32*18, 32*6 + 15, null);
					}
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
				
				//shows starter equipment on the head
				if(player.Class == "warrior") {
					g.drawImage(bigLeather.guy, 3, 30, null);
				}
				else {
					if(player.Class == "thief") {
						g.drawImage(bigThief.guy, 2, 30, null);
					}
					else {
						g.drawImage(bigHood.guy, 2, 30, null);
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
		
		//armor restock timer and caught checking timer
		if(armorRestock > 0) {
			armorRestock--;
		}
		
		//it is used to see if caught is below 10 for about 1 second
		if(caughtChecker > 0) {
			caughtChecker--;
		}
		
		//caught is chosen randomly based on the player's sneak stat, this catches you if it is below 10
		//so if you have 10 sneak you have a 10% or 1/10 chance to get away with it
		if(caught < 10 && caughtChecker == 0) {
			caughtChecker = -3;
			wanted = true;
		}
		
		//makes the armor dissapear after it's clicked
		if(mousemanager.mousex > armorx && mousemanager.mousex < armorx + armorw && mousemanager.mousey > armory && mousemanager.mousey < armory + armorh) {
			if(mousemanager.isLeftPressed()) {
				armorVisible = false;
				armorRestock = 125;
				//sets caught to a random number between 0 and the player's sneak stat
				caught = random.nextInt(player.sneak);
				caughtChecker = 25;	//25 is about 1 second
			}
		}
		
		if(location == "armorer") {
			if(mousemanager.mousex > 32*16 && mousemanager.mousex < 32*17 && mousemanager.mousey > HEIGHT-181 && mousemanager.mousey < HEIGHT-149) {
				if(mousemanager.isLeftPressed()) {
					location = "town";
				}
			}
		}
		
		if(state == "main menu") {	//main menu logic
			//adds class bonuses to stats
			if(player.Class == "thief") {
				sneakBonus = 5;
				speechBonus = 5;
				intBonus = 0;
				hpBonus = 0;
				luckBonus = 0;
				strBonus = 0;
			}
			else {
				if(player.Class == "mage") {
					intBonus = 5;
					luckBonus = 5;
					sneakBonus = 0;
					speechBonus = 0;
					hpBonus = 0;
					strBonus = 0;
				}
				else {
					strBonus = 5;
					hpBonus = 5;
					intBonus = 0;
					luckBonus = 0;
					sneakBonus = 0;
					speechBonus = 0;
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
			
			if(mousemanager.mousex >= hpLeft.x && mousemanager.mousex <= hpLeft.x + hpLeft.w && mousemanager.mousey >= hpLeft.y && mousemanager.mousey <= hpLeft.y + hpLeft.h) {
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
			
			if(mousemanager.mousex >= hpRight.x && mousemanager.mousex <= hpRight.x + hpRight.w && mousemanager.mousey >= hpRight.y && mousemanager.mousey <= hpRight.y + hpRight.h) {
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
			
			if(mousemanager.mousex >= raceLeft.x && mousemanager.mousex <= raceLeft.x + raceLeft.w && mousemanager.mousey >= raceLeft.y && mousemanager.mousey <= raceLeft.y + raceLeft.h) {
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
			
			if(mousemanager.mousex >= raceRight.x && mousemanager.mousex <= raceRight.x + raceRight.w && mousemanager.mousey >= raceRight.y && mousemanager.mousey <= raceRight.y + raceRight.h) {
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
			
			
			if(mousemanager.mousex >= strLeft.x && mousemanager.mousex <= strLeft.x + strLeft.w && mousemanager.mousey >= strLeft.y && mousemanager.mousey <= strLeft.y + strLeft.h) {
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
			
			if(mousemanager.mousex >= strRight.x && mousemanager.mousex <= strRight.x + strRight.w && mousemanager.mousey >= strRight.y && mousemanager.mousey <= strRight.y + strRight.h) {
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
			
			
			if(mousemanager.mousex >= luckLeft.x && mousemanager.mousex <= luckLeft.x + luckLeft.w && mousemanager.mousey >= luckLeft.y && mousemanager.mousey <= luckLeft.y + luckLeft.h) {
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
			
			if(mousemanager.mousex >= luckRight.x && mousemanager.mousex <= luckRight.x + luckRight.w && mousemanager.mousey >= luckRight.y && mousemanager.mousey <= luckRight.y + luckRight.h) {
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
			
			
			if(mousemanager.mousex >= IntLeft.x && mousemanager.mousex <= IntLeft.x + IntLeft.w && mousemanager.mousey >= IntLeft.y && mousemanager.mousey <= IntLeft.y + IntLeft.h) {
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
			
			if(mousemanager.mousex >= IntRight.x && mousemanager.mousex <= IntRight.x + IntRight.w && mousemanager.mousey >= IntRight.y && mousemanager.mousey <= IntRight.y + IntRight.h) {
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
			
			
			if(mousemanager.mousex >= sneakLeft.x && mousemanager.mousex <= sneakLeft.x + sneakLeft.w && mousemanager.mousey >= sneakLeft.y && mousemanager.mousey <= sneakLeft.y + sneakLeft.h) {
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
			
			if(mousemanager.mousex >= sneakRight.x && mousemanager.mousex <= sneakRight.x + sneakRight.w && mousemanager.mousey >= sneakRight.y && mousemanager.mousey <= sneakRight.y + sneakRight.h) {
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
			
			
			if(mousemanager.mousex >= speechLeft.x && mousemanager.mousex <= speechLeft.x + speechLeft.w && mousemanager.mousey >= speechLeft.y && mousemanager.mousey <= speechLeft.y + speechLeft.h) {
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
			
			if(mousemanager.mousex >= speechRight.x && mousemanager.mousex <= speechRight.x + speechRight.w && mousemanager.mousey >= speechRight.y && mousemanager.mousey <= speechRight.y + speechRight.h) {
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
			
			
			if(mousemanager.mousex >= classLeft.x && mousemanager.mousex <= classLeft.x + classLeft.w && mousemanager.mousey >= classLeft.y && mousemanager.mousey <= classLeft.y + classLeft.h) {
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
			
			if(mousemanager.mousex >= classRight.x && mousemanager.mousex <= classRight.x + classRight.w && mousemanager.mousey >= classRight.y && mousemanager.mousey <= classRight.y + classRight.h) {
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
			
			
			if(mousemanager.mousex >= doneButton.x && mousemanager.mousex <= doneButton.x + doneButton.w && mousemanager.mousey >= doneButton.y && mousemanager.mousey <= doneButton.y + doneButton.h) {
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
