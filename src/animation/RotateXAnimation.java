package animation;

import render.data.RotateType;

public class RotateXAnimation extends RotateAnimation {

    public RotateXAnimation(double degPerSec) {
        super(degPerSec, RotateType.ROTATE_X);
        this.name = "Rotaze X";
    }
}