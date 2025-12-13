package view;

import raster.RasterBufferedImage;

import javax.swing.*;
import java.awt.*;


public class Panel extends JPanel {

    private RasterBufferedImage raster;
    private String[] drawStringInfo = new String[]{"", ""};

    public Panel(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        raster = new RasterBufferedImage(width, height);
    }

    public void resizeRaster(int width, int height) {
        raster = new RasterBufferedImage(width, height);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(raster.getImage(), 0, 0, getWidth(), getHeight(), null);
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 16));

        int i = 1;
        for (String info : drawStringInfo)
            g.drawString(info, 5, 20 * i++);
    }

    public void setDrawStringInfo(String[] drawStringInfo) {
        this.drawStringInfo = drawStringInfo;
    }

    public RasterBufferedImage getRaster() {
        return raster;
    }
}
