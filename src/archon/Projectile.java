package archon;

import java.awt.Color;
import java.awt.Graphics;

public class Projectile extends GameObject {
	int xspeed;
	int yspeed;
	int topspeed;

	public Projectile(int x, int y, int width, int height) {
		super(x, y, width, height);
		xspeed = 0;
		yspeed = 0;
		xspeedAdder = 0;
		yspeedAdder = 0;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		super.update();
		if (x > GamePanel.morrow.x) {
			xspeedAdder = -3;
		}
		if (y > (GamePanel.morrow.y + (GamePanel.morrow.height / 2))) {
			if (yspeedAdder > -2) {
				yspeedAdder--;
			}
		} else if (y < (GamePanel.morrow.y + (GamePanel.morrow.height / 2))) {
			if (yspeedAdder < 2) {
				yspeedAdder++;
			}

		}
		yspeed = yspeedAdder;
		xspeed = xspeedAdder;
		yspeed += gravispeed;
		x += xspeed;
		y += yspeed;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.green);
		g.fillRect(x, y, width, height);
	}
}
