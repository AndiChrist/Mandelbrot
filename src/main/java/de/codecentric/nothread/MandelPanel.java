/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codecentric.nothread;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Andreas Christ <andreas.christ@codecentric.de>
 */
public class MandelPanel extends JPanel {

    private static Image image;

    void zeige(BufferedImage image) {
        this.image = image;
        JFrame f = new JFrame("Mandelbrot " + image.getWidth() + "x" + image.getHeight());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(this);
        f.setSize(image.getWidth(), image.getHeight());
        f.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, image.getWidth(this), image.getHeight(this), this);
        }
    }

}
