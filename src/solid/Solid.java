package solid;

import transforms.*;

import java.util.*;

public class Solid {
    /** Index buffer */
    protected List<Integer> ib = new ArrayList<>();
    /** Vertex buffer */
    protected List<Point3D> vb = new ArrayList<>();
    protected Col color = new Col(0xffffff);
    /** Jméno solidu */
    protected String name = "";
    protected Mat4 model = new Mat4Identity();

    protected void addIndices(Integer... indices) {
        this.ib.addAll(Arrays.asList(indices));
    }

    /** Metoda, která spočítá prostřední bod tvaru, kolem kterého bude docházet k rotacím */
    private Point3D getCenterPoint() {
        double centerX = this.vb.stream().mapToDouble(Point3D::getX).average().getAsDouble();
        double centerY = this.vb.stream().mapToDouble(Point3D::getY).average().getAsDouble();
        double centerZ = this.vb.stream().mapToDouble(Point3D::getZ).average().getAsDouble();

        return new Point3D(centerX, centerY, centerZ);
    }

    public void mulSolid(Mat4 mat) {
        this.model = this.model.mul(mat);
    }

    /** Posune tvar do počátku a zrotuje o úhel*/
    public void rotateSolid(double angle) {
        Point3D centerPoint = this.getCenterPoint();
        this.mulSolid(new Mat4Transl(centerPoint.mul(-1)));
        this.mulSolid(new Mat4RotZ(Math.toRadians(angle)));
        this.mulSolid(new Mat4Transl(centerPoint));
    }

    public String getName() {
        return name;
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

    public Col getColor() {
        return color;
    }
}
