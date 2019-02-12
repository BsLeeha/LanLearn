package com.tutorial.main;

import javax.swing.*;
import java.awt.*;

/* extends -> inheritance -> is a / code reusing */
public class Window extends Canvas {
    // no need SerialVersionUID generated

    public Window(int width, int height, String title, Game game) {
        JFrame frame = new JFrame(title);

        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(game);
        frame.setVisible(true);
        game.start();
    }
}