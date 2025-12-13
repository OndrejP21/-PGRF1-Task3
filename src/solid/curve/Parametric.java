package solid.curve;

import transforms.Point3D;

public interface Parametric {
    Point3D compute(double t);
}
