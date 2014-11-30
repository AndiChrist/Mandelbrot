/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codecentric.nothread;

import de.codecentric.common.ImageDimension;
import java.util.logging.Logger;

/**
 *
 * @author Andreas Christ <andreas.christ@codecentric.de>
 */
public class Fraktal {

    private final static Logger LOGGER = Logger.getLogger(Fraktal.class.getName());

    private static final ImageDimension dimension = new ImageDimension();

    private static final int[] bild = new int[dimension.getWidth() * dimension.getHeight()];

    public static void main(String[] args) {

        MandelPanel panel = new MandelPanel();
        //ComputeJulia.berechne(bild, dimension);
        ComputeMandelbrot.berechne(bild, dimension);
        panel.zeige(bild, dimension);
    }

}
