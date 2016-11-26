/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.andichrist.common;

import java.awt.Color;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author Andreas Christ <andreas.christ@sixt.com>
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

    static ColorModel createPalette(int limit) {
        byte[] red = new byte[limit];
        byte[] green = new byte[limit];
        byte[] blue = new byte[limit];
        for (int n = 0; n < limit; n++) {
            Color color = Color.getHSBColor((float) (n % 64) / 64.0f,
                    0.6f + 0.4f * (float) Math.cos((float) n / 40.0f), 1.0f);
            red[n] = (byte) color.getRed();
            blue[n] = (byte) color.getBlue();
            green[n] = (byte) color.getGreen();
        }
        return new IndexColorModel(8, limit, red, green, blue);
    }

    public static ColorModel createPalette8Colors(int limit) {
        Color[] colors = {Color.red, Color.green, Color.blue,
            Color.cyan, Color.magenta, Color.yellow,
            Color.white, Color.black};

        byte[] red = new byte[limit];
        byte[] green = new byte[limit];
        byte[] blue = new byte[limit];
        for (int n = 0; n < limit; n++) {
            red[n] = (byte) colors[n % colors.length].getRed();
            blue[n] = (byte) colors[n % colors.length].getBlue();
            green[n] = (byte) colors[n % colors.length].getGreen();
        }
        return new IndexColorModel(8, limit, red, green, blue);
    }

    /*
    public static ColorModel createBlackWhite() {
        ColorModel cm = new IndexColorModel(1, 2,
                new byte[]{(byte) 0, (byte) 255},
                new byte[]{(byte) 0, (byte) 255},
                new byte[]{(byte) 0, (byte) 255});

        return cm;
    }
    */

}
