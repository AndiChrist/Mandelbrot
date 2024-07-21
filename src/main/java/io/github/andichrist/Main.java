package io.github.andichrist;

import io.github.andichrist.common.RainbowColor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends JPanel {

  private final static Logger LOG = Logger.getLogger(Main.class.getName());

  public static void main(String[] args) {
    // Breite und Höhe des Bildes
    int width = 800;
    int height = 800;
    int maxIteration = 300;

    Context context = new Context();

    context.setStrategy(new Mandelbrot(width, height, maxIteration));

    var pic = context.compute();
    pic = filter(pic, maxIteration);
    var image = getImageFromArray(pic, width, height);

    BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
    Graphics g = bufferedImage.getGraphics();
    g.drawImage(image, 0, 0, null);
    g.dispose();

    showImage(bufferedImage, width, height);
    saveImage(bufferedImage);
  }

  private static int[] filter(int[] pic, int maxIterations) {
    new RainbowColor(pic, maxIterations);

    var coloredImage = new int[pic.length];
    for (int i = 0; i < pic.length; i++) {
      coloredImage[i] = RainbowColor.color(pic[i], maxIterations);
    }

    return coloredImage;
  }

  private static void showImage(BufferedImage bufferedImage, int width, int height) {
    // Das Bild in einem JFrame anzeigen
    SwingUtilities.invokeLater(() -> {
      JFrame frame = new JFrame("Angezeigtes Bild");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(width, height);

      // Das Bild in einem JLabel anzeigen
      JLabel label = new JLabel(new ImageIcon(bufferedImage));
      frame.add(label);

      frame.pack();
      frame.setVisible(true);
    });
  }

  private static Image getImageFromArray(int[] pixels, int width, int height) {
    var mis = new MemoryImageSource(width, height, pixels, 0, width);
    var tk = Toolkit.getDefaultToolkit();

    return tk.createImage(mis);
  }

  private static void saveImage(BufferedImage bufferedImage) {
    File f = new File("./output.png");
    try {
      ImageIO.write(bufferedImage, "png", f);
    } catch (IOException e) {
      LOG.log(Level.WARNING, "Failed to write PNG file", e);
    }
  }
}
