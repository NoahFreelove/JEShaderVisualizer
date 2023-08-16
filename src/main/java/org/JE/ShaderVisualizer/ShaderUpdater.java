package main.java.org.JE.ShaderVisualizer;

import org.JE.JE2.IO.Filepath;
import org.JE.JE2.Resources.DataLoader;

import java.io.File;

public class ShaderUpdater {
    public static volatile String vertexShader;
    public static volatile String fragmentShader;
    public static volatile String fragFilepath = new Filepath("lightSprite.frag", true).getPath(false);
    public static volatile String vertFilepath = new Filepath("lightSprite.vert", true).getPath(false);


    public static String loadFile(String fp){
        if(!new File(fp).exists())
            return "";
        try {
            String[] str = DataLoader.readTextFile(fp);
            StringBuilder file = new StringBuilder();

            for(int i = 0; i < str.length; i++){
                file.append(str[i].strip()).append("\n");
            }
            return file.toString();
        }
        catch (Exception e){
            return "";
        }
    }

    public static void startSearchThread(){


        Thread findFile = new Thread(() -> {
            while (true) {
                String tmpFragmentShader = loadFile(fragFilepath);
                String tmpVertexShader = loadFile(vertFilepath);

                boolean fragUpdated = !tmpFragmentShader.equals(fragmentShader);
                boolean vertUpdated = !tmpVertexShader.equals(vertexShader);
                fragmentShader = tmpFragmentShader;
                vertexShader = tmpVertexShader;
                if(fragUpdated || vertUpdated){
                    ActiveShader.update(vertexShader, fragmentShader);
                }

                //System.out.println("Frag updated within the last second: " + fragUpdated);
                //System.out.println("Vert updated within the last second: " + vertUpdated);

                try {
                    Thread.sleep(1000); // Sleep for 1 second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        findFile.start();
    }
}
