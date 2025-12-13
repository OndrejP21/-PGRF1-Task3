package solid;

import render.data.CubicType;
import render.data.RotateType;
import solid.curve.Curve;
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
    /** Určuje, zda má uvnitř sebe vykreslenou nějakou křivku (podle zadání úkolu uvnitř tvaru) */
    private Optional<Curve> curveSolidInside = Optional.empty();

    protected void addIndices(Integer... indices) {
        this.ib.addAll(Arrays.asList(indices));
    }

    protected void generateCurve(Point3D[] points, int n, CubicType type) {
        this.curveSolidInside = Optional.of(new Curve(Arrays.stream(points).map(x -> x.mul(this.model)).toArray(Point3D[]::new), n, type));
    }

    public void generateCurve(int n, CubicType type) {
        throw new UnsupportedOperationException("Metoda generateCurve() není implementována.");
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

        // Pokud existuje křivka uvnitř, změníme i tu
        if (this.hasCurveSolidInside())
            this.curveSolidInside.get().mulSolid(mat);
    }

    /** Metoda, která vykoná operaci vzhledem k posunutí solidu do počátku, poté ho posune zpět (pro škálování a rotaci) */
    private void mulWithCenterPosition(Mat4 mat4) {
        Point3D c = getCenterPoint();
        Mat4 spin = new Mat4Transl(c.mul(-1)).mul(mat4).mul(new Mat4Transl(c));

        this.model = spin.mul(this.model);
    }

    public void scaleSolid(Vec3D vec) {
        this.mulWithCenterPosition(new Mat4Scale(vec.getX(), vec.getY(), vec.getZ()));

        // Pokud existuje křivka uvnitř, škálujeme i tu
        if (this.hasCurveSolidInside())
            this.curveSolidInside.get().scaleSolid(vec);
    }

    /** Posune tvar do počátku a zrotuje o úhel v ose Z*/
    public void rotateSolid(double angleDeg, RotateType rotateType) {
        double radiansAngle = Math.toRadians(angleDeg);

        Mat4 rotateMat = rotateType == RotateType.ROTATE_X ? new Mat4RotX(radiansAngle) : rotateType == RotateType.ROTATE_Y ? new Mat4RotY(radiansAngle) : new Mat4RotZ(radiansAngle);
        this.mulWithCenterPosition(rotateMat);

        // Pokud existuje křivka uvnitř, orotujeme i tu
        if (this.hasCurveSolidInside())
            this.curveSolidInside.get().rotateSolid(angleDeg, rotateType);
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

    public Col getColor() {
        return color;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(Col color) {
        this.color = color;
    }

    public boolean hasCurveSolidInside() {
        return this.curveSolidInside.isPresent();
    }

    public Optional<Curve> getCurveSolidInside() {
        return this.curveSolidInside;
    }
}
