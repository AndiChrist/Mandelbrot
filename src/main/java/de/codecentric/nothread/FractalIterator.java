/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codecentric.nothread;

import java.awt.Color;
import java.util.logging.Logger;
import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author Andreas Christ <andreas.christ@codecentric.de>
 */
public class FractalIterator {

    private static final Logger LOGGER = Logger.getLogger(FractalIterator.class.getName());

    final static double MAX_INFINITY = 2.0;
    final static int MAX_ITERATION_STEPS = 30;//100;

    public static int iterate(Complex z, Complex c) {
        return iterate(z, c, MAX_ITERATION_STEPS);
    }

    public static int iterate(Complex z, Complex c, int maxIterationSteps) {
        return iterate(z, c, maxIterationSteps, MAX_INFINITY);

    }

    public static int iterate(Complex z, Complex c, int maxIterationSteps, double maxInfinity) {
        int i = 0;

//        while (z.abs() * z.abs() <= maxInfinity && ++i < maxIterationSteps) {
        while (z.abs() <= maxInfinity && i++ < maxIterationSteps) {
            z = z.multiply(z).add(c);
        }

        //if (i >= maxIterationSteps) {
        //    return Color.BLACK.getRGB();
        //}
        //double hue = i - Math.log(Math.log(z.abs()) / Math.log(4)) / Math.log(2);
        //return Color.HSBtoRGB((float) hue, 1.0f, 1.0f);
        //return (i % 2 == 0) ? Color.black.getRGB() : Color.white.getRGB();
        return Color.HSBtoRGB(0.5f * i / maxIterationSteps, 1.0f, 1.0f);
        /*
         double bright = (i % maxIterationSteps) / (0.0 + maxIterationSteps);
         if (bright < 0.5) {
         bright = 1.0 - 1.2 * bright;
         } else {
         bright = -0.2 + 1.2 * bright;
         }
         int cidx = Color.HSBtoRGB(0.0f, 0.0f, (float) (bright));
         return new Color(cidx).getRGB();
         */

        /*
         double hue = (i % maxIterationSteps) / (0.0 + maxIterationSteps);
         hue = (hue < 0.8) ? hue + 0.2 : hue - 0.8;
         int cidx = Color.HSBtoRGB((float) (hue), 1.0f, 1.0f);
         return new Color(cidx).getRGB();
         */
    }

}
