package archon;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

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
			addObject(new Block((Runner.width / 2), 0, 50, 50));
			enemyTimer = System.currentTimeMillis();
		}
	}

	public void checkCollision() {
		for (int i = 0; i < objects.size(); i++) {
			for (int j = i + 1; j < objects.size(); j++) {
				GameObject o1 = objects.get(i);
				GameObject o2 = objects.get(j);

				if (o1.collisionArea.intersects(o2.collisionArea)) {
					/*
					 * if ((o1 instanceof Enemy && o2 instanceof Projectile) || (o2 instanceof Enemy
					 * && o1 instanceof Projectile)) { System.out.println("true"); score++;
					 * System.out.println(score); o1.isAlive = false; o2.isAlive = false; } else if
					 * ((o1 instanceof Enemy && o2 instanceof PlayerOne) || (o2 instanceof Enemy &&
					 * o1 instanceof PlayerOne)) { o1.isAlive = false; o2.isAlive = false; }
					 */

					if (o1 instanceof Block) {
						Block doofon = (Block) o1;
						GameObject oork = (o2 instanceof Block) ? (Block) o2
								: ((o2 instanceof PlayerOne) ? (PlayerOne) o2 : (Projectile) o2);
						if (oork instanceof Projectile) {
							System.err
									.println("Object o2 in collision with " + doofon + "was not a Block or PlayerOne!");
						}
						doofon.y = (doofon.y / 2) < (oork.y / 2) ? oork.y - doofon.height : oork.y + oork.height;
						if ((doofon.y / 2) < (oork.y / 2)) {
							doofon.forcesEnact = false;
						} else if (oork.y < doofon.y) {
							oork.forcesEnact = false;
						}
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