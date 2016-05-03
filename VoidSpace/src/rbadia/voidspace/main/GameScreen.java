package rbadia.voidspace.main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;

import rbadia.voidspace.graphics.GraphicsManager;
import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.Boss;
import rbadia.voidspace.model.EnemyBullet;
import rbadia.voidspace.model.EnemyShip;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.EnemyShip2;
import rbadia.voidspace.model.SecondAsteroid;
import rbadia.voidspace.model.Ship;
import rbadia.voidspace.sounds.SoundManager;

/**
 * Main game screen. Handles all game graphics updates and some of the game logic.
 */
public class GameScreen extends JPanel {
	private static final long serialVersionUID = 1L;

	private BufferedImage backBuffer;
	private Graphics2D g2d;

	private static final int NEW_SHIP_DELAY = 500;
	private static final int NEW_ASTEROID_DELAY = 500;
	private static final int NEW_SECONDASTEROID_DELAY = 500;
	private static final int NEW_ENEMYSHIP_DELAY = 500;
	private static final int NEW_BOSS_DELAY = 500;




	private long lastShipTime;
	private long lastAsteroidTime;
	private long lastSecondAsteroidTime;
	private long lastEnemyShipTime;
	private long lastEnemyBulletTime;
	private long lastBossBulletTime;
	private long lastBossTime;




	private Rectangle asteroidExplosion;
	private Rectangle secondAsteroidExplosion;
	private Rectangle shipExplosion;
	private Rectangle enemyShipExplosion;


	private JLabel shipsValueLabel;
	private JLabel destroyedValueLabel;
	private JLabel scoreValueLabel;

	private Random rand;

	private Font originalFont;
	private Font bigFont;
	private Font biggestFont;

	private GameStatus status;
	private SoundManager soundMan;
	private GraphicsManager graphicsMan;
	private GameLogic gameLogic;


	private int asteroidsDestroyed = 0;
	private int ships=3;

	private boolean end = false;
	private boolean end2 =false;
	private boolean endBoss =false;




	/**
	 * This method initializes 
	 * 
	 */
	public GameScreen() {
		super();
		// initialize random number generator
		rand = new Random();

		initialize();

		// init graphics manager
		graphicsMan = new GraphicsManager();

		// init back buffer image
		backBuffer = new BufferedImage(500, 400, BufferedImage.TYPE_INT_RGB);
		g2d = backBuffer.createGraphics();
	}

	/**
	 * Initialization method (for VE compatibility).
	 */

	private void initialize() {
		// set panel properties
		this.setSize(new Dimension(500, 400));
		this.setPreferredSize(new Dimension(500, 400));
		this.setBackground(Color.BLACK);
	}

