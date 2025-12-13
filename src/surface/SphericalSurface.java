package surface;

import transforms.Point3D;

/** Třída přepočítávající sférické souřadnice na kartézské */
public class SphericalSurface implements ComputeSurface {
    private final Point3D center;
    private final double radius;

    public SphericalSurface(Point3D center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    /** Spočítáme podle prezentace: azimut = u, zenit = v, zadáme jako desetinná čísla v intervalu <0;1> a přepočteme*/
    public Point3D compute(double u, double v) {
        double azimut = u * 2.0 * Math.PI; // 0..2π
        double zenit   = v * Math.PI;       // -π/2..π/2

        double x = center.getX() + radius * Math.cos(zenit) * Math.cos(azimut);
        double y = center.getY() + radius * Math.cos(zenit) * Math.sin(azimut);
        double z = center.getZ() + radius * Math.sin(zenit);

        return new Point3D(x, y, z);
    }
}
