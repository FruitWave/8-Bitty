package archon;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;

public class PlayerOne extends GameObject implements ActionListener {
	int playeryspeedAdder;
	int playerxspeedAdder;
	int yspeed;
	int xspeed;
	GamePanel panelope;
	boolean mani_to_player1_there_is_no_collision = true;
	static Timer turngravbackon;
	static int jumpCount = 0;

	public PlayerOne(int x, int y, int width, int height, boolean gravAffectedMaster, GamePanel panelope,
			GameManager master) {
		super(x, y, width, height);
		yspeed = 0;
		xspeed = 0;
		playeryspeedAdder = 0;
		playerxspeedAdder = 0;
		this.panelope = panelope;
		turngravbackon = new Timer(10000, this);
		turngravbackon.setInitialDelay(250);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see archon.GameObject#update()
	 * 
	 * the code: if ((y + height) == Runner.height) { gravitEnact = false; } else {
	 * gravitEnact = true; } needs to be modified to work for ALL scenarios. when
	 * creating one of those scenarios, MODIFY THIS CODE!!!!
	 */
	@Override
	public void update() {
		super.update();
		if (y > Runner.height) {
			isAlive = false;
		}
		if (mani_to_player1_there_is_no_collision) {
			gravispeed = 1;
		} else if (!mani_to_player1_there_is_no_collision) {
			gravispeed = 0;
			// JOptionPane.showMessageDialog(null, "GRAVI DISABLED");
		}

		yspeed = playeryspeedAdder;
		xspeed = playerxspeedAdder;
		// if (gravitEnact) {
		yspeed += gravispeed;
		// }

		y += yspeed;
		x += xspeed;

		/*
		 * if (jumping == true) { y -= 2; yspeed -= 2; x += 2; panelope.jumpPluses -= 2;
		 * }
		 */
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.cyan);
		g.fillRect(x, y, width, height);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		mani_to_player1_there_is_no_collision = true;
	}
}
