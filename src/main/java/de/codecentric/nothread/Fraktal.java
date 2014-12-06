/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codecentric.nothread;

import de.codecentric.common.Picture;
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
        Properties typeProp = new Properties();
        typeProp.load(Fraktal.class.getClassLoader().getResourceAsStream("fraktal.properties"));

        String type = typeProp.getProperty("type");

        Properties prop = new Properties();
        prop.load(Fraktal.class.getClassLoader().getResourceAsStream(type + ".properties"));

        Double minRe = Double.valueOf(prop.getProperty("min.re"));
        Double minIm = Double.valueOf(prop.getProperty("min.im"));
        Complex min = new Complex(minRe, minIm);

        Double maxRe = Double.valueOf(prop.getProperty("max.re"));
        Double maxIm = Double.valueOf(prop.getProperty("max.im"));
        Complex max = new Complex(maxRe, maxIm);

        Integer width = Integer.valueOf(prop.getProperty("image.width"));
        Integer height = Integer.valueOf(prop.getProperty("image.height"));

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        ComputeFractal fractal = null;
        switch (type) {
            case "mandelbrot":
                fractal = new ComputeMandelbrot(min, max);
                break;
            case "julia":
                fractal = new ComputeJulia(min, max);
                break;
        }

        if (fractal != null) {
            fractal.compute(image);
        }

        Picture pic = new Picture(image);
        pic.display();
    }

}
