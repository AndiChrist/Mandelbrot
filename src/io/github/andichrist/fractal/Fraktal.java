/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.andichrist.fractal;

import io.github.andichrist.common.PicturePanel;
import io.github.andichrist.common.PropertyLoader;
import io.github.andichrist.fractal.strategies.ComputeJulia;
import io.github.andichrist.fractal.strategies.ComputeMandelbrot;
import io.github.andichrist.fractal.strategies.FractalInvoker;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.math3.complex.Complex;

import static io.github.andichrist.common.PropertyLoader.*;

/**
 *
 * @author Andreas Christ <andreas.christ@sixt.com>
 */
public class Fraktal {

    private final static Logger LOGGER = Logger.getLogger(Fraktal.class.getName());

    private static final FraktalStrategy strategy = new FraktalStrategy();

    private Complex min;
    private Complex max;
    private int width;
    private int height;
    private double infinity;
    private int iteration;

    public static void main(String[] args) throws IOException {
        LOGGER.info(Arrays.toString(args));
        LOGGER.info(System.getProperty("simple.message"));
        
        Fraktal fraktal = new Fraktal();
        
        fraktal.setMin(readComplexProp("min.re", "min.im"));
        fraktal.setMax(readComplexProp("max.re", "max.im"));

        fraktal.setWidth(Integer.valueOf(getProperty("image.width")));
        fraktal.setHeight(Integer.valueOf(getProperty("image.height")));

        fraktal.setInfinity(Double.parseDouble(getProperty("infinity")));
        fraktal.setIteration(Integer.valueOf(getProperty("max.iteration")));
        
        // set rendering strategy
        switch (getProperty("type")) {
            case "mandelbrot":
                strategy.setStrategy(new ComputeMandelbrot());
                break;
            case "julia":
                Complex c = readComplexProp("c.re", "c.im");
                strategy.setStrategy(new ComputeJulia(c));
                break;
            case "mandelbrottask":
                strategy.setStrategy(new FractalInvoker());
                break;
        }
        
        // compute raw image data
        int[][] image = fraktal.computeFractal();
        
        // show image
        new PicturePanel(image).display();

    }

    private int[][] computeFractal() {
        int[][] image = new int[width][height];

        strategy.setMin(min);
        strategy.setMax(max);
        strategy.setInfinity(infinity);
        strategy.setIteration(iteration);
        strategy.compute(image);

        return image;
    }

    /**
     * reads coordinate from property file
     * 
     * @param re Real value
     * @param im Imaginary value
     * @return coordinate as complex object
     */
    private static Complex readComplexProp(String re, String im) {
        Double doubleRe = Double.valueOf(getProperty(re));
        Double doubleIm = Double.valueOf(getProperty(im));
        return new Complex(doubleRe, doubleIm);
    }

    private void setMin(Complex min) {
        this.min = min;
    }

    private void setMax(Complex max) {
        this.max = max;
    }

    private void setWidth(int width) {
        this.width = width;
    }

    private void setHeight(int height) {
        this.height = height;
    }

    private void setInfinity(double infinity) {
        this.infinity = infinity;
    }

    private void setIteration(int iteration) {
        this.iteration = iteration;
    }

}
