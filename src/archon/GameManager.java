package archon;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

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
						System.out.println("Collision.");
						Block doofon = o1 instanceof Block ? (Block) o1 : (Block) o2;
						PlayerOne oork = o1 instanceof PlayerOne ? (PlayerOne) o1 : (PlayerOne) o2;
						oork.playeryspeedAdder = 0;
						GamePanel.fallnow.stop();
						GamePanel.fallnowcount = 0;
						GamePanel.morrow.mani_to_player1_there_is_no_collision = false;
						if (!GamePanel.upbutton && (doofon.y > oork.y)) {
							oork.y = doofon.y - oork.height;
						}
					}
				} else {

					GamePanel.morrow.mani_to_player1_there_is_no_collision = true;
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