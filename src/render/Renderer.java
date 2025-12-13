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

        // Modelovací t. => pohledová t. => projekční t.
        Mat4 mvp = solid.getModel().mul(this.viewMat).mul(this.projectMat);

        List<Point3D> transformedVb = new ArrayList<>();

        for (Point3D p : vb) {
            Point3D v = p.mul(mvp);

            // Ořezání v homogenních souřadnicích
            if (!checkSlice(v)) {
                return; // celý útvar pryč
            }

            transformedVb.add(v);
        }

        for (int i = 0; i < ib.size(); i += 2) {
            int indexA = ib.get(i);
            int indexB = ib.get(i + 1);

            Point3D pointA = transformedVb.get(indexA);
            Point3D pointB = transformedVb.get(indexB);

            // Matice: MVP => Model-View-Projection
            // Modelovací transformace
           /* pointA = pointA.mul(solid.getModel());
            pointB = pointB.mul(solid.getModel());

            // Pohledová transformace
            pointA = pointA.mul(this.viewMat);
            pointB = pointB.mul(this.viewMat);

            // Projekční transformace
            pointA = pointA.mul(this.projectMat);
            pointB = pointB.mul(this.projectMat);*/

            // Dehomogenizace
            pointA = getDehomogedOrDefault(pointA);
            pointB = getDehomogedOrDefault(pointB);

            // Transformace do okna obrazovky
            Vec3D vecA = transformToWindow(pointA);
            Vec3D vecB = transformToWindow(pointB);

            // TODO: vykreslit červenou osu x, zelenou osu y, modrou osu z
            this.lineRasterizer.rasterize(vecA, vecB, solid.getColor().getRGB());
        }
    }

    private Vec3D transformToWindow(Point3D p) {
        return new Vec3D(p).mul(new Vec3D(1, -1, 1)).add(new Vec3D(1, 1, 0)).mul(new Vec3D((this.width - 1) / (double) 2,(this.height - 1) / (double) 2, 1));
    }

    private Point3D getDehomogedOrDefault(Point3D p) {
        Point3D dehomogedPointOrDefault = new Point3D();
        Optional<Vec3D> optional = p.dehomog();

        if (optional.isPresent())
            dehomogedPointOrDefault = new Point3D(optional.get());

        return dehomogedPointOrDefault;
    }

    public void renderSolid(List<Solid> solids) {
        for (Solid s : solids) {
            this.renderSolid(s);
            if (s.hasCurveSolidInside())
                this.renderSolid(s.getCurveSolidInside().get());
        }
    }

    private boolean checkSlice(Point3D p) {
        double x = p.getX();
        double y = p.getY();
        double z = p.getZ();
        double w = p.getW();

        return x > -w && x < w && y > -w && y < w && z > 0 && z < w;
    }

    public void setViewMat(Mat4 viewMat) {
        this.viewMat = viewMat;
    }

    public void setProjectMat(Mat4 projectMat) {
        this.projectMat = projectMat;
    }
}
