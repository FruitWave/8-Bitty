package archon;

import java.awt.Color;
import java.awt.Graphics;

public class PlayerOne extends GameObject {
	int playeryspeedAdder;
	int playerxspeedAdder;
	int yspeed;
	int xspeed;
	boolean jumping;
	GamePanel panelope;

	public PlayerOne(int x, int y, int width, int height, GamePanel panelope) {
		super(x, y, width, height);
		yspeed = 0;
		xspeed = 0;
		playeryspeedAdder = 0;
		playerxspeedAdder = 0;
		forcesEnact = true;
		gravitEnact = true;
		jumping = false;
		this.panelope = panelope;
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

		if ((y + height) > Runner.height) {
			System.out.println("Correcting player from " + y);
			y = Runner.height - height;
			System.out.println("Corrected to " + y);
			System.out.println("Height is " + height);

		}
		yspeed = playeryspeedAdder;
		xspeed = playerxspeedAdder;
		yspeed += gravispeed;
		y += yspeed;
		x += xspeed;
		if (jumping = true) {
			y += 2;
			x += 2;
			panelope.jumpPluses -= 2;
		}
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.cyan);
		g.fillRect(x, y, width, height);
	}
}
