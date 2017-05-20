/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.andichrist;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

import io.github.andichrist.common.RainbowColor;
import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author Andreas Christ <andreas.christ@sixt.com>
 */
public class Mandelbrot extends JPanel {

    private final static Logger LOGGER = Logger.getLogger(Mandelbrot.class.getName());

    private static final String MANDELBROT_PROPERTIES = "mandelbrot.properties";


    private final Double aCorner;
    private final Double bCorner;

    private final double sideWidth;
    private final double sideHeight;

    private final double infinity;
    private final int maxIterations;

    private static Image image;

    private Properties properties;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Mandelbrot m = new Mandelbrot();

        int[] pic = m.compute();
        pic = filter(pic, m.maxIterations);
        image = m.getImageFromArray(pic, m.getWidth(), m.getHeight());

        m.showImage();
        m.saveImage();
    }

    private static int[] filter(int[] pic, int maxIterations) {
        int[] coloredImage = new int[pic.length];
        for (int i=0; i<pic.length; i++) {
            coloredImage[i] = RainbowColor.color(pic[i], maxIterations);
        }

        return coloredImage;
    }

    private void showImage() {
        JFrame window = new JFrame("MandelbrotOldSchool");
        window.add(this);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setSize(this.getWidth(), this.getHeight());
        window.setVisible(true);
    }

    public Mandelbrot() {

        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(MANDELBROT_PROPERTIES)) {
            properties.load(fis);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int width = Integer.valueOf(properties.getProperty("image.width"));
        int height = Integer.valueOf(properties.getProperty("image.height"));

        this.setBounds(0, 0, width, height);

        this.aCorner = Double.parseDouble(properties.getProperty("min.re"));
        this.bCorner = Double.parseDouble(properties.getProperty("min.im"));
        double maxRe = Double.parseDouble(properties.getProperty("max.re"));
        double maxIm = Double.parseDouble(properties.getProperty("max.im"));


        this.sideWidth = (maxRe - aCorner) / width;
        this.sideHeight = (maxIm - bCorner) / height;

        this.infinity = Double.parseDouble(properties.getProperty("infinity"));
        this.maxIterations = Integer.valueOf(properties.getProperty("max.iteration"));
    }


    private int[] compute() {
        int[] pic = new int[getWidth() * getHeight()];

        for (int m = 0; m < getHeight(); m++) {
            double im = bCorner + m * sideHeight;
            for (int n = 0; n < getWidth(); n++) {
                double re = aCorner + n * sideWidth;

                Complex c = new Complex(re, im);
                Complex z = Complex.ZERO;

                int count = 0;

                while (z.abs() <= infinity && count++ <= maxIterations) {
                    z = z.multiply(z).add(c);
                }

                /*
                 * Pixels for which the size of z reaches 2 after only a few iterations are colored red.
                 * Pixels for which the size of z reaches 2 after relatively many iterations are colored violet,
                 * at the other end of the spectrum.
                 * Pixels for which the size of z is less than 2 even after 1,000 iterations are assumed to lie
                 * in the MandelbrotOldSchool set; they are colored black.
                 */
                pic[m * getWidth() + n] = count;
            }
        }

        return pic;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        //Paint it on screen
        g2d.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        g2d.dispose();
    }

    private Image getImageFromArray(int[] pixels, int width, int height) {
        MemoryImageSource mis = new MemoryImageSource(width, height, pixels, 0, width);
        Toolkit tk = Toolkit.getDefaultToolkit();
        return tk.createImage(mis);
    }

    private void saveImage() {
        BufferedImage bufferedImage = new BufferedImage(
                image.getWidth(this),
                image.getHeight(this),
                BufferedImage.TYPE_INT_RGB);

        bufferedImage.getGraphics().drawImage(image,0,0, image.getWidth(this), image.getHeight(this), this);

        File f = new File("./output.png");
        try {
            ImageIO.write(bufferedImage, "png", f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
