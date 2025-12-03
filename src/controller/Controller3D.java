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
import transforms.*;
import view.Panel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;

public class Controller3D {
    private final Panel panel;
    private final RasterBufferedImage raster;
    private LineRasterizer lineRasterizer;
    private Renderer renderer;
    private Camera camera;
    private Mat4 proj;
    private Arrow arrow;
    private List<Axis> axis;
    private boolean animate;

    public Controller3D(Panel panel) {
        this.panel = panel;
        this.raster = panel.getRaster();
        this.lineRasterizer = new LineRasterizerTrivial(this.raster);

        // defaultně kameru kouká ve směru osy x
        this.camera = new Camera().withPosition(new Vec3D(0.5,-1.5,1)).withAzimuth(Math.toRadians(90)).withZenith(Math.toRadians(-25)).withFirstPerson(true);
        this.proj = new Mat4PerspRH(Math.toRadians(90), this.panel.getHeight() / (double) this.panel.getWidth(), 0.1, 100);

        this.renderer = new Renderer(this.lineRasterizer, this.panel.getWidth(), this.panel.getHeight(), this.camera.getViewMatrix(), this.proj);

        this.axis = new ArrayList<>();
        this.axis.add(new AxisX());
        this.axis.add(new AxisY());
        this.axis.add(new AxisZ());

        this.arrow = new Arrow();

        this.animate = false;

        initListeners();
    }

    private void initListeners() {
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        arrow.mulSolid(new Mat4Transl(-0.5, 0, 0));
                        break;

                    case KeyEvent.VK_RIGHT:
                        arrow.mulSolid(new Mat4Transl(0.5, 0, 0));
                            break;

                    case KeyEvent.VK_R:
                        arrow.rotateSolid(new Point3D(0.25,0,0));
                        break;

                    case KeyEvent.VK_W:
                        camera = camera.forward(0.5);
                        break;

                    case KeyEvent.VK_A:
                        camera = camera.left(0.5);
                        break;

                    case KeyEvent.VK_S:
                        camera = camera.backward(0.5);
                        break;

                    case KeyEvent.VK_D:
                        camera = camera.right(0.5);
                        break;

                    case KeyEvent.VK_P:
                        animate = !animate;
                        break;
                }

                drawScrene();
            }
        });
    }

    private void drawScrene() {
        panel.getRaster().clear();

        this.renderer.setViewMat(this.camera.getViewMatrix());

        // Dodatečné stringové informace k vykreslení
        panel.setDrawStringInfo(new String[]{"Info"});

        this.renderer.renderSolid(this.arrow);
        for (Axis axis : this.axis)
            this.renderer.renderSolid(axis);

        panel.repaint();
    }

}
