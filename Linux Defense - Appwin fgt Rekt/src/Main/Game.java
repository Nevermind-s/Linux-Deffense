package Main;
import java.awt.*;
public class Game {
    public int worldWidth = 12;
    public int worldHeight = 8;
    public int blockSize = 52;
    
    public Tower[][] tower;
    
    public Game() {
        define();
    }
    
    public void define() {
        tower = new Tower[worldHeight][worldWidth];
        
        for (int y = 0; y < tower.length; y++ ) {
            for (int x = 0; x < tower[0].length; x++) {
                tower[y][x] = new Tower((Screen.myWidth/2) - (worldWidth*blockSize/2) + (x * blockSize), y * blockSize, blockSize, blockSize, Bind.groundGrass, Bind.airAir);      
            }
        }
    }
    
    public void physics() {
        for (int y = 0; y < tower.length; y++ ) {
            for (int x = 0; x < tower[0].length; x++) {
                tower[y][x].physics(); 
            }
        }
    }
    
    public void draw(Graphics g) {
        for (int y = 0; y < tower.length; y++ ) {
            for (int x = 0; x < tower[0].length; x++) {
                tower[y][x].draw(g); 
            }
        }
        
        for (int y = 0; y < tower.length; y++ ) {
            for (int x = 0; x < tower[0].length; x++) {
                tower[y][x].attack(g); 
            }
        }
        
    }
}
