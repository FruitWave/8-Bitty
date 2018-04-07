package archon;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GameManager implements ActionListener {
	ArrayList<GameObject> objects;

	private int score = 0;

	int enemySpawnTime = 900;

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
		for (int i = 0; i < objects.size(); i++) {
			GameObject gameObject = objects.get(i);
			if (gameObject instanceof Enemy) {
				Enemy henry = (Enemy) gameObject;
				if (henry.bulletType.equals("minigunRight") || henry.bulletType.equals("minigunLeft")) {
					enemySpawnTime = 300;
				} else if (henry.bulletType.equals("upwards") || henry.bulletType.equals("downwards")
						|| henry.bulletType.equals("rightwards")) {
					enemySpawnTime = 1000;
				}
				if (System.currentTimeMillis() - henry.enemyTimer >= enemySpawnTime) {
					String yuppa;
					if (GamePanel.level > 5) {
						yuppa = henry.bulletType;
						addObject(new Projectile(henry.x, henry.y + (henry.height / 2), 10, 5, yuppa));
					} else {
						addObject(new Projectile(henry.x, henry.y + (henry.height / 2), 10, 5, null));
					}

					henry.enemyTimer = System.currentTimeMillis();
				}
			}
		}

	}

	public void checkCollision() {

		for (int i = 0; i < objects.size(); i++) {
			for (int j = i + 1; j < objects.size(); j++) {
				GameObject o1 = objects.get(i);
				GameObject o2 = objects.get(j);

				if (o1.collisionArea.intersects(o2.collisionArea)) {
					if (((o1 instanceof PlayerOne) && (o2 instanceof Backburner))
							|| ((o2 instanceof PlayerOne) && (o1 instanceof Backburner))) {
						PlayerOne p = o1 instanceof PlayerOne ? (PlayerOne) o1 : (PlayerOne) o2;
						Backburner ba = o1 instanceof Backburner ? (Backburner) o1 : (Backburner) o2;
						if (!(o2 instanceof Block)) {
							p.donotfall = false;
							// System.out.println("falling?");
						}
					}
					// if ((((o1 instanceof PlayerOne) && (o2 instanceof Backburner))
					// || ((o2 instanceof PlayerOne) && (o1 instanceof Backburner)))
					// || (((o1 instanceof PlayerOne) && (o2 instanceof Block))
					// || ((o2 instanceof PlayerOne) && (o1 instanceof Block)))) {
					// System.out.println("Thing 1 touching: " + o1 + " Thing 2 touching: " + o2);
					// }
					if ((o1 instanceof Block) && (o2 instanceof Block)) {
						Block doofon = (Block) o1;
						Block oork = (Block) o2;
						doofon.y = (doofon.y) < (oork.y) ? oork.y - doofon.height : oork.y + oork.height;

					}
					if (((o1 instanceof PlayerOne) && (o2 instanceof Block))
							|| ((o2 instanceof PlayerOne) && (o1 instanceof Block))) {

						Block doofon = o1 instanceof Block ? (Block) o1 : (Block) o2;
						PlayerOne oork = o1 instanceof PlayerOne ? (PlayerOne) o1 : (PlayerOne) o2;
						if ((doofon.deadlybedrock) && (!GamePanel.immortal)) {
							oork.isAlive = false;
						}
						// System.out.println("Collision. Y speed is " + oork.yspeed);
						// oork.yspeedAdder = 0;

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

						Block doofon = o1 instanceof Block ? (Block) o1 : (Block) o2;
						Enemy oork = o1 instanceof Enemy ? (Enemy) o1 : (Enemy) o2;
						// System.out.println("Collision. Y speed is " + oork.yspeed);
						oork.enemyonBlock = true;
						// System.out.println("Enemy: " + oork.y);
						// System.out.println("Block: " + doofon.y);
					}

					if ((o1 instanceof Enemy) && (o2 instanceof Enemy)) {

						// System.out.println("Collision. Y speed is " + oork.yspeed);
						if (o1.y < o2.y) {
							o1.yspeedAdder = 0;
							((Enemy) o1).enemyonBlock = true;
						} else {
							o2.yspeedAdder = 0;
							((Enemy) o2).enemyonBlock = true;

						}

					}
					if (((o1 instanceof Projectile) && (o2 instanceof Block))
							|| ((o2 instanceof Projectile) && (o1 instanceof Block))) {
						Projectile onki = o1 instanceof Projectile ? (Projectile) o1 : (Projectile) o2;
						if (!onki.hasInvisiblock) {
							onki.isAlive = false;
						}

					}
					if (((o1 instanceof PlayerOne) && (o2 instanceof Projectile))
							|| ((o2 instanceof PlayerOne) && (o1 instanceof Projectile))) {
						PlayerOne onki = o1 instanceof PlayerOne ? (PlayerOne) o1 : (PlayerOne) o2;
						if (!GamePanel.immortal) {
							onki.isAlive = false;
						}
						Projectile inket = o1 instanceof Projectile ? (Projectile) o1 : (Projectile) o2;
						inket.isAlive = false;
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}