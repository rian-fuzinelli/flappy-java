package core;

import java.awt.Graphics;
import java.awt.Image;

public class Bird {
    public int x, y, width, height;
    public Image img;

    public Bird(Image img, int x, int y, int width, int height) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void draw(Graphics g) {
        g.drawImage(img, x, y, width, height, null);
    }
}
