package de.codecentric.example;

/**
 * ***********************************************************************
 * Compilation: javac ColorJulia.java
 *
 * Execution: java ColorJulia a b Dependencies: Picture.java
 *
 * Plots the Julia for the complex point c = a + ib.
 *
 * The set of points in the Julia set is connected if and only if c is in the
 * Mandelbrot set.
 *
 * % java ColorJulia -0.75 0.1
 *
 * % java ColorJulia -1.25 0
 *
 * % java ColorJulia 0.1 0.7
 *
 *
 * -1.25 0.00
 *
 * -0.75 0.10
 *
 ************************************************************************
 */
import de.codecentric.common.PicturePanel;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import org.apache.commons.math3.complex.Complex;

public class ColorJulia {

    // return number of iterations to check z is in the Julia set of c
    static int julia(Complex c, Complex z, int ITERS) {
        for (int t = 0; t < ITERS; t++) {
            if (z.abs() > 2.0) {
                return t;
            }
            z = z.multiply(z).add(c);
        }
        return ITERS - 1;
    }

    public static void main(String[] args) throws IOException {
        double real = -.75; // Double.parseDouble(args[0]);      // a
        double imag = 0.1; // Double.parseDouble(args[1]);      // b

        Complex c = new Complex(real, imag);            // c = a + ib

        double xmin = -1.8;
        double ymin = -1.8;
        double width = 3.6;
        double height = 3.6;

        int N = 512;
        int ITERS = 100;

        BufferedImage bufferedImage = new BufferedImage(N, N, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < N; i++) {
            double x = xmin + i * width / N;
            for (int j = 0; j < N; j++) {
                double y = ymin + j * height / N;
                Complex z = new Complex(x, y);
                int t = julia(c, z, ITERS);

                bufferedImage.setRGB(i, N - 1 - j, Color.HSBtoRGB(0.5f * t / ITERS, 1.0f, 1.0f));
            }
        }

        new PicturePanel(bufferedImage).display();
    }

}
