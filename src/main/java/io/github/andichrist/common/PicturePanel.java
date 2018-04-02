/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.andichrist.common;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ColorModel;
import java.awt.image.ImageObserver;
import java.awt.image.MemoryImageSource;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Andreas Christ <andreas.christ@sixt.com>
 */
public class PicturePanel extends JPanel {
    
    private static final Logger LOGGER = Logger.getLogger(PicturePanel.class.getName());

    private final Image image;
    private static final int LIMIT = 1 << 8;
    private MemoryImageSource source;
    private ImageObserver myObserver;

    public PicturePanel(int[][] picture) {
        int picWidth = picture.length;
        int picHeight = picture[0].length;
        int[] pixels = new int[picWidth * picHeight];
        
        for (int i = 0; i < picWidth; i++) {
            for (int j = 0; j < picHeight; j++) {
                pixels[j * picWidth + i] = picture[i][j];
            }
        }

        ColorModel model = ColorManager.createPalette(LIMIT);
        source = new MemoryImageSource(picWidth, picHeight, model, pixels, 0, picWidth);
        this.image = createImage(source);

        myObserver = (Image img, int infoflags, int x, int y, int width, int height) -> true;
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
        f.setTitle("MandelbrotOldSchool " + width + "x" + height);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setContentPane(this);
        f.setSize(width, height);
        f.setVisible(true);
    }

}
