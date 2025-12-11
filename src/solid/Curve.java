package solid;

import transforms.Col;
import transforms.Cubic;
import transforms.Point3D;

public class Curve extends Solid {

    public Curve(int n) {
        this.color = new Col(0xff00ff);
        Cubic cubic = new Cubic(Cubic.BEZIER, new Point3D(0,0,0), new Point3D(0.25, 0, 0.25), new Point3D(0.65,0,1.25), new Point3D(1,0,0));

        for (int i = 0; i < n; i++) {
            float step = i / (float) n;
            //this.vb.add(new Point3D(step, 0,Math.cos(step)));
            vb.add(cubic.compute(step));
        }

        for(int i = 0; i < this.vb.size() - 1; i++) {
            this.addIndices(i, i+1);
        }
    }
}
