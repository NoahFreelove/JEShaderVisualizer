package main.java.org.JE.ShaderVisualizer;

import org.JE.JE2.Manager;
import org.JE.JE2.Rendering.Shaders.ShaderProgram;
import org.JE.JE2.Window.GLAgent;

public class ActiveShader {
    public static ShaderProgram active;
    public static GLAgent queueCompileStatus;
    public static boolean lighting = false;
    public static boolean fragExists = false;
    public static boolean vertExists = false;

    public static void init(){
        active = ShaderProgram.invalidShader();
        active.logCommonErrors = false;
    }
    public static void update(String vert, String frag){
        //System.out.println("Updating shader: " + vert + " " + frag);
        if(vert == null || frag == null || vert.isEmpty() || frag.isEmpty()) {
            active = ShaderProgram.invalidShader();
            vertExists = !(vert == null || vert.isEmpty());
            fragExists = !(frag == null || frag.isEmpty());
            return;
        }
        fragExists = true;
        vertExists = true;
        queueCompileStatus = Manager.queueGLFunction(() -> {
            active = ShaderProgram.ShaderProgramNow(vert,frag,lighting);
            active.logCommonErrors = false;
            ShaderDebug.shaderUpdated = true;
        });

    }
}
