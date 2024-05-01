package me.niculicicris.visualiser.application;

import me.niculicicris.visualiser.renderer.SceneRenderer;
import me.niculicicris.visualiser.renderer.Window;

public class VisualiserApplication {

    public static void run() {
        Window.getInstance().initialize();

        SceneRenderer renderer = SceneRenderer.getInstance();
        renderer.run();

        Window.getInstance().destroy();
    }
}
