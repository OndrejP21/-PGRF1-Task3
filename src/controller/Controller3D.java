package controller;


import animation.AnimationLoop;
import model.Point;
import raster.RasterBufferedImage;
import rasterize.lineRasterizers.LineRasterizer;
import rasterize.lineRasterizers.LineRasterizerTrivial;
import render.axis.AxisX;
import render.axis.AxisY;
import render.axis.AxisZ;
import render.data.CubicType;
import render.data.RotateType;
import render.data.SurfaceType;
import solid.Axis;
import solid.Solid;
import transforms.*;
import view.*;

import java.awt.event.*;
import java.util.*;

public class Controller3D {
    private final Panel panel;
    private final RasterBufferedImage raster;
    private LineRasterizer lineRasterizer;
    private SolidController solidController;
    private List<Axis> axis;
    private ControlsWindow controlsWindow;
    private CurveWindow curveWindow;
    private BicubicWindow bicubicWindow;
    private Point lastDrag;
    private AnimationLoop loop;

    public Controller3D(Panel panel) {
        this.panel = panel;
        this.raster = panel.getRaster();
        this.lineRasterizer = new LineRasterizerTrivial(this.raster);
        this.solidController = new SolidController(panel, this.lineRasterizer);

        this.controlsWindow = new ControlsWindow();
        this.curveWindow = new CurveWindow(this.solidController.getSolids(), this::createCurve);
        this.bicubicWindow = new BicubicWindow(this::createSurface);

        this.axis = new ArrayList<>();
        this.axis.add(new AxisX());
        this.axis.add(new AxisY());
        this.axis.add(new AxisZ());

        initListeners();
        drawScene();

        this.loop = new AnimationLoop(this::drawScene, 15);
    }

    private void initListeners() {
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                Solid selectedSolid = solidController.getSelectedSolid();
                TransformControlsPanel controls = controlsWindow.getControlsPanel();
                AnimationController animationController = loop.getController();

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

                    case KeyEvent.VK_1:
                        solidController.setRotateType(RotateType.ROTATE_X);
                        break;
                    case KeyEvent.VK_2:
                        solidController.setRotateType(RotateType.ROTATE_Y);
                        break;
                    case KeyEvent.VK_3:
                        solidController.setRotateType(RotateType.ROTATE_Z);
                        break;
                    case KeyEvent.VK_R:
                        selectedSolid.rotateSolid(controls.getAngleDegrees(), solidController.getRotateType());
                        break;
                    case KeyEvent.VK_Q:
                        if (!animationController.isAnimated(selectedSolid))
                            animationController.setAnimation(selectedSolid,  loop.getRotateAnimation(solidController.getRotateType()));
                        else
                            animationController.clearAnimation(selectedSolid);
                        break;
                    case KeyEvent.VK_T:
                        animationController.clearAll();
                        break;

                    case KeyEvent.VK_X:
                        selectedSolid.scaleSolid(new Vec3D(controls.getScaleX(), controls.getScaleY(), controls.getScaleZ()));
                        break;

                    case KeyEvent.VK_P:
                        solidController.changeProjectionType();
                        break;

                    case KeyEvent.VK_SPACE:
                        solidController.changeSelectedIndex();
                        break;

                    case KeyEvent.VK_C:
                        curveWindow.setVisible(!curveWindow.isVisible());
                        break;

                    case KeyEvent.VK_B:
                        bicubicWindow.setVisible(!bicubicWindow.isVisible());
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

                drawScene();
            }
        });

        panel.addMouseListener(new MouseAdapter() {
            @Override public void mousePressed(MouseEvent e) {
                lastDrag = new Point(e.getX(), e.getY());
            }
        });

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override public void mouseDragged(MouseEvent e) {
                Point p = new Point(e.getX(), e.getY());
                if (lastDrag == null) {
                    lastDrag = p;
                    return;
                }

                int dx = e.getX() - lastDrag.getX();
                int dy = e.getY() - lastDrag.getY();

                double sensitivity = 0.0025;
                solidController.addAzimuth(dx * sensitivity);
                solidController.addZenith(-dy * sensitivity);

                lastDrag = p;
                drawScene();
            }
        });
    }

    /** Metoda pro vytvoření křivky uvnitř Solidu */
    private void createCurve(Solid solid, CubicType type, Integer n) {
        solid.generateCurve(n, type);
        this.drawScene();
    }

    /** Metoda pro vytvoření surfacu */
    public void createSurface(CubicType type, Point3D[] points, SurfaceType surfaceType) {
        this.solidController.createSurface(type, points, surfaceType);
        this.drawScene();
    }

    private void drawScene() {
        panel.getRaster().clear();

        // Dodatečné stringové informace k vykreslení
        panel.setDrawStringInfo(new String[]{"Zvolený útvar: " + this.solidController.getSelectedSolid().getName(), "Typ projekce: " + this.solidController.getProjectionType(), "Rotace: " + this.solidController.getRotateType()});

        this.solidController.updateRenderer();

        for (Axis axis : this.axis)
            this.solidController.renderSolid(axis);

        this.solidController.renderAllSolids();

        panel.repaint();
    }

}
