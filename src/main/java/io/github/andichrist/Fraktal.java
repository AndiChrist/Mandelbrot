/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.andichrist;

import io.github.andichrist.common.PicturePanel;
import io.github.andichrist.fractal.FraktalStrategy;
import io.github.andichrist.fractal.strategies.ComputeJulia;
import io.github.andichrist.fractal.strategies.ComputeMandelbrot;
import io.github.andichrist.fractal.strategies.FractalInvoker;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * @author Andreas Christ <andreas.christ@sixt.com>
 */
public class Fraktal {

    private static final Logger LOGGER;

    static {
        try (InputStream inputStream = Fraktal.class.getClassLoader().getResourceAsStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(inputStream);

        } catch (IOException e) {
            Logger.getGlobal().log(Level.SEVERE, "init logging system", e);
        }
        LOGGER = Logger.getLogger(Fraktal.class.getName());

        System.out.println("LOG LEVEL: " + LOGGER.getLevel());

    }

    private static final FraktalStrategy strategy = new FraktalStrategy();

    public static void main(String[] args) {

        Fraktal fraktal = new Fraktal();

        String type = "mandelbrottask";
        LOGGER.info("render " + type);
        // set rendering strategy
        switch (type) {
            case "mandelbrot" -> strategy.setStrategy(new ComputeMandelbrot());
            case "julia" -> strategy.setStrategy(new ComputeJulia());
            case "mandelbrottask" -> strategy.setStrategy(new FractalInvoker());
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
