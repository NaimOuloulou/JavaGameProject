/**
 * 
 */
package main;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * @author bouab
 *
 */
public class Personnage {
	private float posX = 500, posY = 300;
	private int direction = 0;
	private boolean moving = false;
	private boolean onStair;
	private Animation[] animations = new Animation[8];
	private Map map;

	public Personnage(Map map) {
		this.map = map;
	}
	
	public void init() throws SlickException {
		SpriteSheet spriteSheet = new SpriteSheet("sprites/sprite01.png", 64, 64);
		this.animations[0] = loadAnimation(spriteSheet, 0, 1, 8);
	    this.animations[1] = loadAnimation(spriteSheet, 0, 1, 9);
	    this.animations[2] = loadAnimation(spriteSheet, 0, 1, 10);
	    this.animations[3] = loadAnimation(spriteSheet, 0, 1, 11);
	    this.animations[4] = loadAnimation(spriteSheet, 1, 9, 8);
	    this.animations[5] = loadAnimation(spriteSheet, 1, 9, 9);
	    this.animations[6] = loadAnimation(spriteSheet, 1, 9, 10);
	    this.animations[7] = loadAnimation(spriteSheet, 1, 9, 11);
	}
	
	private Animation loadAnimation(SpriteSheet spriteSheet, int startX, int endX, int y) {
	    Animation animation = new Animation();
	    for (int x = startX; x < endX; x++) {
	        animation.addFrame(spriteSheet.getSprite(x, y), 100);
	    }
	    return animation;
	}
	
	public void render(Graphics arg1) throws SlickException {
		arg1.setColor(new Color(0, 0, 0, .5f));
	    arg1.fillOval(posX - 16, posY - 8, 32, 16);
		arg1.drawAnimation(animations[direction + (moving ? 4 : 0)], posX-32, posY-60);
	}
	
	public void update(int delta) {
		  if (this.moving) {
		    float futurX = getFuturX(delta);
		    float futurY = getFuturY(delta);
		    boolean collision = this.map.isCollision(futurX, futurY);
		    if (collision){
		      this.moving = false;
		    } else {
		      this.posX = futurX;
		      this.posY = futurY;
		    }
		  }
		}

		private float getFuturX(int delta) {
		  float futurX = this.posX;
		  switch (this.direction) {
		  case 1: futurX = this.posX - .1f * delta; break;
		  case 3: futurX = this.posX + .1f * delta; break;
		  }
		  return futurX;
		}

		private float getFuturY(int delta) {
		  float futurY = this.posY;
		  switch (this.direction) {
		  case 0: futurY = this.posY - .1f * delta; break;
		  case 2: futurY = this.posY + .1f * delta; break;
		  case 1: if (this.onStair) {
		            futurY = this.posY + .1f * delta;
		          } break;
		  case 3: if (this.onStair) {
		            futurY = this.posY - .1f * delta;
		          } break;
		  }
		  return futurY;
		}
		
		public float getX() { return posX; }
		public void setX(float x) { this.posX = x; }
		public float getY() { return posY; }
		public void setY(float y) { this.posY = y; }
		public int getDirection() { return direction; }
		public void setDirection(int direction) { this.direction = direction; }
		public boolean isMoving() { return moving; }
		public void setMoving(boolean moving) { this.moving = moving; }
		public boolean isOnStair() { return onStair; }
		public void setOnStair(boolean onStair) { this.onStair = onStair; }
}
