/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codecentric.simple;

/**
 *
 * @author Andreas Christ <andreas.christ@codecentric.de>
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author andichrist
 */
public class Mandelbrot extends JPanel {

    private final static Logger LOGGER = Logger.getLogger(Mandelbrot.class.getName());

    private final Double aEcke = -2.0;
    private final Double bEcke = -1.25;

    private final double seite = 2.5;

    private final int breite = 800;
    private final int höhe = 800;

    private final int[] bild = new int[breite * höhe];
    private final double spaltBreite = seite / breite;
    private final double spaltHöhe = seite / höhe;

    private final static int maxIterations = 30; // 1000

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Mandelbrot m = new Mandelbrot();
        m.berechne();
        m.zeige();
    }

    public void berechne() {
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
    }

    private int farbe(int zähler) {
        //return ColorMap.getColor(zähler).getRGB();
        return Color.HSBtoRGB(0.5f * zähler / maxIterations, 1.0f, 1.0f);
        // return new Color(zähler * 16777).getRGB();
                /*
         if (zähler < 2 || zähler > 1000) {
         return Color.BLACK.getRGB();
         } else if (zähler < 10) {
         return Color.RED.getRGB();
         } else if (zähler < 20) {
         return Color.YELLOW.getRGB();
         } else if (zähler < 30) {
         return Color.BLUE.getRGB();
         } else {
         return Color.GREEN.getRGB();
         }*/
    }

    static Image image;

    public void zeige() {
        JFrame f = new JFrame("Mandelbrot");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        image = getImageFromArray(bild, breite, höhe);
        f.add(new Mandelbrot());
        f.setSize(breite, höhe);
        f.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (image != null) {
            g.drawImage(image, 0, 0, breite, höhe, this);
        }
    }

    public Image getImageFromArray(int[] pixels, int width, int height) {
        MemoryImageSource mis = new MemoryImageSource(width, height, pixels, 0, width);
        Toolkit tk = Toolkit.getDefaultToolkit();
        return tk.createImage(mis);
    }
}
