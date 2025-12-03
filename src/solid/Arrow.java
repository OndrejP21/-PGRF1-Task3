package solid;

import transforms.Point3D;

public class Arrow extends Solid {
    public Arrow() {
        // vb
        this.vb.add(new Point3D(0, 0, 0)); // v0
        this.vb.add(new Point3D(0.4, 0, 0)); // v1
        this.vb.add(new Point3D(0.4, -0.1, 0)); // v2
        this.vb.add(new Point3D(0.5, 0, 0)); // v3
        this.vb.add(new Point3D(0.4, 0.1, 0)); // v4

        // ib
        this.addIndices(0,1,2,3,3,4,4,2);
    }
}
