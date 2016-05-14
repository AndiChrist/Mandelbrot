/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.andichrist.oldschool;

/**
 *
 * @author andichrist
 */
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import org.apache.commons.math3.complex.Complex;

public class Mandelbrot extends JFrame {

    private final int MAX_ITERATION = 570;
    private final double ZOOM = 150;

    final private BufferedImage image;
    private double newRe, newIm;

    private Complex z, c;

    public Mandelbrot() {
        super("Mandelbrot Set");
        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                z = Complex.ZERO;
                c = new Complex((x - 400) / ZOOM, (y - 300) / ZOOM);
                int iteration = MAX_ITERATION;
                while (z.getReal() * z.getReal() + z.getImaginary() * z.getImaginary() < 4 && iteration > 0) {
                    newRe = z.getReal() * z.getReal() - z.getImaginary() * z.getImaginary() + c.getReal();
                    newIm = 2.0 * z.getReal() * z.getImaginary() + c.getImaginary();
                    z = new Complex(newRe, newIm);
                    iteration--;
                }
                image.setRGB(x, y, iteration | (iteration << 8));
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(image, 0, 0, this);
    }

    public static void main(String[] args) {
        new Mandelbrot().setVisible(true);
    }
}
