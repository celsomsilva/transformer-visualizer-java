package com.celso.swingtransformer.ui;

import com.celso.swingtransformer.core.SimulationState;
import com.celso.swingtransformer.core.TokenSequence;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.Locale;

public class AttentionPanel extends JPanel {

    private TokenSequence tokens;
    private SimulationState sim;

    private final JSpinner step;
    private final JTable table;
    private final AttentionTableModel model;

    public AttentionPanel() {
        super(new BorderLayout(8, 8));

        model = new AttentionTableModel();
        table = new JTable(model);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Step:"));
        step = new JSpinner(new SpinnerNumberModel(1, 1, 32, 1));
        step.addChangeListener(e -> refresh());
        top.add(step);
        top.add(new JLabel("  (Rows sum ~ 1.0)"));

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public void setTokens(TokenSequence tokens) {
        this.tokens = tokens;
        refresh();
    }

    public void setSimulation(SimulationState sim) {
        this.sim = sim;
        int max = sim != null ? Math.max(1, sim.steps()) : 1;
        ((SpinnerNumberModel) step.getModel()).setMaximum(max);
        step.setValue(1);
        refresh();
    }

    public void clear() {
        this.tokens = null;
        this.sim = null;
        ((SpinnerNumberModel) step.getModel()).setMaximum(1);
        step.setValue(1);
        model.setMatrix(null);
    }

    private void refresh() {
        if (tokens == null || sim == null) {
            model.setMatrix(null);
            return;
        }
        int s = ((Number) step.getValue()).intValue() - 1;
        s = Math.max(0, Math.min(s, sim.steps() - 1));
        model.setMatrix(sim.attentionAt(s));
    }

    private static class AttentionTableModel extends AbstractTableModel {
        private double[][] A;

        public void setMatrix(double[][] A) {
            this.A = A;
            fireTableStructureChanged();
        }

        @Override public int getRowCount() { return A == null ? 0 : A.length; }
        @Override public int getColumnCount() { return A == null ? 0 : A[0].length; }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            double v = A[rowIndex][columnIndex];
            return String.format(Locale.US, "%.3f", v);
        }

        @Override
        public String getColumnName(int column) {
            return String.valueOf(column);
        }
    }
}
