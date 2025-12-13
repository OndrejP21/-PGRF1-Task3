package animation;

import render.data.RotateType;

public class RotateZAnimation extends RotateAnimation {

    public RotateZAnimation(double degPerSec) {
        super(degPerSec, RotateType.ROTATE_Z);
        this.name = "Rotaze Z";
    }
}