	/**
	 * Update the game screen.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// draw current backbuffer to the actual game screen
		g.drawImage(backBuffer, 0, 0, this);

	}

	/**
	 * Update the game screen's backbuffer image.
	 */
	public void updateScreen(){
		Ship ship = gameLogic.getShip();
		Asteroid asteroid = gameLogic.getAsteroid();
		SecondAsteroid secondAsteroid = gameLogic.getSecondAsteroid();
		EnemyShip enemyShip = gameLogic.getEnemyShip();
		EnemyBullet enemyBullets =  gameLogic.getEnemyBullets();
		EnemyBullet bossBullets =  gameLogic.getEnemyBullets();
		List<Bullet> bullets = gameLogic.getBullets();
		Boss bossShip = gameLogic.getBossShip();



		// set orignal font - for later use
		if(this.originalFont == null){
			this.originalFont = g2d.getFont();
			this.bigFont = originalFont;
		}

		// erase screen
		g2d.setPaint(Color.BLACK);
		g2d.fillRect(0, 0, getSize().width, getSize().height);

		// draw 50 random stars
		drawStars(50);

		// if the game is starting, draw "Get Ready" message
		if(status.isGameStarting()){
			drawGetReady();
			return;
		}

		if(status.getAsteroidsDestroyed()==5 && status.numLevel()==1){
			drawLevel2();	
			status.setNewLevel(true);

			return;
		}

		if(status.getAsteroidsDestroyed()==10 && status.numLevel()==2){
			drawLevel3();	
			status.setNewLevel(true);
			return;
		}
		if( status.getEnemyShipDestroyed() ==  5 && status.numLevel()==3){
			drawLevel4();	
			status.setNewLevel(true);
			return;
		}
		if (status.getBossLife()==20) {
			status.setYouWin(true);
			YouWin();
			return;
		}





		// if the game is over, draw the "Game Over" message
		if(status.isGameOver()){
			// draw the message
			drawGameOver();

			long currentTime = System.currentTimeMillis();
			// draw the explosions until their time passes
			if((currentTime - lastAsteroidTime) < NEW_ASTEROID_DELAY){
				graphicsMan.drawAsteroidExplosion(asteroidExplosion, g2d, this);

			}
			if((currentTime - lastShipTime) < NEW_SHIP_DELAY){
				graphicsMan.drawShipExplosion(shipExplosion, g2d, this);

			}
			if((currentTime - lastEnemyShipTime) < NEW_ENEMYSHIP_DELAY){
				graphicsMan.drawEnemyShipExplosion(enemyShipExplosion, g2d, this);

			}


			if((currentTime - lastSecondAsteroidTime) < NEW_SECONDASTEROID_DELAY){
				graphicsMan.drawAsteroidExplosion(secondAsteroidExplosion, g2d, this);

			}

			if((currentTime - lastBossTime) < NEW_BOSS_DELAY){
				graphicsMan.drawAsteroidExplosion(secondAsteroidExplosion, g2d, this);

			}

			return;
		}

		// the game has not started yet
		if(!status.isGameStarted()){
			initialMessage();
			return;
		}


		// draw asteroid
		if(!status.isNewAsteroid()){
			// draw the asteroid until it reaches the bottom of the screen
			if(asteroid.getY() + asteroid.getSpeed() < this.getHeight()){
				asteroid.translate(0, asteroid.getSpeed());
				graphicsMan.drawAsteroid(asteroid, g2d, this);
			}
			else{
				asteroid.setLocation(rand.nextInt(getWidth() - asteroid.width), 0);
			}
		}
		else{
			long currentTime = System.currentTimeMillis();
			if((currentTime - lastAsteroidTime) > NEW_ASTEROID_DELAY){
				// draw a new asteroid
				lastAsteroidTime = currentTime;
				status.setNewAsteroid(false);
				asteroid.setLocation(rand.nextInt(getWidth() - asteroid.width), 0);
			}
			else{
				if (!(status.numLevel()==4)) {
					graphicsMan.drawAsteroidExplosion(asteroidExplosion, g2d, this);

				}// draw explosion

			}
		}

		//DrawSecondAsteroid
		if (status.numLevel()==2 || status.numLevel()==3 || status.numLevel()==4) {


			if(!status.isNewAsteroid()){
				// draw the asteroid until it reaches the bottom of the screen
				if(secondAsteroid.getY() + secondAsteroid.getSpeed() < this.getHeight()){
					secondAsteroid.translate(1, secondAsteroid.getSpeed()+2 );
					graphicsMan.drawSecondAsteroid(secondAsteroid, g2d, this);

				}
				else{
					secondAsteroid.setLocation(rand.nextInt(getWidth() - secondAsteroid.width), 0);
				}
			}
			else{
				long currentTime = System.currentTimeMillis();
				if((currentTime - lastSecondAsteroidTime) > NEW_SECONDASTEROID_DELAY){
					// draw a new asteroid
					lastSecondAsteroidTime = currentTime;
					status.setNewAsteroid(false);
					asteroid.setLocation(rand.nextInt(getWidth() - asteroid.width), 0);
				}
				else{
					// draw explosion
					graphicsMan.drawAsteroidExplosion(asteroidExplosion, g2d, this);
					soundMan.playShipExplosionSound();
				}
			}

		}
		if(status.numLevel() == 3 || status.numLevel() == 4){
			if(!status.isNewSecondAsteroid()){
				// draw the asteroid until it reaches the bottom of the screen
				if(secondAsteroid.getY() + secondAsteroid.getSpeed() < this.getHeight()){
					secondAsteroid.translate(0, secondAsteroid.getSpeed()-4);
					graphicsMan.drawSecondAsteroid(secondAsteroid, g2d, this);
					//secondAsteroid.setSpeed(6);
				}
				else{
					secondAsteroid.setLocation(rand.nextInt(getWidth() - secondAsteroid.width), 0);
				}
			}
			else{
				long currentTime = System.currentTimeMillis();
				if((currentTime - lastSecondAsteroidTime) > NEW_SECONDASTEROID_DELAY){
					// draw a new asteroid
					lastSecondAsteroidTime = currentTime;
					status.setNewSecondAsteroid(false);
					secondAsteroid.setLocation(rand.nextInt(getWidth() - secondAsteroid.width), 0);
				}
				else{
					// draw explosion
					graphicsMan.drawAsteroidExplosion(asteroidExplosion, g2d, this);
				}
			}
		}
		if(status.numLevel() == 3){
			//Draw enemy spaceship
			if(!status.isEnemyShip()){

				if(enemyBullets.getY() + enemyBullets.getSpeed() < this.getHeight()){
					enemyBullets.translate(0, enemyBullets.getSpeed());
					graphicsMan.drawEnemyBullet(enemyBullets, g2d, this);

				}
				else{
					enemyBullets.setLocation((int)enemyShip.getCenterX(), (int)enemyShip.getCenterY());
					soundMan.playBulletSound();
				}

				graphicsMan.drawEnemyShip(enemyShip, g2d, this);
				if(!end  && !end2){
					enemyShip.translate(enemyShip.getSpeed(), 0);
					if (enemyShip.getX()+enemyShip.width >= this.getWidth()){
						end = true;
						end2 = false;
					}
				}
				if(end  && !end2) {
					enemyShip.translate(0,enemyShip.getSpeed());
					if (enemyShip.getY()+enemyShip.height >= 4*enemyShip.height){
						end = false;
						end2 = true;
					}
				}
				if(!end  && end2 ) {
					enemyShip.translate( -enemyShip.getSpeed(), 0);
					if (enemyShip.getX() <= 0){
						end = true;
						end2 = true;
					}
				}
				if(end  && end2 ) {
					enemyShip.translate(0, enemyShip.getSpeed());
					if (enemyShip.getY()+enemyShip.height >= 4*enemyShip.height){
						end = false;
						end2 = false;
					}
				}
			}
			else{
				long currentTime = System.currentTimeMillis();
				if((currentTime - lastEnemyShipTime) > NEW_ENEMYSHIP_DELAY){
					// draw a new enemy Spaceship
					lastEnemyShipTime = currentTime;
					status.setNewEnemyShip(false);
					enemyShip.setLocation(-enemyShip.width, 0);
				}
				else{
					// draw explosion
					graphicsMan.drawEnemyShipExplosion(enemyShipExplosion, g2d, this);
				}
			}
		}



		if(status.numLevel() == 2 || status.numLevel() == 3 || status.numLevel() == 4){
			if(secondAsteroid.intersects(ship)){
				// decrease number of ships left
				status.setShipsLeft(status.getShipsLeft() - 1);
				status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 1);
				status.setScore(status.getScore() + 100);
				asteroidsDestroyed++;
				ships--;


				// "remove" asteroid
				secondAsteroidExplosion = new Rectangle(
						secondAsteroid.x,
						secondAsteroid.y,
						secondAsteroid.width,
						secondAsteroid.height);
				secondAsteroid.setLocation(-secondAsteroid.width, -secondAsteroid.height);
				status.setNewSecondAsteroid(true);
				lastSecondAsteroidTime = System.currentTimeMillis();

				// "remove" ship
				shipExplosion = new Rectangle(
						ship.x,
						ship.y,
						ship.width,
						ship.height);
				ship.setLocation(this.getWidth() + ship.width, -ship.height);
				status.setNewShip(true);
				lastShipTime = System.currentTimeMillis();

				// play ship explosion sound
				soundMan.playShipExplosionSound();
				// play asteroid explosion sound
				soundMan.playAsteroidExplosionSound();
			}
		}


