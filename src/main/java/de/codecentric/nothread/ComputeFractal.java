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
public interface ComputeFractal {

    public void compute(BufferedImage image);

    public void setInfinity(Double infinity);

    public void setIteration(Integer iteration);

}
