package com.leviathanstudio.mineide.main;

import com.leviathanstudio.mineide.compiler.MainClassGenerator;

public class MineIDE
{
    
    public static void main(String[] args) throws Exception
    {
        // Debug
        MainClassGenerator.setMainClassPackage("fr.zeamateis.test");
        MainClassGenerator.setMainClassName("testMainClass");
        MainClassGenerator.generateMainClass();
    }
}