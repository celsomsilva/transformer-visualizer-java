package com.celso.swingtransformer.ui;

import javax.swing.*;
import java.awt.*;

public class OutputPanel extends JPanel {

    private final JTextArea area;

    public OutputPanel() {
        super(new BorderLayout(8, 8));
        area = new JTextArea();
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);

        add(new JScrollPane(area), BorderLayout.CENTER);
    }

    public void setOutput(String text) {
        area.setText(text != null ? text : "");
        area.setCaretPosition(0);
    }

    public void clear() {
        area.setText("");
    }
}
