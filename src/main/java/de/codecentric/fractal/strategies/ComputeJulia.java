/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codecentric.fractal.strategies;

import de.codecentric.common.ColorManager;
import de.codecentric.fractal.FractalIterator;
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

    static Complex min;
    static Complex max;

    static double infinity;
    static int iteration;

    private static Complex c;

    public ComputeJulia(Complex min, Complex max, Complex c) {
        ComputeJulia.min = min;
        ComputeJulia.max = max;
        ComputeJulia.c = c;
    }

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

    @Override
    public void setInfinity(Double infinity) {
        ComputeJulia.infinity = infinity;
    }

    @Override
    public void setIteration(Integer iteration) {
        ComputeJulia.iteration = iteration;
    }

    private static class ComputeCallable implements Callable<BufferedImage> {

        final BufferedImage bild;

        Complex z;

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

                    int i = FractalIterator.iterate(z, c, iteration, infinity);
                    bild.setRGB(n, m, ColorManager.HSBtoRGB(i, iteration));

                }
            }
            return bild;
        }
    }

}
