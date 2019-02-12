package com.tutorial.main;

import java.awt.*;

public class BasicEnemy extends GameObject {
    public BasicEnemy(int x, int y, ID id) {
        super(x, y, id);
        velX = 5;
        velY = 5;
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

        if (x <= 0 | x >= Game.HEIGHT - 32) velX *= -1;
        if (y <= 0 | y >= Game.HEIGHT - 32) velY *= -1;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(x, y, 16, 16);
    }
}
