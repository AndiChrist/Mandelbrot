/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codecentric.nothread;

import de.codecentric.common.PicturePanel;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;
import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author Andreas Christ <andreas.christ@codecentric.de>
 */
public class Fraktal {

    private final static Logger LOGGER = Logger.getLogger(Fraktal.class.getName());

    public static void main(String[] args) throws FileNotFoundException, IOException {
        Fraktal fraktal = new Fraktal();

        Properties typeProp = new Properties();
        typeProp.load(Fraktal.class.getClassLoader().getResourceAsStream("fraktal.properties"));

        String type = typeProp.getProperty("type");
        fraktal.setType(type);

        Properties prop = new Properties();
        prop.load(Fraktal.class.getClassLoader().getResourceAsStream(type + ".properties"));

        fraktal.setMin(readProp(prop, "min.re", "min.im"));
        fraktal.setMax(readProp(prop, "max.re", "max.im"));

        fraktal.setWidth(Integer.valueOf(prop.getProperty("image.width")));
        fraktal.setHeight(Integer.valueOf(prop.getProperty("image.height")));

        fraktal.setInfinity(Double.parseDouble(prop.getProperty("infinity")));
        fraktal.setIteration(Integer.valueOf(prop.getProperty("max.iteration")));

        BufferedImage image = fraktal.computeFractal();
        new PicturePanel(image).display();

    }

    private BufferedImage computeFractal() {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        ComputeFractal fractal = null;
        switch (this.type) {
            case "mandelbrot":
                fractal = new ComputeMandelbrot(min, max);
                break;
            case "julia":
                fractal = new ComputeJulia(min, max);
                break;
        }

        if (fractal != null) {
            fractal.setInfinity(infinity);
            fractal.setIteration(iteration);

            fractal.compute(image);
        }

        return image;
    }

    private static Complex readProp(Properties prop, String re, String im) {
        Double minRe = Double.valueOf(prop.getProperty(re));
        Double minIm = Double.valueOf(prop.getProperty(im));
        return new Complex(minRe, minIm);
    }

    private String type;
    private Complex min;
    private int width;
    private Complex max;
    private int height;
    private double infinity;
    private int iteration;

    private void setType(String type) {
        this.type = type;
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
