package archon;

import java.awt.Color;
import java.awt.Graphics;

public class Block extends GameObject {
	int eternalY;
	int eternalX;
	boolean locked;
	boolean deadlybedrock;
	boolean topblock;
	int imageheight;
	int imagewidth;

	public Block(int x, int y, int width, int height, boolean lockedInPlace, boolean deadly, boolean topblock) {
		super(x, y, width, height);
		// if (lockedInPlace) {
		eternalX = x;
		eternalY = y;
		// }
		locked = lockedInPlace;
		deadlybedrock = deadly;
		this.topblock = topblock;
	}

	@Override
	public void update() {
		super.update();
		if (!locked) {
			y += gravispeed;
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
			gravispeed = 0;
		}
	}

	@Override
	public void draw(Graphics marshmellow) {
		if (deadlybedrock) {
			if (topblock) {
				// imagewidth = GamePanel.dirttopdeadly.getWidth();
				// imageheight = GamePanel.dirttopdeadly.getHeight();
				marshmellow.drawImage(Panel.dirttopdeadly, x, y, width, height, null);
			} else {
				// imagewidth = GamePanel.dirtdeadly.getWidth();
				// imageheight = GamePanel.dirtdeadly.getHeight();
				marshmellow.drawImage(Panel.dirtdeadly, x, y, width, height, null);
			}
		} else {
			if (topblock) {
				// imagewidth = GamePanel.dirttop.getWidth();
				// imageheight = GamePanel.dirttop.getHeight();
				marshmellow.drawImage(Panel.dirttop, x, y, width, height, null);
			} else {
				// imagewidth = GamePanel.dirt.getWidth();
				// imageheight = GamePanel.dirt.getHeight();
				marshmellow.drawImage(Panel.dirt, x, y, width, height, null);
			}
		}
	}
}
