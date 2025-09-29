package com.nolan.run;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    private final Timer timer = new Timer(16, this);
    private final Game game = new Game();
    private final Random rng = new Random();

    public GamePanel() {
        setPreferredSize(new Dimension(Game.WIDTH, Game.HEIGHT));
        setFocusable(true);
        setupInput();
        timer.start();
    }

    private void setupInput() {
        InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getActionMap();

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false), "JUMP");
        am.put("JUMP", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                if (!game.isGameOver()) game.getPlayer().jump();
            }
        });

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0, false), "RESET");
        am.put("RESET", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                game.reset(rng);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!game.isGameOver()) game.update();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render((Graphics2D) g);
    }
}
