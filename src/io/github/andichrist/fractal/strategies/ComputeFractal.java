/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.andichrist.fractal.strategies;

import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author Andreas Christ <andreas.christ@sixt.com>
 */
public abstract class ComputeFractal {

    public abstract void compute(int[][] image);

    public abstract int getWidth();

    public abstract int getHeight();


    /**
     * reads coordinate from property file
     *
     * @param re Real value
     * @param im Imaginary value
     * @return coordinate as complex object
     */
    Complex readComplexProp(String re, String im) {
        Double doubleRe = Double.valueOf(re);
        Double doubleIm = Double.valueOf(im);
        return new Complex(doubleRe, doubleIm);
    }
}
