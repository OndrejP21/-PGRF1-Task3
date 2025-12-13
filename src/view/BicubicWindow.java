package view;

import render.data.CubicType;
import render.data.SurfaceType;
import transforms.Point3D;

import javax.swing.*;
import java.awt.*;

public class BicubicWindow extends JFrame {

    private final BicubicControlsPanel panel;

    @FunctionalInterface
    public interface ApplyHandler {
        void apply(CubicType basis, Point3D[] controlPoints16, SurfaceType surfaceType);
    }

    public BicubicWindow(ApplyHandler onApply) {
        setTitle("Bicubic – kontrolní body (4×4)");
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setLayout(new BorderLayout());

        panel = new BicubicControlsPanel();
        add(panel, BorderLayout.CENTER);

        JButton resetBtn = new JButton("Reset (výchozí)");
        resetBtn.addActionListener(e -> panel.resetToDefaultHill());

        JButton applyBtn = new JButton("Apply");
        applyBtn.addActionListener(e -> {
            if (onApply == null) return;
            onApply.apply(panel.getSelectedBasis(), panel.getControlPoints(), panel.getSurfaceType());
        });

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(resetBtn);
        bottom.add(applyBtn);
        add(bottom, BorderLayout.SOUTH);

        setMinimumSize(new Dimension(520, 420));
        pack();
        setLocationRelativeTo(null);
    }

    public BicubicControlsPanel getControlsPanel() {
        return panel;
    }
}
