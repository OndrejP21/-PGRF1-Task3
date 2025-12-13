package animation;

import controller.AnimationController;
import render.data.RotateType;

import javax.swing.*;

public class AnimationLoop {
    private final Timer timer;
    private final AnimationController controller;
    private final Runnable repaintAction;

    private long lastNanos = -1;
    private double degPerSeconds = 15;

    public AnimationLoop(Runnable repaintAction, int fps) {
        this.controller = new AnimationController();
        this.repaintAction = repaintAction;

        int delayMs = Math.max(1, 1000 / fps);
        this.timer = new Timer(delayMs, e -> tick());

        this.start();
    }

    private void tick() {
        long now = System.nanoTime();
        if (lastNanos < 0) {
            lastNanos = now;
            return;
        }
        double dt = (now - lastNanos) / 1_000_000_000.0;
        lastNanos = now;

        controller.updateAll(dt);
        repaintAction.run();
    }

    public void start() { lastNanos = -1; timer.start(); }
    public void stop()  { timer.stop(); }
    public boolean isRunning() { return timer.isRunning(); }

    public void setDegPerSeconds(double degPerSeconds) {
        this.degPerSeconds = degPerSeconds;
    }

    public Animation getRotateAnimation(RotateType rotateType) {
        switch (rotateType) {
            case ROTATE_Z -> {
                return new RotateZAnimation(this.degPerSeconds);
            }
            case ROTATE_Y -> {
                return new RotateYAnimation(this.degPerSeconds);
            }
            case ROTATE_X -> {
                return new RotateXAnimation(this.degPerSeconds);
            }
        }

        return new RotateZAnimation(this.degPerSeconds);
    }

    public AnimationController getController() {
        return controller;
    }
}

