package com.celso.swingtransformer.ui;

import com.celso.swingtransformer.core.EngineConfig;
import com.celso.swingtransformer.core.TransformerEngine;
import com.celso.swingtransformer.core.TransformerRun;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private final TransformerEngine engine;

    private final InputPanel inputPanel;
    private final TokensPanel tokensPanel;
    private final AttentionPanel attentionPanel;
    private final LogitsPanel logitsPanel;
    private final TracePanel tracePanel;
    private final OutputPanel outputPanel;
    private final ControlsPanel controlsPanel;

    public MainFrame(TransformerEngine engine) {
        super("Swing Transformer — Visual Didactic Simulator");
        this.engine = engine;

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1100, 740);
        setLocationRelativeTo(null);

        inputPanel = new InputPanel();
        tokensPanel = new TokensPanel();
        attentionPanel = new AttentionPanel();
        logitsPanel = new LogitsPanel();
        tracePanel = new TracePanel();
        outputPanel = new OutputPanel();
        controlsPanel = new ControlsPanel();

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Tokens", tokensPanel);
        tabs.addTab("Attention", attentionPanel);
        tabs.addTab("Logits", logitsPanel);
        tabs.addTab("Decode Trace", tracePanel);
        tabs.addTab("Output", outputPanel);

        JPanel top = new JPanel(new BorderLayout(8, 8));
        top.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        top.add(inputPanel, BorderLayout.CENTER);
        top.add(controlsPanel, BorderLayout.EAST);

        setLayout(new BorderLayout());
        add(top, BorderLayout.NORTH);
        add(tabs, BorderLayout.CENTER);

        controlsPanel.onRun(() -> runOnce());
        controlsPanel.onClear(() -> clearAll());
    }

    // Runs the engine off the EDT and updates the UI when done
    private void runOnce() {
        String input = inputPanel.getInputText();
        EngineConfig cfg = controlsPanel.getConfig();

        new Thread(() -> {
            TransformerRun run = engine.run(input, cfg);

            SwingUtilities.invokeLater(() -> {
                tokensPanel.setTokens(run.tokens());
                attentionPanel.setTokens(run.tokens());
                attentionPanel.setSimulation(run.simulation());
                logitsPanel.setSimulation(run.simulation());
                tracePanel.setTrace(run.simulation().decodeTrace());
                outputPanel.setOutput(run.output());
            });
        }).start();
    }


    private void clearAll() {
        tokensPanel.clear();
        attentionPanel.clear();
        logitsPanel.clear();
        tracePanel.clear();
        outputPanel.clear();
    }
}
