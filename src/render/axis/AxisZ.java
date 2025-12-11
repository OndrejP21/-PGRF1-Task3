package render.axis;

import solid.Axis;
import transforms.Col;
import transforms.Point3D;

public class AxisZ extends Axis {

    public AxisZ() {
        super();

        this.vb.add(new Point3D(0, 0, 0));
        this.vb.add(new Point3D(0, 0, 1));

        this.color = new Col(0x0000ff);
    }
}
