package com.leviathanstudio.mineide.utils;

import java.io.File;

public enum EnumOS
{
    WINDOWS, LINUX, MACOS, OTHER;
    
    public static EnumOS getPlatform()
    {
        final String osName = System.getProperty("os.name").toLowerCase();
        return osName.contains("win") ? WINDOWS : (osName.contains("mac") ? MACOS : (osName.contains("linux") ? LINUX : (osName.contains("unix") ? LINUX : OTHER)));
    }
    
    public static File getWorkingDirectory()
    {
        final String userHome = System.getProperty("user.home", ".");
        final File workingDirectory;
        
        switch(getPlatform())
        {
            case WINDOWS:
                workingDirectory = new File(System.getenv("APPDATA"), ".WolfIDE/");
                break;
            case MACOS:
                workingDirectory = new File(userHome, "Library/Application Support/" + ".WolfIDE/");
                break;
            case LINUX:
                workingDirectory = new File(userHome, ".WolfIDE/");
                break;
            default:
                workingDirectory = new File(userHome, ".WolfIDE/");
                break;
        }
        return workingDirectory;
    }
}