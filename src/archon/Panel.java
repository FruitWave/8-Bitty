package archon;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Panel extends JPanel implements ActionListener, KeyListener {
	Timer masterclock;
	GameManager atari;
	boolean initialstart = true;
	static boolean inJumpingProcess;
	static PlayerOne morrow;
	static Enemy sixer;
	Block new_friendly;
	Block initial_enemyblock1;
	Block initial_onethirdblock;
	Block initial_twothirdsblock;
	static Timer fallnow;
	static int fallnowcount = 0;
	static boolean playerupbutton;
	static Timer respawnTimer;
	static int currentState = 1;
	final static int INFO_STATE = 0;
	final static int GAME_STATE = 1;
	final static int END_STATE = 3;
	final static int VICTORY_STATE = 2;
	Color olive = new Color(30, 128, 30);
	Font font1;
	Font font2;
	double startTimeInMs;
	public static int level = 1;
	// Timer enemspawne;
	// int spawnedde;
	boolean drawandStartNextLevel = false;
	Backburner backgrundi;
	static int whichLevelCommonKnowledge;
	boolean ingameMessage = false;
	static String levelmessage;
	static boolean endNotTouched = true;
	int makeTowerBlockNum;
	static boolean restartPressed = false;
	boolean adminSkipLevel = false;
	static boolean immortal = false;
	boolean levelwasskippedto = false;
	static final String cheatcode = "noxluna";
	static boolean cheatsenabled = false;
	static BufferedImage morrowright;
	static BufferedImage morrowleft;
	static BufferedImage dirttop;
	static BufferedImage dirttopdeadly;
	static BufferedImage dirt;
	static BufferedImage dirtdeadly;
	static BufferedImage enemy;
	static BufferedImage bullet;

	public Panel() {
		masterclock = new Timer(1000 / 120, this);
		fallnow = new Timer(10, this);
		respawnTimer = new Timer(5, this);
		// respawnTimer.setInitialDelay(1);
		font1 = new Font("Arial", 0, 24);
		font2 = new Font("Arial", 0, 24);
		// enemspawne = new Timer(700, this);
		try {
			morrowright = ImageIO.read(this.getClass().getResourceAsStream("MorrowRight.jpg"));
			morrowleft = ImageIO.read(this.getClass().getResourceAsStream("MorrowLeft.jpg"));
			dirttop = ImageIO.read(this.getClass().getResourceAsStream("DirtTop.jpg"));
			dirttopdeadly = ImageIO.read(this.getClass().getResourceAsStream("DirtTopDeadly.jpg"));
			dirt = ImageIO.read(this.getClass().getResourceAsStream("Dirt.jpg"));
			dirtdeadly = ImageIO.read(this.getClass().getResourceAsStream("DirtDeadly.jpg"));
			enemy = ImageIO.read(this.getClass().getResourceAsStream("Enemy.jpg"));
			bullet = ImageIO.read(this.getClass().getResourceAsStream("Bullet.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (morrow != null) {
			if (e.getKeyCode() == KeyEvent.VK_I) {
				if (currentState != END_STATE) {
					currentState = currentState == INFO_STATE ? GAME_STATE : INFO_STATE;
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				String czechthis = JOptionPane.showInputDialog("Enter your cheat code below.");
				if (czechthis.equalsIgnoreCase(cheatcode)) {
					cheatsenabled = true;
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_R) {
				restartPressed = true;
				respawnTimer.restart();
				morrow.timesdied = 0;
			}
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				playerupbutton = true;
				fallnowcount = 0;
				fallnow.restart();

			}

			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				morrow.xspeedAdder = -2;
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				morrow.xspeedAdder = 2;
			}
			if (cheatsenabled) {
				if (e.getKeyCode() == KeyEvent.VK_P) {
					JOptionPane.showMessageDialog(null, "donotfall is " + morrow.donotfall);
				}
				if (e.getKeyCode() == KeyEvent.VK_A) {
					immortal = immortal == false ? true : false;
				}
				if (e.getKeyCode() == KeyEvent.VK_V) {
					currentState = VICTORY_STATE;
				}
				if (e.getKeyCode() == KeyEvent.VK_L) {
					adminSkipLevel = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_T) {
					morrow.x = Runner.width - 60;
				}
			}

		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (morrow != null) {
			morrow.xspeedAdder = 0;
			playerupbutton = false;
		}
	}

	void updateVictoryState() {

	}

	void drawVictoryState(Graphics anki) {
		anki.setColor(Color.BLACK);
		anki.fillRect(0, 0, Runner.width, Runner.height);
		for (int i = 0; i < 30; i++) {
			int rundoe = new Random().nextInt(6);
			anki.setColor(setRandomColor(rundoe));
			anki.fillRect(new Random().nextInt(Runner.width), new Random().nextInt(Runner.height), 10, 10);
			anki.setFont(font1);
			font1 = new Font("Arial", 0, 200);
			anki.drawString("!You Win!", Runner.width / 4, Runner.height / 2);

		}
	}

	Color setRandomColor(int rundoe) {
		switch (rundoe) {
		case 0:
			return Color.cyan;
		case 1:
			return Color.green;
		case 2:
			return Color.magenta;
		case 3:
			return Color.orange;
		case 4:
			return Color.red;
		case 5:
			return Color.white;
		default:
			return null;
		}
	}

	void updateGameState() {
		if (morrow != null) {
			if ((atari != null)) {
				if (morrow.donotfall) {
					morrow.yspeedAdder = 0;
				}
				if (morrow.yspeed >= 2) {
					System.out.println("YSPEED IS " + morrow.yspeed);
					morrow.yspeed = 0;
				}
				if (morrow.y < 0) {
					// morrow.yspeedAdder = -morrow.yspeedAdder + 1;
					morrow.isAlive = false;
				}
				atari.update();
				atari.checkCollision();
				atari.manageEnemies();

				if (morrow.isAlive == false) {
					respawnTimer.start();
				}

				if (((morrow.x + morrow.width >= Runner.width) && endNotTouched) || (adminSkipLevel)) {
					endNotTouched = false;
					level += 1;
					drawandStartNextLevel = true;
					if (adminSkipLevel) {
						levelwasskippedto = true;
					}

				} else if (morrow.x + morrow.width < Runner.width) {
					endNotTouched = true;
				}

			}
		}
	}

	void updateEndState() {
	}

	void drawGameState(Graphics elevi) {
		if (morrow != null) {
			elevi.setColor(Color.BLACK);
			elevi.fillRect(0, 0, Runner.width, Runner.height);
			if (atari != null) {
				atari.draw(elevi);
			}
			elevi.setColor(Color.CYAN);
			elevi.setFont(font1);
			elevi.drawString("Morrow X-Speed:   " + morrow.xspeed, 100, 25);
			elevi.drawString("Morrow Y-Speed:   " + morrow.yspeed, 100, 50);
			elevi.drawString("Times Died:   " + morrow.timesdied, 100, 75);
			elevi.drawString("Lives:   " + morrow.lives, 100, 100);
			double now = System.currentTimeMillis();
			double timeelsapsed = (now - startTimeInMs) / 1000;
			elevi.drawString("Time: " + timeelsapsed, Runner.width / 3, 25);
			if (ingameMessage) {
				ingamemessage(levelmessage, elevi);
			}

		}
	}

	private void drawInformationState(Graphics oalr) {
		oalr.setColor(Color.black);
		oalr.fillRect(0, 0, Runner.width, Runner.height);
		oalr.setFont(font1);
		oalr.setColor(olive);
		oalr.drawString("Welcome to the Information Panel", Runner.width / 20, Runner.height / 10);
		oalr.drawString("Use the UP, LEFT, and RIGHT arrow keys to move", Runner.width / 20, (Runner.height / 10) * 2);
		oalr.drawString("Hit the right side of the window to progress to the next level", Runner.width / 20,
				(Runner.height / 10) * 3);
		oalr.drawString(
				"DO NOT HOLD ANY LETTER KEYS DOWN (you can hold arrow keys down). DOING SO WILL LOCK YOUR KEYBOARD.",
				Runner.width / 20, (Runner.height / 10) * 4);
		oalr.drawString("Hit 'r' to restart", Runner.width / 20, (Runner.height / 10) * 5);
		oalr.drawString(
				"You will not be given any more information., but will instead learn by exploring the game for yourself",
				Runner.width / 20, (Runner.height / 10) * 6);
		oalr.drawString(
				"Press 'i' to re-open this panel from in-game (this will pause the game), and 'i' right now to close this panel and unpause the game",
				Runner.width / 20, (Runner.height / 10) * 7);
		oalr.setColor(Color.red);
		oalr.drawString(
				"LAST BUT NOT LEAST, MAJOR EPILEPSY WARNING (this really only applies to the Victory screen, but I just want to establish this anyway.",
				Runner.width / 20, (Runner.height / 10) * 8);
		oalr.setColor(olive);
		oalr.drawString("Have Fun!", Runner.width / 20, (Runner.height / 10) * 9);
		oalr.setColor(Color.darkGray);
		if (!cheatsenabled) {
			oalr.drawString("Enter your cheat code by pressing 'enter/return'", Runner.width / 20,
					(Runner.height / 20) * 19);
		} else {
			oalr.drawString("Cheats are enabled.", Runner.width / 20, (Runner.height / 20) * 19);
		}

	}

	private void updateInformationState() {

	}

	void drawEndState(Graphics elevi) {
		elevi.setColor(Color.black);
		elevi.fillRect(0, 0, Runner.width, Runner.height);
		elevi.setColor(Color.CYAN);
		elevi.setFont(font1);
		font1 = new Font("Arial", 0, 100);
		elevi.drawString("GAME OVER", Runner.width / 4, Runner.height / 2);
		elevi.drawString("PRESS R TO RESTART", Runner.width / 4, Runner.height * 2 / 3);

	}

	public void startGame(boolean fullRestarting) {
		if (initialstart == true) {
			initialstart = false;
			currentState = INFO_STATE;
		}
		if ((level == 1)) {
			backgrundi = new Backburner(0, 0, Runner.width, Runner.height);
			startTimeInMs = System.currentTimeMillis();
			font1 = new Font("Arial", 0, 24);
			morrow = new PlayerOne(100, 300, 50, 50, true, this, atari);
			if (fullRestarting) {
				morrow.lives = 3;
			}

			sixer = new Enemy(Runner.width - 100, 300, 50, 50, false, null, this);
			new_friendly = new Block(100, 400, 50, 50, true, false, true);

			initial_enemyblock1 = new Block(Runner.width - 100, 400, 50, 50, true, false, true);
			initial_onethirdblock = new Block(Runner.width / 3, 400, 50, 50, false, false, true);
			initial_twothirdsblock = new Block(Runner.width * 2 / 3, 400, 50, 50, false, false, true);
			/* spawnedde = 0; */
			atari = new GameManager();
			atari.addObject(backgrundi);
			atari.addObject(morrow);
			atari.addObject(sixer);

			// atari.addObject(new_friendly);
			atari.addObject(initial_enemyblock1);
			atari.addObject(initial_onethirdblock);
			atari.addObject(initial_twothirdsblock);
			atari.addObject(new_friendly);
			outsourceOneTowerBuild(new_friendly, false, false, true);

			// makeTowers(false, new_friendly.x, new_friendly.x, new_friendly.y, 1);
			// enemspawne.start();

			masterclock.start();
			System.out.println("Started!");
			ingameMessage = true;
			levelmessage = "In this level, you'll find out what to avoid, and how the game's basic physics work.";
		} else {
			// JOptionPane.showMessageDialog(null, "LEVEL IS NOT ONE");
		}
	}

	public void makeMultipleTowers(int startingX, int endingX, int startingHeight, int widthOfBlock, int heightOfBlock,
			int numberOfTowers, boolean deadly, boolean toBot) {
		startingHeight += heightOfBlock;
		Block keystone = new Block(startingX, startingHeight, widthOfBlock, heightOfBlock, true, deadly, true);

		int numTowersMade = 0;
		for (double i = startingX /* start at the starting x */; endingX
				- i >= 0 /* as long as there is space, continue */; i += (endingX - startingX)
						/ (numberOfTowers - 1) /* increment by space/towers */) {
			// System.out.println("Increment by " + ((endingX - startingX) / (numberOfTowers
			// - 1)));
			if (numTowersMade < numberOfTowers /* if the number of towers quota is not filled, continue */) {
				keystone.x = (int) i;
				// atari.addObject(keystone);
				outsourceOneTowerBuild(keystone, true, deadly, toBot);
				numTowersMade++;
				// System.out.println(
				// "Num of towers to have total: " + numberOfTowers + " Number yet made: " +
				// numTowersMade);
				if (whichLevelCommonKnowledge == 2 || whichLevelCommonKnowledge == 3) {
					Enemy jacobs = new Enemy(keystone.x, keystone.y - 150, 50, 50, false, null, this);
					atari.addObject(jacobs);
				}
			}
		}

	}

	public void outsourceOneTowerBuild(Block referenceBlock, boolean makingMultiple, boolean deadly,
			boolean toBot /* (to bottom) */) {
		// must have existing block (added to atari) for reference!!!
		// Block permanitial = initialBlock;
		if (toBot) {
			for (int p = 0; Runner.height - referenceBlock.y
					+ referenceBlock.height >= 0 /* is there vertical space left */; p++) {
				Block newblock;
				if (makingMultiple && (p == 0)) {
					newblock = new Block(referenceBlock.x, referenceBlock.y + referenceBlock.height,
							referenceBlock.width, referenceBlock.height, true, deadly, true);
				} else {
					newblock = new Block(referenceBlock.x, referenceBlock.y + referenceBlock.height,
							referenceBlock.width, referenceBlock.height, true, deadly, false);
				}
				newblock.y = referenceBlock.y + referenceBlock.height;

				// new block just below reference point
				referenceBlock = newblock;
				// set new block to the reference point
				atari.addObject(newblock);

				// add newly set new reference point
				/*
				 * if (makingMultiple) { System.out.println("X is " + referenceBlock.x); }
				 */

			}
		} else {
			for (int pr = 0; referenceBlock.y > 0 /* is there vertical space left */; pr++) {
				Block newblock = new Block(referenceBlock.x, referenceBlock.y - referenceBlock.height,
						referenceBlock.width, referenceBlock.height, true, deadly, false);
				newblock.y = referenceBlock.y - referenceBlock.height;
				// new block just below reference point
				referenceBlock = newblock;
				// set new block to the reference point
				atari.addObject(newblock);
				// add newly set new reference point
				/*
				 * if (makingMultiple) { System.out.println("X is " + referenceBlock.x); }
				 */

			}

		}

	}

	private void ingamemessage(String stringgeroo, Graphics oalr) {
		oalr.setColor(Color.RED);
		oalr.setFont(font2);
		oalr.drawString(stringgeroo, Runner.width / 4, 300);
		// System.out.println("ingamemessage called");
	}

	public void startNextLevel(int whichlevel, Graphics oalr) {
		// switchedLevel = true;
		masterclock.restart();
		atari = new GameManager();
		whichLevelCommonKnowledge = whichlevel;
		// System.out.println("Level is " + whichLevelCommonKnowledge);
		levelwasskippedto = adminSkipLevel ? true : false;
		adminSkipLevel = false;
		fallnowcount = 0;
		fallnow.restart();
		startTimeInMs = System.currentTimeMillis();
		atari.reset();
		backgrundi = new Backburner(0, 0, Runner.width, Runner.height);
		atari.addObject(backgrundi);

		switch (whichlevel) {
		case 2:
			startLevel2(oalr);
			break;
		case 3:
			startLevel3(oalr);
			break;
		case 4:
			startLevel4(oalr);
			break;
		case 5:
			startLevel5(oalr);
			break;
		case 6:
			startLevel6(oalr);
			break;
		case 7:
			startLevel7(oalr);
			break;
		case 8:
			startLevel8(oalr);
			break;
		case 9:
			startLevel9(oalr);
			break;
		case 10:
			startLevel10(oalr);
			break;
		case 11:
			currentState = VICTORY_STATE;
			break;
		default:
			JOptionPane.showMessageDialog(null, "ERROR! LEVEL IS NOT GREATER THAN ONE AND LESS THAN 12");
			break;
		}
	}

	public void startLevel10(Graphics oalr) {
		ingameMessage = false;
		morrow = new PlayerOne(0, Runner.height / 2, 5, 5, false, this, atari);
		atari.addObject(morrow);
		for (int i = 0; i < 5; i++) {
			Enemy depthcharge = new Enemy(i * Runner.width / 5, Math.abs(5 - i) * Runner.height / 5, 100, 100, false,
					"allDirection", this);
			atari.addObject(depthcharge);
		}

	}

	public void startLevel9(Graphics oalr) {
		ingameMessage = true;
		levelmessage = "Be carefull...they see you....";
		morrow = new PlayerOne(100, Runner.height / 3, 50, 50, true, this, atari);
		atari.addObject(morrow);
		Enemy hell = new Enemy(300, Runner.height / 3, 50, 50, true, "leftwards", this);
		Enemy negahell = new Enemy(Runner.width - 300, 2 * Runner.height / 3, 50, 50, true, "rightwards", this);
		Enemy hellfire = new Enemy(4 * Runner.width / 5, 100, 50, 50, false, "leftwards", this);
		Enemy fireball = new Enemy(Runner.width / 5, Runner.height - 100, 50, 50, false, "leftwards", this);
		Enemy duonin = new Enemy((Runner.width / 2) - 50, (Runner.height / 2) - 25, 50, 50, true, "allDirection", this);
		Enemy ninodu = new Enemy((Runner.width / 2), (Runner.height / 2) - 25, 50, 50, true, "allDirection", this);
		atari.addObject(duonin);
		atari.addObject(ninodu);
		fireball.reverseGrav = true;
		atari.addObject(fireball);
		atari.addObject(hell);
		atari.addObject(negahell);
		atari.addObject(hellfire);
	}

	public void startLevel8(Graphics oalr) {
		morrow = new PlayerOne(25, Runner.height / 2, 50, 50, true, this, atari);
		atari.addObject(morrow);
		int startingheight = 150;
		for (int i = startingheight; i < Runner.height - startingheight; i += 50) {
			Enemy end = new Enemy(i + Runner.width / 2, i, 50, 50, true, "minigunLeft", this);
			atari.addObject(end);
			if (morrow.x > end.x) {
				end.bulletType = "minigunRight";
			}
		}
	}

	public void startLevel7(Graphics oalr) {
		ingameMessage = false;
		morrow = new PlayerOne((Runner.width / 2) - 25, Runner.height / 2, 50, 50, true, this, atari);
		atari.addObject(morrow);
		for (int i = 0; i < 4; i++) {
			atari.addObject(new Enemy(new Random().nextInt(Runner.width - 50), new Random().nextInt(Runner.height - 50),
					50, 50, true, "allDirection", this));
		}

	}

	public void startLevel6(Graphics oalr) {
		ingameMessage = true;
		levelmessage = "Conglaturations! You have made it to Level " + whichLevelCommonKnowledge + "!!!";
		morrow = new PlayerOne(10, Runner.height / 2, 50, 50, true, this, atari);
		atari.addObject(morrow);
		atari.addObject(new Enemy(Runner.width / 10, Runner.height / 10 * 8, 50, 50, true, "upwards", this));
		atari.addObject(new Enemy(9 * Runner.width / 10, Runner.height / 10 * 8, 50, 50, true, "upwards", this));
		// outer upward-firing enemies
		atari.addObject(
				new Enemy((Runner.width / 10) + 51, Runner.height / 10 * 8, 50, 50, true, "minigunRight", this));
		atari.addObject(
				new Enemy(9 * (Runner.width / 10) - 51, Runner.height / 10 * 8, 50, 50, true, "minigunLeft", this));
		// minigunners on bottom
		atari.addObject(new Enemy((Runner.width / 10) + 101, Runner.height / 10, 50, 50, true, "downwards", this));
		atari.addObject(new Enemy(9 * (Runner.width / 10) - 101, Runner.height / 10, 50, 50, true, "downwards", this));
		// inner downward-firing enemies
		atari.addObject(new Enemy(Runner.width / 4, (2 * Runner.height / 10) - 25, 50, 50, false, "rightwards", this));
		atari.addObject(new Block(Runner.width / 4, (2 * Runner.height / 10) + 50, 50, 50, true, true, true));
		// the above pair is in the middle area on the left
		atari.addObject(
				new Enemy(3 * Runner.width / 4, (2 * Runner.height / 10) - 25, 50, 50, false, "leftwards", this));
		atari.addObject(new Block(3 * Runner.width / 4, (2 * Runner.height / 10) + 50, 50, 50, true, true, true));
		// the above pair is in the middle area on the right
		atari.addObject(new Block(3 * Runner.width / 8, (Runner.height / 2) - 25, 50, 50, true, true, true));
		atari.addObject(new Block(5 * Runner.width / 8, (Runner.height / 2) - 25, 50, 50, true, true, true));
		atari.addObject(new Block((Runner.width / 2), (Runner.height / 2) - 25, 50, 50, true, true, true));
		// middle area obstacles
		atari.addObject(new Enemy(Runner.width / 4, (8 * Runner.height / 10) - 175, 50, 50, false, "rightwards", this));
		atari.addObject(new Block(Runner.width / 4, (8 * Runner.height / 10) - 100, 50, 50, true, true, true));
		// bottom right-firing pair
		atari.addObject(
				new Enemy(3 * Runner.width / 4, (8 * Runner.height / 10) - 175, 50, 50, false, "leftwards", this));
		atari.addObject(new Block(3 * Runner.width / 4, (8 * Runner.height / 10) - 100, 50, 50, true, true, true));
		// bottom left-firing pair
	}

	public void startLevel5(Graphics oalr) {
		ingameMessage = true;
		levelmessage = "Conglaturations! You have made it to Level " + whichLevelCommonKnowledge + "!!!";
		morrow = new PlayerOne(200, 600, 50, 50, true, this, atari);
		atari.addObject(morrow);
		int dotz = (Runner.width / 19) + (Runner.height / 19);
		// int dotz = 1000;
		for (int i = 0; i < dotz; i++) {
			int wow = new Random().nextInt(Runner.width);
			int wowzer = new Random().nextInt(Runner.height);
			int waaaaaa = new Random().nextInt(5);
			boolean ah = waaaaaa >= 0 && waaaaaa <= 3 ? true : false;

			if (!(!((wow > morrow.x + morrow.width + 100) || (wow < morrow.x - 100))
					&& !((wowzer > morrow.y + morrow.height + 100) || (wowzer < morrow.y - 100)))) {
				// why do i need the double negative statement, as opposed to NO contraditions
				// (!s)???
				Block woah = new Block(wow, wowzer, 5, 5, true, ah, true);
				atari.addObject(woah);
			} else {
				i--;
			}

		}

	}

	private void startLevel4(Graphics oalr) {
		ingameMessage = true;
		levelmessage = "Don't touch the don't-touchies.";
		morrow = new PlayerOne(200, 600, 50, 50, true, this, atari);
		atari.addObject(morrow);
		makeMultipleTowers(Runner.width / 10, Runner.width - 100, 375, 50, 50, 8, true, false);
		makeMultipleTowers(Runner.width / 10, Runner.width - 100, Runner.height - 375, 50, 50, 8, true, true);
	}

	public void startLevel3(Graphics oalr) {

		ingameMessage = true;
		levelmessage = "Now the bullets get more creative...and scary. Use your knowledge to survive and advance.";
		morrow = new PlayerOne(200, 600, 50, 50, true, this, atari);
		atari.addObject(morrow);
		makeMultipleTowers(Runner.width / 3, (Runner.width / 3) + 150, 300, 50, 50, 2, false, true);
		makeMultipleTowers(2 * Runner.width / 3, (2 * (Runner.width / 3)) + 150, 300, 50, 50, 2, false, true);
	}

	public void startLevel2(Graphics oalr) {
		ingameMessage = true;
		levelmessage = "In this level, you can discover how bullets and blocks interact, as well as how your avatar can interact with blocks.";
		morrow = new PlayerOne(200, 600, 50, 50, true, this, atari);
		atari.addObject(morrow);
		makeMultipleTowers(Runner.width / 3, 2 * Runner.width / 3, Runner.height / 8, 50, 50, 3, false, true);
		masterclock.restart();
		// System.out.println(jacobs.enemyonBlock);
	}

	public void paintComponent(Graphics oalr) {
		if (drawandStartNextLevel) {
			drawandStartNextLevel = false;
			startNextLevel(level, oalr);
		}
		if (currentState == INFO_STATE) {
			drawInformationState(oalr);
		} else if (currentState == GAME_STATE) {
			drawGameState(oalr);
		} else if (currentState == END_STATE) {
			drawEndState(oalr);
		} else if (currentState == VICTORY_STATE) {
			drawVictoryState(oalr);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
		// if (e.getSource() == enemspawne) {
		// // spawnedde++;
		// // Enemy spawneth = new Enemy(Runner.width * 2 / 3, -500, 50, 50, this);
		// // atari.addObject(spawneth);
		// System.out.println("hi");
		// }

		if (e.getSource() == respawnTimer) {
			if (morrow != null) {
				System.out.println("Morrow is not null.");
				respawnTimer.stop();

				morrow.timesdied++;
				morrow.lives--;
				sixer = null;
				new_friendly = null;
				initial_enemyblock1 = null;
				initial_onethirdblock = null;
				initial_twothirdsblock = null;
				playerupbutton = false;

				// Timer enemspawne;
				// int spawnedde;
				drawandStartNextLevel = false;
				backgrundi = null;
				ingameMessage = false;
				levelmessage = null;
				endNotTouched = true;
				fallnowcount = 0;
				fallnow.restart();
				if (!levelwasskippedto) {
					level = 1;
				} else {
					startNextLevel(whichLevelCommonKnowledge, getGraphics());
				}
				if (morrow.lives <= 0) {
					System.out.println("Morrow has " + morrow.lives + " lives.");
					atari.reset();
					morrow.lives = 3;
					morrow.timesdied = 0;
					currentState = END_STATE;
				} else if ((currentState == END_STATE) && (restartPressed)) {
					System.out.println("Full restart from end state");
					currentState = GAME_STATE;
					morrow.timesdied = 0;
					morrow.lives = 3;
					restartPressed = false;
					startGame(true);
				} else if (morrow.lives >= 1) {
					System.out.println("Lives are at: " + morrow.lives);
					startGame(false);
				}
			} else {
				JOptionPane.showMessageDialog(null,
						"Morrow is null. Please rewrite the code in this block to prevent this from happening.");
			}
		}

		if (e.getSource() == fallnow) {
			inJumpingProcess = true;

			if (morrow != null) {
				fallnowcount++;
				if (fallnowcount < 8) {
					morrow.yspeedAdder -= 1;
				} else if ((fallnowcount > 7) && (fallnowcount < 15)) {
					morrow.yspeedAdder += 1;

				} else {
					morrow.donotfall = false;
					fallnow.stop();

					inJumpingProcess = false;
				}
			}

		}

		if (currentState == GAME_STATE) {
			updateGameState();
		} else if (currentState == END_STATE) {
			updateEndState();
		} else if (currentState == INFO_STATE) {
			updateInformationState();
		} else if (currentState == VICTORY_STATE) {
			updateVictoryState();
		}

	}
}