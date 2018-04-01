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
	Block initiallastblocktobottom;
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
			if (e.getKeyCode() == KeyEvent.VK_V) {
				currentState = VICTORY_STATE;
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
				// if (morrow.isAlive == false) {
				// currentState = END_STATE;
				// score = atari.getScore();
				// atari.reset();
				// tardis = new TARDIS(250, 700, 100, 100);
				// atari.addObject(tardis);
				// }
				if (morrow.isAlive == false) {
					respawnTimer.start();
				}

				if ((morrow.x + morrow.width >= Runner.width) && endNotTouched) {
					endNotTouched = false;
					level += 1;
					drawandStartNextLevel = true;
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
			elevi.drawString("Time: " + timeelsapsed, 400, 25);
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
		if (level == 1) {
			backgrundi = new Backburner(0, 0, Runner.width, Runner.height);
			startTimeInMs = System.currentTimeMillis();
			font1 = new Font("Arial", 0, 24);
			morrow = new PlayerOne(100, 300, 50, 50, true, this, atari);
			if (fullRestarting) {
				morrow.lives = 3;
			}

			sixer = new Enemy(Runner.width - 100, 300, 50, 50, this);
			new_friendly = new Block(100, 400, 50, 50, false, true);
			initial_enemyblock1 = new Block(Runner.width - 100, 400, 50, 50, false, true);
			initial_onethirdblock = new Block(Runner.width / 3, 400, 50, 50, false, false);
			initial_twothirdsblock = new Block(Runner.width * 2 / 3, 400, 50, 50, false, false);
			initiallastblocktobottom = new_friendly;
			/* spawnedde = 0; */
			atari = new GameManager();
			atari.addObject(backgrundi);
			atari.addObject(morrow);
			atari.addObject(sixer);

			atari.addObject(new_friendly);
			atari.addObject(initial_enemyblock1);
			atari.addObject(initial_onethirdblock);
			atari.addObject(initial_twothirdsblock);
			int blocknum = 0;

			for (; Runner.height - initiallastblocktobottom.y + initiallastblocktobottom.height >= 0;) {
				Block newftblock = new Block(initiallastblocktobottom.x,
						initiallastblocktobottom.y + initiallastblocktobottom.height, 50, 50, false, true);

				blocknum++;
				initiallastblocktobottom = newftblock;

				atari.addObject(newftblock); // System.out.println("New Block #" + blocknum + " made.");
			}

			// makeTowers(false, new_friendly.x, new_friendly.x, new_friendly.y, 1);
			// enemspawne.start();

			masterclock.start();
			System.out.println("Started!");
			ingameMessage = true;
			levelmessage = "In this level, you'll find out what to avoid, and how the game's basic physics work.";
		} else {
			JOptionPane.showMessageDialog(null, "LEVEL IS NOT ONE");
		}
	}

	public void makeTowers(boolean makeMultiple, int startingX, int endingX, int startingHeight, int numberOfTowers) {
		/*
		 * FOR SINGLE TOWERS, SET makeMultiple TO false, SET startingX TO [desired x
		 * value], SET endingX TO [anything greater than or equal to startingX], SET
		 * startingHeight TO [desired top y value], AND SET numberOfTowers TO 1
		 */
		if (!makeMultiple) {
			endingX = 0;
			numberOfTowers = 0;
		}
		Block keystone;
		Block newgrounds = new Block(startingX, startingHeight, 25, 25, false, true);
		atari.addObject(newgrounds);
		keystone = newgrounds;
		makeTowerBlockNum = 0;
		int numTowersMade = 0;
		if (makeMultiple) {
			for (int i = startingX /* start at the starting x */; endingX
					- i >= 0 /* as long as there is space, continue */; i += (endingX - startingX)
							/ numberOfTowers /* increment by space/towers */) {
				if (numTowersMade < numberOfTowers /* if the number of towers quota is not filled, continue */) {
					keystone.x = i;
					outsourceOneTowerBuild(keystone, true);
					numTowersMade++;
					System.out.println(
							"Num of towers to have total: " + numberOfTowers + " Number yet made: " + numTowersMade);

					if ((level == 2) || (level == 3)) {
						Enemy jacobs = new Enemy(keystone.x, startingHeight - 50, 50, 50, this);
						atari.addObject(jacobs);
						// there will be one less enemies than the num of towers (one tower has already
						// been completed)
					}
				}

			}
		} else {
			outsourceOneTowerBuild(keystone, false);
		}
		// System.out.println("New tower has been started.");

	}

	private void outsourceOneTowerBuild(Block initialBlock, boolean makingMultiple) {
		for (int j = 0; Runner.height - initialBlock.y
				+ initialBlock.height >= 0 /* is there vertical space left */; j++) {

			Block newblock = new Block(initialBlock.x, initialBlock.y + initialBlock.height, initialBlock.width,
					initialBlock.height, false, true);
			// new block just below reference point
			// System.out.println(newblock.x + " (x)," + newblock.y + " (y)");
			makeTowerBlockNum++;
			initialBlock = newblock;
			// set new block to the reference point
			atari.addObject(newblock);
			// add newly set new reference point
			if (!makingMultiple) {
				System.out.println("New Block #" + makeTowerBlockNum + " made.");
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
		atari.reset();
		masterclock.restart();
		fallnow.restart();
		atari = new GameManager();
		whichLevelCommonKnowledge = whichlevel;
		System.out.println("Level is " + whichLevelCommonKnowledge);
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
		ingameMessage = true;
		levelmessage = "Conglaturations! You have made it to Level " + whichLevelCommonKnowledge + "!!!";
	}

	private void startLevel5(Graphics oalr) {
		ingameMessage = true;
		levelmessage = "Conglaturations! You have made it to Level " + whichLevelCommonKnowledge + "!!!";
	}

	private void startLevel4(Graphics oalr) {
		ingameMessage = true;
		levelmessage = "Conglaturations! You have made it to Level " + whichLevelCommonKnowledge + "!!!";
	}

	public void startLevel3(Graphics oalr) {
		fallnow.restart();
		startTimeInMs = System.currentTimeMillis();
		atari.reset();
		backgrundi = new Backburner(0, 0, Runner.width, Runner.height);
		atari.addObject(backgrundi);
		ingameMessage = true;
		levelmessage = "Now the bullets get more creative...and scary. Use your knowledge to survive and advance.";
		morrow = new PlayerOne(200, 600, 50, 50, true, this, atari);
		atari.addObject(morrow);
		makeTowers(true, Runner.width / 3, (Runner.width / 3) + 150, 300, 2);
		makeTowers(true, 2 * Runner.width / 3, (2 * (Runner.width / 3)) + 150, 300, 2);
	}

	public void startLevel2(Graphics oalr) {
		fallnow.restart();
		startTimeInMs = System.currentTimeMillis();
		atari.reset();
		backgrundi = new Backburner(0, 0, Runner.width, Runner.height);
		atari.addObject(backgrundi);
		ingameMessage = true;
		levelmessage = "In this level, you can discover how bullets and blocks interact, as well as how your avatar can interact with blocks.";
		morrow = new PlayerOne(200, 600, 50, 50, true, this, atari);
		atari.addObject(morrow);
		makeTowers(true, Runner.width / 3, 2 * (Runner.width / 3), Runner.height / 8, 3);
		makeTowers(false, Runner.width / 6, 0, 6 * Runner.height / 8, 0);
		makeTowers(false, 5 * Runner.width / 6, 0, 6 * Runner.height / 8, 0);
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
				initiallastblocktobottom = null;
				level = 1;
				// Timer enemspawne;
				// int spawnedde;
				drawandStartNextLevel = false;
				backgrundi = null;
				ingameMessage = false;
				levelmessage = null;
				endNotTouched = true;
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