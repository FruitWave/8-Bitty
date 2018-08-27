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
	int imagewidth;
	int imageheight;
	int yspeed;
	int xspeed;
	Panel panelope;
	public boolean playeronBlock;
	static Timer turngravbackon;
	static int jumpCount = 0;
	static int rotateAmount = 0;
	static int timesdied = 0;
	static int lives = 3;
	boolean donotfall = false;
	final int eternalGravSpeed = gravispeed;

	public PlayerOne(int x, int y, int width, int height, boolean gravAffectedMaster, Panel panelope,
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

	@Override
	public void update() {
		super.update();

		yspeed = yspeedAdder;
		xspeed = xspeedAdder;
		if ((!donotfall) && (!Panel.inJumpingProcess)) {
			yspeed += eternalGravSpeed;
		}
		if ((!Panel.inJumpingProcess) && (yspeedAdder < 0)) {
			Panel.fallnowcount = 0;
			Panel.fallnow.stop();
			yspeedAdder = 0;
		}
		y += yspeed;
		x += xspeed;
		if (y > Runner.height) {
			isAlive = false;
		}
	}

	@Override
	public void draw(Graphics garbazon) {
		if ((xspeed > 0) || (xspeed == 0)) {
			// imagewidth = GamePanel.morrowright.getWidth();
			// imageheight = GamePanel.morrowright.getHeight();
			garbazon.drawImage(Panel.morrowright, x, y, width, height, null);
		} else {
			// imagewidth = GamePanel.morrowleft.getWidth();
			// imageheight = GamePanel.morrowleft.getHeight();
			garbazon.drawImage(Panel.morrowleft, x, y, width, height, null);
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}
}
