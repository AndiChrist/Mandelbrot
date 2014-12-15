/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codecentric.thread;

import de.codecentric.common.PicturePanel;
import java.util.concurrent.ForkJoinPool;

/**
 *
 * @author Andreas Christ <andreas.christ@codecentric.de>
 */
public class Mandelbrot {

    private static final int WIDTH = 768;
    private static final int HEIGHT = 768;

    public static void main(String[] args) {
        int[][] image = new int[WIDTH][HEIGHT];

        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(
                new MandelbrotTask(image, 0, WIDTH - 1, 0, HEIGHT - 1)
        );

        new PicturePanel(image).display();
    }

}
