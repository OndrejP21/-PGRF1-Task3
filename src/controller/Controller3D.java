package controller;


import raster.RasterBufferedImage;
import rasterize.lineRasterizers.LineRasterizer;
import rasterize.lineRasterizers.LineRasterizerTrivial;
import render.axis.AxisX;
import render.Renderer;
import render.axis.AxisY;
import render.axis.AxisZ;
import solid.Arrow;
import solid.Axis;
import view.Panel;
import java.util.*;

public class Controller3D {
    private final Panel panel;
    private final RasterBufferedImage raster;
    private LineRasterizer lineRasterizer;
    private Renderer renderer;
    private Arrow arrow;
    private List<Axis> axis;

    public Controller3D(Panel panel) {
        this.panel = panel;
        this.raster = panel.getRaster();
        this.lineRasterizer = new LineRasterizerTrivial(this.raster);
        this.renderer = new Renderer(this.lineRasterizer, this.panel.getWidth(), this.panel.getHeight());

        this.axis = new ArrayList<>();
        this.axis.add(new AxisX());
        this.axis.add(new AxisY());
        this.axis.add(new AxisZ());

        this.arrow = new Arrow();

        initListeners();

        drawScrene();
    }

    private void initListeners() {
    }

    private void drawScrene() {
        panel.getRaster().clear();

        // Dodatečné stringové informace k vykreslení
        panel.setDrawStringInfo(new String[]{"Info"});

        this.renderer.renderSolid(this.arrow);
        for (Axis axis : this.axis)
            this.renderer.renderSolid(axis);

        panel.repaint();
    }

}
