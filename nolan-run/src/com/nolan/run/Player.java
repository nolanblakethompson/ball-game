package com.nolan.run;

import java.awt.*;

public class Player {
    private int x;
    private int y;
    private int r;          // radius
    private int vy;         // vertical velocity

    public Player(int x, int groundY, int radius) {
        this.x = x;
        this.r = radius;
        this.y = groundY - r;
        this.vy = 0;
    }

    public void update() {
        vy += Game.GRAVITY;
        y += vy;
        int groundTop = Game.GROUND_Y - r;
        if (y > groundTop) {
            y = groundTop;
            vy = 0;
        }
    }

    public void jump() {
        // jump only if on ground
        if (y >= Game.GROUND_Y - r && vy == 0) {
            vy = Game.JUMP_VEL;
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x - r, y - r, 2 * r, 2 * r);
    }

    public void draw(Graphics2D g) {
        g.setColor(new Color(186, 184, 219));
        g.fillOval(x - r, y - r, 2 * r, 2 * r);
    }
}
