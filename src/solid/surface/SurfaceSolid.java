package solid.surface;

import solid.Solid;
import surface.ComputeSurface;
import transforms.Col;
import transforms.Point3D;

/** Třída pro vytvoření sférické koule */
public class SurfaceSolid extends Solid {

    public SurfaceSolid(ComputeSurface surface, int uSteps, int vSteps, String name) {
        this.name = name;
        this.color = new Col(0x00ffff);

        // 1. VB
        for (int i = 0; i <= uSteps; i++) {
            double u = i / (double) uSteps;
            for (int j = 0; j <= vSteps; j++) {
                double v = j / (double) vSteps;
                Point3D p = surface.compute(u, v);
                this.vb.add(p);
            }
        }

        // 2. IB: horizontální čáry (v) + vertikální čáry (u)
        for (int i = 0; i <= uSteps; i++) {
            for (int j = 0; j < vSteps; j++) {
                addIndices(this.getIndex(vSteps, i, j), this.getIndex(vSteps, i, j + 1));
            }
        }

        for (int i = 0; i < uSteps; i++) {
            for (int j = 0; j <= vSteps; j++) {
                addIndices(this.getIndex(vSteps, i, j), this.getIndex(vSteps, i + 1, j));
            }
        }
    }

    /** Metoda pro výpočet indexu */
    private int getIndex(int vSteps, int i, int j) {
        int cols = vSteps + 1;
        return i * cols + j;
    }
}
