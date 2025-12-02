package render;

import rasterize.lineRasterizers.LineRasterizer;
import solid.Solid;
import transforms.Point3D;
import transforms.Vec3D;

import java.util.*;

public class Renderer {
    private LineRasterizer lineRasterizer;
    private int width, height;

    public Renderer(LineRasterizer lineRasterizer, int width, int height) {
        this.lineRasterizer = lineRasterizer;
        this.width = width;
        this.height = height;
    }

    public void renderSolid(Solid solid) {
        List<Integer> ib = solid.getIb();
        List<Point3D> vb = solid.getVb();

        for (int i = 0; i < ib.size(); i += 2) {
            int indexA = ib.get(i);
            int indexB = ib.get(i + 1);

            Point3D pointA = vb.get(indexA);
            Point3D pointB = vb.get(indexB);

            // Transformace do okna obrazovky
            // Potřebujeme vektory
            Vec3D vecA = new Vec3D(pointA);
            Vec3D vecB = new Vec3D(pointB);

            // FIXME: Dodělat metodu
            vecA = vecA.mul(new Vec3D(1, -1, 1)).add(new Vec3D(1, 1, 0)).mul(new Vec3D((this.width - 1) / (double) 2,(this.height - 1) / (double) 2, 1));
            vecB = vecB.mul(new Vec3D(1, -1, 1)).add(new Vec3D(1, 1, 0)).mul(new Vec3D((this.width - 1) / (double) 2,(this.height - 1) / (double) 2, 1));

            // TODO: vykreslit červenou osu x, zelenou osu y, modrou osu z
            this.lineRasterizer.rasterize(vecA, vecB, solid.getColor());
        }
    }

    public void renderSolids(List<Solid> solids) {
        for (Solid s : solids) {
            this.renderSolid(s);
        }
    }

    public void setLineRasterizer(LineRasterizer lineRasterizer) {
        this.lineRasterizer = lineRasterizer;
    }
}
