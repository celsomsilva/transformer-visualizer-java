package com.celso.swingtransformer.ui;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TracePanel extends JPanel {

    private final JTextArea area;

    public TracePanel() {
        super(new BorderLayout(8, 8));
        area = new JTextArea();
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);

        add(new JScrollPane(area), BorderLayout.CENTER);
    }

    public void setTrace(List<String> trace) {
        StringBuilder sb = new StringBuilder();
        for (String line : trace) sb.append(line).append('\n');
        area.setText(sb.toString());
        area.setCaretPosition(0);
    }

    public void clear() {
        area.setText("");
    }
}
