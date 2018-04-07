package archon;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements ActionListener, KeyListener {
	Timer masterclock;
	GameManager atari;
	static PlayerOne morrow;
	static Enemy sixer;
	// static Enemy sux0r;
	Block new_friendly;
	Block initial_enemyblock1;
	Block initial_onethirdblock;
	Block initial_twothirdsblock;
	static Timer fallnow;
	static int fallnowcount = 0;
	static boolean playerupbutton;
	static Timer respawnTimer;
	static int currentState = 1;
	final static int GAME_STATE = 1;
	final static int VICTORY_STATE = 4;
	final static int END_STATE = 3;
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

	public GamePanel() {
		masterclock = new Timer(1000 / 120, this);
		fallnow = new Timer(10, this);
		respawnTimer = new Timer(5, this);
		// respawnTimer.setInitialDelay(1);
		font1 = new Font("Arial", 0, 24);
		font2 = new Font("Arial", 0, 24);
		// enemspawne = new Timer(700, this);
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (morrow != null) {
			if (e.getKeyCode() == KeyEvent.VK_I) {
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
			if (e.getKeyCode() == KeyEvent.VK_R) {
				restartPressed = true;
				respawnTimer.restart();
				morrow.timesdied = 0;
			}
			if (/* (morrow.playeronBlock) && */ e.getKeyCode() == KeyEvent.VK_UP) {
				playerupbutton = true;
				fallnow.restart();
			}

			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				morrow.xspeedAdder = -2;
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				morrow.xspeedAdder = 2;
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
		anki.drawRect(0, 0, Runner.width, Runner.height);
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
				if (morrow.y < 0) {
					morrow.yspeedAdder = -morrow.yspeedAdder + 1;
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
		if ((level == 1) /* && (!immortal) */) {
			backgrundi = new Backburner(0, 0, Runner.width, Runner.height);
			startTimeInMs = System.currentTimeMillis();
			font1 = new Font("Arial", 0, 24);
			morrow = new PlayerOne(100, 300, 50, 50, true, this, atari);
			if (fullRestarting) {
				morrow.lives = 3;
			}

			sixer = new Enemy(Runner.width - 100, 300, 50, 50, this);
			new_friendly = new Block(100, 400, 50, 50, true, false);
			initial_enemyblock1 = new Block(Runner.width - 100, 400, 50, 50, true, false);
			initial_onethirdblock = new Block(Runner.width / 3, 400, 50, 50, false, false);
			initial_twothirdsblock = new Block(Runner.width * 2 / 3, 400, 50, 50, false, false);
			/* spawnedde = 0; */
			atari = new GameManager();
			atari.addObject(backgrundi);
			atari.addObject(morrow);
			atari.addObject(sixer);

			// atari.addObject(new_friendly);
			atari.addObject(initial_enemyblock1);
			atari.addObject(initial_onethirdblock);
			atari.addObject(initial_twothirdsblock);
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

	public void makeMultipleTowers(int startingX, int endingX, int startingHeight, int width, int height,
			int numberOfTowers, boolean deadly, boolean toBot) {
		Block keystone = new Block(startingX, startingHeight, width, height, true, deadly);
		// atari.addObject(newgrounds);
		int numTowersMade = 0;
		for (double i = startingX /* start at the starting x */; endingX
				- i >= 0 /* as long as there is space, continue */; i += (endingX - startingX)
						/ (numberOfTowers - 1) /* increment by space/towers */) {
			// System.out.println("Increment by " + ((endingX - startingX) / (numberOfTowers
			// - 1)));
			if (numTowersMade < numberOfTowers /* if the number of towers quota is not filled, continue */) {
				keystone.x = (int) i;
				outsourceOneTowerBuild(keystone, true, deadly, toBot);
				numTowersMade++;
				System.out.println(
						"Num of towers to have total: " + numberOfTowers + " Number yet made: " + numTowersMade);

			}
		}

	}

	public void outsourceOneTowerBuild(Block referenceBlock, boolean makingMultiple, boolean deadly,
			boolean toBot /* (to bottom) */) {
		// must have existing block (added to atari) for reference!!!
		// Block permanitial = initialBlock;
		atari.addObject(referenceBlock);
		if (toBot) {
			for (int p = 0; Runner.height - referenceBlock.y
					+ referenceBlock.height >= 0 /* is there vertical space left */; p++) {
				Block newblock = new Block(referenceBlock.x, referenceBlock.y + referenceBlock.height,
						referenceBlock.width, referenceBlock.height, true, deadly);
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
						referenceBlock.width, referenceBlock.height, true, deadly);
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
		default:
			JOptionPane.showMessageDialog(null, "ERROR! LEVEL IS NOT GREATER THAN ONE AND LESS THAN SEVEN");
			break;
		}
	}

	private void startLevel6(Graphics oalr) {
		levelwasskippedto = adminSkipLevel ? true : false;
		adminSkipLevel = false;
		fallnow.restart();
		startTimeInMs = System.currentTimeMillis();
		atari.reset();
		backgrundi = new Backburner(0, 0, Runner.width, Runner.height);
		atari.addObject(backgrundi);
		ingameMessage = true;
		levelmessage = "Conglaturations! You have made it to Level " + whichLevelCommonKnowledge + "!!!";
		morrow = new PlayerOne(200, 600, 50, 50, true, this, atari);
		atari.addObject(morrow);
	}

	public void startLevel5(Graphics oalr) {
		levelwasskippedto = adminSkipLevel ? true : false;
		adminSkipLevel = false;
		fallnow.restart();
		startTimeInMs = System.currentTimeMillis();
		atari.reset();
		backgrundi = new Backburner(0, 0, Runner.width, Runner.height);
		atari.addObject(backgrundi);
		ingameMessage = true;
		levelmessage = "Conglaturations! You have made it to Level " + whichLevelCommonKnowledge + "!!!";
		morrow = new PlayerOne(200, 600, 50, 50, true, this, atari);
		atari.addObject(morrow);
		int dotz = (Runner.width / 9) + (Runner.height / 9);
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
				Block woah = new Block(wow, wowzer, 5, 5, true, ah);
				atari.addObject(woah);
			} else {
				i--;
			}

		}

	}

	private void startLevel4(Graphics oalr) {
		levelwasskippedto = adminSkipLevel ? true : false;
		adminSkipLevel = false;
		fallnow.restart();
		startTimeInMs = System.currentTimeMillis();
		atari.reset();
		backgrundi = new Backburner(0, 0, Runner.width, Runner.height);
		atari.addObject(backgrundi);
		ingameMessage = true;
		levelmessage = "Don't touch the don't-touchies.";
		morrow = new PlayerOne(200, 600, 50, 50, true, this, atari);
		atari.addObject(morrow);
		makeMultipleTowers(Runner.width / 10, Runner.width - 100, 375, 50, 50, 8, true, false);
		makeMultipleTowers(Runner.width / 10, Runner.width - 100, Runner.height - 375, 50, 50, 8, true, true);
	}

	public void startLevel3(Graphics oalr) {
		levelwasskippedto = adminSkipLevel ? true : false;
		adminSkipLevel = false;
		fallnow.restart();
		startTimeInMs = System.currentTimeMillis();
		atari.reset();
		backgrundi = new Backburner(0, 0, Runner.width, Runner.height);
		atari.addObject(backgrundi);
		ingameMessage = true;
		levelmessage = "Now the bullets get more creative...and scary. Use your knowledge to survive and advance.";
		morrow = new PlayerOne(200, 600, 50, 50, true, this, atari);
		atari.addObject(morrow);
		makeMultipleTowers(Runner.width / 3, (Runner.width / 3) + 150, 300, 50, 50, 2, false, true);
		makeMultipleTowers(2 * Runner.width / 3, (2 * (Runner.width / 3)) + 150, 300, 50, 50, 2, false, true);
	}

	public void startLevel2(Graphics oalr) {
		levelwasskippedto = adminSkipLevel ? true : false;
		adminSkipLevel = false;
		fallnow.restart();
		startTimeInMs = System.currentTimeMillis();
		atari.reset();
		backgrundi = new Backburner(0, 0, Runner.width, Runner.height);
		atari.addObject(backgrundi);
		ingameMessage = true;
		levelmessage = "In this level, you can discover how bullets and blocks interact, as well as how your avatar can interact with blocks.";
		morrow = new PlayerOne(200, 600, 50, 50, true, this, atari);
		Enemy jacobs = new Enemy(Runner.width / 3, (Runner.height / 8) - 50, 50, 50, this);
		atari.addObject(jacobs);
		jacobs.enemyonBlock = false;
		atari.addObject(morrow);
		makeMultipleTowers(Runner.width / 3, 2 * Runner.width / 3, Runner.height / 8, 50, 50, 3, false, true);
		masterclock.restart();
	}

	public void paintComponent(Graphics oalr) {
		if (drawandStartNextLevel) {
			drawandStartNextLevel = false;
			startNextLevel(level, oalr);
		}
		if (currentState == GAME_STATE) {
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

				fallnow.stop();
				fallnowcount = 0;
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
			if (morrow != null) {
				fallnowcount++;
				if (fallnowcount < 8) {
					morrow.yspeedAdder -= 1;
				} else if ((fallnowcount > 7) && (fallnowcount < 15)) {

					morrow.yspeedAdder += 1;

				} else {

					fallnow.stop();
					fallnowcount = 0;
				}
			}
		}

		if (currentState == GAME_STATE) {
			updateGameState();
		} else if (currentState == END_STATE) {
			updateEndState();
		} else if (currentState == VICTORY_STATE) {
			updateVictoryState();
		}

	}
}