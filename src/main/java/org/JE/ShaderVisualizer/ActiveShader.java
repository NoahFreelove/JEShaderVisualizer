package main.java.org.JE.ShaderVisualizer;

import org.JE.JE2.Manager;
import org.JE.JE2.Rendering.Shaders.ShaderProgram;
import org.JE.JE2.Window.GLAgent;

public class ActiveShader {
    public static ShaderProgram active;
    public static GLAgent queueCompileStatus;
    public static boolean lighting = false;

    public static void init(){
        active = ShaderProgram.invalidShader();
    }
    public static void update(String vert, String frag){
        //System.out.println("Updating shader: " + vert + " " + frag);
        if(vert == null || frag == null){
            active = ShaderProgram.invalidShader();
            return;
        }
        queueCompileStatus = Manager.queueGLFunction(() -> active = ShaderProgram.ShaderProgramNow(vert,frag,lighting));

    }
}
