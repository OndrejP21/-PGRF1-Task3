package animation;

import render.data.RotateType;
import solid.Solid;

public abstract class RotateAnimation implements Animation {
    protected double degPerSec;
    protected String name;
    protected RotateType rotateType;

    public RotateAnimation(double degPerSec, RotateType rotateType) {
        this.degPerSec = degPerSec;
        this.name = "Rotaze";
        this.rotateType = rotateType;
    }

    @Override public String getName() { return this.name; }

    @Override
    public void update(Solid solid, double dtSeconds) {
        solid.rotateSolid(degPerSec * dtSeconds, this.rotateType);
    }
}
