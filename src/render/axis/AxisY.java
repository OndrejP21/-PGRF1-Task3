package render.axis;

import solid.Axis;
import transforms.Point3D;

public class AxisY extends Axis {
    public AxisY() {
        super();

        this.vb.add(new Point3D(0, 0, 0));
        this.vb.add(new Point3D(0, 1, 0));

        this.color = 0x00ff00;
    }
}
