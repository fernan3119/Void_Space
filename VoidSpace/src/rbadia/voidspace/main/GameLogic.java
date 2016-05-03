package rbadia.voidspace.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.Boss;
import rbadia.voidspace.model.BossBullet;
import rbadia.voidspace.model.EnemyBullet;
import rbadia.voidspace.model.EnemyShip;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.SecondAsteroid;
import rbadia.voidspace.model.Ship;
import rbadia.voidspace.sounds.SoundManager;


/**
 * Handles general game logic and status.
 */
public class GameLogic {
	private GameScreen gameScreen;
	private GameStatus status;
	private SoundManager soundMan;

	private Ship ship;
	private Asteroid asteroid;
	private SecondAsteroid secondAsteroid;
	private List<Bullet> bullets;
	private List<BossBullet> bossBullet;
	private EnemyShip enemyShip;
	private EnemyBullet enemyBullets;
	private Boss bossShip;

	/**
	 * Craete a new game logic handler
	 * @param gameScreen the game screen
	 */
	public GameLogic(GameScreen gameScreen){
		this.gameScreen = gameScreen;

		// initialize game status information
		status = new GameStatus();
		// initialize the sound manager
		soundMan = new SoundManager();

		// init some variables
		bullets = new ArrayList<Bullet>();
	}

	/**
	 * Returns the game status
	 * @return the game status 
	 */
	public GameStatus getStatus() {
		return status;
	}

	public SoundManager getSoundMan() {
		return soundMan;
	}

	public GameScreen getGameScreen() {
		return gameScreen;
	}

