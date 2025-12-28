package com.celso.swingtransformer.ui;

import com.celso.swingtransformer.core.Token;
import com.celso.swingtransformer.core.TokenSequence;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TokensPanel extends JPanel {

    private final TokensTableModel model;
    private final JTable table;

    public TokensPanel() {
        super(new BorderLayout(8, 8));
        model = new TokensTableModel();
        table = new JTable(model);

        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public void setTokens(TokenSequence tokens) {
        model.setTokens(tokens.tokens());
    }

    public void clear() {
        model.setTokens(List.of());
    }

    private static class TokensTableModel extends AbstractTableModel {
        private final String[] cols = {"#", "Token", "Token ID"};
        private List<Token> tokens = new ArrayList<>();

        public void setTokens(List<Token> tokens) {
            this.tokens = new ArrayList<>(tokens);
            fireTableDataChanged();
        }

        @Override public int getRowCount() { return tokens.size(); }
        @Override public int getColumnCount() { return cols.length; }
        @Override public String getColumnName(int column) { return cols[column]; }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Token t = tokens.get(rowIndex);
            return switch (columnIndex) {
                case 0 -> rowIndex;
                case 1 -> t.text();
                case 2 -> t.id();
                default -> "";
            };
        }
    }
}
