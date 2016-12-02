/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.andichrist.fractal.strategies;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ForkJoinPool;
import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author Andreas Christ <andreas.christ@sixt.com>
 */
public class FractalInvoker extends ComputeFractal {

    private static final String MANDELBROTTASK_PROPERTIES = "mandelbrottask.properties";

    private static Complex min;
    private static Complex max;

    private static double infinity;
    private static int iteration;

    private Properties properties;

    public FractalInvoker() {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(MANDELBROTTASK_PROPERTIES)) {
            properties.load(fis);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        min = readComplexProp(properties.getProperty("min.re"), properties.getProperty("min.im"));
        max = readComplexProp(properties.getProperty("max.re"), properties.getProperty("max.im"));

        infinity = Double.parseDouble(properties.getProperty("infinity"));
        iteration = Integer.valueOf(properties.getProperty("max.iteration"));

    }

    @Override
    public void compute(int[][] image) {
        ComputeMandelbrotRecursive task = new ComputeMandelbrotRecursive(image);

        task.setMin(min);
        task.setMax(max);
        task.setInfinity(infinity);
        task.setIteration(iteration);

        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(task);
    }

    @Override
    public int getWidth() {
        return Integer.valueOf(properties.getProperty("image.width"));
    }

    @Override
    public int getHeight() {
        return Integer.valueOf(properties.getProperty("image.height"));
    }

}