	/**
	 * Prepare for a new game.
	 */
	public void newGame(){
		soundMan.BackgroundMusic1();

		status.setGameStarting(true);

		// init game variables
		bullets = new ArrayList<Bullet>();

		status.setShipsLeft(8);
		status.setGameOver(false);

		if (!status.getNewLevel()) {
			status.setAsteroidsDestroyed(0);
		}

		status.setNewAsteroid(false);

		// init the ship and the asteroid
		newShip(gameScreen);
		newAsteroid(gameScreen);

		// prepare game screen
		gameScreen.doNewGame();
		soundMan.StopMusic2();
		soundMan.StopMusic3();
		soundMan.StopMusic4();
		soundMan.StopMusic5();
		soundMan.StopMusic6();
		// delay to display "Get Ready" message for 1.5 seconds
		Timer timer = new Timer(1500, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				status.setGameStarting(false);
				status.setGameStarted(true);	

				status.setLevel(1);
				status.setNumLevel(1);
				status.setScore(0);
				status.setYouWin(false);
				status.setBossLife(0);
			}
		});
		timer.setRepeats(false);
		timer.start();
	}

	public void newLevel2Game(){
		soundMan.BackgroundMusic3();
		status.setGameStarting(true);
		// init game variables
		bullets = new ArrayList<Bullet>();

		status.setShipsLeft(status.getShipsLeft());
		status.setGameOver(false);
		status.setAsteroidsDestroyed(status.getAsteroidsDestroyed());
		status.setScore(status.getScore());
		status.setNewAsteroid(false);
		status.setNewSecondAsteroid(false);



		// init the ship and the asteroid 
		newShip(gameScreen);
		newAsteroid(gameScreen);
		newSecondAsteroid(gameScreen);

		// prepare game screen
		gameScreen.doNewGame();
		soundMan.StopMusic2();
		soundMan.StopMusic();
		soundMan.StopMusic4();
		soundMan.StopMusic5();
		soundMan.StopMusic6();
		// delay to display "Get Ready" message for 1.5 seconds
		Timer timer = new Timer(1500, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				status.setGameStarting(false);
				status.setGameStarted(true);
				status.setLevel(2);
				status.setNumLevel(2);
			}
		});
		timer.setRepeats(false);
		timer.start();
	}

	public void newLevel3Game(){
		soundMan.BackgroundMusic6();
		status.setGameStarting(true);
		// init game variables
		bullets = new ArrayList<Bullet>();

		status.setShipsLeft(status.getShipsLeft());
		status.setGameOver(false);
		status.setAsteroidsDestroyed(status.getAsteroidsDestroyed());
		status.setScore(status.getScore());
		status.setNewAsteroid(false);
		status.setNewSecondAsteroid(false);




		// init the ship and the asteroid 
		newShip(gameScreen);
		newAsteroid(gameScreen);
		newSecondAsteroid(gameScreen);
		newEnemyShip(gameScreen);
		newEnemyBullet(gameScreen);



		// prepare game screen
		gameScreen.doNewGame();

		soundMan.StopMusic2();
		soundMan.StopMusic3();
		soundMan.StopMusic4();
		soundMan.StopMusic5();
		soundMan.StopMusic();

		// delay to display "Get Ready" message for 1.5 seconds
		Timer timer = new Timer(1500, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				status.setGameStarting(false);
				status.setGameStarted(true);
				status.setLevel(3);
				status.setNumLevel(3);
				status.setEnemyShipsDestroyed(0);
			}
		});
		timer.setRepeats(false);
		timer.start();
	}

	public void newLevel4Game(){

		soundMan.BackgroundMusic5();
		status.setGameStarting(true);
		// init game variables
		bullets = new ArrayList<Bullet>();

		status.setShipsLeft(status.getShipsLeft());
		status.setGameOver(false);
		status.setAsteroidsDestroyed(status.getAsteroidsDestroyed());
		status.setScore(status.getScore());
		status.setNewAsteroid(false);
		status.setNewSecondAsteroid(false);




		// init the ship and the asteroid 
		newShip(gameScreen);
		newAsteroid(gameScreen);
		newSecondAsteroid(gameScreen);
		newEnemyShip(gameScreen);
		newEnemyBullet(gameScreen);
		newBossShip(gameScreen);




		// prepare game screen
		gameScreen.doNewGame();
		soundMan.StopMusic2();
		soundMan.StopMusic3();
		soundMan.StopMusic4();
		soundMan.StopMusic();
		soundMan.StopMusic6();

		// delay to display "Get Ready" message for 1.5 seconds
		Timer timer = new Timer(1500, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				status.setGameStarting(false);
				status.setGameStarted(true);
				status.setLevel(4);
				status.setNumLevel(4);
			}
		});
		timer.setRepeats(false);
		timer.start();
	}





	/**
	 * Check game or level ending conditions.
	 */
	public void checkConditions(){
		// check game over conditions
		if(!status.isGameOver() && status.isGameStarted()){
			if(status.getShipsLeft() == 0){
		
				soundMan.StopMusic3();
				soundMan.StopMusic4();
				soundMan.StopMusic5();
				soundMan.StopMusic6();
				soundMan.BackgroundMusic2();
				gameOver();
				soundMan.StopMusic();
			}
		}
		if(!status.isGameOver() && status.isBossDestroyed()&& status.isGameStarted()){
			if(status.isBossDestroyed()){
				soundMan.StopMusic5();
				soundMan.BackgroundMusic4();
				gameWinner();

			}

		}
	}



	/**
	 * Actions to take when the game is over.
	 */
	public void gameOver(){
		status.setGameStarted(false);
		status.setGameOver(true);
		gameScreen.doNewGame();
		soundMan.StopMusic();
	
		// delay to display "Game Over" messa	private Object rand;

		Timer timer = new Timer(3000, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				status.setGameOver(false);
			}
		});
		timer.setRepeats(false);
		timer.start();
	}


	public void gameWinner(){

		status.setGameStarted(false);
		gameScreen.doNewGame();

		// delay to display "Game Over" messa	private Object rand;

		Timer timer = new Timer(3000, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				status.setGameOver(false);
			}
		});
		timer.setRepeats(false);
		timer.start();
	}


	public void bossDestroyed(){
		status.setGameStarted(false);
		status.setGameOver(true);
		status.setLevel(1);
		status.setNumLevel(1);
		status.setAsteroidsDestroyed(0);


		// delay to display "Game Over" message for 3 seconds
		Timer timer = new Timer(3000, new ActionListener(){
			public void actionPerformed(ActionEvent e) {

				status.setGameStarting(false);
				status.setBossDestroyed(false);
				status.setLevel(1);
				status.setNumLevel(1);		

			}
		});
		timer.setRepeats(false);
		timer.start();
	}


	/**
	 * Fire a bullet from ship.
	 */
	public void fireBullet(){
		Bullet bullet = new Bullet(ship);
		bullets.add(bullet);
		soundMan.playBulletSound();
	}

	/**
	 * Move a bullet once fired.
	 * @param bullet the bullet to move
	 * @return if the bullet should be removed from screen
	 */
	public boolean moveBullet(Bullet bullet){
		if(bullet.getY() - bullet.getSpeed() >= 0){
			bullet.translate(0, -bullet.getSpeed());
			return false;
		}
		else{
			return true;
		}
	}
		public boolean moveBossBullet(Bullet bullet){
			if(bullet.getY() - bullet.getSpeed() >= 0){
				bullet.translate(0, -bullet.getSpeed());
				return false;
			}
			else{
				return true;
			}
	}

	/**
	 * Create a new ship (and replace current one).
	 */
	public Ship newShip(GameScreen screen){
		this.ship = new Ship(screen);
		return ship;
	}

	public Boss newBossShip(GameScreen screen){
		this.bossShip = new Boss(screen);
		return bossShip;
	}


	/**
	 * Create a new asteroid.
	 */
	public Asteroid newAsteroid(GameScreen screen){
		this.asteroid = new Asteroid(screen);
		return asteroid;
	}

	public SecondAsteroid newSecondAsteroid(GameScreen screen){
		this.secondAsteroid = new SecondAsteroid(screen);
		return secondAsteroid;
	}

	public EnemyShip newEnemyShip(GameScreen screen){
		this.enemyShip = new EnemyShip(screen);
		return enemyShip;
	}

	public EnemyBullet newEnemyBullet(GameScreen screen){
		this.enemyBullets = new EnemyBullet(screen);
		return enemyBullets;
	}

	/**
	 * Returns the ship.
	 * @return the ship
	 */
	public Ship getShip() {
		return ship;
	}

	/**
	 * Returns the asteroid.
	 * @return the asteroid
	 */
	public Asteroid getAsteroid() {
		return asteroid;
	}

	public SecondAsteroid getSecondAsteroid() {
		return secondAsteroid;
	}


	/**
	 * Returns the list of bullets.
	 * @return the list of bullets
	 */
	public List<BossBullet> getBossBullets() {
		return bossBullet;
	}
	public List<Bullet> getBullets() {
		return bullets;
	}

	public EnemyShip getEnemyShip() {
		return enemyShip;
	}

	public EnemyBullet getEnemyBullets() {
		return enemyBullets;
	}

	public Boss getBossShip() {
		return bossShip;
	}

}
