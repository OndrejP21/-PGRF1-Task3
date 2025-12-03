package solid;

import transforms.*;

import java.util.*;

public class Solid {
    /** Index buffer */
    protected List<Integer> ib = new ArrayList<>();
    /** Vertex buffer */
    protected List<Point3D> vb = new ArrayList<>();
    protected int color = 0xffffff;
    protected Mat4 model = new Mat4Identity();

    protected void addIndices(Integer... indices) {
        this.ib.addAll(Arrays.asList(indices));
    }

    public List<Integer> getIb() {
        return ib;
    }

    public List<Point3D> getVb() {
        return vb;
    }

    public Mat4 getModel() {
        return model;
    }

    public void setModel(Mat4 model) {
        this.model = model;
    }

    public int getColor() {
        return color;
    }

    public void mulSolid(Mat4 mat) {
        this.model = this.model.mul(mat);
    }

    public void rotateSolid(Point3D translate) {
        this.mulSolid(new Mat4Transl(-translate.getX(), -translate.getY(), -translate.getZ()));
        this.mulSolid(new Mat4RotZ(Math.toRadians(15)));
        this.mulSolid(new Mat4Transl(translate.getX(), translate.getY(), translate.getZ()));
    }
}
