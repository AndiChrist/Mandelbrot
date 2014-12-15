/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codecentric.fractal.strategies;

import de.codecentric.fractal.FractalIterator;
import static java.util.concurrent.ForkJoinTask.invokeAll;
import java.util.concurrent.RecursiveAction;
import java.util.logging.Logger;
import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author Andreas Christ <andreas.christ@codecentric.de>
 */
public class ComputeMandelbrotRecursive extends RecursiveAction {

    private final static Logger LOGGER = Logger.getLogger(ComputeMandelbrotRecursive.class.getName());

    private static double maxInfinity;
    private static int maxIterationSteps;
    private static Complex min;
    private static Complex max;
    //private static BufferedImage image;
    private static int[][] image;
    private final int xStart, xEnd, yStart, yEnd;
    private final static int taskSplitThreshold = 1024;

    public ComputeMandelbrotRecursive(int[][] image) {
        this(0, image.length - 1, 0, image[0].length - 1);
        ComputeMandelbrotRecursive.image = image;
    }

    public ComputeMandelbrotRecursive(int xStart, int xEnd, int yStart, int yEnd) {
        this.xStart = xStart;
        this.xEnd = xEnd;
        this.yStart = yStart;
        this.yEnd = yEnd;
    }

    public void render() {
        int width = image.length;
        int height = image[0].length;

        double spaltenBreite = (max.getReal() - min.getReal()) / width;
        double spaltenHöhe = (max.getImaginary() - min.getImaginary()) / height;

        for (int m = yStart; m <= yEnd; m++) {
            double im = min.getImaginary() + m * spaltenHöhe;

            for (int n = xStart; n <= xEnd; n++) {
                double re = min.getReal() + n * spaltenBreite;

                Complex c = new Complex(re, im);
                // Complex z = Complex.ZERO;

                int i = FractalIterator.iterate(Complex.ZERO, c, maxIterationSteps, maxInfinity);
                image[n][m] = i;
                //image.setRGB(n, m, ColorManager.HSBtoRGB(i, maxIterationSteps));
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
                    new ComputeMandelbrotRecursive(
                            xStart, xStart + width / 2,
                            yStart, yStart + height / 2), // top left
                    new ComputeMandelbrotRecursive(
                            xStart + width / 2 + 1, xEnd,
                            yStart, yStart + height / 2), // top right
                    new ComputeMandelbrotRecursive(
                            xStart, xStart + width / 2,
                            yStart + height / 2 + 1, yEnd), // bottom left
                    new ComputeMandelbrotRecursive(
                            xStart + width / 2 + 1, xEnd,
                            yStart + height / 2 + 1, yEnd) // bottom right
            );
        }
    }

    public void setInfinity(double infinity) {
        ComputeMandelbrotRecursive.maxInfinity = infinity;
    }

    public void setIteration(int iteration) {
        ComputeMandelbrotRecursive.maxIterationSteps = iteration;
    }

    void setMin(Complex min) {
        ComputeMandelbrotRecursive.min = min;
    }

    void setMax(Complex max) {
        ComputeMandelbrotRecursive.max = max;
    }

}
