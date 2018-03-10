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
	final static int LEVEL1_STATE = 1;
	final static int LEVEL2_STATE = 2;
	final static int VICTORY_STATE = 4;
	final static int END_STATE = 3;
	Block initiallastblocktobottom;
	Font font1;
	double startTimeInMs;
	public static int level = 1;
	// Timer enemspawne;
	// int spawnedde;
	boolean drawandStartNexLevel = false;
	Backburner backgrundi;
	static int whichLevelCommonKnowledge;

	public GamePanel() {
		masterclock = new Timer(1000 / 120, this);
		fallnow = new Timer(10, this);
		respawnTimer = new Timer(5000, this);
		respawnTimer.setInitialDelay(1);
		font1 = new Font("Arial", 0, 24);
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
				morrow.x = Runner.width - 10;
			}
			if (e.getKeyCode() == KeyEvent.VK_R) {

				respawnTimer.restart();
				morrow.timesdied = 0;
				morrow.lives = 3;

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
				if (morrow.x + morrow.width >= Runner.width) {
					level += 1;
					drawandStartNexLevel = true;

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

	public void startGame() {
		if (level == 1) {
			backgrundi = new Backburner(0, 0, Runner.width, Runner.height);
			startTimeInMs = System.currentTimeMillis();
			font1 = new Font("Arial", 0, 24);
			morrow = new PlayerOne(100, 300, 50, 50, true, this, atari);
			sixer = new Enemy(Runner.width - 100, 300, 50, 50, this);
			// sux0r = new Enemy(Runner.width * 2 / 3, 350, 50, 50, this);
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
			// atari.addObject(sux0r);
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

				atari.addObject(newftblock);
				// System.out.println("New Block #" + blocknum + " made.");
			}
			// enemspawne.start();

			masterclock.start();
			System.out.println("Started!");
		} else {
			JOptionPane.showMessageDialog(null, "LEVEL IS NOT ONE");
		}
	}

	public void makeTowers(int startingX, int startingHeight, int numberOfTowers) {
		Block afterstartlastblocktobottom;
		Block newgrounds = new Block(100, Runner.height - startingHeight, 50, 50, false, true);
		atari.addObject(newgrounds);
		afterstartlastblocktobottom = newgrounds;
		int bnum = 0;
		for (int i = Runner.width / numberOfTowers; Runner.width - i >= 0; i += Runner.width / numberOfTowers) {
			if (Runner.height - afterstartlastblocktobottom.y
					+ afterstartlastblocktobottom.height <= afterstartlastblocktobottom.height) {
				afterstartlastblocktobottom = new Block(i, Runner.height - startingHeight, 50, 50, false, true);
			}
			for (int j = 0; Runner.height - afterstartlastblocktobottom.y
					+ afterstartlastblocktobottom.height >= 0; j++) {

				Block newblock = new Block(afterstartlastblocktobottom.x,
						afterstartlastblocktobottom.y + afterstartlastblocktobottom.height, 50, 50, false, true);
				System.out.println(newblock.x + " (x)," + newblock.y + " (y)");
				bnum++;
				afterstartlastblocktobottom = newblock;
				atari.addObject(newblock);

				System.out.println("New Block #" + bnum + " made.");
			}

			System.out.println("New tower has been started.");
		}
	}

	public void startNextLevel(int whichlevel, Graphics oalr) {
		atari.reset();
		masterclock.restart();
		fallnow.restart();
		atari = new GameManager();
		whichLevelCommonKnowledge = whichlevel;
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

	private void ingamemessage(String stringgeroo, Graphics oalr) {
		oalr.drawString(stringgeroo, Runner.width / 2, 100);
		System.out.println("ingamemessage called");
	}

	private void startLevel6(Graphics oalr) {
		font1 = new Font("Arial", 0, 24);
		ingamemessage("Conglaturations! You have made it to Level " + whichLevelCommonKnowledge + "!!!", oalr);
	}

	private void startLevel5(Graphics oalr) {
		font1 = new Font("Arial", 0, 24);
		ingamemessage("Conglaturations! You have made it to Level " + whichLevelCommonKnowledge + "!!!", oalr);
	}

	private void startLevel4(Graphics oalr) {
		font1 = new Font("Arial", 0, 24);
		ingamemessage("Conglaturations! You have made it to Level " + whichLevelCommonKnowledge + "!!!", oalr);
	}

	private void startLevel3(Graphics oalr) {
		font1 = new Font("Arial", 0, 24);
		ingamemessage("Conglaturations! You have made it to Level " + whichLevelCommonKnowledge + "!!!", oalr);
	}

	public void startLevel2(Graphics oalr) {
		fallnow.restart();
		startTimeInMs = System.currentTimeMillis();
		atari.reset();
		backgrundi = new Backburner(0, 0, Runner.width, Runner.height);
		font1 = new Font("Arial", 0, 24);
		ingamemessage("Conglaturations! You have made it to Level " + whichLevelCommonKnowledge + "!!!", oalr);
		atari.addObject(backgrundi);
		morrow = new PlayerOne(100, 200, 50, 50, true, this, atari);
		atari.addObject(morrow);
		makeTowers(0, 400, 10);
		// }

		/* enemspawne.start(); */

		masterclock.restart();
	}

	public void paintComponent(Graphics oalr) {
		if (drawandStartNexLevel) {
			drawandStartNexLevel = false;
			startNextLevel(level, oalr);
		}
		if (currentState == LEVEL1_STATE) {
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
				if (morrow.lives >= 1) {
					respawnTimer.stop();
					currentState = LEVEL1_STATE;
					atari.reset();
					morrow.timesdied++;
					morrow.lives--;
					if (morrow.lives == 0) {
						morrow.isAlive = false;
					}
					if (level > 1) {
						drawandStartNexLevel = true;
					} else {
						startGame();
					}

				} else {

					currentState = END_STATE;
				}
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

		if (currentState == LEVEL1_STATE) {
			updateGameState();
		} else if (currentState == END_STATE) {
			updateEndState();
		} else if (currentState == VICTORY_STATE) {
			updateVictoryState();
		}

	}
}