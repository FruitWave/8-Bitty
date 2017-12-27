package archon;

import java.awt.Color;
import java.awt.Graphics;

public class PlayerOne extends GameObject {

	public PlayerOne(int x, int y, int width, int height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update() {
		super.update();
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.cyan);
		g.fillRect(x, y, width, height);
	}
}
