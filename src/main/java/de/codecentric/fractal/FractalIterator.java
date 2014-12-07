/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codecentric.fractal;

import java.util.logging.Logger;
import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author Andreas Christ <andreas.christ@codecentric.de>
 */
public class FractalIterator {

    private static final Logger LOGGER = Logger.getLogger(FractalIterator.class.getName());

    public static int iterate(Complex z, Complex c, int maxIterationSteps, double maxInfinity) {
        int i = 0;

        while (z.abs() <= maxInfinity && i++ < maxIterationSteps) {
            z = z.multiply(z).add(c);
        }

        return i;

    }

}
