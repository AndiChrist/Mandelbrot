/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codecentric.common;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Andreas Christ <andreas.christ@codecentric.de>
 */
public class Picture extends JPanel {

    private final BufferedImage image;

    public Picture(int x, int y) {
        this.image = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, null); // see javadoc for more info on the parameters
        }
    }

    public void display() {
        JFrame f = new JFrame("Mandelbrot " + image.getWidth() + "x" + image.getHeight());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(this);
        f.setSize(image.getWidth(), image.getHeight());
        f.setVisible(true);

        this.setSize(this.image.getHeight(), this.image.getWidth());
        this.setVisible(true);
    }

    public void set(int x, int y, Color color) {
        image.setRGB(x, y, color.getRGB());
    }

    public void set(int x, int y, int color) {
        image.setRGB(x, y, color);
    }
}
