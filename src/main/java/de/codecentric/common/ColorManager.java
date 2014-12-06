/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codecentric.common;

import java.awt.Color;
import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author Andreas Christ <andreas.christ@codecentric.de>
 */
public class ColorManager {

    public static int getMap(int i) {
        return ColorMap.getColor(i).getRGB();
    }

    public static int HSBtoRGB(int i, int maxIterationSteps) {
        return Color.HSBtoRGB(0.5f * i / maxIterationSteps, 1.0f, 1.0f);
    }

    public static int hueColor(int i, Complex z) {
        double hue = i - Math.log(Math.log(z.abs()) / Math.log(4)) / Math.log(2);
        return Color.HSBtoRGB((float) hue, 1.0f, 1.0f);
    }

    public static int hueColor2(int i, int maxIterationSteps) {
        double hue = (i % maxIterationSteps) / (0.0 + maxIterationSteps);
        hue = (hue < 0.8) ? hue + 0.2 : hue - 0.8;
        int cidx = Color.HSBtoRGB((float) (hue), 1.0f, 1.0f);
        return new Color(cidx).getRGB();
    }

    public static int blackWhite(int i) {
        return (i % 2 == 0) ? Color.black.getRGB() : Color.white.getRGB();
    }

    public static int brighter(int i, int maxIterationSteps) {
        double bright = (i % maxIterationSteps) / (0.0 + maxIterationSteps);
        if (bright < 0.5) {
            bright = 1.0 - 1.2 * bright;
        } else {
            bright = -0.2 + 1.2 * bright;
        }
        int cidx = Color.HSBtoRGB(0.0f, 0.0f, (float) (bright));
        return new Color(cidx).getRGB();
    }

}
