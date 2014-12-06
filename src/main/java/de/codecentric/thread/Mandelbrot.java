/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codecentric.thread;

import de.codecentric.common.PicturePanel;
import java.awt.image.BufferedImage;
import java.util.concurrent.ForkJoinPool;

/**
 *
 * @author Andreas Christ <andreas.christ@codecentric.de>
 */
public class Mandelbrot {

    public static void main(String[] args) {
        BufferedImage image = new BufferedImage(768, 768, BufferedImage.TYPE_INT_RGB);

        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(
                new MandelbrotTask(image, 0, image.getWidth() - 1, 0, image.getHeight() - 1)
        );

        new PicturePanel(image).display();
    }

}
