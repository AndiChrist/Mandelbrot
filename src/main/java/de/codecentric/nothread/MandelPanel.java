/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codecentric.nothread;

import de.codecentric.common.ImageDimension;
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

    public void zeige(int[] bild, ImageDimension dimension) {
        JFrame f = new JFrame("Mandelbrot " + dimension.getWidth() + "x" + dimension.getHeight());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        image = getImageFromArray(bild, dimension);
        f.add(this);
        f.setSize(dimension.getWidth(), dimension.getHeight());
        f.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (image != null) {
            g.drawImage(image, 0, 0, image.getWidth(this), image.getHeight(this), this);
        }
    }

    public Image getImageFromArray(int[] pixels, ImageDimension dimension) {
        return getImageFromArray(pixels, dimension.getWidth(), dimension.getHeight());
    }

    public Image getImageFromArray(int[] pixels, int width, int height) {
        MemoryImageSource mis = new MemoryImageSource(width, height, pixels, 0, width);
        return Toolkit.getDefaultToolkit().createImage(mis);
    }

}
