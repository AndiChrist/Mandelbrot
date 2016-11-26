/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.andichrist.thread;

import io.github.andichrist.fractal.FractalIterator;
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
    private final int[][] image;
    private final int xStart, xEnd, yStart, yEnd;
    private final static int taskSplitThreshold = 1024;

    MandelbrotTask(int[][] image, int xStart, int xEnd, int yStart, int yEnd) {
        this.image = image;
        this.xStart = xStart;
        this.xEnd = xEnd;
        this.yStart = yStart;
        this.yEnd = yEnd;
    }

    public void render() {
        int width = image.length;
        int height = image[0].length;

        for (int x = xStart; x <= xEnd; x++) {
            double re = x * size / width - size / 2 + offsetX;

            for (int y = yStart; y <= yEnd; y++) {
                double im = y * size / height - size / 2;

                Complex c = new Complex(re, im);

                int i = FractalIterator.iterate(Complex.ZERO, c, maxIterations, thresholdSq);
                image[x][y] = i;
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
