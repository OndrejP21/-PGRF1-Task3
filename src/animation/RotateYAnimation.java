package animation;

import render.data.RotateType;

public class RotateYAnimation extends RotateAnimation {

    public RotateYAnimation(double degPerSec) {
        super(degPerSec, RotateType.ROTATE_Y);
        this.name = "Rotaze Y";
    }

}