		// draw bullets
		for(int i=0; i<bullets.size(); i++){
			Bullet bullet = bullets.get(i);
			graphicsMan.drawBullet(bullet, g2d, this);

			boolean remove = gameLogic.moveBullet(bullet);
			if(remove){
				bullets.remove(i);
				i--;
			}
		}

		// check bullet-asteroid collisions
		for(int i=0; i<bullets.size(); i++){
			Bullet bullet = bullets.get(i);
			if(asteroid.intersects(bullet)){
				// increase asteroids destroyed count
				status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 1);
				status.setScore(status.getScore() + 100);

				// "remove" asteroid
				asteroidExplosion = new Rectangle(
						asteroid.x,
						asteroid.y,
						asteroid.width,
						asteroid.height);
				asteroid.setLocation(-asteroid.width, -asteroid.height);
				status.setNewAsteroid(true);
				lastAsteroidTime = System.currentTimeMillis();

				// play asteroid explosion sound
				graphicsMan.drawAsteroidExplosion(asteroidExplosion, g2d, this);
				soundMan.playShipExplosionSound();


				// remove bullet
				bullets.remove(i);
				break;
			}
		}


		if(status.numLevel() == 2 || status.numLevel() == 3 || status.numLevel() == 4){
			for(int i=0; i<bullets.size(); i++){
				Bullet bullet = bullets.get(i);
				if(secondAsteroid.intersects(bullet)){
					status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 1);
					// increase asteroids destroyed count
					status.setScore(status.getScore() + 100);
					asteroidsDestroyed ++;

					// "remove" asteroid
					asteroidExplosion = new Rectangle(
							secondAsteroid.x,
							secondAsteroid.y,
							secondAsteroid.width,
							secondAsteroid.height);
					secondAsteroid.setLocation(-secondAsteroid.width, -secondAsteroid.height);
					status.setNewSecondAsteroid(true);
					lastSecondAsteroidTime = System.currentTimeMillis();
					graphicsMan.drawAsteroidExplosion(asteroidExplosion, g2d, this);
					soundMan.playShipExplosionSound();
					// remove bullet
					bullets.remove(i);
					break;
				}
			}
		}

		//Draw boss spaceship level 4
		if(status.numLevel() == 4){
			//Draw enemy spaceship
			if(!status.isBossShip()){
				// draw the enemy Spaceship until it reaches the bottom of the screen

				if(bossBullets.getY() + bossBullets.getSpeed() < this.getHeight()){
					bossBullets.translate(0, bossBullets.getSpeed());
					graphicsMan.drawEnemyBullet(bossBullets, g2d, this);

				}
				else{
					bossBullets.setLocation((int)bossShip.getCenterX(), (int)bossShip.getCenterY());
					soundMan.playBulletSound();
				}

				graphicsMan.drawBossShip(bossShip, g2d, this);
				if(!endBoss){
					bossShip.translate(bossShip.getSpeed(), 0);
					if (bossShip.getX()+bossShip.width >= this.getWidth()){

						endBoss = true;
					}
				}

				if(endBoss) {

					bossShip.translate( -bossShip.getSpeed(), 0);
					if (bossShip.getX() <= 0){
						endBoss = false;
					}
				}


			}
			else{
				long currentTime = System.currentTimeMillis();
				if((currentTime - lastBossTime) > NEW_BOSS_DELAY){
					// draw a new enemy Spaceship
					lastBossTime = currentTime;
					status.setNewBossEnemySpaceship(false);
					bossShip.setLocation(1, 0);
				}
				else{
					// draw explosion
					graphicsMan.drawEnemyShipExplosion(shipExplosion, g2d, this);
					status.setBossDestroyed(true);

				}
			}

		}
		if( status.numLevel() == 4){
			for(int i=0; i<bullets.size(); i++){
				Bullet bullet = bullets.get(i);
				if(bossShip.intersects(bullet)){
					// increase asteroids destroyed count
					status.setBossLife(status.getBossLife() + 1);
					bullets.remove(i);
					soundMan.playShipExplosionSound();
					break;
				}
			}


		}



		if(status.numLevel() == 3 ){

			if(enemyBullets.intersects(ship)){
				// decrease number of ships left
				status.setShipsLeft(status.getShipsLeft() - 1);
				status.setScore(status.getScore() + 100);
				ships--;


				// "remove" ship
				shipExplosion = new Rectangle(
						ship.x,
						ship.y,
						ship.width,
						ship.height);
				ship.setLocation(this.getWidth() + ship.width, ship.height);
				status.setNewShip(true);
				lastShipTime = System.currentTimeMillis();

				// play ship explosion sound
				soundMan.playShipExplosionSound();

			}
		}

		if(status.numLevel() == 3 ){
			for(int i=0; i<bullets.size(); i++){
				Bullet bullet = bullets.get(i);
				if(enemyShip.intersects(bullet)){
					status.setEnemyShipsDestroyed(status.getEnemyShipDestroyed() + 1);
					// increase asteroids destroyed count
					status.setScore(status.getScore() + 250);

					// "remove" asteroid
					enemyShipExplosion = new Rectangle(
							enemyShip.x,
							enemyShip.y,
							enemyShip.width,
							enemyShip.height);
					enemyShip.setLocation(-enemyShip.width, -enemyShip.height);
					status.setNewEnemyShip(true);
					lastEnemyShipTime = System.currentTimeMillis();
					graphicsMan.drawAsteroidExplosion(asteroidExplosion, g2d, this);
					soundMan.playShipExplosionSound();
					// remove bullet
					bullets.remove(i);
					break;
				}
			}
		}


		if (status.numLevel() == 3){

			//enemy collision with ship
			if(enemyShip.intersects(ship)){
				// decrease number of ships left
				status.setShipsLeft(status.getShipsLeft() - 1);

				// "remove" asteroid
				enemyShipExplosion = new Rectangle(
						enemyShip.x,
						enemyShip.y,
						enemyShip.width,
						enemyShip.height);
				enemyShip.setLocation(-enemyShip.width, -enemyShip.height);
				status.setNewEnemyShip(true);
				lastEnemyShipTime = System.currentTimeMillis();

				// "remove" ship
				shipExplosion = new Rectangle(
						ship.x,
						ship.y,
						ship.width,
						ship.height);
				ship.setLocation(this.getWidth() + ship.width, -ship.height);
				status.setNewShip(true);
				lastEnemyShipTime = System.currentTimeMillis();

				// play ship explosion sound
				soundMan.playShipExplosionSound();
				// play asteroid explosion sound
			}

		}



		// update asteroids destroyed label
		destroyedValueLabel.setText(Long.toString(status.getAsteroidsDestroyed()));

		// update ships left label
		shipsValueLabel.setText(Integer.toString(status.getShipsLeft()));

		//update score
		scoreValueLabel.setText(Integer.toString(status.getScore()));


		if( status.numLevel() == 4){
			if(bossBullets.intersects(ship)){
				// decrease number of ships left
				status.setShipsLeft(status.getShipsLeft() - 1);
				status.setScore(status.getScore() + 100);
				ships--;


				// "remove" ship
				shipExplosion = new Rectangle(
						ship.x,
						ship.y,
						ship.width,
						ship.height);
				ship.setLocation(this.getWidth() + ship.width, ship.height);
				status.setNewShip(true);
				lastShipTime = System.currentTimeMillis();

				// play ship explosion sound
				soundMan.playShipExplosionSound();

			}
		}



		if(status.numLevel() == 2 || status.numLevel() == 3 || status.numLevel() == 4){
			for(int i=0; i<bullets.size(); i++){
				Bullet bullet = bullets.get(i);
				if(secondAsteroid.intersects(bullet)){
					status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 1);
					// increase asteroids destroyed count
					status.setScore(status.getScore() + 100);
					asteroidsDestroyed ++;

					// "remove" asteroid
					asteroidExplosion = new Rectangle(
							secondAsteroid.x,
							secondAsteroid.y,
							secondAsteroid.width,
							secondAsteroid.height);
					secondAsteroid.setLocation(-secondAsteroid.width, -secondAsteroid.height);
					status.setNewSecondAsteroid(true);
					lastSecondAsteroidTime = System.currentTimeMillis();
					graphicsMan.drawAsteroidExplosion(asteroidExplosion, g2d, this);
					soundMan.playShipExplosionSound();
					// remove bullet
					bullets.remove(i);
					break;
				}
			}
		}
		// draw ship
		if(!status.isNewShip()){
			// draw it in its current location
			graphicsMan.drawShip(ship, g2d, this);
		}

		else{
			// draw a new one
			long currentTime = System.currentTimeMillis();
			if((currentTime - lastShipTime) > NEW_SHIP_DELAY){
				lastShipTime = currentTime;
				status.setNewShip(false);
				ship = gameLogic.newShip(this);
			}
			else{
				// draw explosion
				graphicsMan.drawShipExplosion(asteroidExplosion, g2d, this);
			}
		}

		// check ship-asteroid collisions
		if(asteroid.intersects(ship)){
			// decrease number of ships left
			status.setShipsLeft(status.getShipsLeft() - 1);

			status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 1);

			// "remove" asteroid
			asteroidExplosion = new Rectangle(
					asteroid.x,
					asteroid.y,
					asteroid.width,
					asteroid.height);
			asteroid.setLocation(-asteroid.width, -asteroid.height);
			status.setNewAsteroid(true);
			soundMan.playAsteroidExplosionSound();
			lastAsteroidTime = System.currentTimeMillis();


			// "remove" ship
			shipExplosion = new Rectangle(
					ship.x,
					ship.y,
					ship.width,
					ship.height);
			ship.setLocation(this.getWidth() + ship.width, -ship.height);
			status.setNewShip(true);
			lastShipTime = System.currentTimeMillis();

			// play ship explosion sound
			soundMan.playShipExplosionSound();
			// play asteroid explosion sound
			soundMan.playAsteroidExplosionSound();
		}

		// update asteroids destroyed label
		destroyedValueLabel.setText(Long.toString(status.getAsteroidsDestroyed()));

		// update ships left label
		shipsValueLabel.setText(Integer.toString(status.getShipsLeft()));

		//update score
		scoreValueLabel.setText(Integer.toString(status.getScore()));

	}

	private void drawLevel2() {

		String readyStr = "Press Space to begin Level 2";
		g2d.setFont(originalFont.deriveFont(originalFont.getSize2D() + 1));
		FontMetrics fm = g2d.getFontMetrics();
		int ascent = fm.getAscent();
		int strWidth = fm.stringWidth(readyStr);
		int strX = (this.getWidth() - strWidth)/2;
		int strY = (this.getHeight() + ascent)/2;
		g2d.setPaint(Color.WHITE);
		g2d.drawString(readyStr, strX, strY);

	}

	private void drawLevel3() {

		String readyStr = "Press Space to begin Level 3";
		g2d.setFont(originalFont.deriveFont(originalFont.getSize2D() + 1));
		FontMetrics fm = g2d.getFontMetrics();
		int ascent = fm.getAscent();
		int strWidth = fm.stringWidth(readyStr);
		int strX = (this.getWidth() - strWidth)/2;
		int strY = (this.getHeight() + ascent)/2;
		g2d.setPaint(Color.WHITE);
		g2d.drawString(readyStr, strX, strY);

	}

	private void drawLevel4() {
		String gameTitleStr = "FINAL LEVEL";

		Font currentFont = biggestFont == null? bigFont : biggestFont;
		float fontSize = currentFont.getSize2D();
		bigFont = currentFont.deriveFont(fontSize + 1).deriveFont(Font.BOLD).deriveFont(Font.ITALIC);
		FontMetrics fm = g2d.getFontMetrics(bigFont);
		int strWidth = fm.stringWidth(gameTitleStr);
		if(strWidth > this.getWidth() - 10){
			bigFont = currentFont;
			biggestFont = currentFont;
			fm = g2d.getFontMetrics(currentFont);
			strWidth = fm.stringWidth(gameTitleStr);
		}
		g2d.setFont(bigFont);
		int ascent = fm.getAscent();
		int strX = (this.getWidth() - strWidth)/2;
		int strY = (this.getHeight() + ascent)/2 - ascent;
		g2d.setPaint(Color.RED);
		g2d.drawString(gameTitleStr, strX, strY);

		g2d.setFont(originalFont);
		fm = g2d.getFontMetrics();
		String newGameStr = "Press <Space> to Start";
		strWidth = fm.stringWidth(newGameStr);
		strX = (this.getWidth() - strWidth)/2;
		strY = (this.getHeight() + fm.getAscent())/2 + ascent + 16;
		g2d.setPaint(Color.red);
		g2d.drawString(newGameStr, strX, strY);
	}


	/**
	 * Draws the "Game Over" message.
	 */
	private void drawGameOver() {
		String gameOverStr = "GAME OVER";
		Font currentFont = biggestFont == null? bigFont : biggestFont;
		float fontSize = currentFont.getSize2D();
		bigFont = currentFont.deriveFont(fontSize + 1).deriveFont(Font.BOLD);
		FontMetrics fm = g2d.getFontMetrics(bigFont);
		int strWidth = fm.stringWidth(gameOverStr);
		if(strWidth > this.getWidth() - 10){
			biggestFont = currentFont;
			bigFont = biggestFont;
			fm = g2d.getFontMetrics(bigFont);
			strWidth = fm.stringWidth(gameOverStr);
		}
		int ascent = fm.getAscent();
		int strX = (this.getWidth() - strWidth)/2;
		int strY = (this.getHeight() + ascent)/2;
		g2d.setFont(bigFont);
		g2d.setPaint(Color.WHITE);
		g2d.drawString(gameOverStr, strX, strY);



	}

	/**
	 * Draws the initial "Get Ready!" message.
	 */
	private void drawGetReady() {
		String readyStr = "Get Ready!";
		g2d.setFont(originalFont.deriveFont(originalFont.getSize2D() + 1));
		FontMetrics fm = g2d.getFontMetrics();
		int ascent = fm.getAscent();
		int strWidth = fm.stringWidth(readyStr);
		int strX = (this.getWidth() - strWidth)/2;
		int strY = (this.getHeight() + ascent)/2;
		g2d.setPaint(Color.WHITE);
		g2d.drawString(readyStr, strX, strY);

	}



	/**
	 * Draws the specified number of stars randomly on the game screen.
	 * @param numberOfStars the number of stars to draw
	 */
	private void drawStars(int numberOfStars) {
		g2d.setColor(Color.WHITE);
		for(int i=0; i<numberOfStars; i++){
			int x = (int)(Math.random() * this.getWidth());
			int y = (int)(Math.random() * this.getHeight());
			g2d.drawLine(x, y, x, y);

		}
	}

	/**
	 * Display initial game title screen.
	 */
	private void initialMessage() {
		String gameTitleStr = "Void Space";


		Font currentFont = biggestFont == null? bigFont : biggestFont;
		float fontSize = currentFont.getSize2D();
		bigFont = currentFont.deriveFont(fontSize + 1).deriveFont(Font.BOLD).deriveFont(Font.ITALIC);
		FontMetrics fm = g2d.getFontMetrics(bigFont);
		int strWidth = fm.stringWidth(gameTitleStr);
		if(strWidth > this.getWidth() - 10){
			bigFont = currentFont;
			biggestFont = currentFont;
			fm = g2d.getFontMetrics(currentFont);
			strWidth = fm.stringWidth(gameTitleStr);
		}
		g2d.setFont(bigFont);
		int ascent = fm.getAscent();
		int strX = (this.getWidth() - strWidth)/2;
		int strY = (this.getHeight() + ascent)/2 - ascent;
		g2d.setPaint(Color.YELLOW);
		g2d.drawString(gameTitleStr, strX, strY);

		g2d.setFont(originalFont);
		fm = g2d.getFontMetrics();
		String newGameStr = "Press <Space> to Start a New Game.";
		strWidth = fm.stringWidth(newGameStr);
		strX = (this.getWidth() - strWidth)/2;
		strY = (this.getHeight() + fm.getAscent())/2 + ascent + 16;
		g2d.setPaint(Color.WHITE);
		g2d.drawString(newGameStr, strX, strY);

		fm = g2d.getFontMetrics();
		String exitGameStr = "Press <Esc> to Exit the Game.";
		strWidth = fm.stringWidth(exitGameStr);
		strX = (this.getWidth() - strWidth)/2;
		strY = strY + 16;
		g2d.drawString(exitGameStr, strX, strY);




	}

	private void YouWin() {
		String gameTitleStr = "WINNER";



		Font currentFont = biggestFont == null? bigFont : biggestFont;
		float fontSize = currentFont.getSize2D();
		bigFont = currentFont.deriveFont(fontSize + 1).deriveFont(Font.BOLD).deriveFont(Font.ITALIC);
		FontMetrics fm = g2d.getFontMetrics(bigFont);
		int strWidth = fm.stringWidth(gameTitleStr);
		if(strWidth > this.getWidth() - 10){
			bigFont = currentFont;
			biggestFont = currentFont;
			fm = g2d.getFontMetrics(currentFont);
			strWidth = fm.stringWidth(gameTitleStr);
		}
		g2d.setFont(bigFont);
		int ascent = fm.getAscent();
		int strX = (this.getWidth() - strWidth)/2;
		int strY = (this.getHeight() + ascent)/2 - ascent;
		g2d.setPaint(Color.YELLOW);
		g2d.drawString(gameTitleStr, strX, strY);

		g2d.setFont(originalFont);
		fm = g2d.getFontMetrics();
		String newGameStr = "Press <Space> to Start a New Game.";
		strWidth = fm.stringWidth(newGameStr);
		strX = (this.getWidth() - strWidth)/2;
		strY = (this.getHeight() + fm.getAscent())/2 + ascent + 16;
		g2d.setPaint(Color.WHITE);
		g2d.drawString(newGameStr, strX, strY);

		fm = g2d.getFontMetrics();
		String exitGameStr = "Press <Esc> to Exit the Game.";
		strWidth = fm.stringWidth(exitGameStr);
		strX = (this.getWidth() - strWidth)/2;
		strY = strY + 16;
		g2d.drawString(exitGameStr, strX, strY);


	}
	/**
	 * Prepare screen for game over.
	 */
	public void doGameOver(){
		shipsValueLabel.setForeground(new Color(128, 0, 0));



	}

	/**
	 * Prepare screen for a new game.
	 */
	public void doNewGame(){		
		lastAsteroidTime = -NEW_ASTEROID_DELAY;
		lastShipTime = -NEW_SHIP_DELAY;

		bigFont = originalFont;
		biggestFont = null;

		// set labels' text
		shipsValueLabel.setForeground(Color.BLACK);
		shipsValueLabel.setText(Integer.toString(status.getShipsLeft()));
		destroyedValueLabel.setText(Long.toString(status.getAsteroidsDestroyed()));

		scoreValueLabel.setForeground(Color.BLACK);
		scoreValueLabel.setText(Integer.toString(status.getScore()));

		if (status.numLevel()==1) {
			soundMan.BackgroundMusic1();
		}




	}
	
	

	/**
	 * Sets the game graphics manager.
	 * @param graphicsMan the graphics manager
	 */
	public void setGraphicsMan(GraphicsManager graphicsMan) {
		this.graphicsMan = graphicsMan;

	}

	/**
	 * Sets the game logic handler
	 * @param gameLogic the game logic handler
	 */
	public void setGameLogic(GameLogic gameLogic) {
		this.gameLogic = gameLogic;
		this.status = gameLogic.getStatus();
		this.soundMan = gameLogic.getSoundMan();

	}

	/**
	 * Sets the label that displays the value for asteroids destroyed.
	 * @param destroyedValueLabel the label to set
	 */
	public void setDestroyedValueLabel(JLabel destroyedValueLabel) {
		this.destroyedValueLabel = destroyedValueLabel;
	}

	/**
	 * Sets the label that displays the value for ship (lives) left
	 * @param shipsValueLabel the label to set
	 */
	public void setShipsValueLabel(JLabel shipsValueLabel) {
		this.shipsValueLabel = shipsValueLabel;
	}

	public void setScoreValueLabel(JLabel scoreValueLabel) {
		this.scoreValueLabel  = scoreValueLabel;
	}
}
