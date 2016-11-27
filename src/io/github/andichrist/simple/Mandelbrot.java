/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.andichrist.simple;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

import io.github.andichrist.common.PropertyLoader;
import org.apache.commons.math3.complex.Complex;

import static io.github.andichrist.common.PropertyLoader.getProperty;

/**
 *
 * @author Andreas Christ <andreas.christ@sixt.com>
 */
public class Mandelbrot extends JPanel {

    private final static Logger LOGGER = Logger.getLogger(Mandelbrot.class.getName());

    private final Double aEcke;
    private final Double bEcke;

    private final double seite;

    private final double spaltBreite;
    private final double spaltHöhe;

    private final int maxIterations;

    private static Image image;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Mandelbrot m = new Mandelbrot();

        int[] bild = m.berechne();
        image = m.getImageFromArray(bild, m.getWidth(), m.getHeight());

        m.showImage();
        m.saveImage();

    }

    private void showImage() {
        JFrame window = new JFrame("Mandelbrot");
        window.add(this);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setSize(this.getWidth(), this.getHeight());
        window.setVisible(true);
    }

    public Mandelbrot() {

        int width = Integer.valueOf(getProperty("image.width"));
        int height = Integer.valueOf(getProperty("image.height"));
        this.setBounds(0, 0, width, height);

        this.seite = Double.parseDouble(getProperty("seite"));
        this.aEcke = Double.parseDouble(getProperty("aEcke"));
        this.bEcke = Double.parseDouble(getProperty("bEcke"));

        this.spaltBreite = this.seite / this.getWidth();
        this.spaltHöhe = this.seite / this.getHeight();

        this.maxIterations = Integer.valueOf(getProperty("max.iteration"));
    }


    private int[] berechne() {
        int[] bild = new int[getWidth() * getHeight()];

        for (int m = 0; m < getHeight(); m++) {
            double im = bEcke + m * spaltHöhe;
            for (int n = 0; n < getWidth(); n++) {
                double re = aEcke + n * spaltBreite;

                Complex c = new Complex(re, im);
                Complex z = Complex.ZERO;

                int zähler = 0;

                do {
                    z = z.multiply(z).add(c);
                    zähler++;
                } while (z.abs() <= 2.0 && zähler <= maxIterations);

                // switch for color
                bild[m * getWidth() + n] = farbe(zähler);
            }
        }

        return bild;
    }

    private int farbe(int zähler) {
        return Color.HSBtoRGB(0.5f * zähler / maxIterations, 1.0f, 1.0f);
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
        BufferedImage bufferedImage= new BufferedImage(
                getWidth(),
                getHeight(),
                BufferedImage.TYPE_INT_RGB);
        bufferedImage.getGraphics().drawImage(image,0,0, this);

        File f = new File("./output.png");
        try {
            ImageIO.write(bufferedImage, "png", f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
