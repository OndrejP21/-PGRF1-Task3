package view;

import javax.swing.*;
import java.awt.*;

public class ControlsWindow extends JFrame {

    private final TransformControlsPanel controlsPanel;

    public ControlsWindow() {
        setTitle("Transform Controls");
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setLayout(new BorderLayout());

        controlsPanel = new TransformControlsPanel();
        add(controlsPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }

    public TransformControlsPanel getControlsPanel() {
        return controlsPanel;
    }
}
