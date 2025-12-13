package surface;

import transforms.Point3D;

/** Rozhraní určující, zda má třída možnost metody compute pro spočítání 3DPointu*/
public interface ComputeSurface {
    Point3D compute(final double paramU, final double paramV);
}
