package main.java.org.JE.ShaderVisualizer;

import org.JE.JE2.Objects.GameObject;
import org.JE.JE2.Rendering.Camera;
import org.JE.JE2.Rendering.Debug.RenderColoredArea;
import org.JE.JE2.Rendering.Renderers.SpriteRenderer;
import org.JE.JE2.Rendering.Shaders.ShaderProgram;
import org.JE.JE2.Scene.Scene;
import org.JE.JE2.UI.UIElements.Buttons.Button;
import org.JE.JE2.UI.UIElements.Label;
import org.JE.JE2.UI.UIElements.Sliders.Slider;
import org.JE.JE2.UI.UIElements.Spacer;
import org.JE.JE2.UI.UIElements.Style.Color;
import org.JE.JE2.UI.UIObjects.UIWindow;
import org.joml.Vector2f;

import static org.lwjgl.nuklear.Nuklear.*;

public class VisualizerScene extends Scene {
    public static GameObject renderObject;
    private static Color color = Color.WHITE;
    private static SpriteRenderer renderer;
    private static Button debugButton;
    private static Label debugLabel;
    private static ShaderProgram defaultProgram;
    private static UIWindow debugWindow;
    public VisualizerScene(){
        defaultProgram = ShaderProgram.defaultShader();
        Camera mainCam = new Camera();
        GameObject camObj = new GameObject();
        camObj.addScript(mainCam);
        renderObject = new GameObject();
        renderer = new SpriteRenderer();
        renderObject.addScript(renderer);
        renderObject.getRenderer().material.setBaseColor(color);

        setSquare();

        add(camObj);
        setCamera(mainCam);
        add(renderObject);

        createUI();

    }

    private void createUI() {
        UIWindow uiWindow = new UIWindow("Adjust Visuals",
                NK_WINDOW_TITLE | NK_WINDOW_BORDER | NK_WINDOW_MINIMIZABLE,
                new Vector2f(0,0));
        uiWindow.setSize(new Vector2f(150,650));

        uiWindow.setBackgroundColor(Color.DARK_GREY);

        Label colorLabel = new Label("Set Material Color");

        Label R = new Label("Red");
        Slider r = new Slider(0f, 0f, 1, 0.05f);
        r.onChange = value -> {
            color.r(value);
            renderObject.getRenderer().getMaterial().setBaseColor(color);
        };

        Label G = new Label("Green");
        Slider g = new Slider(0f, 0f, 1, 0.05f);
        g.onChange = value -> {
            color.g(value);
            renderObject.getRenderer().getMaterial().setBaseColor(color);
        };

        Label B = new Label("Blue");
        Slider b = new Slider(0f, 0f, 1, 0.05f);
        b.onChange = value -> {
            color.b(value);
            renderObject.getRenderer().getMaterial().setBaseColor(color);
        };

        Label A = new Label("Alpha");
        Slider a = new Slider(0f, 0f, 1, 0.05f);
        a.onChange = value -> {
            color.a(value);
            renderObject.getRenderer().getMaterial().setBaseColor(color);
        };
        r.v = color.r();
        g.v = color.g();
        b.v = color.b();
        a.v = color.a();

        uiWindow.addElement(colorLabel,R,r,G,g,B,b,A,a, new Spacer());

        Label shapeLabel = new Label("Various Shapes");

        Button squareBtn = new Button("Square", this::setSquare);
        Button circleBtn = new Button("Circle", this::setCircle);
        Button triangleBtn = new Button("Triangle", this::setTriangle);

        uiWindow.addElement(shapeLabel,squareBtn,circleBtn,triangleBtn, new Spacer());

        debugWindow = new UIWindow("Debug",
                NK_WINDOW_TITLE | NK_WINDOW_BORDER | NK_WINDOW_MINIMIZABLE,
                new Vector2f(0,650));
        debugWindow.setSize(new Vector2f(800,150));

        debugWindow.setBackgroundColor(Color.DARK_GREY);
        debugLabel = new Label("This shader doesn't work or isn't compiled yet.");
        debugButton = new Button("Debug", () -> ShaderDebug.startDebugProcess(ActiveShader.active));

        debugWindow.addElement(debugLabel,debugButton);



        addUI(uiWindow);
        addUI(debugWindow);
    }


    @Override
    public void update(boolean physicsUpdate) {
        super.update(physicsUpdate);
        //System.out.println(ActiveShader.active.valid());
        if(ActiveShader.active.fragmentCompileStatus && ActiveShader.active.vertexCompileStatus){
            renderObject.getRenderer().setShaderProgram(ActiveShader.active);

            debugWindow.setVisibility(false);
        }
        else{

            renderObject.getRenderer().setShaderProgram(defaultProgram);
            debugWindow.setVisibility(true);

        }
    }

    private void setCircle(){
        renderer.getTextureSegments()[0].getVao2fRef().setVertices(RenderColoredArea.generateCircleCoords(100));

        Vector2f scale = new Vector2f(3,3);
        renderObject.getTransform().setScale(scale);
        renderObject.getTransform().setPosition(0,0);
    }
    private void setSquare(){
        renderer.getTextureSegments()[0].getVao2fRef().setVertices(new Vector2f[]{
                new Vector2f(0,0),
                new Vector2f(1,0),
                new Vector2f(1,1),
                new Vector2f(0,1)
        });
        Vector2f scale = new Vector2f(5,5);
        renderObject.getTransform().setScale(scale);
        renderObject.getTransform().setPosition(new Vector2f(-scale.x()/2,-scale.y()/2));
    }

    private void setTriangle(){
        renderer.getTextureSegments()[0].getVao2fRef().setVertices(new Vector2f[]{
                new Vector2f(0,0),
                new Vector2f(0.5f,1),
                new Vector2f(1,0)
        });
        Vector2f scale = new Vector2f(5,5);
        renderObject.getTransform().setScale(scale);
        renderObject.getTransform().setPosition(new Vector2f(-scale.x()/2,-scale.y()/2));
    }


}
