package solid;

import render.data.CubicType;
import transforms.Col;
import transforms.Point3D;

public class StarCylinder extends Solid {
    private final int points;
    private final int segments;
    private final Point3D center;
    private final double outerRadius;
    private final double innerRadius;
    private final double height;

    public StarCylinder() {
        this(5); //
    }

    public StarCylinder(int points) {
        // Počet vrcholů hvězdicové podstavy (2 * points)
        this.points = points;
        this.segments = points * 2;

        // Střed spodní podstavy
        this.center = new Point3D(-0.25, -0.25, 0.0);

        // Vnější a vnitřní poloměr hvězdy
        this.outerRadius = 0.25;
        this.innerRadius = 0.12;
        this.height = 0.5;

        double fullCircle = 2 * Math.PI;

        // 1. Spodní podstava – střídání vnějšího a vnitřního poloměru
        for (int i = 0; i < segments; i++) {
            double angle = fullCircle * i / segments;

            double radius = (i % 2 == 0) ? outerRadius : innerRadius;

            double x = center.getX() + radius * Math.cos(angle);
            double y = center.getY() + radius * Math.sin(angle);

            this.vb.add(new Point3D(x, y, center.getZ()));
        }

        // 2. Propojení spodní podstavy – obrys hvězdy
        buildLoopIndices(segments, 0);

        // 3. Horní podstava – posun spodních bodů o výšku
        for (int i = 0; i < segments; i++) {
            Point3D bottomPoint = this.vb.get(i);
            this.vb.add(bottomPoint.add(new Point3D(0, 0, height, 0)));
        }

        // 4. Propojení horní podstavy
        buildLoopIndices(segments, segments);

        // 5. Svislé hrany mezi spodní a horní podstavou
        for (int i = 0; i < segments; i++) {
            this.addIndices(i, segments + i);
        }

        this.color = new Col(0xFFAA00);
        this.name  = "Hvězdicový hranol";
    }

    @Override
    public void generateCurve(int n, CubicType type) {
        // Indexy ve spodní podstavě
        int i0 = 0;
        int i1 = segments / 2;
        int i2 = segments / 4;
        int i3 = (3 * segments) / 4;

        Point3D[] pts = new Point3D[] {
                vb.get(i0),              // spodní hrot
                vb.get(i2 + segments),   // horní hrot (pootočený)
                vb.get(i1),              // spodní protilehlý hrot
                vb.get(i3 + segments)    // horní protilehlý hrot (pootočený)
        };

        super.generateCurve(pts, n, type);
    }
    
    private void buildLoopIndices(int segments, int offset) {
        for (int i = 0; i < segments; i++) {
            int next = offset + ((i + 1) % segments);
            this.addIndices(offset + i, next);
        }
    }
}
