package Main;

import java.awt.*;

public class Tower extends Rectangle {
    public Rectangle towerSquare;
    public int towerSquareSize = 130;
    
    public int groundID;
    public int airID;
    public int loseTime = 100, loseFrame = 0;
    
    public int shotMob = -1;
    
    public boolean shooting = false, freezing = false;
    
    public Tower(int x, int y, int width, int height, int groundID, int airID) {
        setBounds(x, y, width, height);
        towerSquare = new Rectangle(x - towerSquareSize/2, y - towerSquareSize/2, width + towerSquareSize, height + towerSquareSize);
        this.groundID = groundID;
        this.airID = airID;
    }
    
    public void draw(Graphics g) {
        g.drawImage(Screen.tileset_ground[groundID], x, y, width, height, null);
        
        if (airID != Bind.airAir) {
            g.drawImage(Screen.tileset_air[airID], x, y, width, height, null);
        }
        
    }
    
    public void physics() {
        if (shotMob != -1 && towerSquare.intersects(Screen.mobs[shotMob])) {
            shooting = true;
        } else {
            shooting = false;
        }
        
        if (!shooting) {
            if (airID == Bind.airTowerLaser) {
                for (int i = 0; i < Screen.mobs.length; i++) {
                    if (Screen.mobs[i].inGame) {
                        if (towerSquare.intersects(Screen.mobs[i])) {
                            shooting = true;
 
                            shotMob = i;
                        }
                    }
                }
            }            else if (airID == Bind.towerFreeze) {
                for (int i = 0; i < Screen.mobs.length; i++) {
                    if (Screen.mobs[i].inGame) {
                        if (towerSquare.intersects(Screen.mobs[i])) {
                            freezing = true;
 
                            shotMob = i;
                        }
                    }
                }
            }
        }
        
        if (shooting) {
            if (loseFrame >= loseTime) {
                Screen.mobs[shotMob].loseHealth(4);
                loseFrame = 0;
            }
      
            
            else {
                loseFrame += 1;
            }
            
            if (Screen.mobs[shotMob].isDeath()) {
                shooting = false;
                shotMob = -1;
            }
        }
        if (freezing) {
            if (loseFrame >= loseTime) {
                Screen.mobs[shotMob].freeze(0.2);
                Screen.mobs[shotMob].loseHealth(1);
                loseFrame = 0;
            } 
            
        }
        

    }
    
    public void getMoney(int mobID) {
        Screen.coins += Bind.deathReward[mobID];
    }
    
    public void attack(Graphics g) {
        
        if (shooting) {
            g.setColor(new Color(255, 255, 0));
            g.drawLine(x + width/2, y + height/2, Screen.mobs[shotMob].x + Screen.mobs[shotMob].width/2, Screen.mobs[shotMob].y + Screen.mobs[shotMob].height/2);
        }
    }
}
