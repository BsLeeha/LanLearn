package com.tutorial.main;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.Serializable;
import java.util.Random;

public class Game extends Canvas implements Runnable {
    // no need SerialVersionUID generated
    public static final int WIDTH = 640, HEIGHT = WIDTH / 12 * 9;

    private Thread thread;
    private boolean running = false;

    private Handler handler = new Handler();
    private Random r = new Random();

    public Game() {
        this.setFocusable(true);
        this.addKeyListener(new KeyInput(handler));

        new Window(WIDTH, HEIGHT, "StartUpGame", this);

        handler.addObject(new Player(WIDTH/2-32, HEIGHT/2-32, ID.Player));
        handler.addObject(new BasicEnemy(WIDTH/2-32, HEIGHT/2-32, ID.BasicEnemy));
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;         // 1/60 sec
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }
            if (running)
                render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
//                System.out.println("FPS: "+ frames);
                frames = 0;
            }
        }
        stop();
    }

    private void tick(){
        handler.tick();
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return ;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.BLACK);
        g.fillRect(0,0, WIDTH, HEIGHT);

        handler.render(g);

        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {
        // create an object with reference
        new Game();
        // g is not an object its a reference variable which holds the hashCode
        // of the object in hexadecimal notation
//        Game g = new Game();
    }
}