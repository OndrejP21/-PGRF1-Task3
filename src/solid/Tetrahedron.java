package solid;

import render.data.CubicType;
import transforms.Col;
import transforms.Point3D;

public class Tetrahedron extends Solid {
    public Tetrahedron() {
        this.vb.add(new Point3D(0.25, 0.5, 0.0)); // 0
        this.vb.add(new Point3D(0.0,  1.0, 0.0)); // 1
        this.vb.add(new Point3D(0.5,  1.0, 0.0)); // 2

        // vrchol
        this.vb.add(new Point3D(0.25, 0.75, 0.5)); // 3

        // Hrany základny (trojúhelník)
        this.addIndices(0, 1, 1, 2, 2, 0);

        // Hrany ke špičce (boční hrany)
        this.addIndices(0, 3, 1, 3, 2, 3);

        this.color = new Col(0xf0f0f0);
        this.name  = "Čtyřstěn";
    }

    @Override
    public void generateCurve(int n, CubicType type) {
        Point3D[] pts = new Point3D[] {
                vb.get(0),
                vb.get(1),
                vb.get(2),
                vb.get(3)
        };

        super.generateCurve(pts, n, type);
    }
}
