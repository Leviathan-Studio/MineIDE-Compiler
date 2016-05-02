package com.leviathanstudio.mineide.main;

import com.leviathanstudio.mineide.compiler.java.JavaMainClassCompiler;
import com.leviathanstudio.mineide.compiler.java.JavaProxiesCompiler;
import com.leviathanstudio.mineide.compiler.json.JsonMCModInfoCompiler;
import com.leviathanstudio.mineide.utils.Utils;

public class MineIDECompiler
{
    /**
     * Debug Main Class for testing compiler
     */
    public static void main(String[] args) throws Exception
    {
        JavaMainClassCompiler mainClassCompiler = new JavaMainClassCompiler();
        
        mainClassCompiler.setMainClassPackage("fr.zeamateis.test");
        mainClassCompiler.setMainClassName("testMainClass");
        mainClassCompiler.compile();
        mainClassCompiler.getMainClassJavaFile().writeTo(Utils.FORGE_SRC_JAVA_DIR);
        
        JavaProxiesCompiler proxiesCompiler = new JavaProxiesCompiler();
        proxiesCompiler.setProxiesPackage(mainClassCompiler.getMainClassPackage() + ".proxy");
        proxiesCompiler.compile();
        proxiesCompiler.getClientProxyJavaFile().writeTo(Utils.FORGE_SRC_JAVA_DIR);
        proxiesCompiler.getCommonProxyJavaFile().writeTo(Utils.FORGE_SRC_JAVA_DIR);
        proxiesCompiler.getServerProxyJavaFile().writeTo(Utils.FORGE_SRC_JAVA_DIR);
        
        JsonMCModInfoCompiler mcModInfoCompiler = new JsonMCModInfoCompiler();
        mcModInfoCompiler.compile();
    }
}
