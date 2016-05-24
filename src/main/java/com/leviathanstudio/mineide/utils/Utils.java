package com.leviathanstudio.mineide.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class Utils
{
    public static final String IMG_DIR = "/mineIDE/img/";
    public static final String CSS_DIR = "/mineIDE/css/";
    public static final String JSON_DIR = "/mineIDE/json/";
    public static final String HTML_DIR = "/mineIDE/html/";
    public static final String LANG_DIR = "/mineIDE/lang/";
    public static final String TEMPLATE_DIR = "/minecraft/template/";
    
    public static final File ROOT = EnumOS.getWorkingDirectory();
    public static final File CONFIG = new File(Utils.ROOT, "config");
    public static final File PROJECT = new File(Utils.ROOT, "project");
    public static final File LOG = new File(Utils.ROOT, "log");
    public static final File FORGE_DIR = new File(Utils.ROOT + "/workspace/forge");
    // public static final File FORGE_SRC_JAVA_DIR = new File(ROOT +
    // "/test/java");
    // public static final File FORGE_SRC_RESOURCE_DIR = new File(ROOT +
    // "/test/resources");
    public static final File FORGE_SRC_JAVA_DIR = new File(Utils.ROOT + "/workspace/forge/src/main/java");
    public static final File FORGE_SRC_RESOURCE_DIR = new File(Utils.ROOT + "/workspace/forge/src/main/resources");
    
    public static void writeFile(File file, String fileContent)
    {
        try
        {
            Files.write(Paths.get(file.toURI()), fileContent.getBytes());
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public static String readFile(File file)
    {
        try
        {
            return Files.lines(Paths.get(file.toURI())).collect(Collectors.joining("\n"));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static String readFile(String filePath, String fileName, String fileExtension)
    {
        return readFile(new File(filePath, fileName + "." + fileExtension));
    }
    
    public static void writeFile(String filePath, String fileName, String fileContent, String fileExtension)
    {
        writeFile(new File(filePath, fileName + "." + fileExtension), "");
    }
    
    public static void checkDir(File file)
    {
        if(!file.exists())
            file.mkdirs();
    }
}