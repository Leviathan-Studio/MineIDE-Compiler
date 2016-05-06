package com.leviathanstudio.mineide.compiler.java.registry;

import com.squareup.javapoet.CodeBlock;

public class JavaGameRegistry
{
    
    private CodeBlock gameRegistry;
    
    public CodeBlock getGameRegistry()
    {
        return gameRegistry;
    }
    
    public void setGameRegistry(String type, Object object)
    {
        this.gameRegistry = CodeBlock.builder().addStatement("GameRegistry.register" + type + "(" + "\"$N\"" + "," + " $L" + ")", object, object).build();
    }
    
}