package render;

import rasterize.lineRasterizers.LineRasterizer;
import solid.Solid;
import transforms.Mat4;
import transforms.Point3D;
import transforms.Vec3D;

import java.util.*;

public class Renderer {
    private LineRasterizer lineRasterizer;
    private int width, height;

    private Mat4 viewMat, projectMat;

    public Renderer(LineRasterizer lineRasterizer, int width, int height, Mat4 viewMat, Mat4 projectMat) {
        this.projectMat = projectMat;
        this.viewMat = viewMat;
        this.height = height;
        this.width = width;
        this.lineRasterizer = lineRasterizer;
    }

    public void renderSolid(Solid solid) {
        List<Integer> ib = solid.getIb();
        List<Point3D> vb = solid.getVb();

        // TODO: před cyklem zpracovávat body pomocí transformací

        for (int i = 0; i < ib.size(); i += 2) {
            int indexA = ib.get(i);
            int indexB = ib.get(i + 1);

            Point3D pointA = vb.get(indexA);
            Point3D pointB = vb.get(indexB);

            // Matice: MVP => Model-View-Projection
            // Modelovací transformace => mezi
            pointA = pointA.mul(solid.getModel());
            pointB = pointB.mul(solid.getModel());

            // Pohledová transformace
            pointA = pointA.mul(this.viewMat);
            pointB = pointB.mul(this.viewMat);

            // Projekční transformace
            pointA = pointA.mul(this.projectMat);
            pointB = pointB.mul(this.projectMat);

            // Ořezání

            // Dehomogenizace
            pointA = pointA.mul(1 / pointA.getW());
            pointB = pointB.mul(1 / pointB.getW());

            // Transformace do okna obrazovky
            Vec3D vecA = transformToWindow(pointA);
            Vec3D vecB = transformToWindow(pointB);

            // TODO: vykreslit červenou osu x, zelenou osu y, modrou osu z
            this.lineRasterizer.rasterize(vecA, vecB, solid.getColor());
        }
    }

    private Vec3D transformToWindow(Point3D p) {
        return new Vec3D(p).mul(new Vec3D(1, -1, 1)).add(new Vec3D(1, 1, 0)).mul(new Vec3D((this.width - 1) / (double) 2,(this.height - 1) / (double) 2, 1));
    }

    public void renderSolids(List<Solid> solids) {
        for (Solid s : solids) {
            this.renderSolid(s);
        }
    }

    public void setLineRasterizer(LineRasterizer lineRasterizer) {
        this.lineRasterizer = lineRasterizer;
    }

    public void setViewMat(Mat4 viewMat) {
        this.viewMat = viewMat;
    }

    public void setProjectMat(Mat4 projectMat) {
        this.projectMat = projectMat;
    }
}
