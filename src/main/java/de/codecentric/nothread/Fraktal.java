/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codecentric.nothread;

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
        Properties prop = new Properties();
        prop.load(Fraktal.class.getClassLoader().getResourceAsStream("fraktal.properties"));

        String type = prop.getProperty("type");

        Double minRe = Double.valueOf(prop.getProperty("min.re"));
        Double minIm = Double.valueOf(prop.getProperty("min.im"));
        Complex min = new Complex(minRe, minIm);

        Double maxRe = Double.valueOf(prop.getProperty("max.re"));
        Double maxIm = Double.valueOf(prop.getProperty("max.im"));
        Complex max = new Complex(maxRe, maxIm);

        Integer width = Integer.valueOf(prop.getProperty("image.width"));
        Integer height = Integer.valueOf(prop.getProperty("image.height"));

        MandelPanel panel = new MandelPanel();
        //ComputeJulia.berechne(bild, dimension);

        int[] bild = new int[width * height];

        ComputeFractal fractal = null;
        switch (type) {
            case "Mandelbrot":
                fractal = new ComputeMandelbrot(min, max);
                break;
            case "Julia":
                fractal = new ComputeJulia(min, max);
                break;
        }

        if (fractal != null) {
            fractal.berechne(bild, width, height);
        }

        panel.zeige(bild, width, height);
    }

}
