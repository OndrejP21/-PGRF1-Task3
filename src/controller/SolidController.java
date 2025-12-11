package controller;

import rasterize.lineRasterizers.LineRasterizer;
import render.Renderer;
import render.data.ProjectionType;
import solid.Arrow;
import solid.Cube;
import solid.Cylinder;
import solid.Solid;
import transforms.*;
import view.Panel;

import java.util.*;

public class SolidController {
    private Panel panel;
    private Renderer renderer;
    private LineRasterizer lineRasterizer;
    private List<Solid> solids;
    private Camera camera;
    /** Aktuálně vybraný index Solidu, který upravujeme */
    private int selectedIndex;
    private ProjectionType projectionType;
    private Mat4 projPersp;
    private Mat4 projParalel;

    public SolidController(Panel panel, LineRasterizer lineRasterizer) {
        this.selectedIndex = 0;
        this.panel = panel;
        this.solids = new ArrayList<>();
        this.lineRasterizer = lineRasterizer;
        this.projectionType = ProjectionType.Perspective;

        // defaultně kameru kouká ve směru osy x
        this.camera = new Camera().withPosition(new Vec3D(0.5,-1.5,1)).withAzimuth(Math.toRadians(90)).withZenith(Math.toRadians(-25)).withFirstPerson(true);
        this.projPersp = new Mat4PerspRH(Math.toRadians(90), this.panel.getHeight() / (double) this.panel.getWidth(), 0.1, 100);

        double aspect = panel.getWidth() / (double) panel.getHeight(); // 4:3
        double viewHeight = 2;
        double viewWidth  = viewHeight * aspect;

        this.projParalel = new Mat4OrthoRH(viewWidth, viewHeight, 0.1, 200);

        this.renderer = new Renderer(this.lineRasterizer, this.panel.getWidth(), this.panel.getHeight(), this.camera.getViewMatrix(), this.getProjMat());

        this.solids.add(new Arrow());
        this.solids.add(new Cube());
        this.solids.add(new Cylinder(16));
    }

    /** Vyrenderuje všechny aktuální solidy */
    public void renderAllSolids() {
        this.renderer.renderSolid(this.solids);
    }

    /** Metoda pro možnost dorenderování speciálních solidů z controlleru 3D */
    public void renderSolid(Solid solid) {
        this.renderer.renderSolid(solid);
    }

    /** Metoda pro možnost dorenderování speciálních solidů z controlleru 3D */
    public void renderSolid(List<Solid> solids) {
        this.renderer.renderSolid(solids);
    }

    /** Řekneme, že chceme nastavit novou pohledovou matici */
    public void updateRenderer() {
        this.renderer.setViewMat(this.camera.getViewMatrix());
    }

    /** Metoda, která zajistí výběr nového Solidu */
    public void changeSelectedIndex() {
        this.selectedIndex = (this.selectedIndex + 1) % this.solids.size();
    }

    /** Vrátí aktuálně zvolený Solid */
    public Solid getSelectedSolid() {
        return this.solids.get(this.selectedIndex);
    }

    public ProjectionType getProjectionType() {
        return projectionType;
    }

    public void changeProjectionType() {
        this.projectionType = projectionType == ProjectionType.Perspective ? ProjectionType.Paralel : ProjectionType.Perspective;
        this.renderer.setProjectMat(getProjMat());
    }

    private Mat4 getProjMat() {
        return projectionType == ProjectionType.Perspective ? this.projPersp : this.projParalel;
    }

    public void forwardCamera(double d) {
        this.camera = this.camera.forward(d);
    }

    public void backwardCamera(double d) {
        this.camera = this.camera.backward(d);
    }

    public void leftCamera(double d) {
        this.camera = this.camera.left(d);
    }

    public void rightCamera(double d) {
        this.camera = this.camera.right(d);
    }
}
