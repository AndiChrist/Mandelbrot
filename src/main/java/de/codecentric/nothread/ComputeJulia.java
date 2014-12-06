/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codecentric.nothread;

import java.awt.image.BufferedImage;
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
public class ComputeJulia implements ComputeFractal {

    private static final Logger LOGGER = Logger.getLogger(ComputeJulia.class.getName());

    private static Complex min;
    private static Complex max;

    ComputeJulia(Complex min, Complex max) {
        ComputeJulia.min = min;
        ComputeJulia.max = max;
    }

    //private static final Complex min = new Complex(-0.858, -0.605);
    //private static final Complex max = new Complex(0.168, 0.370);
    @Override
    public void compute(BufferedImage bild) {
        Callable<BufferedImage> callable = new ComputeCallable(bild);
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<BufferedImage> result = executor.submit(callable);

        try {
            bild = result.get();
        } catch (InterruptedException | ExecutionException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private static class ComputeCallable implements Callable<BufferedImage> {

        final BufferedImage bild;

        Complex z;

        private final Complex c = new Complex(-0.74543, +0.11301);

        ComputeCallable(BufferedImage bild) {
            this.bild = bild;
        }

        @Override
        public BufferedImage call() throws Exception {
            double spaltenBreite = (max.getReal() - min.getReal()) / bild.getWidth();
            double spaltenHöhe = (max.getImaginary() - min.getImaginary()) / bild.getHeight();

            for (int m = 0; m < bild.getHeight(); m++) {
                double im = min.getImaginary() + m * spaltenHöhe;

                for (int n = 0; n < bild.getWidth(); n++) {
                    double re = min.getReal() + n * spaltenBreite;

                    z = new Complex(re, im);

                    bild.setRGB(n, m, FractalIterator.iterate(z, c));
                }
            }
            return bild;
        }
    }

}
