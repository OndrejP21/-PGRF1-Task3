package animation;

import solid.Solid;

public interface Animation {
    String getName();
    void update(Solid solid, double dtSeconds);
}