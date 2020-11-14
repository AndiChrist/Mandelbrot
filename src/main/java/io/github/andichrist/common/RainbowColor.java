/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.andichrist.common;

import java.awt.Color;

/**
 * @author Andreas Christ <andreas.christ@sixt.com>
 */
public class RainbowColor {

    private static Color[] colors;

    public static int color(int i, int maxIterationSteps) {
        if (colors == null || colors.length == 0) {
            colors = ColorUtils.getColorGradient(maxIterationSteps,
                    Color.decode("#FF0000"),    // RED
                    Color.decode("#FF8000"),    // ORANGE
                    Color.decode("#FFFF00"),    // YELLOW
                    Color.decode("#00FF00"),    // GREEN
                    Color.decode("#0000FF"),    // BLUE
                    Color.decode("#4B0082"),    // INDIGO
                    Color.decode("#7F00FF")     // VIOLET
            );
        }

        if (i >= maxIterationSteps)
            return Color.BLACK.getRGB();
        else
            return colors[i].getRGB();
    }

    public static int colorInverse(int i, int maxIterationSteps) {
        if (colors == null || colors.length == 0) {
            colors = ColorUtils.getColorGradient(maxIterationSteps,
                    Color.decode("#FF0000"),    // RED
                    Color.decode("#FF8000"),    // ORANGE
                    Color.decode("#FFFF00"),    // YELLOW
                    Color.decode("#00FF00"),    // GREEN
                    Color.decode("#0000FF"),    // BLUE
                    Color.decode("#4B0082"),    // INDIGO
                    Color.decode("#7F00FF")     // VIOLET
            );
        }

        if (i >= maxIterationSteps)
            return Color.BLACK.getRGB();
        else
            return colors[maxIterationSteps - i].getRGB();
    }

}
