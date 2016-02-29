package Main;

import java.awt.*;
public class HUD {
    public static int shopWidth = 8;
    public static int buttonSize = 52;
    public static int cellSpace = 2;
    public static int iconSize = 20;
    public static int itemIn = 4;
    public static int heldID = -1;
    public static int realID = -1;
    
    public static int[] buttonID = {Bind.airTowerLaser, Bind.towerFreeze, Bind.airAir, Bind.airAir, Bind.airAir, Bind.airAir, Bind.airAir, Bind.airTrashCan};
    public static int[] buttonPrice = {10, 20, 0, 0, 0, 0, 0, 0};
    
    public Rectangle[] button = new Rectangle[shopWidth];
    public Rectangle buttonHealth;
    public Rectangle buttonCoins;
    
    public boolean holdsItem = false;

    public HUD() {
        define();
    }
    
    public void click(int mouseButton) {
        if (mouseButton == 1) {
            for (int i = 0; i < button.length; i++) {
                if (button[i].contains(Screen.mse)) {
                    if (buttonID[i] != Bind.airAir) {
                        if (buttonID[i] == Bind.airTrashCan) {
                            holdsItem = false;
                        } else {
                            heldID = buttonID[i];
                            realID = i;
                            holdsItem = true;
                        }
                    }
                }
            }
        }
        
        if (holdsItem) {
            if (Screen.coins >= buttonPrice[realID]) {
                for (int y = 0; y < Screen.game.tower.length; y++) {
                    for (int x = 0; x < Screen.game.tower[0].length; x++) {
                        if (Screen.game.tower[y][x].contains(Screen.mse)) {
                            if (Screen.game.tower[y][x].groundID != Bind.groundRoad && Screen.game.tower[y][x].airID == Bind.airAir) {
                                Screen.game.tower[y][x].airID = heldID;
                                System.out.println(realID);
                                Screen.coins -= buttonPrice[realID];
                            }
                        }
                    }
                }
            }
        }
    }
    
    public void define() {
        for (int i = 0; i < button.length; i++) {
            button[i] = new Rectangle(Screen.myWidth/2 - shopWidth*(buttonSize+cellSpace)/2 + (buttonSize+cellSpace)*i, Screen.game.tower[Screen.game.worldHeight-1][0].y + Screen.game.blockSize + 20, buttonSize, buttonSize);
        }
        buttonHealth = new Rectangle(Screen.game.tower[0][0].x, button[0].y, iconSize, iconSize);
        buttonCoins = new Rectangle(Screen.game.tower[0][0].x, button[0].y + button[0].height - iconSize, iconSize, iconSize);
    }
    
    public void draw(Graphics g) {
        for (int i = 0; i < button.length; i++) {
            if (button[i].contains(Screen.mse)) {
                g.setColor(new Color(255, 255, 255, 150));
                g.fillRect(button[i].x, button[i].y, button[i].width, button[i].height);
            }
            g.drawImage(Screen.tileset_res[0], button[i].x, button[i].y, button[i].width, button[i].height, null);
            if (buttonID[i] != Bind.airAir) {
                g.drawImage(Screen.tileset_air[buttonID[i]], button[i].x + itemIn, button[i].y + itemIn, button[i].width - itemIn*2, button[i].height - itemIn*2, null);
            }
            if (buttonPrice[i] > 0) {
                g.setColor(new Color(255, 255, 255));
                g.setFont(new Font("Courier New", Font.BOLD, 14));
                g.drawString("" + buttonPrice[i], button[i].x + 4, button[i].y + 12);
            }
        }
        g.drawImage(Screen.tileset_res[1], buttonHealth.x, buttonHealth.y, iconSize, iconSize, null);
        g.drawImage(Screen.tileset_res[2], buttonCoins.x, buttonCoins.y, iconSize, iconSize, null);
            
        g.setColor(new Color(255, 255, 255));
        g.setFont(new Font("Courier New", Font.BOLD, 14));
        g.drawString("" + Screen.health, buttonHealth.x + buttonHealth.width + 3, buttonHealth.y + 14);
        g.drawString("" + Screen.coins, buttonCoins.x + buttonCoins.width + 3, buttonCoins.y + 14);
        g.drawString("Score : " + Screen.score,  + buttonCoins.width + 3, (buttonCoins.y + 40));
        if (holdsItem) {
            g.drawImage(Screen.tileset_air[heldID], Screen.mse.x - (button[0].width - itemIn*2)/2 - itemIn, Screen.mse.y - (button[0].height - itemIn*2)/2 - itemIn, button[0].width , button[0].height, null);
        }
        
    }
}
