package view;

import render.data.CubicType;
import solid.Solid;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/** Okno pro zobrazení CurveControlsPanelu */
public class CurveWindow extends JFrame {

    private final CurveControlsPanel controlsPanel;

    public CurveWindow(List<Solid> solids, TriConsumer<Solid, CubicType, Integer> onCreate) {
        setTitle("Curve generator");
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setLayout(new BorderLayout());

        controlsPanel = new CurveControlsPanel(solids);
        add(controlsPanel, BorderLayout.CENTER);

        JButton createBtn = new JButton("Vytvořit křivku");
        createBtn.addActionListener(e -> {
            if (onCreate == null) return;

            Solid s = controlsPanel.getSelectedSolid();
            CubicType type = controlsPanel.getSelectedCubicType();
            int n = controlsPanel.getN();

            if (s == null || type == null) return;
            onCreate.accept(s, type, n);
            controlsPanel.updateWarning();
            this.setVisible(false);
        });

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(createBtn);
        add(bottom, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    public CurveControlsPanel getControlsPanel() {
        return controlsPanel;
    }

    @FunctionalInterface
    public interface TriConsumer<A, B, C> {
        void accept(A a, B b, C c);
    }
}
