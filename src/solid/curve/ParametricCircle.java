package solid.curve;

import transforms.Point3D;

public class ParametricCircle implements Parametric {
    private double r;
    private double x, y, z;

    public ParametricCircle(double r, double x, double y, double z) {
        this.r = r;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public Point3D compute(double t) {
        return new Point3D(
                x + r * Math.cos(t),
                y + r * Math.sin(t),
                z
        );    }
}
