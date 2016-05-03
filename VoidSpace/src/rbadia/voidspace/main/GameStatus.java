package rbadia.voidspace.main;

/**
 * Container for game flags and/or status variables.
 */
public class GameStatus {
	// game flags
	private boolean gameStarted = false;
	private boolean gameStarting = false;
	private boolean gameOver = false;
	private boolean youWin = false;


	// status variables
	private boolean newEnemyShip;
	private boolean newShip;
	private boolean newAsteroid;
	private boolean newBossShip;
	private boolean newSecondAsteroid;
	private boolean newLevel = false;
	private long asteroidsDestroyed = 0;
	private int shipsLeft;
	private int scoreCounter = 0;
	private long level = 1;
	private long numLevel = 1;
	private long enemyShipsDestroyed = 0;
	private long bossDestroyed;
	private long bossLife=0;



	public GameStatus(){

	}

	/**
	 * Indicates if the game has already started or not.
	 * @return if the game has already started or not
	 */
	public synchronized boolean isGameStarted() {
		return gameStarted;
	}

	public synchronized void setGameStarted(boolean gameStarted) {
		this.gameStarted = gameStarted;
	}

	/**
	 * Indicates if the game is starting ("Get Ready" message is displaying) or not.
	 * @return if the game is starting or not.
	 */
	public synchronized boolean isGameStarting() {
		return gameStarting;
	}

	public synchronized void setGameStarting(boolean gameStarting) {
		this.gameStarting = gameStarting;
	}

	/**
	 * Indicates if the game has ended and the "Game Over" message is displaying.
	 * @return if the game has ended and the "Game Over" message is displaying.
	 */
	public synchronized boolean isGameOver() {
		return gameOver;
	}

	public synchronized boolean isBossDestroyed() {
		return youWin;
	}

	public synchronized void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
	public synchronized void setYouWin(boolean youWin) {
		this.youWin = youWin;
	}

	/**
	 * Indicates if a new ship should be created/drawn.
	 * @return if a new ship should be created/drawn
	 */
	public synchronized boolean isNewShip() {
		return newShip;
	}
	
	public synchronized boolean isEnemyShip() {
		return newEnemyShip;
	}
	
	public boolean isBossShip() {
		return newBossShip;
	}
	public synchronized void setNewShip(boolean newShip) {
		this.newShip = newShip;
	}

	/**
	 * Indicates if a new asteroid should be created/drawn.
	 * @return if a new asteroid should be created/drawn
	 */
	public synchronized boolean isNewAsteroid() {
		return newAsteroid;
	}

	public synchronized void setNewAsteroid(boolean newAsteroid) {
		this.newAsteroid = newAsteroid;
	}
	public synchronized boolean isNewSecondAsteroid() {
		return newSecondAsteroid;
	}

	public synchronized void setNewSecondAsteroid(boolean newSecondAsteroid) {
		this.newSecondAsteroid = newSecondAsteroid;
		}
	public void setBossDestroyed(boolean boss) {
		this.newBossShip = boss;
	}

	/**
	 * Returns the number of asteroid destroyed. 
	 * @return the number of asteroid destroyed
	 */
	public synchronized long getAsteroidsDestroyed() {
		return asteroidsDestroyed;
	}

	public synchronized void setAsteroidsDestroyed(long asteroidsDestroyed) {
		this.asteroidsDestroyed = asteroidsDestroyed;
	}

	public synchronized void setScore(int score) {
		this.scoreCounter = score;
	}

	public synchronized int  getScore() {
		return this.scoreCounter;
	}

	//Level

	public synchronized long getLevel() {
		return this.level;
	}

	public synchronized void setLevel(long level) {
		this.level = level;
	}

	public synchronized long numLevel() {
		return numLevel;
	}

	public synchronized void setNumLevel(long numLevel) {
		this.numLevel = numLevel;
	}
	/**
	 * Returns the number ships/lives left.
	 * @return the number ships left
	 */
	public synchronized int getShipsLeft() {
		return shipsLeft;
	}

	public synchronized void setShipsLeft(int shipsLeft) {
		this.shipsLeft = shipsLeft;
	}

	public synchronized boolean getNewLevel() {
		return newLevel;
	}


	public synchronized void setNewLevel(boolean newLevel) {
		this.newLevel = newLevel;

	}

	public void setNewEnemyShip(boolean enemyShip) {
		this.newEnemyShip = enemyShip;
		
	}

	public synchronized long getEnemyShipDestroyed() {
		return this.enemyShipsDestroyed;
	}
	public synchronized void setEnemyShipsDestroyed(long enemyShipsDestroyed) {
		this.enemyShipsDestroyed = enemyShipsDestroyed;
	}

	

	public void setNewBossEnemySpaceship(boolean bossShip) {
		this.newBossShip = bossShip;
		
	}

	public void setBossLife(long hit) {
		this.bossLife = hit;
		
	}

	public synchronized long getBossLife() {
		return bossLife;
	}




}
