package com.celso.swingtransformer;

import com.celso.swingtransformer.backend.MockBackend;
import com.celso.swingtransformer.backend.ModelBackend;
import com.celso.swingtransformer.core.TransformerEngine;
import com.celso.swingtransformer.ui.MainFrame;

import javax.swing.*;
import java.awt.*;

public class App {
    public static void main(String[] args) {
    	// Some reasonable default UI settings (still plain Swing)

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        EventQueue.invokeLater(() -> {
            ModelBackend backend = new MockBackend();
            TransformerEngine engine = new TransformerEngine(backend);
            MainFrame frame = new MainFrame(engine);
            frame.setVisible(true);
        });
    }
}
