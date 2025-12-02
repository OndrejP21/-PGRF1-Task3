package render.axis;

import solid.Axis;
import transforms.Point3D;

public class AxisX extends Axis {
    public AxisX() {
        super();

        this.vb.add(new Point3D(-1, 0, 0));
        this.vb.add(new Point3D(1, 0, 0));

        this.color = 0xff0000;
    }
}
