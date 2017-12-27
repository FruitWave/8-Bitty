package archon;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject {
	int x;
	int y;
	int width;
	int height;
	boolean isAlive = true;
	public int gravispeed = 5;
	public boolean gravitEnact;
	public boolean forcesEnact;
	Rectangle collisionArea = new Rectangle(x, y, width, height);

	public GameObject(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public void update() {
		collisionArea.setBounds(x, y, width, height);
		// if (y > Runner.height - height) {
		// y = Runner.height - height;
		// }
		// fix sinking into floor D:
	}

	public void draw(Graphics g) {
	}

}