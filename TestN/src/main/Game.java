package main;
//import org.newdawn.slick.*;
import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.tiled.TiledMap;


//import org.newdawn.
/**
 * 
 */

/**
 * @author bouab
 *
 */
public class Game extends BasicGame {
	
	private GameContainer container;
	private Map map  = new Map();
	private Personnage player = new Personnage(map);
	/*private TiledMap map;
	private float x = 500, y = 300;
	private int direction = 0;
	private boolean moving = false;
	private boolean onStair;
	private Animation[] animations = new Animation[8];*/
	
	

	public Game() {
        super("Game");
    }
	
	public static void main(String[] args) throws SlickException {
        new AppGameContainer(new Game(),576, 576, false).start();
    }
	
	/* (non-Javadoc)
	 * @see org.newdawn.slick.Game#render(org.newdawn.slick.GameContainer, org.newdawn.slick.Graphics)
	 */
	@Override
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		// TODO Auto-generated method stub
		/*this.map.render(0, 0, 0);
		arg1.setColor(new Color(0, 0, 0, .5f));
	    arg1.fillOval(x - 16, y - 8, 32, 16);
		arg1.drawAnimation(animations[direction + (moving ? 4 : 0)], x-32, y-60);*/
		this.map.renderBackground();
		  this.player.render(arg1);
		  this.map.renderForeground();
		
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.BasicGame#init(org.newdawn.slick.GameContainer)
	 */
	@Override
	public void init(GameContainer arg0) throws SlickException {
		// TODO Auto-generated method stub
		//this.container = arg0;
		/*this.map = new TiledMap("map/macartee.tmx");
		SpriteSheet spriteSheet = new SpriteSheet("sprites/sprite01.png", 64, 64);
		this.animations[0] = loadAnimation(spriteSheet, 0, 1, 8);
	    this.animations[1] = loadAnimation(spriteSheet, 0, 1, 9);
	    this.animations[2] = loadAnimation(spriteSheet, 0, 1, 10);
	    this.animations[3] = loadAnimation(spriteSheet, 0, 1, 11);
	    this.animations[4] = loadAnimation(spriteSheet, 1, 9, 8);
	    this.animations[5] = loadAnimation(spriteSheet, 1, 9, 9);
	    this.animations[6] = loadAnimation(spriteSheet, 1, 9, 10);
	    this.animations[7] = loadAnimation(spriteSheet, 1, 9, 11);*/
		
		this.container = arg0;
		this.map.init(); 
		this.player.init();
	    Music background = new Music("sound/Harry Potter Theme Song - YouTube.ogg");
	    background.loop();
	}
	
	/*private Animation loadAnimation(SpriteSheet spriteSheet, int startX, int endX, int y) {
	    Animation animation = new Animation();
	    for (int x = startX; x < endX; x++) {
	        animation.addFrame(spriteSheet.getSprite(x, y), 100);
	    }
	    return animation;
	}*/

	/* (non-Javadoc)
	 * @see org.newdawn.slick.BasicGame#update(org.newdawn.slick.GameContainer, int)
	 */
	@Override
	public void update(GameContainer arg0, int delta) throws SlickException {
		// TODO Auto-generated method stub
			updateTrigger();
			//updateCharacter(delta);
			this.player.update(delta);
			
		}
		
		private void updateTrigger() throws SlickException {
			this.player.setOnStair(false);
			for (int objectID = 0; objectID < this.map.getObjectCount(); objectID++) {
				//System.out.println(isInTrigger(objectID));
				if (isInTrigger(objectID)) {
					if ("teleport".equals(this.map.getObjectType(objectID))) {
												//this.map.render(0, 0, 0);
						teleport(objectID);
						this.map.changeMap("map/macartee3.tmx");

						
					} else if ("stair".equals(this.map.getObjectType(objectID))) {
						this.player.setOnStair(true);
					}
				}
			}
		}
		
	/*	private void updateCharacter(int delta) {
			if (this.moving) {
				float futurX = getFuturX(delta);
				float futurY = getFuturY(delta);
				boolean collision = isCollision(futurX, futurY);
				if (collision) {
					this.moving = false;
				} else {
					this.x = futurX;
					this.y = futurY;
				}
			}
		}*/
		
		private boolean isInTrigger(int id) {
			return this.player.getX() > this.map.getObjectX(id)
					&& this.player.getX() < this.map.getObjectX(id) + this.map.getObjectWidth(id)
					&& this.player.getY() > this.map.getObjectY(id)
					&& this.player.getY() < this.map.getObjectY(id) + this.map.getObjectHeight(id);		
		}

		private void teleport(int objectID) {
			/*System.out.println("destX = " +this.map.getObjectProperty(0, objectID, "dest-x",
					Float.toString(x)));*/
			/*System.out.println("destY = " +this.map.getObjectProperty(0, objectID, "dest-y",
					Float.toString(y)));*/
			this.player.setX(Float.parseFloat(this.map.getObjectProperty(objectID, "dest-x",
					Float.toString(player.getX()))));
			this.player.setY(Float.parseFloat(this.map.getObjectProperty(objectID, "dest-y",
					Float.toString(player.getY()))));
		}

	
	/*private boolean isCollision(float x, float y) {
	    int tileW = this.map.getTileWidth();
	    int tileH = this.map.getTileHeight();
	    int logicLayer = this.map.getLayerIndex("Calque 2");
	    Image tile = this.map.getTileImage((int) x / tileW, (int) y / tileH, logicLayer);
	    boolean collision = tile != null;
	    if (collision) {
	        Color color = tile.getColor((int) x % tileW, (int) y % tileH);
	        collision = color.getAlpha() > 0;
	    }
	    return collision;
	}

	private float getFuturX(int delta) {
	    float futurX = this.x;
	    switch (this.direction) {
	    case 1: futurX = this.x - .1f * delta; break;
	    case 3: futurX = this.x + .1f * delta; break;
	    }
	    return futurX;
	}

	private float getFuturY(int delta) {
	    float futurY = this.y;
	    switch (this.direction) {
	    case 0: futurY = this.y - .1f * delta; break;
	    case 2: futurY = this.y + .1f * delta; break;
	    }
	    return futurY;
	}
	*/
	 @Override
	    public void keyReleased(int key, char c) {
		 	this.player.setMoving(false);
	        if (Input.KEY_ESCAPE == key) {
	            container.exit();
	        }
	    }
	 
	 @Override
	 public void keyPressed(int key, char c) {
	     switch (key) {
	         case Input.KEY_UP:    this.player.setDirection(0); this.player.setMoving(true);break;
	         case Input.KEY_LEFT:  this.player.setDirection(1); this.player.setMoving(true);break;
	         case Input.KEY_DOWN:  this.player.setDirection(2); this.player.setMoving(true);break;
	         case Input.KEY_RIGHT: this.player.setDirection(3); this.player.setMoving(true);break;
	     }
	 }

}
