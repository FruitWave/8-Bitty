package archon;

import java.awt.Color;
import java.awt.Graphics;

public class Backburner extends GameObject {
	int eternalX = 0;
	int eternalY = 0;

	public Backburner(int x, int y, int width, int height) {
		super(x, y, width, height);
		gravispeed = 0;
	}

	@Override
	public void update() {
		collisionArea.setBounds(x, y, width, height);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.darkGray);
		g.fillRect(eternalX, eternalY, width, height);

	}

}
