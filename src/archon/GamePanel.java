package archon;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements ActionListener, KeyListener {
	Timer masterclock;
	GameManager atari;
	static PlayerOne morrow;
	Enemy sixer;
	Block initial_friendly;
	Block initial_enemy;
	static Timer fallnow;
	static int fallnowcount = 0;
	static boolean playerupbutton;
	static Timer respawnTimer;
	static int currentState = 1;
	final static int LEVEL1_STATE = 1;
	final static int LEVEL2_STATE = 2;
	final static int END_STATE = 3;
	Block lastblocktobottom;
	Font font1;

	public GamePanel() {
		masterclock = new Timer(1000 / 120, this);
		fallnow = new Timer(10, this);
		respawnTimer = new Timer(5000, this);
		respawnTimer.setInitialDelay(1);
		font1 = new Font("Arial", 0, 24);
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_R) {
			respawnTimer.restart();
			morrow.timesdied = 0;
			morrow.lives = 4;
		}
		if (/* (morrow.playeronBlock) && */ e.getKeyCode() == KeyEvent.VK_UP) {
			playerupbutton = true;
			fallnow.restart();
			System.out.println("Up");
		}

		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			morrow.xspeedAdder -= 3;
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			morrow.xspeedAdder += 3;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		morrow.xspeedAdder = 0;
		playerupbutton = false;
	}

	void updateGameState() {

		if (atari != null) {
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
		font1 = new Font("Arial", 0, 24);
		morrow = new PlayerOne(100, 300, 50, 50, true, this, atari);
		sixer = new Enemy(Runner.width - 100, 300, 50, 50);
		initial_friendly = new Block(100, 400, 50, 50, false, true);
		initial_enemy = new Block(Runner.width - 100, 400, 50, 50, false, true);
		lastblocktobottom = initial_friendly;

		atari = new GameManager();
		atari.addObject(morrow);
		atari.addObject(sixer);
		atari.addObject(initial_friendly);
		atari.addObject(initial_enemy);
		int blocknum = 0;
		for (; Runner.height - lastblocktobottom.y + lastblocktobottom.height >= 0;) {
			Block newftblock = new Block(lastblocktobottom.x, lastblocktobottom.y + lastblocktobottom.height, 50, 50,
					false, true);
			blocknum++;
			lastblocktobottom = newftblock;
			atari.addObject(newftblock);
			// System.out.println("New Block #" + blocknum + " made.");
		}
		masterclock.start();
		System.out.println("Started!");
	}

	public void paintComponent(Graphics g) {
		if (currentState == LEVEL1_STATE) {
			drawGameState(g);
		} else if (currentState == END_STATE) {
			drawEndState(g);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
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
				System.out.println("AHA");
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
			System.out.println("end");
			updateEndState();
		}

	}
}