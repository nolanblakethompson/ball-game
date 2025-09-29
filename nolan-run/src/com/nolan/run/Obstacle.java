package com.nolan.run;

import java.awt.*;

public class Obstacle {
    private int x;
    private final int size;

    public Obstacle(int startX, int size) {
        this.x = startX;
        this.size = size;
    }

    public void update(int speed) {
        x -= speed;
    }

    public boolean isOffscreen() {
        return x + size < 0;
    }

    public Rectangle getBounds() {
        // squares sit on the ground
        return new Rectangle(x, Game.GROUND_Y - size, size, size);
    }

    public void draw(Graphics2D g) {
        g.setColor(new Color(176, 229, 164));
        Rectangle r = getBounds();
        g.fillRect(r.x, r.y, r.width, r.height);
    }
}
