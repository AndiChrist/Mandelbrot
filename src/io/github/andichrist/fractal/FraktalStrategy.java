/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.andichrist.fractal;

import io.github.andichrist.fractal.strategies.ComputeFractal;
import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author Andreas Christ <andreas.christ@sixt.com>
 */
public class FraktalStrategy {

    private ComputeFractal strategy = null;

    public void setStrategy(final ComputeFractal strategy) {
        this.strategy = strategy;
    }

    public ComputeFractal getStrategy() {
        return this.strategy;
    }

    public void compute(int[][] image) {
        strategy.compute(image);
    }

}
