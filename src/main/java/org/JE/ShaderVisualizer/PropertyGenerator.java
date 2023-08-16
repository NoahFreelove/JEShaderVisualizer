package main.java.org.JE.ShaderVisualizer;

import org.JE.JE2.IO.Filepath;
import org.JE.JE2.Rendering.Texture;
import org.JE.JE2.UI.UIElements.*;
import org.JE.JE2.UI.UIElements.Buttons.Button;
import org.JE.JE2.UI.UIElements.Style.Color;
import org.JE.JE2.Utility.WindowDialogs;

import java.io.File;
import java.util.Random;

public class PropertyGenerator {


    public static Group generateTextureUI(){
        Group g = new Group();
        Label textureLabel = new Label("Texture FP");
        textureLabel.getStyle().setTextColor(Color.WHITE());
        TextField textureField = new TextField(256);
        Button setTextureButton = new Button("Set Texture", () -> {
            String fp = textureField.getValue();
            if(new File(fp).exists() && (fp.endsWith(".png") || fp.endsWith(".jpg") || fp.endsWith(".jpeg") || fp.endsWith(".bmp"))){
                Random randName = new Random();
                String name = "Texture" + randName.nextInt(100000);
                VisualizerScene.renderer.setTexture(Texture.createTexture(name,new Filepath(fp,false),true));
                ActiveShader.active.supportsTextures = true;
            }
            else {
                textureField.setValue("File DNE!");
            }
        });
        Button selectFromFileExplorer = new Button("Select...", new Runnable() {
            @Override
            public void run() {
                File f = WindowDialogs.getFile("Choose A Image","", new String[]{"png","jpg","jpeg","bmp"});
                if(f.exists()){
                    textureField.setValue(f.getAbsolutePath());
                }
            }
        });

        Label normalLabel = new Label("Normal FP");
        normalLabel.getStyle().setTextColor(Color.WHITE());
        TextField normalField = new TextField(128);
        Button setNormalButton = new Button("Set Normal", () -> {
            String fp = normalField.getValue();
            if(new File(fp).exists() && (fp.endsWith(".png") || fp.endsWith(".jpg") || fp.endsWith(".jpeg") || fp.endsWith(".bmp"))){
                Random randName = new Random();
                String name = "Normal" + randName.nextInt(100000);
                VisualizerScene.renderer.setTexture(Texture.createTexture(name,new Filepath(fp,false),true));
                ActiveShader.active.supportsTextures = true;
            }
            else {
                textureField.setValue("File DNE!");
            }
        });
        Button selectNormalFromFileExplorer = new Button("Select...", new Runnable() {
            @Override
            public void run() {
                File f = WindowDialogs.getFile("Choose A Image","", new String[]{"png","jpg","jpeg","bmp"});
                if(f.exists()){
                    normalField.setValue(f.getAbsolutePath());
                }
            }
        });
        g.addElements(textureLabel, textureField, setTextureButton, selectFromFileExplorer, new Spacer(), normalLabel,normalField,setNormalButton,selectNormalFromFileExplorer);


        return g;
    }

    public static Group generateShaderFileUI(){
        Group g = new Group();
        Label textureLabel = new Label("Vertex Shader");
        textureLabel.getStyle().setTextColor(Color.WHITE());
        TextField textureField = new TextField(256);
        Button setTextureButton = new Button("Set Vertex Shader", () -> {
            String fp = textureField.getValue();
            if(new File(fp).exists()){
                ShaderUpdater.vertFilepath = new File(fp).getAbsolutePath();
            }
            else {
                textureField.setValue("File DNE!");
            }
        });
        Button selectFromFileExplorer = new Button("Select...", new Runnable() {
            @Override
            public void run() {
                File f = WindowDialogs.getFile("Choose Vertex Shader","", new String[]{"vert","vertex","*"});
                if(f.exists()){
                    textureField.setValue(f.getAbsolutePath());
                }
            }
        });

        Label setFragmentLabel = new Label("Fragment Shader");
        textureLabel.getStyle().setTextColor(Color.WHITE());
        TextField fragmentField = new TextField(256);
        Button setFragButton = new Button("Set Fragment Shader", () -> {
            String fp = fragmentField.getValue();
            if(new File(fp).exists()){
                ShaderUpdater.fragFilepath = new File(fp).getAbsolutePath();
            }
            else {
                fragmentField.setValue("File DNE!");
            }
        });
        Button selectFrag = new Button("Select...", new Runnable() {
            @Override
            public void run() {
                File f = WindowDialogs.getFile("Choose Fragment Shader","", new String[]{"frag","fragment","*"});
                if(f.exists()){
                    fragmentField.setValue(f.getAbsolutePath());
                }
            }
        });
        g.addElements(textureLabel, textureField, setTextureButton, selectFromFileExplorer, new Spacer(), setFragmentLabel,fragmentField,setFragButton,selectFrag);


        return g;
    }

    public static UIElement generateMaterialUI() {
        return new Group();
    }
}
