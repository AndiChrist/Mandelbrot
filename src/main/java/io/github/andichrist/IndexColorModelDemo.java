package io.github.andichrist;

import java.awt.*;
import java.awt.image.IndexColorModel;
import java.awt.image.MemoryImageSource;

/**
 * Created by andichrist on 18.12.16.
 */
public class IndexColorModelDemo extends Frame {
    Image i;
    static int w = 400, h = 400;

    int pixels[] = new int[w * h];

    Color colors[] = {
            Color.red, Color.orange, Color.yellow,
            Color.green, Color.blue, Color.magenta
    };

    IndexColorModelDemo() {
        int colorCnt = colors.length;

        byte r[] = new byte[colorCnt],
                g[] = new byte[colorCnt],
                b[] = new byte[colorCnt];

        for (int i = 0; i < colorCnt; i++) {
            r[i] = (byte) colors[i].getRed();
            g[i] = (byte) colors[i].getGreen();
            b[i] = (byte) colors[i].getBlue();
        }

        int index = 0;
        for (int y = 0; y < h; y++)
            for (int x = 0; x < w; x++)
                pixels[index++] = (int) (Math.random() * colorCnt);

        i = createImage(new MemoryImageSource(w, h,
                new IndexColorModel(8, colorCnt, r, g, b),
                pixels, 0, w));
    }

    public void paint(Graphics g) {
        if (i != null)
            g.drawImage(i, 0, 0, this);
    }

    public static void main(String args[]) {
        IndexColorModelDemo d = new IndexColorModelDemo();
        d.setSize(w, h);
        d.show();
    }
}
