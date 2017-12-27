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
	PlayerOne morrow;
	Enemy sixer;
	Block initial_friendly;
	Block initial_enemy;
	int jumpPluses = 200;

	public GamePanel() {
		masterclock = new Timer(1000 / 120, this);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_UP) {
			morrow.jumping = true;

			// if ((morrow.y + morrow.height) == Runner.height) {
			// morrow.y -= 30;
			// }

		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (morrow.playeryspeedAdder < 6) {
				morrow.playeryspeedAdder++;
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (morrow.playeryspeedAdder > -6) {
				morrow.playerxspeedAdder--;
			}
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (morrow.playeryspeedAdder < 6) {
				morrow.playerxspeedAdder++;
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_B) {
			morrow.yspeed = 0;
			morrow.xspeed = 0;
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
			atari.update();
			atari.checkCollision();
			atari.manageEnemies();
			// if (morrow.isAlive == false) {
			// currentState = END_STATE;
			// score = atari.getScore();
			// atari.reset();
			// tardis = new TARDIS(250, 700, 50, 50);
			// atari.addObject(tardis);
			// }
			if (morrow.isAlive == false) {
				for (int i = 0; i > -1; i++) {
					JOptionPane.showMessageDialog(null, "No lives remaining.");
				}
			}
		}
	}

	void drawGameState(Graphics elevi) {
		elevi.setColor(Color.BLACK);
		elevi.fillRect(0, 0, Runner.width, Runner.height);
		if (atari != null) {
			atari.draw(elevi);
		}
	}

	public void startGame() {
		morrow = new PlayerOne(100, 300, 50, 50, this);
		sixer = new Enemy(Runner.width - 100, 300, 50, 50);
		initial_friendly = new Block(Runner.width / 2, 100, 50, 50);
		initial_enemy = new Block(Runner.width / 2, 500, 50, 50);
		atari = new GameManager();
		atari.addObject(morrow);
		atari.addObject(sixer);
		atari.addObject(initial_friendly);
		atari.addObject(initial_enemy);
		masterclock.start();
		System.out.println("Started!");
	}

	public void paintComponent(Graphics g) {
		drawGameState(g);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
		updateGameState();
	}
}
