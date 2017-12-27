package archon;

import java.awt.Color;
import java.awt.Graphics;

public class PlayerOne extends GameObject {
	int playeryspeedAdder;
	int playerxspeedAdder;
	int yspeed;
	int xspeed;

	public PlayerOne(int x, int y, int width, int height) {
		super(x, y, width, height);
		yspeed = 0;
		xspeed = 0;
		playeryspeedAdder = 0;
		playerxspeedAdder = 0;
		forcesEnact = true;
		gravitEnact = true;
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
		yspeed = playeryspeedAdder /* I haven't added any forces yet */;
		xspeed = playerxspeedAdder /* I haven't added any horizontal forces yet */;
		if (forcesEnact) {
			if (gravitEnact) {
				yspeed += gravispeed;
			}
		}
		y += yspeed;
		x += xspeed;
		if ((y + height) > Runner.height) {
			System.out.println("Correcting player from " + y);
			y = Runner.height - height;
			System.out.println("Corrected to " + y);
			System.out.println("Height is " + height);
			gravitEnact = false;
		}
		if ((y + height) == Runner.height) {
			gravitEnact = false;
		} else {
			gravitEnact = true;
		}

	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.cyan);
		g.fillRect(x, y, width, height);
	}
}
