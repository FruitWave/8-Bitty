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
	public boolean playeronBlock;
	static Timer turngravbackon;
	static int jumpCount = 0;
	static int rotateAmount = 0;
	static int timesdied = 0;
	static int lives = 3;
	boolean donotfall = false;
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

	@Override
	public void update() {
		super.update();

		yspeed = yspeedAdder;
		xspeed = xspeedAdder;
		if ((!donotfall) && (!GamePanel.inJumpingProcess)) {
			yspeed += eternalGravSpeed;
		}
		if ((!GamePanel.inJumpingProcess) && (yspeed < 0)) {
			GamePanel.fallnowcount = 0;
			GamePanel.fallnow.restart();
		}
		y += yspeed;
		x += xspeed;
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
	}
}
