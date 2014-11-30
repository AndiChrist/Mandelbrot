/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codecentric.nothread;

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

    private static Complex min;
    private static Complex max;

    ComputeMandelbrot(Complex min, Complex max) {
        ComputeMandelbrot.min = min;
        ComputeMandelbrot.max = max;
    }

    @Override
    public void berechne(int[] bild, Integer width, Integer height) {

        Callable<int[]> callable = new ComputeCallable(bild, width, height);
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<int[]> result = executor.submit(callable);

        try {
            bild = result.get();
        } catch (InterruptedException | ExecutionException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private static class ComputeCallable implements Callable<int[]> {

        final int[] bild;

        private final int width;
        private final int height;

        Complex c;
        Complex z;

        ComputeCallable(int[] bild, int width, int height) {
            this.bild = bild;
            this.width = width;
            this.height = height;
        }

        @Override
        public int[] call() throws Exception {
            double spaltenBreite = (max.getReal() - min.getReal()) / this.width;
            double spaltenHöhe = (max.getImaginary() - min.getImaginary()) / this.height;

            for (int m = 0; m < this.height; m++) {
                double im = min.getImaginary() + m * spaltenHöhe;

                for (int n = 0; n < this.width; n++) {
                    double re = min.getReal() + n * spaltenBreite;

                    c = new Complex(re, im);
                    z = Complex.ZERO;

                    bild[m * this.width + n] = FractalIterator.iterate(z, c);
                }
            }
            return bild;
        }
    }

}
