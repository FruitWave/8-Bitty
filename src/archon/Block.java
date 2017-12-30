package archon;

import java.awt.Color;
import java.awt.Graphics;

public class Block extends GameObject {
	int eternalY;
	int eternalX;
	boolean locked;

	public Block(int x, int y, int width, int height, boolean gravityAffected, boolean lockedInPlace) {
		super(x, y, width, height);
		// if (lockedInPlace) {
		eternalX = x;
		eternalY = y;
		// }
		locked = lockedInPlace;
	}

	@Override
	public void update() {
		super.update();
		if (!locked) {

			if (!locked) {
				y += gravispeed;
			}

		}

		if ((y + height) > Runner.height) {
			// System.out.println("Correcting Block from " + height);
			y = Runner.height - height;
			// System.out.println("Corrected to " + y);
			// System.out.println("Height is " + height);
			/*
			 * figure out "Correcting Block from 50, Corrected to 993, Height is 50" problem
			 */
		}

		if (locked) {
			// if (x != eternalX) {
			x = eternalX;
			// }
			// if (y != eternalY) {
			y = eternalY;
			// }
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
