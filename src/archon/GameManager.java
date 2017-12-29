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
					if ((((o1 instanceof PlayerOne) && (o2 instanceof Block))
							|| ((o2 instanceof PlayerOne) && (o1 instanceof Block)))
							|| (((o1 instanceof Enemy) && (o2 instanceof Block))
									|| ((o2 instanceof Enemy) && (o1 instanceof Block)))) {
						Block doofon = o1 instanceof Block ? (Block) o1 : (Block) o2;
						GameObject oork;
						if (o1 instanceof PlayerOne || o2 instanceof PlayerOne) {
							oork = o1 instanceof PlayerOne ? (PlayerOne) o1 : (PlayerOne) o2;
						} else {
							oork = o1 instanceof Enemy ? (Enemy) o1 : (Enemy) o2;
						}

						// System.out.println("Collision. Y speed is " + oork.yspeed);
						oork.yspeedAdder = 0;
						if (oork instanceof PlayerOne) {
							((PlayerOne) oork).playeronBlock = true;
						} else if (oork instanceof Enemy) {
							((Enemy) oork).enemyonBlock = true;
						}

						GamePanel.morrow.mani_to_player1_there_is_no_collision = false;
						if (oork instanceof PlayerOne) {
							if (!GamePanel.playerupbutton && (doofon.y > oork.y)) {
								oork.y = doofon.y - oork.height + 1;
								oork.gravispeed = ((PlayerOne) oork).eternalGravSpeed;
							} else {
								oork.gravispeed = 0;
							}
						} else if (oork instanceof Enemy) {
							if (doofon.y > oork.y) {
								oork.y = doofon.y - oork.height + 1;
							}
						}

					}
				} else {
					// PlayerOne.turngravbackon.restart();
					GamePanel.morrow.playeronBlock = false;
					GamePanel.morrow.gravispeed = GamePanel.morrow.eternalGravSpeed;
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