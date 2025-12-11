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
import solid.Curve;
import solid.Solid;
import transforms.*;
import view.ControlsWindow;
import view.Panel;
import view.TransformControlsPanel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;

public class Controller3D {
    private final Panel panel;
    private final RasterBufferedImage raster;
    private LineRasterizer lineRasterizer;
    private SolidController solidController;
    private List<Axis> axis;
    private Curve curve;
    private ControlsWindow controlsWindow;

    public Controller3D(Panel panel) {
        this.panel = panel;
        this.raster = panel.getRaster();
        this.lineRasterizer = new LineRasterizerTrivial(this.raster);
        this.solidController = new SolidController(panel, this.lineRasterizer);

        this.controlsWindow = new ControlsWindow();

        this.axis = new ArrayList<>();
        this.axis.add(new AxisX());
        this.axis.add(new AxisY());
        this.axis.add(new AxisZ());

        this.curve = new Curve(10);

        initListeners();
        drawScrene();
    }

    private void initListeners() {
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                Solid selectedSolid = solidController.getSelectedSolid();
                TransformControlsPanel controls = controlsWindow.getControlsPanel();

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        selectedSolid.mulSolid(new Mat4Transl(-controls.getTranslateStep(), 0, 0));
                        break;

                    case KeyEvent.VK_RIGHT:
                        selectedSolid.mulSolid(new Mat4Transl(controls.getTranslateStep(), 0, 0));
                            break;
                    case KeyEvent.VK_UP:
                        selectedSolid.mulSolid(new Mat4Transl(0, controls.getTranslateStep(), 0));
                        break;

                    case KeyEvent.VK_DOWN:
                        selectedSolid.mulSolid(new Mat4Transl(0, -controls.getTranslateStep(), 0));
                        break;

                    case KeyEvent.VK_R:
                        selectedSolid.rotateSolid(controls.getAngleDegrees());
                        break;

                    case KeyEvent.VK_X:
                        selectedSolid.mulSolid(new Mat4Scale(controls.getScaleX(), controls.getScaleY(), controls.getScaleZ()));
                        break;

                    case KeyEvent.VK_P:
                        solidController.changeProjectionType();
                        break;

                    case KeyEvent.VK_SPACE:
                        solidController.changeSelectedIndex();
                        break;

                    case KeyEvent.VK_W:
                        solidController.forwardCamera(0.5);
                        break;

                    case KeyEvent.VK_A:
                        solidController.leftCamera(0.5);
                        break;

                    case KeyEvent.VK_S:
                        solidController.backwardCamera(0.5);
                        break;

                    case KeyEvent.VK_D:
                        solidController.rightCamera(0.5);
                        break;

                    case KeyEvent.VK_E:
                        controlsWindow.setVisible(!controlsWindow.isVisible());
                        break;
                }

                drawScrene();
            }
        });
    }

    private void drawScrene() {
        panel.getRaster().clear();

        // Dodatečné stringové informace k vykreslení
        panel.setDrawStringInfo(new String[]{"Info", "Zvolený útvar: " + this.solidController.getSelectedSolid().getName(), "Typ projekce: " + this.solidController.getProjectionType()});

        this.solidController.updateRenderer();

        for (Axis axis : this.axis)
            this.solidController.renderSolid(axis);

        this.solidController.renderSolid(this.curve);

        this.solidController.renderAllSolids();

        panel.repaint();
    }

}
