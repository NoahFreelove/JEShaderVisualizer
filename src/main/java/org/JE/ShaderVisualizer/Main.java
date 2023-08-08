package main.java.org.JE.ShaderVisualizer;

import org.JE.JE2.IO.Logging.Logger;
import org.JE.JE2.Manager;
import org.JE.JE2.Window.WindowPreferences;
import org.joml.Vector2i;

public class Main {
    public static void main(String[] args) {
        WindowPreferences wp = new WindowPreferences();
        wp.windowSize = new Vector2i(800,800);
        wp.vSync = true;
        wp.initializeNuklear = true;
        wp.windowResizable = false;
        wp.windowTitle = "Shader";
        Logger.disableAllLogging();
        Manager.start(wp);
        ActiveShader.init();
        ShaderUpdater.startSearchThread();
        Manager.setScene(new VisualizerScene());
    }
}