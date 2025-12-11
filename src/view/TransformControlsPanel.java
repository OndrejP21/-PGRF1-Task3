package view;

import javax.swing.*;
import java.awt.*;

public class TransformControlsPanel extends JPanel {
    private final JSpinner angleSpinner;
    private final JSpinner scaleXSpinner;
    private final JSpinner scaleYSpinner;
    private final JSpinner scaleZSpinner;
    private final JSpinner translateStepSpinner;

    public TransformControlsPanel() {
        super(new GridLayout(0, 2, 5, 5));

        // Modely
        // Úhel ve stupních
        angleSpinner = new JSpinner(new SpinnerNumberModel(10.0, null, null, 1.0));

        // Škálování
        scaleXSpinner = new JSpinner(new SpinnerNumberModel(2.0, null, null, 0.1));
        scaleYSpinner = new JSpinner(new SpinnerNumberModel(2.0, null, null, 0.1));
        scaleZSpinner = new JSpinner(new SpinnerNumberModel(2.0, null, null, 0.1));

        // Step posunu
        translateStepSpinner = new JSpinner(new SpinnerNumberModel(0.25, null, null, 0.05));

        add(new JLabel("Úhel rotace (°):"));
        add(angleSpinner);

        add(new JLabel("Scale X:"));
        add(scaleXSpinner);

        add(new JLabel("Scale Y:"));
        add(scaleYSpinner);

        add(new JLabel("Scale Z:"));
        add(scaleZSpinner);

        add(new JLabel("Krok posunu:"));
        add(translateStepSpinner);
    }

    public double getAngleDegrees() {
        return ((Number) angleSpinner.getValue()).doubleValue();
    }

    public double getScaleX() {
        return ((Number) scaleXSpinner.getValue()).doubleValue();
    }

    public double getScaleY() {
        return ((Number) scaleYSpinner.getValue()).doubleValue();
    }

    public double getScaleZ() {
        return ((Number) scaleZSpinner.getValue()).doubleValue();
    }

    public double getTranslateStep() {
        return ((Number) translateStepSpinner.getValue()).doubleValue();
    }
}
