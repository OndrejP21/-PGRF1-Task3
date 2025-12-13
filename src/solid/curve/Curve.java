package solid.curve;

import render.data.CubicType;
import solid.Solid;
import transforms.Col;
import transforms.Cubic;
import transforms.Mat4;
import transforms.Point3D;

import java.util.Arrays;

public class Curve extends Solid {
    private CubicType cubicType;

    private Point3D[] points;
    private int n;

    public Curve(Point3D[] points, int n, CubicType type) {
        this.n = n;
        this.cubicType = type;
        this.points = Arrays.stream(points).limit(4).toArray(Point3D[]::new); // 4 body

        this.color = new Col(0xff00ff);
        Cubic cubic = new Cubic(this.getCubicType(), this.points);

        for (int i = 0; i < n; i++) {
            float step = i / (float) n;
            vb.add(cubic.compute(step));
        }

        for(int i = 0; i < this.vb.size() - 1; i++) {
            this.addIndices(i, i+1);
        }
    }

    public void changePoint(Point3D p, int index) {
        if (index < points.length)
            this.points[index] = p;
    }

    public void setCubicType(CubicType cubicType) {
        this.cubicType = cubicType;
    }

    public void setN(int n) {
        this.n = n;
    }

    public Mat4 getCubicType() {
        switch (cubicType) {
            case BEZIER -> {
                return Cubic.BEZIER;
            }
            case COONS -> {
                return Cubic.COONS;
            }
            case FERGUSON -> {
                return Cubic.FERGUSON;
            }
        }

        return Cubic.BEZIER;
    }
}
