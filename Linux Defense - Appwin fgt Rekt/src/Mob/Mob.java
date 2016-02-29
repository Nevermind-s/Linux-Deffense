package Mob;
import Main.Screen;

import Main.Bind;
import java.awt.*;

public class Mob extends Rectangle {
	
	private static final long serialVersionUID = 1L;
	public int xC, yC;
    public int health;
    public int mobSize = 52;
    public int mobWalk = 0;
    public int upward = 0, downward = 1, right = 2, left = 3;
    public int direction = right;
    public int mobID = Bind.mobp; 
    public boolean inGame = false;
    public boolean hasUpward = false;
    public boolean hasDownward = false;
    public boolean hasRight = false;
    public boolean hasLeft = false;
    public int walkFrame = 0;
	double walkSpeed;
    
    public Mob() {}
    
    
    public void spawnMob(int mobID) {        
        for (int y = 0; y < Screen.game.tower.length; y++) {
            if (Screen.game.tower[y][0].groundID == Bind.groundRoad) {
                setBounds(Screen.game.tower[y][0].x, Screen.game.tower[y][0].y, mobSize, mobSize);
                xC = 0;
                yC = y;
            }
        }
        
        this.mobID = mobID;
        inGame = true;
        health = 70;
        
       if (mobID == 0) {
    	   walkSpeed = 7;
    	   health = 70;
		
	} else if (mobID == 1 ) {
		walkSpeed=20; 
		health = 100;

	}
    }
    

    public void physics() {
    	
        if (walkFrame >= walkSpeed) {
            if (direction == right) {
                x += 1;
            } else if (direction == left) {
                x -= 1;
            } else if (direction == upward) {
                y -= 1;
            } else if (direction == downward) {
                y += 1;
            }
           
           mobWalk += 1;
           if (mobWalk == Screen.game.blockSize) {
                if (direction == right) {
                    xC += 1;
                    hasRight = true;
                } else if (direction == left) {
                    xC -= 1;
                    hasLeft = true;
                } else if (direction == upward) {
                    yC -= 1;
                    hasUpward = true;
                } else if (direction == downward) {
                    yC += 1;
                    hasDownward = true;
                }
                
                try {
                    if (Screen.game.tower[yC+1][xC].groundID == Bind.groundRoad && !hasUpward) {
                        direction = downward;
                    } else if (Screen.game.tower[yC-1][xC].groundID == Bind.groundRoad && !hasDownward) {
                        direction = upward;
                    } else if (Screen.game.tower[yC][xC+1].groundID == Bind.groundRoad && !hasLeft) {
                        direction = right;
                    } else if (Screen.game.tower[yC][xC-1].groundID == Bind.groundRoad && !hasRight) {
                        direction = left;
                    }
                } catch (Exception e) { }
                
                if (Screen.game.tower[yC][xC].airID == 0) {
                    deleteCreep();
                    looseHealth(0);
                }
                
                hasRight = false;
                hasLeft = false;
                hasUpward = false;
                hasDownward = false;
                mobWalk = 0;
           }
           
           walkFrame = 0;
        } else {
            walkFrame += 1;
        }
        
    }
    
    public void deleteCreep() {
        inGame = false;
        health=0; 
        direction = right; 
        mobWalk = 0;
       
    }
    
    public void looseHealth(int mobID) {
    	if (mobID == 0) {
			 Screen.health -= 5;
		}
    	else 
    		Screen.health -= 20;
       
    }
    
    public void loseHealth(int damage) {
        health -= damage;
        checkDeath();
    }
    
    public void freeze (double freeze) {
        walkSpeed = walkSpeed + freeze;
        checkDeath();
    }
    
    public boolean isDeath() {
        return !inGame;
    }
    
    public void checkDeath() {
        if (health < 1) {
            deleteCreep();
            Screen.coins+=5;
        }
    }
    
    public void draw(Graphics g) {
    	if (mobID == 1) {
    		g.drawImage(Screen.tileset_creep[1], x, y, width, height, null);
    	}
    		
    	else 
    		g.drawImage(Screen.tileset_creep[0], x, y, width, height, null);
    		


        

        
        // draw health bar
        g.setColor(new Color(180, 50, 50));
        g.fillRect(x, y - 10, width, 3);

        // draw health
        g.setColor(new Color(50, 180, 50));
        g.fillRect(x, y - 10, health, 3);
        
        // draw outline for health baro;;
        g.setColor(new Color(0, 0, 0));
        g.drawRect(x, y - 10, health - 1, 2);
    }
}
