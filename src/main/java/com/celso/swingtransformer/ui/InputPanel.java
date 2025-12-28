package com.celso.swingtransformer.ui;

import javax.swing.*;
import java.awt.*;

public class InputPanel extends JPanel {
    private final JTextArea input;

    public InputPanel() {
        super(new BorderLayout(8, 8));

        JLabel label = new JLabel("Input text / prompt:");
        input = new JTextArea(3, 60);
        input.setLineWrap(true);
        input.setWrapStyleWord(true);
        input.setText("Write your ask here.");

        JScrollPane sp = new JScrollPane(input);
        sp.setPreferredSize(new Dimension(800, 90));

        add(label, BorderLayout.NORTH);
        add(sp, BorderLayout.CENTER);
    }

    public String getInputText() {
        return input.getText();
    }
}
