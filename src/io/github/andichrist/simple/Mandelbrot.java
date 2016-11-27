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
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author Andreas Christ <andreas.christ@sixt.com>
 */
public class Mandelbrot extends JPanel {

    private final static Logger LOGGER = Logger.getLogger(Mandelbrot.class.getName());

    private final Double aEcke = -2.0;
    private final Double bEcke = -1.25;

    private final double seite = 2.5;

    private static final int breite = 800;
    private static final int höhe = 800;

    private final double spaltBreite = seite / breite;
    private final double spaltHöhe = seite / höhe;

    private final static int maxIterations = 30; // 1000

    private static Image image;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame window = new JFrame("Mandelbrot");
        window.add(new Mandelbrot());
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setSize(breite, höhe);
        window.setVisible(true);
    }

    public Mandelbrot() {
        int[] bild = berechne();
        image = getImageFromArray(bild, breite, höhe);

        saveImage();
    }

    private int[] berechne() {
        int[] bild = new int[breite * höhe];

        for (int m = 0; m < höhe; m++) {
            double im = bEcke + m * spaltHöhe;
            for (int n = 0; n < breite; n++) {
                double re = aEcke + n * spaltBreite;

                Complex c = new Complex(re, im);
                Complex z = Complex.ZERO;

                int zähler = 0;

                do {
                    z = z.multiply(z).add(c);
                    zähler++;
                } while (z.abs() <= 2.0 && zähler <= maxIterations);

                // switch for color
                bild[m * breite + n] = farbe(zähler);
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
        g2d.drawImage(image, 0, 0, breite, höhe, this);
        g2d.dispose();
    }

    private Image getImageFromArray(int[] pixels, int width, int height) {
        MemoryImageSource mis = new MemoryImageSource(width, height, pixels, 0, width);
        Toolkit tk = Toolkit.getDefaultToolkit();
        return tk.createImage(mis);
    }

    private void saveImage() {
        BufferedImage bufferedImage= new BufferedImage(
                image.getWidth(this),
                image.getHeight(this),
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
