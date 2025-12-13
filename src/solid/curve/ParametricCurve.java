package solid.curve;

import solid.Solid;
import transforms.Col;

public class ParametricCurve extends Solid {

    public ParametricCurve(String name, Col color,
                                Parametric curve,
                                double t0, double t1,
                                int segments) {
        this.name = name;
        this.color = color;

        // vb
        for (int i = 0; i < segments; i++) {
            double segment = i / (double) (segments - 1);
            double t = t0 + segment * (t1 - t0);
            vb.add(curve.compute(t));
        }

        // ib
        for (int i = 0; i < segments - 1; i++) {
            addIndices(i, i + 1);
        }
    }
}
