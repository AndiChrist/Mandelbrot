/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codecentric.fractal;

import de.codecentric.common.PicturePanel;
import de.codecentric.fractal.strategies.ComputeJulia;
import de.codecentric.fractal.strategies.ComputeMandelbrot;
import de.codecentric.fractal.strategies.FractalInvoker;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author Andreas Christ <andreas.christ@codecentric.de>
 */
public class Fraktal {

    private final static Logger LOGGER = Logger.getLogger(Fraktal.class.getName());
    private static final Properties prop = new Properties();
    private static final FraktalStrategy strategy = new FraktalStrategy();
    
    private static final String FRAKTAL = "fraktal.";
    
    private Complex min;
    private Complex max;
    private int width;
    private int height;
    private double infinity;
    private int iteration;

    public static void main(String[] args) throws FileNotFoundException, IOException {
        Fraktal fraktal = new Fraktal();
        ClassLoader fraktalClassLoader = Fraktal.class.getClassLoader();
        
        Properties typeProp = new Properties();
        typeProp.load(fraktalClassLoader.getResourceAsStream("fraktal.properties"));

        // read fractal type (mandelbrot, julia etc.)
        String type = typeProp.getProperty("type");
        LOGGER.log(Level.INFO, "Rendering stategy: {0}", type);
        
        // load special fractal properties
        prop.load(fraktalClassLoader.getResourceAsStream(FRAKTAL + type + ".properties"));
        
        fraktal.setMin(readProp(prop, "min.re", "min.im"));
        fraktal.setMax(readProp(prop, "max.re", "max.im"));

        fraktal.setWidth(Integer.valueOf(prop.getProperty("image.width")));
        fraktal.setHeight(Integer.valueOf(prop.getProperty("image.height")));

        fraktal.setInfinity(Double.parseDouble(prop.getProperty("infinity")));
        fraktal.setIteration(Integer.valueOf(prop.getProperty("max.iteration")));
        
        // set rendering strategy
        switch (type) {
            case "mandelbrot":
                strategy.setStrategy(new ComputeMandelbrot(fraktal.min, fraktal.max));
                break;
            case "julia":
                Complex c = readProp(prop, "c.re", "c.im");
                strategy.setStrategy(new ComputeJulia(fraktal.min, fraktal.max, c));
                break;
            case "mandelbrottask":
                strategy.setStrategy(new FractalInvoker(fraktal.min, fraktal.max));
                break;
        }
        
        // compute raw image data
        int[][] image = fraktal.computeFractal();
        
        // show image
        new PicturePanel(image).display();

    }

    private int[][] computeFractal() {
        int[][] image = new int[width][height];

        strategy.setInfinity(infinity);
        strategy.setIteration(iteration);
        strategy.compute(image);

        return image;
    }

    /**
     * reads coordinate from property file
     * 
     * @param prop
     * @param re
     * @param im
     * @return coordinate as complex object
     */
    private static Complex readProp(Properties prop, String re, String im) {
        Double minRe = Double.valueOf(prop.getProperty(re));
        Double minIm = Double.valueOf(prop.getProperty(im));
        return new Complex(minRe, minIm);
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
