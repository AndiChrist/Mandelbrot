/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codecentric.learning;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.concurrent.ForkJoinPool;
import javax.swing.JFrame;

/**
 *
 * @author Andreas Christ <andreas.christ@codecentric.de>
 */
public class Mandelbrot extends JFrame {

    @Override
    public void paint(Graphics g) {
        BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        ForkJoinPool pool = new ForkJoinPool(); // defaults thread per processor
        pool.invoke(new MandelbrotTask(image, 0, image.getWidth() - 1, 0,
                image.getHeight() - 1));
        g.drawImage(image, 0, 0, null);
    }

    public static void main(String[] args) {
        Mandelbrot mandy = new Mandelbrot();
        mandy.setSize(768, 768);
        mandy.setVisible(true);
    }
}
