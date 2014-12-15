/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codecentric.common;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ColorModel;
import java.awt.image.ImageObserver;
import java.awt.image.MemoryImageSource;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Andreas Christ <andreas.christ@codecentric.de>
 */
public class PicturePanel extends JPanel {

    private final Image image;
    private static final int LIMIT = 1 << 8;
    MemoryImageSource source;
    ImageObserver myObserver;

    public PicturePanel(int[][] image) {
        int width = image.length;
        int height = image[0].length;
        int[] pixels = new int[width * height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                pixels[j * width + i] = image[i][j];
            }
        }

        ColorModel model = ColorManager.createPalette(LIMIT);
        source = new MemoryImageSource(width, height, model, pixels, 0, width);
        this.image = createImage(source);

        myObserver = (Image image1, int flags1, int x1, int y1, int width1, int height1) -> {
            if ((flags1 & HEIGHT) != 0) {
                System.out.println("Image height = " + height1);
            }
            if ((flags1 & WIDTH) != 0) {
                System.out.println("Image width = " + width1);
            }
            if ((flags1 & FRAMEBITS) != 0) {
                System.out.println("Another frame finished.");
            }
            if ((flags1 & SOMEBITS) != 0) {
                System.out.println("Image section :" + new Rectangle(x1, y1, width1, height1));
            }
            if ((flags1 & ALLBITS) != 0) {
                System.out.println("Image finished!");
            }
            if ((flags1 & ABORT) != 0) {
                System.out.println("Image load aborted...");
            }
            return true;
        };
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // erase what's maybe left behind
        if (image != null) {
            g.drawImage(image, 0, 0, null);
        }
    }

    public void display() {
        int width = image.getWidth(myObserver);
        int height = image.getHeight(myObserver);

        JFrame f = new JFrame();
        f.setTitle("Mandelbrot " + width + "x" + height);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setContentPane(this);
        f.setSize(width, height);
        f.setVisible(true);
    }

}
