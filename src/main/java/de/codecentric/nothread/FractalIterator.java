/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codecentric.nothread;

import de.codecentric.common.ColorManager;
import java.util.logging.Logger;
import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author Andreas Christ <andreas.christ@codecentric.de>
 */
public class FractalIterator {

    private static final Logger LOGGER = Logger.getLogger(FractalIterator.class.getName());

    final static double MAX_INFINITY = 2.0;
    final static int MAX_ITERATION_STEPS = 100;

    public static int iterate(Complex z, Complex c) {
        return iterate(z, c, MAX_ITERATION_STEPS);
    }

    public static int iterate(Complex z, Complex c, int maxIterationSteps) {
        return iterate(z, c, maxIterationSteps, MAX_INFINITY);

    }

    public static int iterate(Complex z, Complex c, int maxIterationSteps, double maxInfinity) {
        int i = 0;

        while (z.abs() <= maxInfinity && i++ < maxIterationSteps) {
            z = z.multiply(z).add(c);
        }

        return ColorManager.HSBtoRGB(i, maxIterationSteps);

    }

}
