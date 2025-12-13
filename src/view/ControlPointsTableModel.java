package view;

import transforms.Point3D;

import javax.swing.table.AbstractTableModel;

public class ControlPointsTableModel extends AbstractTableModel {

    private final String[] columns = {"i", "j", "X", "Y", "Z"};
    private final Point3D[] points; // 16 bodů v pořadí p11..p44 (řádky po 4)

    public ControlPointsTableModel(Point3D[] initialPoints) {
        if (initialPoints == null || initialPoints.length != 16) {
            throw new IllegalArgumentException("Musí být zadáno 16 bodů!.");
        }
        this.points = initialPoints;
    }

    public Point3D[] getPointsCopy() {
        Point3D[] copy = new Point3D[16];
        for (int k = 0; k < 16; k++) copy[k] = points[k];
        return copy;
    }

    public void setPoints(Point3D[] newPoints) {
        if (newPoints == null || newPoints.length != 16) {
            throw new IllegalArgumentException("Musí být zadáno 16 bodů!.");
        }
        System.arraycopy(newPoints, 0, points, 0, 16);
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return 16;
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int col) {
        return columns[col];
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        // i, j need not be editable
        return col >= 2;
    }

    @Override
    public Object getValueAt(int row, int col) {
        int i = row / 4; // 0..3
        int j = row % 4; // 0..3
        Point3D p = points[row];

        return switch (col) {
            case 0 -> i;
            case 1 -> j;
            case 2 -> p.getX();
            case 3 -> p.getY();
            case 4 -> p.getZ();
            default -> null;
        };
    }

    @Override
    public void setValueAt(Object aValue, int row, int col) {
        if (col < 2) return;

        double x = points[row].getX();
        double y = points[row].getY();
        double z = points[row].getZ();

        double val;
        try {
            val = (aValue instanceof Number n) ? n.doubleValue() : Double.parseDouble(aValue.toString());
        } catch (Exception e) {
            return;
        }

        switch (col) {
            case 2 -> x = val;
            case 3 -> y = val;
            case 4 -> z = val;
        }

        points[row] = new Point3D(x, y, z);
        fireTableCellUpdated(row, col);
    }
}
