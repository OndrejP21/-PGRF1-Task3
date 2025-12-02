package rasterize.lineRasterizers;

import constants.Constants;
import model.Line;
import model.Point;
import raster.RasterBufferedImage;
import transforms.Point3D;
import transforms.Vec3D;

public abstract class LineRasterizer {
    protected RasterBufferedImage image;
    protected int color;

    public LineRasterizer(RasterBufferedImage image) {
        this.image = image;
        this.color = Constants.COLOR;
    }

    /** isPatternFill je povětšinou false, true pouze v případě vyplňování ScanLine algoritmem, kdy chceme vyplňovat vzorem */
    public void rasterize(int x1, int y1, int x2, int y2, int color) {

    }

    public void rasterize(int x1, int y1, int x2, int y2) {
        this.rasterize(x1, y1, x2, y2, 0xffffff);
    }

    public void rasterize(Point p1, Point p2, int color) {
        rasterize(p1.getX(), p1.getY(), p2.getX(), p2.getY(), color);
    }

    public void rasterize(Point3D p1, Point3D p2, int color) {
        rasterize((int) Math.round(p1.getX()), (int) Math.round(p1.getY()), (int) Math.round(p2.getX()), (int) Math.round(p2.getY()), color);
    }

    public void rasterize(Vec3D p1, Vec3D p2, int color) {
        rasterize((int) Math.round(p1.getX()), (int) Math.round(p1.getY()), (int) Math.round(p2.getX()), (int) Math.round(p2.getY()), color);
    }

    public void rasterize(Line line, int color) {
        rasterize(line.getP1(), line.getP2(), color);
    }

    public void setColor(int color) {
        this.color = color;
    }
}
