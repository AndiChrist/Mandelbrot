/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.andichrist.fractal.strategies;

import io.github.andichrist.fractal.FractalIterator;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
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
 * @author Andreas Christ <andreas.christ@sixt.com>
 */
public class ComputeJulia extends ComputeFractal {

    private static final Logger LOGGER = Logger.getLogger(ComputeJulia.class.getName());

    private static final String JULIA_PROPERTIES = "julia.properties";

    static Complex min;
    static Complex max;

    static double infinity;
    static int iteration;

    private static Complex c;

    private final Properties properties;

    public ComputeJulia() {
        properties = new Properties();
            try (InputStream fis = ComputeJulia.class.getClassLoader().getResourceAsStream(JULIA_PROPERTIES)) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ComputeJulia.c = readComplexProp(properties.getProperty("c.re"), properties.getProperty("c.im"));

        min = readComplexProp(properties.getProperty("min.re"), properties.getProperty("min.im"));
        max = readComplexProp(properties.getProperty("max.re"), properties.getProperty("max.im"));

        infinity = Double.parseDouble(properties.getProperty("infinity"));
        iteration = Integer.parseInt(properties.getProperty("max.iteration"));
    }

    @Override
    @SuppressWarnings("UnusedAssignment")
    public void compute(int[][] bild) {
        Callable<int[][]> callable = new ComputeCallable(bild);
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<int[][]> result = executor.submit(callable);

        try {
            bild = result.get();
        } catch (InterruptedException | ExecutionException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int getWidth() {
        return Integer.parseInt(properties.getProperty("image.width"));
    }

    @Override
    public int getHeight() {
        return Integer.parseInt(properties.getProperty("image.height"));
    }

    private static class ComputeCallable implements Callable<int[][]> {

        final int[][] bild;

        Complex z;

        ComputeCallable(int[][] bild) {
            this.bild = bild;
        }

        @Override
        public int[][] call() {
            int imageWidth = bild.length;
            int imageHeight = bild[0].length;

            double columnWidth = (max.getReal() - min.getReal()) / imageWidth;
            double columnHeight = (max.getImaginary() - min.getImaginary()) / imageHeight;

            for (int n = 0; n < imageWidth; n++) {
                double re = min.getReal() + n * columnWidth;
                for (int m = 0; m < imageHeight; m++) {
                    double im = min.getImaginary() + m * columnHeight;

                    z = new Complex(re, im);

                    bild[n][m] = FractalIterator.iterate(z, c, iteration, infinity);

                }
            }
            return bild;
        }
    }

}
