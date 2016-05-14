/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.andichrist.fractal.strategies;

import io.github.andichrist.fractal.FractalIterator;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.math3.complex.Complex;

/**
 * Zn = Z(n-1)^2 + C
 *
 * @author Andreas Christ <andreas.christ@codecentric.de>
 */
public class ComputeMandelbrot implements ComputeFractal {

    private final static Logger LOGGER = Logger.getLogger(ComputeMandelbrot.class.getName());

    static Complex min;
    static Complex max;

    static double infinity;
    static int iteration;

    public ComputeMandelbrot(Complex min, Complex max) {
        ComputeMandelbrot.min = min;
        ComputeMandelbrot.max = max;
    }

    @Override
    @SuppressWarnings("UnusedAssignment")
    public void compute(int[][] image) {

        Callable<int[][]> callable = new ComputeCallable(image);
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<int[][]> result = executor.submit(callable);

        try {
            image = result.get();
        } catch (InterruptedException | ExecutionException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setInfinity(Double infinity) {
        ComputeMandelbrot.infinity = infinity;
    }

    @Override
    public void setIteration(Integer iteration) {
        ComputeMandelbrot.iteration = iteration;
    }

    private static class ComputeCallable implements Callable<int[][]> {

        final int[][] bild;

        Complex c;

        ComputeCallable(int[][] bild) {
            this.bild = bild;
        }

        @Override
        public int[][] call() throws Exception {
            int imageWidth = bild.length;
            int imageHeight = bild[0].length;
            
            double columnWidth = (max.getReal() - min.getReal()) / imageWidth;
            double columnHeight = (max.getImaginary() - min.getImaginary()) / imageHeight;

            for (int m = 0; m < imageHeight; m++) {
                double im = min.getImaginary() + m * columnHeight;

                for (int n = 0; n < imageWidth; n++) {
                    double re = min.getReal() + n * columnWidth;

                    c = new Complex(re, im);

                    bild[n][m] = FractalIterator.iterate(Complex.ZERO, c, iteration, infinity);
                }
            }
            return bild;
        }
    }

}
