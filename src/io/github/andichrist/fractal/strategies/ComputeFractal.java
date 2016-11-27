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
public interface ComputeFractal {

    void compute(int[][] image);

    void setInfinity(Double infinity);

    void setIteration(Integer iteration);

    void setMin(Complex min);

    void setMax(Complex max);

}
