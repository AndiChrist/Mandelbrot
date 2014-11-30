/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codecentric.common;

/**
 *
 * @author Andreas Christ <andreas.christ@codecentric.de>
 */
public class ImageDimension {

    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 800;

    private int width;
    private int height;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public ImageDimension() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public ImageDimension(int width, int height) {
        this.width = width;
        this.height = height;
    }
}
