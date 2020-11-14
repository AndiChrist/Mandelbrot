package io.github.andichrist.common;

import io.github.andichrist.Mandelbrot;
import java.util.logging.Logger;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by andichrist on 09.12.16.
 */
public class ColorUtils {

    private final static Logger LOGGER = Logger.getLogger(ColorUtils.class.getName());

    /**
     * Return a color that blends into the background. i.e. mix(color,contrast(color),0.5)
     */
    public static Color blend(Color color) {
        return blend(color, 0.5);
    }

    /**
     * Return a color that blends into the background. i.e. mix(color,contrast(color),alpha) alpha varies between 0.0 (use color only) and 1.0 (use
     * contrast(color) only).
     */
    private static Color blend(Color color, double alpha) {
        return mix(color, contrast(color), alpha);
    }

    /**
     * Returns a mix of color and black or white, whichever has the higher contrast to color.
     */
    public static Color blendBlackOrWhite(Color color) {
        return blendBlackOrWhite(color, 0.5);
    }

    /**
     * Returns a mix of color and black or white, whichever has the higher contrast to color.
     *
     * alpha varies between 0.0 (use color only) and 1.0 (use black/white only).
     */
    private static Color blendBlackOrWhite(Color color, double alpha) {
        return mix(color, contrastBlackOrWhite(color), alpha);
    }

    /**
     * Converts a Color object to a hex String representation.
     *
     * Padded to 6 digits for html (fix by Bruce Pascal).
     */
    public static String color2Hex(Color c) {
        String hexString = Integer.toHexString(c.getRGB() & 0x00ffffff);
        return String.format("%1$6s", hexString).replace(" ", "0");
    }

    /**
     * Returns a color that has the highest possible contrast to the input color.
     *
     * The red, green, and blue values are set to 0 if the corresponding value in the input is above (0.5 * 255) and to 255 otherwise.
     */
    private static Color contrast(Color color) {
        return contrast(color, 0.5);
    }

    /**
     * Returns a color that has the highest possible contrast to the input color.
     *
     * The red, green, and blue values are set to 0 if the corresponding value in the input is above (alpha * 255) and to 255 otherwise.
     *
     * alpha varies between 0.0 and 1.0
     */
    private static Color contrast(Color color, double alpha) {
        if ((alpha < 0.0) || (alpha > 1.0)) {
            alpha = 0.5;
        }

        int contrast = (int) (255 * alpha);

        int[] rgb = new int[] { color.getRed(), color.getGreen(), color.getBlue() };

        for (int i = 0; i < rgb.length; i++) {
            rgb[i] = (rgb[i] > contrast) ? 0 : 255;
        }

        return new Color(rgb[0], rgb[1], rgb[2]);
    }

    /**
     * Returns black if the grey value of color is above (0.5 * 255) and white otherwise.
     */
    private static Color contrastBlackOrWhite(Color color) {
        return contrastBlackOrWhite(color, 0.5);
    }

    /**
     * Returns black if the grey value of color is above (alpha * 255) and white otherwise.
     */
    private static Color contrastBlackOrWhite(Color color, double alpha) {
        if ((alpha < 0.0) || (alpha > 1.0)) {
            alpha = 0.5;
        }
        return contrast(toGreyScale(color), alpha);
    }

    /**
     * Returns a darker version of color, i.e. returns mix(color,[0,0,0],0.5).
     */
    public static Color dark(Color color) {
        return dark(color, 0.5);
    }

    /**
     * Returns a lighter version of color, i.e. returns mix(color,[0,0,0],alpha).
     *
     * alpha varies between 0.0 (use color only) and 1.0 (use [0,0,0] only).
     */
    private static Color dark(Color color, double alpha) {
        return mix(color, Color.BLACK, alpha);
    }

    /**
     * Uses <i>mix</i> to obtain a gradient between the provided Colors.
     */
    static Color[] getColorGradient(int maxSteps, Color... colors) {
        int sections = colors.length - 1;

        LOGGER.info("sections: " + sections);
        LOGGER.info("maxSteps: " + maxSteps);
        LOGGER.info("maxSteps/sections: " + maxSteps/sections);
        LOGGER.info("ceil: " + (int) Math.ceil(1.0 * maxSteps / sections));

        if (sections <= 0)
            throw new IllegalArgumentException("At least 2 colors required.");

        List<Color> gradient = new ArrayList<>(maxSteps);
        gradient.add(colors[0]);

        for (int i = 0; i < sections; i++) {
            Color[] nextGradient = getColorsBetween(colors[i], colors[i + 1], (int) Math.ceil(1.0 * maxSteps / sections));

            gradient.addAll(Arrays.asList(nextGradient));
        }
        LOGGER.info("gradient.size(): " + gradient.size());
        return gradient.toArray(new Color[0]);
    }

    /**
     * Uses <i>mix</i> to obtain a gradient between color1 and color2.
     */
    private static Color[] getColorsBetween(Color color1, Color color2, int steps) {
        if (steps < 2)
            throw new IllegalArgumentException("At least 2 steps are required for a color gradient.");

        Color[] colors = new Color[steps];
        //colors[0] = color1;
        colors[colors.length - 1] = color2;

        double alpha = 1d / (double) steps;

        for (int i = 0; i < steps - 1; i++) {
            colors[i] = mix(color1, color2, alpha * (double) i);
//            System.out.println(String.format("#%02x%02x%02x", colors[i].getRed(), colors[i].getGreen(), colors[i].getBlue()));
        }
//        System.out.println(String.format("#%02x%02x%02x", colors[colors.length - 1].getRed(), colors[colors.length - 1].getGreen(), colors[colors.length - 1].getBlue()));

        return colors;
    }


