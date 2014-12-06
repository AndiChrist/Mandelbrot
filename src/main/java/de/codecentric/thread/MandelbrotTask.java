/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codecentric.thread;

import de.codecentric.common.ColorManager;
import java.awt.image.BufferedImage;
import static java.util.concurrent.ForkJoinTask.invokeAll;
import java.util.concurrent.RecursiveAction;
import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author Andreas Christ <andreas.christ@codecentric.de>
 */
public class MandelbrotTask extends RecursiveAction {

    private final static double size = 3.0, offsetX = -0.7, thresholdSq = 2.0;
    private final static int maxIterations = 30;
    private final BufferedImage image;
    private final int xStart, xEnd, yStart, yEnd;
    private final static int taskSplitThreshold = 1024;

    MandelbrotTask(BufferedImage image, int xStart, int xEnd, int yStart,
            int yEnd) {
        this.image = image;
        this.xStart = xStart;
        this.xEnd = xEnd;
        this.yStart = yStart;
        this.yEnd = yEnd;
    }

    public void render() {
        for (int x = xStart; x <= xEnd; x++) {
            double re = x * size / image.getWidth() - size / 2 + offsetX;

            for (int y = yStart; y <= yEnd; y++) {
                double im = y * size / image.getHeight() - size / 2;

                Complex c = new Complex(re, im);
                Complex z = Complex.ZERO;

                int iter = 0;

                do {
                    z = z.multiply(z).add(c);
                    iter++;
                } while (z.abs() <= thresholdSq && iter < maxIterations);

                image.setRGB(x, y, ColorManager.HSBtoRGB(iter, maxIterations));
            }
        }
    }

    @Override
    protected void compute() {
        int width = xEnd - xStart;
        int height = yEnd - yStart;

        if (width * height < taskSplitThreshold) {
            render();
        } else {
            invokeAll(
                    new MandelbrotTask(image,
                            xStart, xStart + width / 2,
                            yStart, yStart + height / 2), // top left
                    new MandelbrotTask(image,
                            xStart + width / 2 + 1, xEnd,
                            yStart, yStart + height / 2), // top right
                    new MandelbrotTask(image,
                            xStart, xStart + width / 2,
                            yStart + height / 2 + 1, yEnd), // bottom left
                    new MandelbrotTask(image,
                            xStart + width / 2 + 1, xEnd,
                            yStart + height / 2 + 1, yEnd) // bottom right
            );
        }
    }
}
