/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.andichrist.fractal;

import io.github.andichrist.common.PicturePanel;
import io.github.andichrist.fractal.strategies.ComputeJulia;
import io.github.andichrist.fractal.strategies.ComputeMandelbrot;
import io.github.andichrist.fractal.strategies.FractalInvoker;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author Andreas Christ <andreas.christ@sixt.com>
 */
public class Fraktal {

    private final static Logger LOGGER = Logger.getLogger(Fraktal.class.getName());

    private static final FraktalStrategy strategy = new FraktalStrategy();

    public static void main(String[] args) throws IOException {

        FileInputStream fis =  new FileInputStream("logging.properties");
        LogManager.getLogManager().readConfiguration(fis);
        System.out.println(LOGGER.getLevel());

        Fraktal fraktal = new Fraktal();

        String type = "mandelbrottask";
        LOGGER.info("render " + type);
        // set rendering strategy
        switch (type) {
            case "mandelbrot":
                strategy.setStrategy(new ComputeMandelbrot());
                break;
            case "julia":
                strategy.setStrategy(new ComputeJulia());
                break;
            case "mandelbrottask":
                strategy.setStrategy(new FractalInvoker());
                break;
        }
        
        // compute raw image data
        int[][] image = fraktal.computeFractal();
        
        // show image
        new PicturePanel(image).display();

    }

    private int[][] computeFractal() {
        int[][] image = new int[strategy.getStrategy().getWidth()][strategy.getStrategy().getHeight()];

        strategy.compute(image);

        return image;
    }

}
