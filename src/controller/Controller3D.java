package controller;


import raster.RasterBufferedImage;
import rasterize.lineRasterizers.LineRasterizer;
import rasterize.lineRasterizers.LineRasterizerTrivial;
import view.Panel;

public class Controller3D {
    private final Panel panel;
    private final RasterBufferedImage raster;
    private LineRasterizer lineRasterizer;

    public Controller3D(Panel panel) {
        this.panel = panel;
        this.raster = panel.getRaster();
        this.lineRasterizer = new LineRasterizerTrivial(this.raster);

        initListeners();

        drawScrene();
    }

    private void initListeners() {
    }

    private void drawScrene() {
        panel.getRaster().clear();

        // Dodatečné stringové informace k vykreslení
        panel.setDrawStringInfo(new String[]{"Info"});

        panel.repaint();
    }

}
