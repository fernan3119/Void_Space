package rbadia.voidspace.sounds;

import java.applet.Applet;
import java.applet.AudioClip;

import rbadia.voidspace.main.GameScreen;

/**
 * Manages and plays the game's sounds.
 */
public class SoundManager {
	private static final boolean SOUND_ON = true;

    private AudioClip shipExplosionSound = Applet.newAudioClip(GameScreen.class.getResource(
    "/rbadia/voidspace/sounds/shipExplosion.wav"));
    private AudioClip bulletSound = Applet.newAudioClip(GameScreen.class.getResource(
    "/rbadia/voidspace/sounds/laser.wav"));
    
    private AudioClip backgroundMusic1 = Applet.newAudioClip(GameScreen.class.getResource(
    	    "/rbadia/voidspace/sounds/The Incredibles Theme 8 bit.wav"));
    private AudioClip backgroundMusic2 = Applet.newAudioClip(GameScreen.class.getResource(
    	    "/rbadia/voidspace/sounds/Gangnam Style (8-Bit NES Remix).wav"));
    private AudioClip backgroundMusic3 = Applet.newAudioClip(GameScreen.class.getResource(
    	    "/rbadia/voidspace/sounds/Sparkman Stage - Super Smash Bros. 3DS.wav"));
    private AudioClip backgroundMusic4 = Applet.newAudioClip(GameScreen.class.getResource(
    	    "/rbadia/voidspace/sounds/lol.wav"));
    private AudioClip backgroundMusic5 = Applet.newAudioClip(GameScreen.class.getResource(
    	    "/rbadia/voidspace/sounds/Slipknot - Before I Forget - 8-bit remix.wav"));
    private AudioClip backgroundMusic6 = Applet.newAudioClip(GameScreen.class.getResource(
    	    "/rbadia/voidspace/sounds/Storm Eagle 8 Bit - Mega Man X.wav"));
    
    
    /**
     * Plays sound for bullets fired by the ship.
     */
    public void playBulletSound(){
    	if(SOUND_ON){
    		new Thread(new Runnable(){
    			public void run() {
    				bulletSound.play();
    				//bulletSound
    			}
    		}).start();
    	}
    }
    
    /**
     * Plays sound for ship explosions.
     */
    public void playShipExplosionSound(){
    	if(SOUND_ON){
    		new Thread(new Runnable(){
    			public void run() {
    				shipExplosionSound.play();
    			}
    		}).start();
    	}
    }
    
    /**
     * Plays sound for asteroid explosions.
     */
    public void playAsteroidExplosionSound(){
		// play sound for asteroid explosions
    	if(SOUND_ON){
    		
    	}
    	
    }
    	 public void BackgroundMusic1(){
    			if(SOUND_ON){
    	    		new Thread(new Runnable(){
    	    			public void run() {
    	    				backgroundMusic1.loop();
    	    			}
    	    		}).start();
    	    	}
    	
    }
    	
      	 public void BackgroundMusic2(){
 			if(SOUND_ON){
 	    		new Thread(new Runnable(){
 	    			public void run() {
 	    				backgroundMusic2.loop();
 	    			}
 	    		}).start();
 	    	}
 	
 }
      	 
      	 public void BackgroundMusic3(){
  			if(SOUND_ON){
  	    		new Thread(new Runnable(){
  	    			public void run() {
  	    				backgroundMusic3.loop();
  	    			}
  	    		}).start();
  	    	}
  	
  }
      	 
      	public void BackgroundMusic4(){
  			if(SOUND_ON){
  	    		new Thread(new Runnable(){
  	    			public void run() {
  	    				backgroundMusic4.loop();
  	    			}
  	    		}).start();
  	    	}
      	}
  			public void BackgroundMusic5(){
  	  			if(SOUND_ON){
  	  	    		new Thread(new Runnable(){
  	  	    			public void run() {
  	  	    				backgroundMusic5.loop();
  	  	    			}
  	  	    		}).start();
  	  	    	}
  	  	
  	  }
  			public void BackgroundMusic6(){
  	  			if(SOUND_ON){
  	  	    		new Thread(new Runnable(){
  	  	    			public void run() {
  	  	    				backgroundMusic6.loop();
  	  	    			}
  	  	    		}).start();
  	  	    	}
  	  			}
  			
  			 public void StopMusic(){
  	 			if(SOUND_ON){
  	 	    		new Thread(new Runnable(){
  	 	    			public void run() {
  	 	    				backgroundMusic1.stop();
  	 	    			}
  	 	    		}).start();
  	 	    	}
  	 			
  	 			
  	 	
  	 }
  	    	 
  	    	 
  	    	 public void StopMusic2(){
  	  			if(SOUND_ON){
  	  	    		new Thread(new Runnable(){
  	  	    			public void run() {
  	  	    				backgroundMusic2.stop();
  	  	    			}
  	  	    		}).start();
  	  	    	}
  	    	 }
  	    	 
  	  			 public void StopMusic3(){
  	  	 			if(SOUND_ON){
  	  	 	    		new Thread(new Runnable(){
  	  	 	    			public void run() {
  	  	 	    				backgroundMusic3.stop();
  	  	 	    			}
  	  	 	    		}).start();
  	  	 	    	}
  	  	 		}
  	  	 			
  	  	 		 public void StopMusic4(){
  	  	 			if(SOUND_ON){
  	  	 	    		new Thread(new Runnable(){
  	  	 	    			public void run() {
  	  	 	    				backgroundMusic4.stop();
  	  	 	    			}
  	  	 	    		}).start();
  	  	 	    	}
  	  	 		 }
  	  	 			
  	  	 		 public void StopMusic5(){
  	  	 			if(SOUND_ON){
  	  	 	    		new Thread(new Runnable(){
  	  	 	    			public void run() {
  	  	 	    				backgroundMusic5.stop();
  	  	 	    			}
  	  	 	    		}).start();
  	  	 	    	}
  	  	 			
  	  	 		 }
  	  	 		 
  	  	 	 public void StopMusic6(){
	  	 			if(SOUND_ON){
	  	 	    		new Thread(new Runnable(){
	  	 	    			public void run() {
	  	 	    				backgroundMusic6.stop();
	  	 	    			}
	  	 	    		}).start();
	  	 	    	}
	  	 			
	  	 		 }
  }
	
