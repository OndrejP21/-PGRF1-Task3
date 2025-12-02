package solid;

import transforms.Point3D;

import java.util.*;

public class Solid {
    /** Index buffer */
    protected List<Integer> ib = new ArrayList<>();
    /** Vertex buffer */
    protected List<Point3D> vb = new ArrayList<>();
    protected int color = 0xffffff;

    protected void addIndices(Integer... indices) {
        this.ib.addAll(Arrays.asList(indices));
    }

    public List<Integer> getIb() {
        return ib;
    }

    public List<Point3D> getVb() {
        return vb;
    }

    public int getColor() {
        return color;
    }
}
