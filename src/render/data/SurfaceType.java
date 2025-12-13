package render.data;

public enum SurfaceType {
    SPHERICAL("Sférická plocha"),
    BICUBIC("Bikubická plocha");

    private final String label;

    SurfaceType(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
