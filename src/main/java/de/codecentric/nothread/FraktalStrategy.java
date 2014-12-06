/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codecentric.nothread;

import java.awt.image.BufferedImage;

/**
 *
 * @author Andreas Christ <andreas.christ@codecentric.de>
 */
class FraktalStrategy {

    private ComputeFractal strategy = null;

    public void setStrategy(final ComputeFractal STRATEGY) {
        strategy = STRATEGY;
    }

    void compute(BufferedImage image) {
        if (strategy != null) {
            strategy.compute(image);
        }
    }

    void setInfinity(double infinity) {
        if (strategy != null) {
            strategy.setInfinity(infinity);
        }
    }

    void setIteration(int iteration) {
        if (strategy != null) {
            strategy.setIteration(iteration);
        }
    }

}