    /**
     * Y = 0.2126 R + 0.7152 G + 0.0722 B
     * http://en.wikipedia.org/wiki/Luminance_%28relative%29
     * added by Bruce Pascal (b pascal at scripps dot edu)
     */
    public static double getLuminosityFunction(Color color) {
        double luminosityFunction = color.getRed() * 0.2126 + color.getGreen() * 0.7152 + color.getBlue() * 0.0722;
        return luminosityFunction;
    }

    /**
     * Uses <i>getSpectrum</i> to obtain a gradient from Red to Blue.
     */
    public static Color[] getRedtoBlueSpectrum(int length) {
        return getSpectrum(length, 0.0, 0.7);
    }

    /**
     * Uses <i>getSpectrum</i> to obtain a gradient from Red to Green.
     */
    public static Color[] getRedtoGreenSpectrum(int length) {
        return getSpectrum(length, 0.0, 0.3);
    }

    /**
     * Uses <i>getSpectrum</i> to obtain a gradient from Red to Yellow.
     */
    public static Color[] getRedtoYellowSpectrum(int length) {
        return getSpectrum(length, 0.0, 0.20);
    }

    /**
     * Obtain a gradient of colors using the HSB color space.
     */
    private static Color[] getSpectrum(int length, double lowerBound, double upperBound) {
        if (length < 2)
            length = 2;

        Color[] colors = new Color[length];

        int ii = length;
        double ds = (upperBound - lowerBound) / (double) length;

        for (double dd = 0; dd < (upperBound - lowerBound) && ii > 0; dd += ds) {
            colors[--ii] = Color.getHSBColor((float) dd, 1.0f, 1.0f);
        }
        return colors;
    }

    /**
     * Converts a hex Sting into a Color object.
     */
    public static Color hex2Color(String hex) {
        return new Color(Integer.valueOf(hex, 16));
    }

    /**
     * Returns the inverse of color.
     */
    public static Color invert(Color color) {
        return new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue());
    }

    /**
     * Returns a lighter version of color, i.e. returns mix(color,[255,255,255],0.5).
     */
    public static Color light(Color color) {
        return light(color, 0.5);
    }

    /**
     * Returns a lighter version of color, i.e. returns mix(color,[255,255,255],alpha).
     *
     * alpha varies between 0.0 (use color only) and 1.0 (use [255,255,255] only).
     */
    private static Color light(Color color, double alpha) {
        return mix(color, Color.WHITE, alpha);
    }

    /**
     * Returns a color that is halfway between color1 and color2.
     */
    public static Color mix(Color color1, Color color2) {
        return mix(color1, color2, 0.5);
    }

    /**
     * Returns a color that is the mixture of color1 and color2.
     *
     * alpha varies between 0.0 (use color1 only) and 1.0 (use color2 only).
     */
    private static Color mix(Color color1, Color color2, double alpha) {
        return new Color(
                (int) (color1.getRed()   + (color2.getRed()   - color1.getRed())   * alpha),
                (int) (color1.getGreen() + (color2.getGreen() - color1.getGreen()) * alpha),
                (int) (color1.getBlue()  + (color2.getBlue()  - color1.getBlue())  * alpha));

    }

    /**
     * Returns a color that is on the opposite side of the color wheel but roughly keeps the saturation and lightness.
     */
    public static Color opposite(Color color) {
        int min = Math.min(Math.min(color.getRed(), color.getGreen()), color.getBlue());
        int max = Math.max(Math.max(color.getRed(), color.getGreen()), color.getBlue());
        int[] rgb = new int[] { color.getRed(), color.getGreen(), color.getBlue() };
        for (int ii = 0; ii < rgb.length; ii++) {
            rgb[ii] = max - rgb[ii] + min;
        }
        return new Color(rgb[0], rgb[1], rgb[2]);
    }

    /**
     * Rounds each component to to the nearest number determined by dividing the range 0..255 into value_count+1 portions.
     *
     * Values of <i>count</i> that are one higher than divisors of 255 yield the best results (e.g. 3+1, 5+1, 7+1, 9+1, 15+1, 17+1, ...).
     */
    public static Color round(Color color, int count) {
        int[] rgb = new int[] { color.getRed(), color.getGreen(), color.getBlue() };
        count--;
        for (int ii = 0; ii < rgb.length; ii++) {
            rgb[ii] = (int) (rgb[ii] * count / 255 + 0.5);
            rgb[ii] = (int) (rgb[ii] * 255 / count + 0.5);
        }
        return new Color(rgb[0], rgb[1], rgb[2]);
    }

    /**
     * Converts color to greyscale.
     */
    private static Color toGreyScale(Color color) {
        int rgb = (int) (((double) color.getRed() * 0.299) + ((double) color.getGreen() * 0.587) + ((double) color.getBlue() * 0.114));
        if (rgb > 255)
            rgb = 255;
        return new Color(rgb, rgb, rgb);
    }

}
