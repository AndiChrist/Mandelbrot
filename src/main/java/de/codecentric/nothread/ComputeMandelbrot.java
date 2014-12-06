/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codecentric.nothread;

import de.codecentric.common.ColorManager;
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
 * Zn = Z(n-1)^2 + C
 *
 * @author Andreas Christ <andreas.christ@codecentric.de>
 */
public class ComputeMandelbrot implements ComputeFractal {

    private final static Logger LOGGER = Logger.getLogger(ComputeMandelbrot.class.getName());

    static Complex min = null;
    static Complex max = null;

    static double infinity = 0.0d;
    static int iteration = 0;

    ComputeMandelbrot(Complex min, Complex max) {
        ComputeMandelbrot.min = min;
        ComputeMandelbrot.max = max;
    }

    @Override
    public void compute(BufferedImage image) {

        Callable<BufferedImage> callable = new ComputeCallable(image);
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<BufferedImage> result = executor.submit(callable);

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

    private static class ComputeCallable implements Callable<BufferedImage> {

        final BufferedImage bild;

        Complex c;

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

                    c = new Complex(re, im);

                    int i = FractalIterator.iterate(Complex.ZERO, c, iteration, infinity);
                    bild.setRGB(n, m, ColorManager.HSBtoRGB(i, iteration));
                }
            }
            return bild;
        }
    }

}
