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
public class Mandelbrot implements FractalStrategy {

    private final static Logger LOG = Logger.getLogger(Mandelbrot.class.getName());

    private static final String MANDELBROT_PROPERTIES = "mandelbrot.properties";

    private final Double aCorner;
    private final Double bCorner;

    private final double sideWidth;
    private final double sideHeight;


    private final int width;
    private final int height;
    private final int maxIterations;
    private final double infinity;

    public Mandelbrot(int width, int height, int maxIterations, double infinity) {
        Properties properties = new Properties();
        try (InputStream fis = Mandelbrot.class.getClassLoader().getResourceAsStream(MANDELBROT_PROPERTIES)) {
            properties.load(fis);
        } catch (IOException e) {
            LOG.log(Level.WARNING, "Failed to load properties file", e);
        }

        this.width = width;
        this.height = height;
        this.maxIterations = maxIterations;
        this.infinity = infinity;

        this.aCorner = Double.parseDouble(properties.getProperty("min.re"));
        this.bCorner = Double.parseDouble(properties.getProperty("min.im"));
        double maxRe = Double.parseDouble(properties.getProperty("max.re"));
        double maxIm = Double.parseDouble(properties.getProperty("max.im"));

        this.sideWidth = (maxRe - aCorner) / width;
        this.sideHeight = (maxIm - bCorner) / height;
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

                Complex c = new Complex(re, im);
                Complex z = Complex.ZERO;

                int iteration = 0;

                while (z.abs() <= infinity && iteration++ <= maxIterations) {
                    z = z.multiply(z).add(c);
                }

                /*
                 * Pixels for which the size of z reaches 2 after only a few iterations are colored red.
                 * Pixels for which the size of z reaches 2 after relatively many iterations are colored violet,
                 * at the other end of the spectrum.
                 * Pixels for which the size of z is less than 2 even after 1,000 iterations are assumed to lie
                 * in the MandelbrotOldSchool set; they are colored black.
                 */
                pic[m * width + n] = iteration;
            }
        }

        return pic;
    }
}
