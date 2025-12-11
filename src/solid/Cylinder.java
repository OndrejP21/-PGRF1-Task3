package solid;

import transforms.Col;
import transforms.Point3D;

public class Cylinder extends Solid {
    public Cylinder(int segments) {
        // Střed spodní podstavy
        Point3D centerPoint = new Point3D(0.75, 0.75,0);

        // Poloměr podstavy
        double radius  = 0.25;
        // Výška válce
        double height  = 0.5;

        double fullCircle = 2 * Math.PI;
        // 1. Spodní podstava – body na kružnici
        for (int i = 0; i < segments; i++) {
            // Úhel v radiánech
            double angle = fullCircle * i / segments;
            // Spojnice na kružnici
            double x = centerPoint.getX() + radius * Math.cos(angle);
            double y = centerPoint.getY() + radius * Math.sin(angle);

            this.vb.add(new Point3D(x, y, centerPoint.getZ()));
        }

        // 2. Propojení spodní podstavy do kružnice
        this.buildCircleIndices(segments);

        // 3. Horní podstava – posuneme všechny body spodní o výšku
        for (int i = 0; i < segments; i++) {
            Point3D bottomPoint = this.vb.get(i);
            this.vb.add(bottomPoint.add(new Point3D(0, 0, height)));
        }

        // 4. Propojení horní podstavy do kružnice
        this.buildCircleIndices(segments, segments);

        // 5. Spojení svislých bodů podstav
        for (int i = 0; i < segments; i++) {
            this.addIndices(i, segments + i);
        }

        this.color = new Col(0x00ff00);
        this.name = "Válec";
    }

    /** Spojí body v kružnici (počet segmentů + od kolikatého bodu začít přidávat) */
    private void buildCircleIndices(int segments, int offset) {
        for (int i = 0; i < segments; i++) {
            int next = offset + ((i + 1) % segments);
            this.addIndices(i + offset, next);
        }
    }

    /** Spojí body v kružnici (počet segmentů + od kolikatého bodu začít přidávat) */
    private void buildCircleIndices(int segments) {
        this.buildCircleIndices(segments, 0);
    }
}
