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
	Block initial_friendly;
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
	Block lastblocktobottom;
	Font font1;
	double startTimeInMs;
	public static int level = 1;
	// Timer enemspawne;
	// int spawnedde;

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
		if (e.getKeyCode() == KeyEvent.VK_V) {
			currentState = VICTORY_STATE;
		}
		if (e.getKeyCode() == KeyEvent.VK_F) {
			startLevel2();
		}
		if (e.getKeyCode() == KeyEvent.VK_R) {
			respawnTimer.restart();
			morrow.timesdied = 0;
			morrow.lives = 4;
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

	@Override
	public void keyReleased(KeyEvent e) {
		morrow.xspeedAdder = 0;
		playerupbutton = false;
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
				startNextLevel(level);
			}
		}
	}

	void updateEndState() {
	}

	void drawGameState(Graphics elevi) {
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

			startTimeInMs = System.currentTimeMillis();
			font1 = new Font("Arial", 0, 24);
			morrow = new PlayerOne(100, 300, 50, 50, true, this, atari);
			sixer = new Enemy(Runner.width - 100, 300, 50, 50, this);
			// sux0r = new Enemy(Runner.width * 2 / 3, 350, 50, 50, this);
			initial_friendly = new Block(100, 400, 50, 50, false, true);
			initial_enemyblock1 = new Block(Runner.width - 100, 400, 50, 50, false, true);
			initial_onethirdblock = new Block(Runner.width / 3, 400, 50, 50, false, false);
			initial_twothirdsblock = new Block(Runner.width * 2 / 3, 400, 50, 50, false, false);
			lastblocktobottom = initial_friendly;
			/* spawnedde = 0; */
			atari = new GameManager();
			atari.addObject(morrow);
			atari.addObject(sixer);
			// atari.addObject(sux0r);
			atari.addObject(initial_friendly);
			atari.addObject(initial_enemyblock1);
			atari.addObject(initial_onethirdblock);
			atari.addObject(initial_twothirdsblock);
			int blocknum = 0;
			for (; Runner.height - lastblocktobottom.y + lastblocktobottom.height >= 0;) {
				Block newftblock = new Block(lastblocktobottom.x, lastblocktobottom.y + lastblocktobottom.height, 50,
						50, false, true);

				blocknum++;
				lastblocktobottom = newftblock;

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

	public void startNextLevel(int whatlevel) {
		atari.reset();
		atari = new GameManager();
		font1 = new Font("Arial", 0, 24);
		morrow = new PlayerOne(100, 10, 50, 50, true, this, atari);
		atari.addObject(morrow);
		JOptionPane.showMessageDialog(null, "Conglaturations! You have made it to Level " + whatlevel + "!!!");
		JOptionPane.showMessageDialog(null, "READY PLAYER ONE");
		switch (whatlevel) {
		case 2:
			startLevel2();
			break;
		case 3:
			startLevel3();
			break;
		case 4:
			startLevel4();
			break;
		case 5:
			startLevel5();
			break;
		case 6:
			startLevel6();
			break;
		default:
			JOptionPane.showMessageDialog(null, "ERROR! LEVEL IS NOT GREATER THAN ONE AND LESS THAN SEVEN");
			break;
		}
	}

	private void startLevel6() {
		// TODO Auto-generated method stub

	}

	private void startLevel5() {
		// TODO Auto-generated method stub

	}

	private void startLevel4() {
		// TODO Auto-generated method stub

	}

	private void startLevel3() {
		// TODO Auto-generated method stub

	}

	public void startLevel2() {
		startTimeInMs = System.currentTimeMillis();
		font1 = new Font("Arial", 0, 24);
		initial_friendly = new Block(100, 400, 50, 50, false, true);
		atari.addObject(initial_friendly);
		lastblocktobottom = initial_friendly;
		for (; Runner.height - lastblocktobottom.y + lastblocktobottom.height >= 0;) {
			Block newftblock = new Block(lastblocktobottom.x, lastblocktobottom.y + lastblocktobottom.height, 50, 50,
					false, true);
			lastblocktobottom = newftblock;
			atari.addObject(newftblock);
		}
		/* enemspawne.start(); */

		masterclock.restart();
	}

	public void paintComponent(Graphics g) {
		if (currentState == LEVEL1_STATE) {
			drawGameState(g);
		} else if (currentState == END_STATE) {
			drawEndState(g);
		} else if (currentState == VICTORY_STATE) {
			drawVictoryState(g);
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
			if (morrow.lives > 1) {
				respawnTimer.stop();
				currentState = LEVEL1_STATE;
				atari.reset();
				morrow.timesdied++;
				morrow.lives--;
				startGame();

				masterclock.restart();
			} else {

				currentState = END_STATE;
			}
		}
		if (e.getSource() == fallnow) {
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

		if (currentState == LEVEL1_STATE) {
			updateGameState();
		} else if (currentState == END_STATE) {
			updateEndState();
		} else if (currentState == VICTORY_STATE) {
			updateVictoryState();
		}

	}
}