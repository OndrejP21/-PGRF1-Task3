package render.axis;

import solid.Axis;
import transforms.Point3D;

public class AxisY extends Axis {
    public AxisY() {
        super();

        this.vb.add(new Point3D(0, 1, 1));
        this.vb.add(new Point3D(0, -1, 1));

        this.color = 0x00ff00;
    }
}
