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

	int yspeed;
	int xspeed;
	GamePanel panelope;
	boolean mani_to_player1_there_is_no_collision = true;
	public boolean playeronBlock;
	static Timer turngravbackon;
	static int jumpCount = 0;
	static int rotateAmount = 0;
	static int timesdied = 0;
	static int lives = 3;

	final int eternalGravSpeed = gravispeed;

	public PlayerOne(int x, int y, int width, int height, boolean gravAffectedMaster, GamePanel panelope,
			GameManager master) {
		super(x, y, width, height);
		yspeed = 0;
		xspeed = 0;
		yspeedAdder = 0;
		xspeedAdder = 0;
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
		/*
		 * if (rotateAmount < 360) { rotateAmount++; } else { boolean cancontinue =
		 * true; for (int i = rotateAmount; i < 360; i -= 360) { if (rotateAmount - 360
		 * > 0) { cancontinue = true; } else { cancontinue = false; } rotateAmount = i;
		 * }
		 * 
		 * }
		 */
		super.update();

		yspeed = yspeedAdder;
		xspeed = xspeedAdder;
		// if (gravitEnact) {
		yspeed += gravispeed;
		// }

		y += yspeed;
		x += xspeed;

		/*
		 * if (jumping == true) { y -= 2; yspeed -= 2; x += 2; panelope.jumpPluses -= 2;
		 * }
		 */
		if (y > Runner.height) {
			isAlive = false;
		}
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
