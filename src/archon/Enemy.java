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
	boolean reverseGrav = false;

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
			yspeed = reverseGrav ? -gravispeed : gravispeed;
		} else {
			yspeed = 0;
		}

		y += yspeed;
		x += xspeed;
	}

	@Override
	public void draw(Graphics godzilla) {
		if (GamePanel.whichLevelCommonKnowledge != 9) {
			godzilla.drawImage(GamePanel.enemy, x, y, width, height, null);
		}

	}

}
