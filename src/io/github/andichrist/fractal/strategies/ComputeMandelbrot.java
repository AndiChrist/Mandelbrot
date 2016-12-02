/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.andichrist.fractal.strategies;

import io.github.andichrist.fractal.FractalIterator;

import java.io.FileInputStream;
import java.io.IOException;
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
 * Zn = Z(n-1)^2 + C
 *
 * @author Andreas Christ <andreas.christ@sixt.com>
 */
public class ComputeMandelbrot extends ComputeFractal {

    private final static Logger LOGGER = Logger.getLogger(ComputeMandelbrot.class.getName());

    private static final String MANDELBROT_PROPERTIES = "mandelbrot.properties";

    private Complex min;
    private Complex max;

    private double infinity;
    private int iteration;

    private Properties properties;

    public ComputeMandelbrot() {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(MANDELBROT_PROPERTIES)) {
            properties.load(fis);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        min = readComplexProp(properties.getProperty("min.re"), properties.getProperty("min.im"));
        max = readComplexProp(properties.getProperty("max.re"), properties.getProperty("max.im"));

        infinity = Double.parseDouble(properties.getProperty("infinity"));
        iteration = Integer.valueOf(properties.getProperty("max.iteration"));
    }

    @Override
    @SuppressWarnings("UnusedAssignment")
    public void compute(int[][] image) {

        Callable<int[][]> callable = new ComputeCallable(image, min, max, infinity, iteration);
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<int[][]> result = executor.submit(callable);

        try {
            image = result.get();
        } catch (InterruptedException | ExecutionException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int getWidth() {
        return Integer.valueOf(properties.getProperty("image.width"));
    }

    @Override
    public int getHeight() {
        return Integer.valueOf(properties.getProperty("image.height"));
    }

    private static class ComputeCallable implements Callable<int[][]> {

        final int[][] bild;

        Complex c;

        private Complex min;
        private Complex max;

        private double infinity;
        private int iteration;

        ComputeCallable(int[][] bild, Complex min, Complex max, double infinity, int iteration) {
            this.bild = bild;
            this.min = min;
            this.max = max;
            this.infinity = infinity;
            this.iteration = iteration;
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
