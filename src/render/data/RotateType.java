package render.data;

public enum RotateType {
    ROTATE_Z("Rotace kolem Z"),
    ROTATE_Y("Rotace kolem Y"),
    ROTATE_X("Rotace kolem X");

    private final String label;
    RotateType(String label) { this.label = label; }
    @Override public String toString() { return label; }
}
