/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codecentric.common;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Andreas Christ <andreas.christ@codecentric.de>
 */
public class PicturePanel extends JPanel {

    private final BufferedImage image;

    public PicturePanel(BufferedImage image) {
        this.image = image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // erase what's maybe left behind
        if (image != null) {
            g.drawImage(image, 0, 0, null);
        }
    }

    public void display() {
        JFrame f = new JFrame();
        f.setSize(image.getWidth(), image.getHeight());
        f.setTitle("Mandelbrot " + image.getWidth() + "x" + image.getHeight());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setContentPane(this);
        f.setVisible(true);
    }

}
