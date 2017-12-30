package archon;

import java.awt.Graphics;
import java.util.ArrayList;

public class GameManager {
	ArrayList<GameObject> objects;

	private int score = 0;

	long enemyTimer = 0;
	int enemySpawnTime = 800;

	public GameManager() {
		objects = new ArrayList<GameObject>();
	}

	public void addObject(GameObject o) {
		objects.add(o);
	}

	public void update() {
		for (int i = 0; i < objects.size(); i++) {
			GameObject o = objects.get(i);
			o.update();

		}

		purgeObjects();
	}

	public void draw(Graphics g) {
		for (int i = 0; i < objects.size(); i++) {
			GameObject o = objects.get(i);
			o.draw(g);
		}
	}

	private void purgeObjects() {
		for (int i = 0; i < objects.size(); i++) {
			if (!objects.get(i).isAlive) {
				objects.remove(i);
			}
		}
	}

	public void manageEnemies() {
		if (System.currentTimeMillis() - enemyTimer >= enemySpawnTime) {
			// addObject(new Block((Runner.width / 2), 0, 50, 50));
			enemyTimer = System.currentTimeMillis();
		}
	}

	public void checkCollision() {

		for (int i = 0; i < objects.size(); i++) {
			for (int j = i + 1; j < objects.size(); j++) {
				GameObject o1 = objects.get(i);
				GameObject o2 = objects.get(j);

				if (o1.collisionArea.intersects(o2.collisionArea)) {

					if ((o1 instanceof Block) && (o2 instanceof Block)) {
						Block doofon = (Block) o1;
						Block oork = (Block) o2;
						doofon.y = (doofon.y) < (oork.y) ? oork.y - doofon.height : oork.y + oork.height;

						if ((doofon.y) < (oork.y)) {
							doofon.gravitEnact = false;
						} else if (oork.y < doofon.y) {
							oork.gravitEnact = false;
						}
					}
					if (((o1 instanceof PlayerOne) && (o2 instanceof Block))
							|| ((o2 instanceof PlayerOne) && (o1 instanceof Block))) {
						System.out.println("on");
						Block doofon = o1 instanceof Block ? (Block) o1 : (Block) o2;
						PlayerOne oork = o1 instanceof PlayerOne ? (PlayerOne) o1 : (PlayerOne) o2;

						System.out.println("Collision. Y speed is " + oork.yspeed);
						oork.yspeedAdder = 0;
						oork.playeronBlock = true; /* unnecessary */

						if (!GamePanel.playerupbutton && (doofon.y > oork.y)) {
							oork.y = doofon.y - oork.height + 1;
							oork.donotfall = true;
						} else if (GamePanel.playerupbutton) {
							oork.y = doofon.y - oork.height;
							oork.donotfall = false;
						}

					} else {
						GamePanel.morrow.donotfall = false;
					}

					if (((o1 instanceof Enemy) && (o2 instanceof Block))
							|| ((o2 instanceof Enemy) && (o1 instanceof Block))) {
						System.out.println("");
						Block doofon = o1 instanceof Block ? (Block) o1 : (Block) o2;
						Enemy oork = o1 instanceof Enemy ? (Enemy) o1 : (Enemy) o2;
						// System.out.println("Collision. Y speed is " + oork.yspeed);
						oork.yspeedAdder = 0;
						oork.enemyonBlock = true;
					}

				}
			}
		}

	}

	public int getScore() {
		return score;
	}

	public void setScore(int s) {
		score = s;
	}

	public void reset() {
		objects.clear();
	}
}