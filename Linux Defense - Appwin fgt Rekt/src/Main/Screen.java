package Main;

import Mob.Mob;

import java.awt.*;

import javax.swing.*;

import java.awt.image.*;
import java.io.*;

public class Screen extends JPanel implements Runnable {
	
    public class KeyTyped  {
    	public void keyESC () {
    		isPaused = false; 
    		millis = 0; 
    		System.out.println("Lol");
    	}
    	public void keySPACE () {
    		isPaused = true; 
    		millis = 1; 
    	}
    }
    
    public Thread thread = new Thread(this);
    
    public static Image[] tileset_ground = new Image[100];
    public static Image[] tileset_air = new Image[100];
    public static Image[] tileset_res = new Image[100];
    public static Image[] tileset_creep = new Image[100];
    
    public static boolean isFirst = true, isWin;
    public static boolean isDebug = true;
    public static int level = 1, maxLevel=4, lvlMob=1; 
    public static Point mse = new Point(0, 0);
    public static long millis = 0;
    
    public static int myWidth, myHeight;
    public static int health = 100;
    public static int coins = 10;
    
    public static Save save;
    public static Game game;
    public static HUD hUD;
    public static Mob[] mobs = new Mob[lvlMob*10];
    
    
    public Screen(Frame frame) {
        frame.addMouseListener(new KeyHandel());
        frame.addMouseMotionListener(new KeyHandel());
        frame.addKeyListener(new KeyHandel(this));
        
        thread.start();
    }
    
    public void define() {
        game = new Game();
        save = new Save();
        hUD = new HUD();
        
        for (int i = 0; i < tileset_ground.length; i++) {
            tileset_ground[i] = new ImageIcon("res/tileset_ground.png").getImage();
            tileset_ground[i] = createImage(new FilteredImageSource(tileset_ground[i].getSource(), new CropImageFilter(0, 26 * i, 26, 26)));
        }
        
        for (int i = 0; i < 5; i++) {
        	 tileset_air[i]= null; 
            tileset_air[i] = new ImageIcon("res/tileset_air.png").getImage();
            tileset_air[i] = createImage(new FilteredImageSource(tileset_air[i].getSource(), new CropImageFilter(0, 52 * i, 52, 52)));
        }
        
        tileset_res[0] = new ImageIcon("res/cell.png").getImage();
        tileset_res[1] = new ImageIcon("res/heart.png").getImage();
        tileset_res[2] = new ImageIcon("res/coin.png").getImage();
        
        tileset_creep[0] = new ImageIcon("res/mob.png").getImage();
        tileset_creep[1] = new ImageIcon("res/mob2.png").getImage();
        
        save.loadSave(new File("save/mission1"));
        
        // define creep
        for (int i = 0; i < mobs.length; i++) {
            mobs[i] = new Mob();
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        if (isFirst) {
            myWidth = getWidth();
            myHeight = getHeight();
            define();
            
            isFirst = false;
        }
        
        g.setColor(new Color(70, 70, 70));
        g.fillRect(0, 0, getWidth(), getHeight());
        
        // drawing the game
        g.setColor(new Color(0, 0, 0));
        game.draw(g); 
        
        // drawing the mobs	
        for (Mob creep : mobs) {
            if (creep.inGame) {
                creep.draw(g);
            }
        }
        
        // drawing the hUD
        hUD.draw(g);
		if (health < 1) {
			g.setColor(new Color(240, 20, 20));
			g.fillRect(0, 0, myWidth, myHeight);
			g.setColor(Color.white);
			g.setFont(new Font("Courier New", Font.BOLD, 86));
			g.drawString("Game Over!", 80, 250);
		}
		if (isWin) {
			g.setColor(Color.white);
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(Color.black);
			g.setFont(new Font("Courier New", Font.BOLD, 14));
			if (level == maxLevel) {
				g.drawString("You won the whole game.. you can exit now..", 80, 250);
			} else {
				g.drawString("You Won! New Level Loading...", 80, 250);
			} }
    }
    
    public double spawnTime = 2400, spawnFrame = 0;
	int inct = 0;

	private boolean isPaused;
	public static int score = 0; 
    public void mobSpawner() {
    
        if (spawnFrame >= spawnTime) {
            for (Mob mob : mobs) {
                if (!mob.inGame) {
						mob.spawnMob(0);
						inct +=1;
						score = (int) (inct *(10 / 1.2)); 
						System.out.println(inct);
						if (inct % 5 == 0) {
							mob.spawnMob(1)
							;}

							
                    break;
                }

            }
            spawnFrame = 0;
        } else {
            spawnFrame += 2.5;
        }
    }
    
    @Override
    public void run() {
        while(true) {
        	if (isPaused) {
        		
        		try {

        			thread.sleep(millis);
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
        	else if (!isFirst && !isPaused) {
                game.physics();
                mobSpawner();
                for (Mob mob : mobs) {
                    if (mob.inGame) {
                        mob.physics();
                    }
                }
            }
            
            repaint();
            
            try {
                Thread.sleep(1);
            }
            catch (Exception e) { }
        }
   
    }

}
