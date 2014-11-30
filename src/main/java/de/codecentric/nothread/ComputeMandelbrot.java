/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codecentric.nothread;

import de.codecentric.common.ImageDimension;
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
public class ComputeMandelbrot {

    private final static Logger LOGGER = Logger.getLogger(ComputeMandelbrot.class.getName());

    private static final Complex min = new Complex(-2.0, -1.25);
    private static final Complex max = new Complex(0.5, 1.25);

    static void berechne(int[] bild, ImageDimension dimension) {

        Callable<int[]> callable = new ComputeCallable(bild, dimension);
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

        ImageDimension dimension;
        Complex c;
        Complex z;

        ComputeCallable(int[] bild, ImageDimension dimension) {
            this.bild = bild;
            this.dimension = dimension;
        }

        @Override
        public int[] call() throws Exception {
            double spaltenBreite = (max.getReal() - min.getReal()) / dimension.getWidth();
            double spaltenHöhe = (max.getImaginary() - min.getImaginary()) / dimension.getHeight();

            for (int m = 0; m < dimension.getHeight(); m++) {
                double im = min.getImaginary() + m * spaltenHöhe;

                for (int n = 0; n < dimension.getWidth(); n++) {
                    double re = min.getReal() + n * spaltenBreite;

                    c = new Complex(re, im);
                    z = Complex.ZERO;

                    bild[m * dimension.getWidth() + n] = FractalIterator.iterate(z, c);
                }
            }
            return bild;
        }
    }

}
