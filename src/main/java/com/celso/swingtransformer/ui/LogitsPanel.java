package com.celso.swingtransformer.ui;

import com.celso.swingtransformer.core.SimulationState;
import com.celso.swingtransformer.core.TopLogits;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LogitsPanel extends JPanel {

    private SimulationState sim;

    private final JSpinner step;
    private final JTable table;
    private final LogitsTableModel model;

    public LogitsPanel() {
        super(new BorderLayout(8, 8));

        model = new LogitsTableModel();
        table = new JTable(model);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Step:"));
        step = new JSpinner(new SpinnerNumberModel(1, 1, 32, 1));
        step.addChangeListener(e -> refresh());
        top.add(step);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public void setSimulation(SimulationState sim) {
        this.sim = sim;
        int max = sim != null ? Math.max(1, sim.steps()) : 1;
        ((SpinnerNumberModel) step.getModel()).setMaximum(max);
        step.setValue(1);
        refresh();
    }

    public void clear() {
        this.sim = null;
        ((SpinnerNumberModel) step.getModel()).setMaximum(1);
        step.setValue(1);
        model.setRows(List.of());
    }

    private void refresh() {
        if (sim == null) {
            model.setRows(List.of());
            return;
        }
        int s = ((Number) step.getValue()).intValue() - 1;
        s = Math.max(0, Math.min(s, sim.steps() - 1));
        TopLogits tl = sim.logitsAt(s);
        model.setTopLogits(tl);
    }

    private static class LogitsTableModel extends AbstractTableModel {
        private final String[] cols = {"Rank", "Token", "Logit", "Prob"};
        private List<Row> rows = new ArrayList<>();

        public void setTopLogits(TopLogits tl) {
            List<Row> r = new ArrayList<>();
            for (int i = 0; i < tl.tokens().size(); i++) {
                r.add(new Row(i + 1, tl.tokens().get(i), tl.logits().get(i), tl.probs().get(i)));
            }
            setRows(r);
        }

        public void setRows(List<Row> rows) {
            this.rows = new ArrayList<>(rows);
            fireTableDataChanged();
        }

        @Override public int getRowCount() { return rows.size(); }
        @Override public int getColumnCount() { return cols.length; }
        @Override public String getColumnName(int column) { return cols[column]; }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Row r = rows.get(rowIndex);
            return switch (columnIndex) {
                case 0 -> r.rank;
                case 1 -> r.token;
                case 2 -> String.format(Locale.US, "%.3f", r.logit);
                case 3 -> String.format(Locale.US, "%.3f", r.prob);
                default -> "";
            };
        }

        private static class Row {
            final int rank;
            final String token;
            final double logit;
            final double prob;

            private Row(int rank, String token, double logit, double prob) {
                this.rank = rank;
                this.token = token;
                this.logit = logit;
                this.prob = prob;
            }
        }
    }
}
