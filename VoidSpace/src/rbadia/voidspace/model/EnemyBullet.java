package rbadia.voidspace.model;
import java.awt.Rectangle;
import java.util.Random;

import rbadia.voidspace.main.GameScreen;

/**
 * Represents a bullet fired by a ship.
 */
public class EnemyBullet extends Rectangle {
	private static final long serialVersionUID = 1L;
	
	private int enemyBulletWidth = 8;
	private int enemyBulletHeight = 8;
	private int speed = 12;

	private Random rand = new Random();

	/**
	 * Creates a new bullet above the ship, centered on it
	 * @param screen
	 */
	public EnemyBullet(GameScreen screen) {
		this.setLocation(rand .nextInt(screen.getWidth() - enemyBulletWidth),0);
		this.setSize(enemyBulletWidth, enemyBulletHeight);

	}

	/**
	 * Return the bullet's speed.
	 * @return the bullet's speed.
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * Set the bullet's speed
	 * @param speed the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}
}
