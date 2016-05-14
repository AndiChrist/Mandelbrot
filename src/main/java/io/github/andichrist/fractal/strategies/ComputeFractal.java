/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.andichrist.fractal.strategies;

/**
 *
 * @author Andreas Christ <andreas.christ@codecentric.de>
 */
public interface ComputeFractal {

    public void compute(int[][] image);

    public void setInfinity(Double infinity);

    public void setIteration(Integer iteration);

}
