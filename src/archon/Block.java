package archon;

import java.awt.Color;
import java.awt.Graphics;

public class Block extends GameObject {

	public Block(int x, int y, int width, int height) {
		super(x, y, width, height);
		forcesEnact = true;
		gravitEnact = true;
	}

	@Override
	public void update() {
		super.update();
		if (forcesEnact) {
			if (gravitEnact) {
				y += gravispeed;
			}
		}

		if ((y + height) > Runner.height) {
			System.out.println("Correcting Block from " + height);
			y = Runner.height - height;
			System.out.println("Corrected to " + y);
			System.out.println("Height is " + height);
			gravitEnact = false;
			/*
			 * figure out "Correcting Block from 50, Corrected to 993, Height is 50" problem
			 */
		}
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.ORANGE);
		g.fillRect(x, y, width, height);
		// g.setColor(Color.BLUE);
		// g.drawLine(0, 993, 2000, 993);
	}
}
