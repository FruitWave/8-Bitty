package archon;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import com.sun.javafx.scene.control.TableColumnSortTypeWrapper;

public class Projectile extends GameObject {
	int xspeed;
	int yspeed;
	int topspeed;
	int everyother;
	private String type;
	private final static String standard = "standard";
	private final static String allDirection = "allDirection";
	private final static String accelerative = "accelerative";
	private final static String duplicating = "duplicating";
	private final static String randomMotion = "randomMotion";
	boolean hasInvisiblock;
	private final static String upwards = "upwards";
	private final static String downwards = "downwards";
	private final static String leftwards = "leftwards";
	private final static String rightwards = "rightwards";
	private final static String minigunLeft = "minigunLeft";
	private final static String minigunRight = "minigunRight";

	public Projectile(int x, int y, int width, int height, String typeToFire) {
		super(x, y, width, height);
		xspeed = 0;
		yspeed = 0;
		xspeedAdder = 0;
		yspeedAdder = 0;
		everyother = 0;
		type = typeToFire;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		super.update();
		if (Panel.level < 5) {
			settypeandtellmove(Panel.level);
		} else {
			advancedActions(type);
		}

		if (type.equals(allDirection)) {
			hasInvisiblock = true;
		} else {
			hasInvisiblock = false;
		}
		yspeed = yspeedAdder;
		xspeed = xspeedAdder;
		if ((type != randomMotion) && (type != allDirection) && (Panel.level != 1) && (Panel.level <= 5)) {
			yspeed += gravispeed;
		}
		x += xspeed;
		y += yspeed;
	}

	private void settypeandtellmove(int whichlevel) {
		switch (whichlevel) {
		case 1:
			type = standard;
			moveXSpeedAdder(type);
			break;
		case 2:
			type = standard;
			decideXorYMotionFirst(type);
			break;
		case 3:
			type = allDirection;
			decideXorYMotionFirst(type);
			break;
		case 4:
			type = allDirection;
			decideXorYMotionFirst(type);
			break;
		case 5:
			type = accelerative;
			decideXorYMotionFirst(type);
			break;
		/*
		 * case 6: type = randomMotion; decideXorYMotionFirst(type); break;
		 */
		default:
			break;
		}

	}

	public void advancedActions(String type) {
		switch (type) {
		case upwards:
			yspeedAdder = -1;
			break;
		case leftwards:
			xspeedAdder = -1;
			break;
		case downwards:
			yspeedAdder = 1;
			break;
		case rightwards:
			xspeedAdder = 1;
			break;
		case minigunLeft:
			xspeedAdder = -2;
			break;
		case minigunRight:
			xspeedAdder = 2;
			break;
		case allDirection:
			if (Math.abs(x - Panel.morrow.x) < Math.abs(y - Panel.morrow.y)) {
				moveXSpeedAdder(type);
				moveYSpeedAdder(type);
			} else {
				moveYSpeedAdder(type);
				moveXSpeedAdder(type);
			}
		default:
			break;
		}
	}

	private void decideXorYMotionFirst(String typethy) {
		if (Math.abs(x - Panel.morrow.x) < Math.abs(y - Panel.morrow.y)) {
			moveXSpeedAdder(type);
			moveYSpeedAdder(type);
		} else {
			moveYSpeedAdder(type);
			moveXSpeedAdder(type);
		}
	}

	private void moveYSpeedAdder(String type) {
		if (type.equals(standard) || type.equals(duplicating)) {
			if (y < Panel.morrow.y) {
				yspeedAdder = 1;
			} else {
				yspeedAdder = -3;
			}
		} else if (type.equals(allDirection)) {
			yspeedAdder = y <= Panel.morrow.y ? 1 : -1;
			// System.out.println("YSPEEDADDER is" + yspeedAdder);
		} else if (type.equals(accelerative)) {
			if (Math.abs(yspeedAdder) < 15) {
				if (y < Panel.morrow.y) {
					yspeedAdder += 1;
				} else {
					yspeedAdder -= 1;
				}
			}

		} else if (type.equals(randomMotion)) {
			int randoyo = new Random().nextInt(2);
			if (randoyo == 0) {
				yspeedAdder = 4;
			} else {
				yspeedAdder = -4;
			}
		}

	}

	private void moveXSpeedAdder(String type) {
		if (type.equals(standard) || type.equals(duplicating)) {
			if (x > Panel.morrow.x) {
				xspeedAdder = -7;
			} else {
				xspeedAdder--;
			}
		} else if (type.equals(allDirection)) {
			if (x == Panel.morrow.x) {
				xspeedAdder = 0;
			} else {
				xspeedAdder = x < Panel.morrow.x ? 1 : -1;
			}
		} else if (type.equals(accelerative)) {
			if (Math.abs(xspeedAdder) < 30) {
				if (x > Panel.morrow.x) {
					xspeedAdder -= 1;
				}
			}

		} else if (type.equals(randomMotion)) {
			int randoxo = new Random().nextInt(2);
			if (randoxo == 0) {
				xspeedAdder = 2;
			} else {
				xspeedAdder = -2;
			}
		}

	}

	@Override
	public void draw(Graphics geegee) {
		geegee.drawImage(Panel.bullet, x, y, width, height, null);
	}
}
