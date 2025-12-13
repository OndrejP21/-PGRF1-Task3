package render.data;

import transforms.Cubic;
import transforms.Mat4;

public enum CubicType {

    BEZIER(Cubic.BEZIER),
    COONS(Cubic.COONS),
    FERGUSON(Cubic.FERGUSON);

    private final Mat4 baseMat;

    CubicType(Mat4 baseMat) {
        this.baseMat = baseMat;
    }

    public Mat4 getBaseMat() {
        return baseMat;
    }
}
