package Main;

import java.io.*;
import java.util.*;

public class Save {
    public void loadSave(File loadPath) {
        try {
            Scanner loadScanner = new Scanner(loadPath);
            
            while(loadScanner.hasNext()) {
                for (int y = 0; y < Screen.game.tower.length; y++) {
                    for (int x = 0; x < Screen.game.tower[0].length; x++) {
                        Screen.game.tower[y][x].groundID = loadScanner.nextInt();
                    }
                }
                
                for (int y = 0; y < Screen.game.tower.length; y++) {
                    for (int x = 0; x < Screen.game.tower[0].length; x++) {
                        Screen.game.tower[y][x].airID = loadScanner.nextInt();
                    }
                }
            }
            
            loadScanner.close();
        } catch(Exception e) {
            
        } 
    }
}
