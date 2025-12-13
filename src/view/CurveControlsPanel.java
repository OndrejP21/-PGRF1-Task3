package view;

import render.data.CubicType;
import solid.Solid;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/** Panel pro vytváření křivek uvnitř Solidů */
public class CurveControlsPanel extends JPanel {

    private final JComboBox<Solid> solidCombo;
    private final JComboBox<CubicType> typeCombo;
    private final JSpinner nSpinner;
    private final JLabel warningLabel;

    public CurveControlsPanel(List<Solid> solids) {
        super(new GridLayout(0, 2, 5, 5));

        // Solid select
        solidCombo = new JComboBox<>(solids.toArray(new Solid[0]));
        solidCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus
            ) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Solid s)
                    setText(s.getName());
                return this;
            }
        });

        // Typ křivky
        typeCombo = new JComboBox<>(CubicType.values());

        // N
        nSpinner = new JSpinner(new SpinnerNumberModel(20, 1, 10_000, 1));

        warningLabel = new JLabel("⚠ Tento solid už obsahuje křivku");
        warningLabel.setForeground(Color.RED);
        warningLabel.setVisible(false);

        solidCombo.addActionListener(e -> updateWarning());

        add(new JLabel("Solid:"));
        add(solidCombo);

        add(new JLabel("Typ křivky:"));
        add(typeCombo);

        add(new JLabel("N:"));
        add(nSpinner);

        add(new JLabel());
        add(warningLabel);
    }

    public void updateWarning() {
        Solid s = getSelectedSolid();
        warningLabel.setVisible(s.hasCurveSolidInside());
    }

    public Solid getSelectedSolid() {
        return (Solid) solidCombo.getSelectedItem();
    }

    public CubicType getSelectedCubicType() {
        return (CubicType) typeCombo.getSelectedItem();
    }

    public int getN() {
        return ((Number) nSpinner.getValue()).intValue();
    }

    public void setSolids(List<Solid> solids) {
        solidCombo.setModel(new DefaultComboBoxModel<>(solids.toArray(new Solid[0])));
        updateWarning();
    }
}
