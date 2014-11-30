package de.codecentric.example;

/**
 * ***********************************************************************
 * Compilation: javac ColorMandelbrot.java
 *
 * Execution: java Mandelbrot xmid ymid size < colors.txt
 *
 * Dependencies: Picture.java StdIn.java
 *
 * Plots the Mandelbrot set in color.
 *
 * % java ColorMandelbrot -.5 0 2
 *
 *  // increase dwell
 *
 * % java ColorMandelbrot -0.7615134027775 0.0794865972225 0.0032285925920
 *
 * -1.5 -1.0 2.0 2.0
 *
 * 0.10259 -0.641 0.0086 0.0086
 * ***********************************************************************
 */
import de.codecentric.common.Picture;
import java.awt.Color;
import java.io.IOException;
import org.apache.commons.math3.complex.Complex;

public class ColorMandelbrot {

    // return number of iterations to check if c = a + ib is in Mandelbrot set
    public static int mand(Complex z0, int d) {
        Complex z = z0;
        for (int t = 0; t < d; t++) {
            if (z.abs() > 2.0) {
                return t;
            }
            z = z.multiply(z).add(z0);
        }
        return d;
    }

    public static void main(String[] args) throws IOException {
        double xc = 0.10259; // Double.parseDouble(args[0]);
        double yc = -0.641; // Double.parseDouble(args[1]);
        double size = 0.0086; // Double.parseDouble(args[2]);

        int N = 512;

        int ITERS = 256;

        // compute Mandelbrot set
        Picture pic = new Picture(N, N);

        for (int i = 0; i < N; i++) {
            double x = xc - size / 2 + size * i / N;
            for (int j = 0; j < N; j++) {
                double y = yc - size / 2 + size * j / N;
                Complex z0 = new Complex(x, y);
                int t = mand(z0, ITERS - 1);
                pic.set(i, N - 1 - j, Color.HSBtoRGB(0.5f * t / ITERS, 1.0f, 1.0f));
                //pic.set(i, j, ColorMap.getColor(t));

            }
        }

        pic.show();
    }

}
