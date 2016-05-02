package com.leviathanstudio.mineide.main;

import com.leviathanstudio.mineide.compiler.java.JavaMainClassCompiler;
import com.leviathanstudio.mineide.compiler.java.JavaProxiesCompiler;
import com.leviathanstudio.mineide.utils.Utils;

public class MineIDECompiler
{
    /**
     * Debug Main Class for testing compiler
     */
    public static void main(String[] args) throws Exception
    {
        JavaMainClassCompiler.setMainClassPackage("fr.zeamateis.test");
        JavaMainClassCompiler.setMainClassName("testMainClass");
        JavaMainClassCompiler.generateMainClass();
        JavaMainClassCompiler.getJavaFile().writeTo(Utils.FORGE_SRC_JAVA_DIR);
        
        JavaProxiesCompiler.setProxiesPackage(JavaMainClassCompiler.getMainClassPackage() + ".proxy");
        JavaProxiesCompiler.generateProxiesClasses();
        JavaProxiesCompiler.getClientProxyJavaFile().writeTo(Utils.FORGE_SRC_JAVA_DIR);
        JavaProxiesCompiler.getCommonProxyJavaFile().writeTo(Utils.FORGE_SRC_JAVA_DIR);
        JavaProxiesCompiler.getServerProxyJavaFile().writeTo(Utils.FORGE_SRC_JAVA_DIR);
    }
}
