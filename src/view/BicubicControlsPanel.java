package view;

import render.data.CubicType;
import render.data.SurfaceType;
import transforms.Point3D;

import javax.swing.*;
import java.awt.*;

public class BicubicControlsPanel extends JPanel {

    private final JComboBox<SurfaceType> surfaceTypeCombo;
    private final JComboBox<CubicType> basisCombo;
    private final ControlPointsTableModel tableModel;
    private final JTable table;

    public BicubicControlsPanel() {
        super(new BorderLayout(10, 10));

        surfaceTypeCombo = new JComboBox<>(SurfaceType.values());
        basisCombo = new JComboBox<>(CubicType.values());

        // default 16 bodů (kopeček)
        Point3D[] defaults = defaultHillPatch(0.0, 0.0, 0.5, 0.5, 0.15);
        tableModel = new ControlPointsTableModel(defaults);

        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);

        // reaguj na změnu typu plochy
        surfaceTypeCombo.addActionListener(e -> updateUiState());

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Typ plochy:"));
        top.add(surfaceTypeCombo);
        top.add(Box.createHorizontalStrut(10));
        top.add(new JLabel("Kubika:"));
        top.add(basisCombo);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        updateUiState();
    }

    private void updateUiState() {
        boolean isBicubic = getSurfaceType() == SurfaceType.BICUBIC;

        basisCombo.setEnabled(isBicubic);
        table.setEnabled(isBicubic);
        table.setVisible(isBicubic);
    }

    public SurfaceType getSurfaceType() {
        return (SurfaceType) surfaceTypeCombo.getSelectedItem();
    }

    public CubicType getSelectedBasis() {
        return (CubicType) basisCombo.getSelectedItem();
    }

    public Point3D[] getControlPoints() {
        return tableModel.getPointsCopy();
    }

    public void resetToDefaultHill() {
        tableModel.setPoints(defaultHillPatch(0.0, 0.0, 0.5, 0.5, 0.15));
    }

    public void setControlPoints(Point3D[] points16) {
        tableModel.setPoints(points16);
    }

    private static Point3D[] defaultHillPatch(
            double x0, double y0, double x1, double y1, double bumpZ
    ) {
        Point3D[] p = new Point3D[16];

        for (int i = 0; i < 4; i++) {
            double v = i / 3.0;
            double y = y0 + v * (y1 - y0);

            for (int j = 0; j < 4; j++) {
                double u = j / 3.0;
                double x = x0 + u * (x1 - x0);

                double z = 0.0;
                if ((i == 1 || i == 2) && (j == 1 || j == 2))
                    z = bumpZ;

                p[i * 4 + j] = new Point3D(x, y, z);
            }
        }
        return p;
    }
}
