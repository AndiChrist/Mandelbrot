/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codecentric.nothread;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Andreas Christ <andreas.christ@codecentric.de>
 */
public class MandelPanel extends JPanel {

    private static Image image;

    void zeige(int[] bild, Integer width, Integer height) {
        JFrame f = new JFrame("Mandelbrot " + width + "x" + height);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        image = getImageFromArray(bild, width, height);
        f.add(this);
        f.setSize(width, height);
        f.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (image != null) {
            g.drawImage(image, 0, 0, image.getWidth(this), image.getHeight(this), this);
        }
    }

    public Image getImageFromArray(int[] pixels, int width, int height) {
        MemoryImageSource mis = new MemoryImageSource(width, height, pixels, 0, width);
        return Toolkit.getDefaultToolkit().createImage(mis);
    }

}
