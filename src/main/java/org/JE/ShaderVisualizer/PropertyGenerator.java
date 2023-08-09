package main.java.org.JE.ShaderVisualizer;

import org.JE.JE2.IO.Filepath;
import org.JE.JE2.Rendering.Texture;
import org.JE.JE2.UI.UIElements.Buttons.Button;
import org.JE.JE2.UI.UIElements.Group;
import org.JE.JE2.UI.UIElements.Label;
import org.JE.JE2.UI.UIElements.Spacer;
import org.JE.JE2.UI.UIElements.Style.Color;
import org.JE.JE2.UI.UIElements.TextField;

import java.io.File;
import java.util.Random;

public class PropertyGenerator {


    public static Group generateTextureUI(){
        Group g = new Group();
        Label textureLabel = new Label("Texture FP");
        textureLabel.getStyle().setTextColor(Color.WHITE);
        TextField textureField = new TextField(256);
        Button setTextureButton = new Button("Set Texture", () -> {
            String fp = textureField.getValue();
            if(new File(fp).exists() && (fp.endsWith(".png") || fp.endsWith(".jpg") || fp.endsWith(".jpeg") || fp.endsWith(".bmp"))){
                Random randName = new Random();
                String name = "Texture" + randName.nextInt(100000);
                VisualizerScene.renderer.setTexture(Texture.createTexture(name,new Filepath(fp,false),true));
            }
            else {
                textureField.setValue("File DNE!");
            }
        });

        Label normalLabel = new Label("Normal FP");
        normalLabel.getStyle().setTextColor(Color.WHITE);
        TextField normalField = new TextField(128);
        Button setNormalButton = new Button("Set Normal", () -> {
            String fp = normalField.getValue();
            if(new File(fp).exists() && (fp.endsWith(".png") || fp.endsWith(".jpg") || fp.endsWith(".jpeg") || fp.endsWith(".bmp"))){
                Random randName = new Random();
                String name = "Normal" + randName.nextInt(100000);
                VisualizerScene.renderer.setTexture(Texture.createTexture(name,new Filepath(fp,false),true));
            }
            else {
                textureField.setValue("File DNE!");
            }
        });
        g.addElements(textureLabel,textureField,setTextureButton,new Spacer(), normalLabel,normalField,setNormalButton);


        return g;
    }
}
