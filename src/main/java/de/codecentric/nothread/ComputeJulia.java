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
 * Z(n+1) = Zn^2 + C
 *
 * @author Andreas Christ <andreas.christ@codecentric.de>
 */
public class ComputeJulia {

    private static final Logger LOGGER = Logger.getLogger(ComputeJulia.class.getName());

    private static final Complex min = new Complex(-0.858, -0.605);
    private static final Complex max = new Complex(0.168, 0.370);

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
        final ImageDimension dimension;

        Complex z;

        private final Complex c;

        ComputeCallable(int[] bild, ImageDimension dimension) {
            this.bild = bild;
            this.dimension = dimension;

            //c = new Complex(-0.74543, +0.11301);
            c = new Complex(-1.26, 0);
        }

        @Override
        public int[] call() throws Exception {
            double spaltenBreite = (max.getReal() - min.getReal()) / dimension.getWidth();
            double spaltenHöhe = (max.getImaginary() - min.getImaginary()) / dimension.getHeight();

            for (int m = 0; m < dimension.getHeight(); m++) {
                double im = min.getImaginary() + m * spaltenHöhe;

                for (int n = 0; n < dimension.getWidth(); n++) {
                    double re = min.getReal() + n * spaltenBreite;

                    z = new Complex(re, im);

                    bild[m * dimension.getWidth() + n] = FractalIterator.iterate(z, c);
                }
            }
            return bild;
        }
    }

}
