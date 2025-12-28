package com.celso.swingtransformer.ui;

import com.celso.swingtransformer.core.EngineConfig;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class ControlsPanel extends JPanel {

    private Runnable runHandler = () -> {};
    private Runnable clearHandler = () -> {};

    private final JSpinner seed;
    private final JSpinner maxNewTokens;
    private final JSpinner topK;
    private final JSpinner temperature;

    public ControlsPanel() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(new JLabel("Controls"));
        add(Box.createVerticalStrut(6));

        seed = spinner(42, 0, Integer.MAX_VALUE, 1);
        maxNewTokens = spinner(24, 1, 64, 1);
        topK = spinner(8, 1, 32, 1);
        temperature = spinnerDouble(0.90, 0.05, 2.0, 0.05);

        add(labeled("Seed", seed));
        add(labeled("Max new tokens", maxNewTokens));
        add(labeled("Top-K", topK));
        add(labeled("Temperature", temperature));
        add(Box.createVerticalStrut(8));

        JButton run = new JButton("Run");
        JButton clear = new JButton("Clear");

        run.addActionListener(e -> runHandler.run());
        clear.addActionListener(e -> clearHandler.run());

        add(run);
        add(Box.createVerticalStrut(4));
        add(clear);
        add(Box.createVerticalGlue());
    }

    public void onRun(Runnable r) {
        this.runHandler = r != null ? r : () -> {};
    }

    public void onClear(Runnable r) {
        this.clearHandler = r != null ? r : () -> {};
    }

    public EngineConfig getConfig() {
        long s = ((Number) seed.getValue()).longValue();
        int m = ((Number) maxNewTokens.getValue()).intValue();
        int k = ((Number) topK.getValue()).intValue();
        double t = ((Number) temperature.getValue()).doubleValue();
        return new EngineConfig(s, m, k, t);
    }

    private JPanel labeled(String name, JComponent comp) {
        JPanel p = new JPanel(new BorderLayout(6, 6));
        p.add(new JLabel(name), BorderLayout.NORTH);
        p.add(comp, BorderLayout.CENTER);
        p.setMaximumSize(new Dimension(260, 60));
        return p;
    }

    private JSpinner spinner(int val, int min, int max, int step) {
        return new JSpinner(new SpinnerNumberModel(val, min, max, step));
    }

    private JSpinner spinnerDouble(double val, double min, double max, double step) {
        return new JSpinner(new SpinnerNumberModel(val, min, max, step));
    }
}
