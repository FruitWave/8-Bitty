package archon;

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
	PlayerOne morrow;
	Enemy sixer;

	public GamePanel() {
		masterclock = new Timer(1000 / 120, this);
	}

	public void updateGameState() {
		if (atari != null) {
			atari.update();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	public void startGame() {
		masterclock.start();
		System.out.println("Started!");
	}

}
