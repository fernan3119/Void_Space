package rbadia.voidspace.model;

import java.awt.Rectangle;
import java.util.Random;

import rbadia.voidspace.main.GameScreen;

public class SecondAsteroid extends Rectangle {
	private static final long serialVersionUID = 1L;
	
	public static final int DEFAULT_SPEED = 4;
	
	private int SecondAsteroidWidth = 32;
	private int SecondAsteroidHeight = 32;
	private int speed = 4;

	private Random rand = new Random();
	
	/**
	 * Crates a new asteroid at a random x location at the top of the screen 
	 * @param screen the game screen
	 */
	public SecondAsteroid(GameScreen screen){
		this.setLocation(rand.nextInt(screen.getWidth() - SecondAsteroidWidth),0);
		this.setSize(SecondAsteroidWidth, SecondAsteroidHeight);
	}
	
	public int getSecondAsteroidWidth() {
		return SecondAsteroidWidth;
	}
	public int getSecondAsteroidHeight() {
		return SecondAsteroidHeight;
	}
	/**
	 * Returns the current asteroid speed
	 * @return the current asteroid speed
	 */
	public int getSpeed() {
		return speed;
	}
	
	/**
	 * Set the current asteroid speed
	 * @param speed the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	/**
	 * Returns the default asteroid speed.
	 * @return the default asteroid speed
	 */
	public int getDefaultSpeed(){
		return DEFAULT_SPEED;
	}
}