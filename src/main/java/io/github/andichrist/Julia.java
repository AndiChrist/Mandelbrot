package io.github.andichrist;

import io.github.andichrist.common.RainbowColor;
import io.github.andichrist.math.Complex;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andreas Christ <andichrist@gmx.de>
 */
public class Julia extends JPanel {

    private final static Logger LOG = Logger.getLogger(Julia.class.getName());

    private static final String MANDELBROT_PROPERTIES = "julia.properties";

    private final Double aCorner;
    private final Double bCorner;

    private final double sideWidth;
    private final double sideHeight;

    private final double infinity;
    private final int maxIterations;

    private static Image image;
    private final Complex c;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Julia m = new Julia();

        int[] pic = m.compute();
        pic = filter(pic, m.maxIterations);
        image = m.getImageFromArray(pic, m.getWidth(), m.getHeight());

        m.showImage();
        m.saveImage();
    }

    private static int[] filter(int[] pic, int maxIterations) {
        new RainbowColor(pic, maxIterations);

        var coloredImage = new int[pic.length];
        for (int i=0; i<pic.length; i++) {
            coloredImage[i] = RainbowColor.color(pic[i], maxIterations);
        }

        return coloredImage;
    }

    private void showImage() {
        JFrame window = new JFrame("Julia");
        window.add(this);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setSize(this.getWidth(), this.getHeight());
        window.setVisible(true);
    }

    public Julia() {
        Properties properties = new Properties();
        try (InputStream fis = Julia.class.getClassLoader().getResourceAsStream(MANDELBROT_PROPERTIES)) {
            properties.load(fis);
        } catch (IOException e) {
            LOG.log(Level.WARNING, "Failed to load properties file", e);
        }

        int width = Integer.parseInt(properties.getProperty("image.width"));
        int height = Integer.parseInt(properties.getProperty("image.height"));

        this.setBounds(0, 0, width, height);

        this.aCorner = Double.parseDouble(properties.getProperty("min.re"));
        this.bCorner = Double.parseDouble(properties.getProperty("min.im"));
        double maxRe = Double.parseDouble(properties.getProperty("max.re"));
        double maxIm = Double.parseDouble(properties.getProperty("max.im"));

        this.sideWidth = (maxRe - aCorner) / width;
        this.sideHeight = (maxIm - bCorner) / height;

        this.infinity = Double.parseDouble(properties.getProperty("infinity"));
        this.maxIterations = Integer.parseInt(properties.getProperty("max.iteration"));

        double cRe = Double.parseDouble(properties.getProperty("c.re"));
        double cIm = Double.parseDouble(properties.getProperty("c.im"));

        this.c = new Complex(cRe, cIm);
    }


    /**
     * Z(n+1) = Zn^2 + C
     */
    private int[] compute() {
        int[] pic = new int[getWidth() * getHeight()];

        for (int m = 0; m < getHeight(); m++) {
            double im = bCorner + m * sideHeight;
            for (int n = 0; n < getWidth(); n++) {
                double re = aCorner + n * sideWidth;

                Complex z = new Complex(re, im);

                int iteration = 0;

                while (z.abs() <= infinity && iteration++ <= maxIterations) {
                    z = z.multiply(z).add(c);
                }

                /*
                 * Pixels for which the size of z reaches 2 after only a few iterations are colored red.
                 * Pixels for which the size of z reaches 2 after relatively many iterations are colored violet,
                 * at the other end of the spectrum.
                 * Pixels for which the size of z is less than 2 even after 1,000 iterations are assumed to lie
                 * in the MandelbrotOldSchool set; they are colored black.
                 */
                pic[m * getWidth() + n] = iteration;
            }
        }

        return pic;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        //Paint it on screen
        g2d.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        g2d.dispose();
    }

    private Image getImageFromArray(int[] pixels, int width, int height) {
        MemoryImageSource mis = new MemoryImageSource(width, height, pixels, 0, width);
        Toolkit tk = Toolkit.getDefaultToolkit();
        return tk.createImage(mis);
    }

    private void saveImage() {
        BufferedImage bufferedImage = new BufferedImage(
                image.getWidth(this),
                image.getHeight(this),
                BufferedImage.TYPE_INT_RGB);

        bufferedImage.getGraphics().drawImage(image,0,0, image.getWidth(this), image.getHeight(this), this);

        File f = new File("./output.png");
        try {
            ImageIO.write(bufferedImage, "png", f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
