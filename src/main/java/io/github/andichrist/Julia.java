package io.github.andichrist;

import io.github.andichrist.math.Complex;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andreas Christ <andichrist@gmx.de>
 */
@SuppressWarnings("unused")
public class Julia implements FractalStrategy {

    private final static Logger LOG = Logger.getLogger(Julia.class.getName());

    private static final String MANDELBROT_PROPERTIES = "julia.properties";

    private final Double aCorner;
    private final Double bCorner;

    private final double sideWidth;
    private final double sideHeight;

    private final int maxIterations;

    private final Complex c;

    private final int width;
    private final int height;

    public Julia(int width, int height, int maxIterations) {
        Properties properties = new Properties();
        try (InputStream fis = Julia.class.getClassLoader().getResourceAsStream(MANDELBROT_PROPERTIES)) {
            properties.load(fis);
        } catch (IOException e) {
            LOG.log(Level.WARNING, "Failed to load properties file", e);
        }

        this.width = width;
        this.height = height;
        this.maxIterations = maxIterations;

        this.aCorner = Double.parseDouble(properties.getProperty("min.re"));
        this.bCorner = Double.parseDouble(properties.getProperty("min.im"));
        double maxRe = Double.parseDouble(properties.getProperty("max.re"));
        double maxIm = Double.parseDouble(properties.getProperty("max.im"));

        this.sideWidth = (maxRe - aCorner) / width;
        this.sideHeight = (maxIm - bCorner) / height;

        double cRe = Double.parseDouble(properties.getProperty("c.re"));
        double cIm = Double.parseDouble(properties.getProperty("c.im"));

        this.c = new Complex(cRe, cIm);
    }


    /**
     * Z(n+1) = Zn^2 + C
     */
    public int[] compute() {
        var pic = new int[width * height];

        for (int m = 0; m < height; m++) {
            double im = bCorner + m * sideHeight;
            for (int n = 0; n < width; n++) {
                double re = aCorner + n * sideWidth;

                Complex z = new Complex(re, im);

                int iteration = 0;

                while (z.abs() <= 2.0 && iteration++ <= maxIterations) {
                    z = z.multiply(z).add(c);
                }

                pic[m * width + n] = iteration;
            }
        }

        return pic;
    }
}
