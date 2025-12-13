package controller;

import animation.Animation;
import solid.Solid;

import java.util.*;

public class AnimationController {
    private final Map<Solid, Animation> active = new HashMap<>();

    public void setAnimation(Solid solid, Animation animation) {
        if (solid == null || animation == null) return;
        active.put(solid, animation);
    }

    public void clearAnimation(Solid solid) {
        active.remove(solid);
    }

    public void clearAll() {
        active.clear();
    }

    public boolean isAnimated(Solid solid) {
        return active.containsKey(solid);
    }

    public void updateAll(double dtSeconds) {
        for (var e : active.entrySet()) {
            e.getValue().update(e.getKey(), dtSeconds);
        }
    }
}
