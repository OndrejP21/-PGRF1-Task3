package solid;

import transforms.Col;
import transforms.Point3D;

public class Cube extends Solid {
    public Cube() {
        // Přední stěna (body)
        this.vb.add(new Point3D(0,0,0)); // 0
        this.vb.add(new Point3D(0,0.5,0)); // 1
        this.vb.add(new Point3D(0.5,0,0)); // 2
        this.vb.add(new Point3D(0.5,0.5,0)); // 3

        // Propojení přední stěny
        this.addIndices(0,1,0,2,1,3,2,3);

        // Zadní stěna (body)
        for (int i = 0; i < 4; i++) {
            Point3D point = this.vb.get(i);

            this.vb.add(point.add(new Point3D(0,0, 0.5)));
            // Propojení mezi zadní a přední stěnou
            this.addIndices(i, 4 + i);
        }

        // Zadní stěna
        this.addIndices(4,5,4,6,5,7,6,7);

        this.color = new Col(0xff0000);
        this.name = "Krychle";
    }
}
