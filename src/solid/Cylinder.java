package solid;

import render.data.CubicType;
import transforms.Col;
import transforms.Point3D;

public class Cylinder extends Solid {
    private final double radius;
    private final double height;
    private final Point3D centerPoint;

    public Cylinder(int segments) {
        // Střed spodní podstavy
        centerPoint = new Point3D(0.75, 0.75,0);
        // Poloměr podstavy
        radius  = 0.25;
        // Výška válce
        height  = 0.5;

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
            this.vb.add(bottomPoint.add(new Point3D(0, 0, height,  0)));
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

    /** Metoda pro vygenerování křivky uvnitř válce */
    @Override
    public void generateCurve(int n, CubicType type) {
        // Body vytváříme uvnitř válce, ne přímo v okrajích, měníme pouze souřadnici z
        double rIn = radius * 0.6;

        double z0 = centerPoint.getZ() + height * 0.15;
        double z1 = centerPoint.getZ() + height * 0.35;
        double z2 = centerPoint.getZ() + height * 0.65;
        double z3 = centerPoint.getZ() + height * 0.85;

        double a0 = Math.toRadians(30);
        double a1 = Math.toRadians(140);
        double a2 = Math.toRadians(220);
        double a3 = Math.toRadians(320);

        // Body budou umístěny podle úhlů různě ve válci
        Point3D[] pts = new Point3D[] {
                new Point3D(
                        centerPoint.getX() + rIn * Math.cos(a0),
                        centerPoint.getY() + rIn * Math.sin(a0),
                        z0
                ),
                new Point3D(
                        centerPoint.getX() + rIn * Math.cos(a1),
                        centerPoint.getY() + rIn * Math.sin(a1),
                        z1
                ),
                new Point3D(
                        centerPoint.getX() + rIn * Math.cos(a2),
                        centerPoint.getY() + rIn * Math.sin(a2),
                        z2
                ),
                new Point3D(
                        centerPoint.getX() + rIn * Math.cos(a3),
                        centerPoint.getY() + rIn * Math.sin(a3),
                        z3
                )
        };

        super.generateCurve(pts, n, type);
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
