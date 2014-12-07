/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codecentric.fractal.strategies;

import java.awt.image.BufferedImage;
import java.util.concurrent.ForkJoinPool;
import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author Andreas Christ <andreas.christ@codecentric.de>
 */
public class FractalInvoker implements ComputeFractal {

    static Complex min;
    static Complex max;

    static double infinity;
    static int iteration;

    public FractalInvoker(Complex min, Complex max) {
        FractalInvoker.min = min;
        FractalInvoker.max = max;
    }

    @Override
    public void compute(BufferedImage image) {
        ComputeMandelbrotRecursive task = new ComputeMandelbrotRecursive(image);
        task.setMin(min);
        task.setMax(max);
        task.setInfinity(infinity);
        task.setIteration(iteration);

        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(task);
    }

    @Override
    public void setInfinity(Double infinity) {
        FractalInvoker.infinity = infinity;
    }

    @Override
    public void setIteration(Integer iteration) {
        FractalInvoker.iteration = iteration;
    }

}
