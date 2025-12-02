package solid;

import transforms.Point3D;

public class Arrow extends Solid {
    public Arrow() {
        // vb
        this.vb.add(new Point3D(-0.5, 0, 1)); // v0
        this.vb.add(new Point3D(0.4, 0, 1)); // v1
        this.vb.add(new Point3D(0.4, -0.1, 1)); // v2
        this.vb.add(new Point3D(0.5, 0, 1)); // v3
        this.vb.add(new Point3D(0.4, 0.1, 1)); // v4

        // ib
        this.addIndices(0,1,2,3,3,4,4,2);
    }
}
