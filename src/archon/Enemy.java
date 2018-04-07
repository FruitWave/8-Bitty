package archon;

import java.awt.Color;
import java.awt.Graphics;

public class Enemy extends GameObject {
	int xspeed;
	int yspeed;
	public boolean enemyonBlock;
	GamePanel archonian;
	long enemyTimer = 0;
	boolean enemyPositionLocked;
	public String bulletType;

	public Enemy(int x, int y, int width, int height, boolean locked, String bulletType, GamePanel archonian) {
		super(x, y, width, height);
		yspeed = 0;
		xspeed = 0;
		this.archonian = archonian;
		gravispeed = 1;
		enemyPositionLocked = locked;
		this.bulletType = bulletType;
		if (this.bulletType == null) {
			this.bulletType = "standard";
		}
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		super.update();
		if (!enemyonBlock && !enemyPositionLocked) {
			yspeed = gravispeed;
		} else {
			yspeed = 0;
		}

		y += yspeed;
		x += xspeed;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(x, y, width, height);
		// g.drawLine(x, y, archonian.morrow.x, archonian.morrow.y);
	}

}
