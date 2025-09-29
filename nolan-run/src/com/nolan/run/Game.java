package com.nolan.run;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Game {
    // World / physics
    public static final int WIDTH = 800;
    public static final int HEIGHT = 400;
    public static final int GROUND_Y = HEIGHT - 60;

    public static final int GRAVITY = 1;
    public static final int JUMP_VEL = -18;

    // Difficulty
    private int speed = 7;
    private int score = 0;

    // Entities
    private final Player player = new Player(120, GROUND_Y, 20);
    private final List<Obstacle> obstacles = new ArrayList<>();
    private boolean gameOver = false;
    private final Random rng = new Random();

    // Spawn timing
    private int spawnTimer = 0;
    private int nextSpawnIn = 60 + rng.nextInt(70); // frames

    public Game() {
        // Start with a couple of obstacles queued off-screen
        obstacles.add(makeObstacle(WIDTH + 200));
        obstacles.add(makeObstacle(WIDTH + 500));
    }

    public void reset(Random seeder) {
        rng.setSeed(seeder.nextLong());
        speed = 5;
        score = 0;
        gameOver = false;
        obstacles.clear();
        obstacles.add(makeObstacle(WIDTH + 200));
        obstacles.add(makeObstacle(WIDTH + 500));
        spawnTimer = 0;
        nextSpawnIn = 60 + rng.nextInt(70);
    }

    public void update() {
        // Update player
        player.update();

        // Update obstacles
        for (Obstacle ob : obstacles) ob.update(speed);

        // Remove offscreen, count score when passed player
        Iterator<Obstacle> it = obstacles.iterator();
        while (it.hasNext()) {
            Obstacle ob = it.next();
            if (ob.isOffscreen()) {
                it.remove();
                score++;
                if (score % 3 == 0 && speed < 12) speed++; // gentle speed-up
            }
        }

        // Spawn new obstacles intermittently
        spawnTimer++;
        if (spawnTimer >= nextSpawnIn) {
            spawnTimer = 0;
            nextSpawnIn = 50 + rng.nextInt(70);
            obstacles.add(makeObstacle(WIDTH + 80 + rng.nextInt(120)));
        }

        // Collision
        Rectangle pb = player.getBounds();
        for (Obstacle ob : obstacles) {
            if (pb.intersects(ob.getBounds())) {
                gameOver = true;
                break;
            }
        }
    }

    private Obstacle makeObstacle(int startX) {
        int size = 28 + rng.nextInt(24); // 28..51 px
        return new Obstacle(startX, size);
    }

    public void render(Graphics2D g) {
        // Smooth edges
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Background
        g.setColor(new Color(30, 35, 27));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // Ground
        g.setColor(new Color(115, 132, 42));
        g.fillRect(0, GROUND_Y, WIDTH, HEIGHT - GROUND_Y);


        // Draw entities
        for (Obstacle ob : obstacles) ob.draw(g);
        player.draw(g);

        // HUD
        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        g.drawString("Score: " + score, 14, 26);

        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        if (!gameOver) {
            g.drawString("SPACE = jump   ·   R = restart", 14, 46);
        } else {
            String msg = "Game Over: press R to restart";
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
            int w = g.getFontMetrics().stringWidth(msg);
            g.drawString(msg, (WIDTH - w) / 2, HEIGHT / 2);
        }
    }

    // Getters
    public boolean isGameOver() { return gameOver; }
    public Player getPlayer() { return player; }
}
