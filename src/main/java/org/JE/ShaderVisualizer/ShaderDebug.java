package main.java.org.JE.ShaderVisualizer;

import org.JE.JE2.IO.Logging.Logger;
import org.JE.JE2.Rendering.Shaders.ShaderProgram;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ShaderDebug {
    public static boolean shaderUpdated = false;
    private static String latestError = "";
    public static void init(){
        Logger.errorListeners.add(je2Error -> parseError(je2Error.getMessage()));
    }
    public static void startDebugProcess(ShaderProgram sp){
        String vert = sp.vertex;
        String frag = sp.fragment;
        debug(vert, false);
        debug(frag, true);
    }

    private static void debug(String src, boolean isFrag){
        String type = (isFrag? "Fragment" : "Vertex");
        String[] lines = src.split("\n");
        String equalBrackets = equalBrackets(src);

        if(!equalBrackets.isEmpty()){
            System.out.println(type + " Shader: Brackets are not equal. " + equalBrackets);
        }
        Integer[] semicolonMissing = semicolonMissing(lines);
        for (int j : semicolonMissing) {
            System.out.println(type + " Shader may be missing a semicolon at line " + j);
        }
    }

    private static Integer[] semicolonMissing(String[] lines) {
        ArrayList<Integer> missingLines = new ArrayList<>();
        for(int i = 0; i < lines.length; i++){
            String line = lines[i];
            if(line.trim().isEmpty())
                continue;
            if(line.trim().startsWith("//"))
                continue;
            if(line.trim().startsWith("/*"))
                continue;
            if(line.trim().endsWith("*/"))
                continue;
            if(line.trim().endsWith(";"))
                continue;
            if(line.trim().startsWith("#"))
                continue;
            if(line.trim().endsWith("{"))
                continue;
            if(line.trim().endsWith("}"))
                continue;
            if(line.trim().endsWith(")"))
            {
                if(i+1 < lines.length){
                    if(lines[i+1].trim().startsWith("{"))
                        continue;
                }
            }
            missingLines.add(i+1);
        }
        return missingLines.toArray(new Integer[0]);
    }

    private static String equalBrackets(String src){
        // Make sure theres the same amount of () [] {} in the string
        int paren = 0;
        int bracket = 0;
        int curly = 0;
        for(char c : src.toCharArray()){
            if(c == '(')
                paren++;
            if(c == ')')
                paren--;
            if(c == '[')
                bracket++;
            if(c == ']')
                bracket--;
            if(c == '{')
                curly++;
            if(c == '}')
                curly--;
        }
        if(paren == 0 && bracket == 0 && curly == 0)
            return "";
        StringBuilder append = equalBracketResponse(paren, bracket, curly);
        return append.toString();
    }

    @NotNull
    private static StringBuilder equalBracketResponse(int paren, int bracket, int curly) {
        StringBuilder append = new StringBuilder();
        if(paren !=0){
            append.append("Parenthesis do not match 0 (").append(paren).append(")\n");
        }
        if(bracket !=0){
            append.append("Brackets do not match 0 [").append(bracket).append("]\n");
        }
        if(curly !=0){
            append.append("Curly Brackets do not match 0 {").append(curly).append("}\n");
        }
        return append;
    }

    public static void parseError(String msg){
        if(msg.startsWith("Error with Shader:"))
            return;
        latestError = msg;
    }

    public static String getLatestError(){
        return latestError;
    }
}
