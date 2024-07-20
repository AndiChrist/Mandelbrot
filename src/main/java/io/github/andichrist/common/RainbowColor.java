/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.andichrist.common;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Andreas Christ <andichrist@gmx.de>
 */
public class RainbowColor {

    private static Color[] colors;

    private static final Color[] PALETTE = {
            Color.WHITE,
            Color.RED,
            Color.ORANGE,
            Color.YELLOW,
            Color.GREEN,
            Color.CYAN,
            Color.BLUE,
            Color.MAGENTA
    };

    private int[] pic;
    private int maxIterations;

    public RainbowColor(int[] pic, int maxIterations) {
        this.pic = pic;
        this.maxIterations = maxIterations;

        this.colors = getColorGradient(maxIterations, PALETTE);
    }

    public static int color(int pixel, int maxIterations) {
        if (pixel >= maxIterations)
            return Color.BLACK.getRGB();
        else
            return colors[pixel].getRGB();
    }

    private static Color[] getColorGradient(int maxSteps, Color... colors) {
        int sections = colors.length - 1;

        if (sections <= 0)
            throw new IllegalArgumentException("At least 2 colors required.");

        var gradient = new ArrayList<Color>(maxSteps);
        gradient.add(colors[0]);

        for (int i = 0; i < sections; i++) {
            Color[] nextGradient = getColorsBetween(colors[i], colors[i + 1], (int) Math.ceil(1.0 * maxSteps / sections));

            gradient.addAll(Arrays.asList(nextGradient));
        }

        return gradient.toArray(new Color[0]);
    }

    private static Color[] getColorsBetween(Color color1, Color color2, int steps) {
        if (steps < 2)
            throw new IllegalArgumentException("At least 2 steps are required for a color gradient.");

        Color[] colors = new Color[steps];
        colors[colors.length - 1] = color2;

        double alpha = 1d / (double) steps;

        for (int i = 0; i < steps - 1; i++) {
            colors[i] = mix(color1, color2, alpha * (double) i);
        }

        return colors;
    }

    private static Color mix(Color color1, Color color2, double alpha) {
        return new Color(
                (int) (color1.getRed()   + (color2.getRed()   - color1.getRed())   * alpha),
                (int) (color1.getGreen() + (color2.getGreen() - color1.getGreen()) * alpha),
                (int) (color1.getBlue()  + (color2.getBlue()  - color1.getBlue())  * alpha));

    }
}
