package archon;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JOptionPane;
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
	static boolean upbutton;
	static Timer respawnTimer;
	static int currentState = 1;
	final static int LEVEL1_STATE = 1;
	final static int LEVEL2_STATE = 2;
	final static int END_STATE = 3;

	public GamePanel() {
		masterclock = new Timer(1000 / 120, this);
		fallnow = new Timer(10, this);
		respawnTimer = new Timer(5000, this);
		respawnTimer.setInitialDelay(1);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_UP) {
			upbutton = true;
			morrow.playeryspeedAdder--;
			fallnow.restart();
			System.out.println("Up");
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			morrow.playerxspeedAdder -= 3;

		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

			morrow.playerxspeedAdder += 3;

		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (morrow != null) {
			morrow.playerxspeedAdder = 0;
			// morrow.playeryspeedAdder = 0;
		}
	}

	void updateGameState() {
		if (atari != null) {
			if (morrow.y < 0) {
				morrow.playeryspeedAdder = -morrow.playeryspeedAdder;
			}
			atari.update();
			atari.checkCollision();
			atari.manageEnemies();
			// if (morrow.isAlive == false) {
			// currentState = END_STATE;
			// score = atari.getScore();
			// atari.reset();
			// tardis = new TARDIS(250, 700, 25, 25);
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
		elevi.drawString("" + morrow.yspeed, 100, 25);
		elevi.drawString("" + morrow.xspeed, 200, 25);
	}

	void drawEndState(Graphics elevi) {
		elevi.setColor(Color.BLUE);
		elevi.fillRect(0, 0, Runner.width, Runner.height);
		elevi.setColor(Color.CYAN);
		elevi.drawString("You Have Died. Respawning...", Runner.width / 3, Runner.height / 2);

	}

	public void startGame() {
		morrow = new PlayerOne(100, 300, 25, 25, true, this, atari);
		sixer = new Enemy(Runner.width - 100, 300, 25, 25);
		initial_friendly = new Block(100, 400, 25, 25, false, true);
		initial_enemy = new Block(Runner.width - 100, 400, 25, 25, false, true);
		// for (int i = 0; i < array.length; i++) {
		//
		// }
		atari = new GameManager();
		atari.addObject(morrow);
		atari.addObject(sixer);
		atari.addObject(initial_friendly);
		atari.addObject(initial_enemy);
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
		if (e.getSource() == respawnTimer) {

			respawnTimer.stop();
			currentState = LEVEL1_STATE;
			atari.reset();
			morrow = new PlayerOne(100, 300, 25, 25, true, this, atari);
			sixer = new Enemy(Runner.width - 100, 300, 25, 25);
			initial_friendly = new Block(100, 400, 25, 25, false, true);
			initial_enemy = new Block(Runner.width - 100, 400, 25, 25, false, true);
			morrow.gravispeed = 1;
			morrow.isAlive = true;
			atari = new GameManager();
			atari.addObject(morrow);
			atari.addObject(sixer);
			atari.addObject(initial_friendly);
			atari.addObject(initial_enemy);
			masterclock.restart();
		}
		if (e.getSource() == fallnow) {
			fallnowcount++;

			if (fallnowcount < 5) {
				System.out.println("less than six");
				morrow.playeryspeedAdder -= 1;
			} /*
				 * else if (fallnowcount == 5) { morrow.playeryspeedAdder = 0;
				 * System.out.println("six"); }
				 */ else if ((fallnowcount > 4) && (fallnowcount < 10)) {

				morrow.playeryspeedAdder += 1;
				System.out.println("falling");
			} else {
				System.out.println("stopped");
				fallnow.stop();
				fallnowcount = 0;
			}

		}
		repaint();
		if (currentState == LEVEL1_STATE) {
			updateGameState();
		} else if (currentState == END_STATE) {
			updateEndState();
		}

	}
}
