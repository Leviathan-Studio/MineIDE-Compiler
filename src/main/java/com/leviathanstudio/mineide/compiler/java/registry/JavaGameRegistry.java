package com.leviathanstudio.mineide.compiler.java.registry;

import java.util.ArrayList;
import java.util.List;

import com.squareup.javapoet.CodeBlock;

public abstract class JavaGameRegistry
{
    private List<CodeBlock> gameRegistryList = new ArrayList<CodeBlock>();
    private CodeBlock.Builder gameRegistry = CodeBlock.builder();
    
    public CodeBlock.Builder getGameRegistry()
    {
        return gameRegistry;
    }
    
    public void setGameRegistry(CodeBlock.Builder gameRegistry)
    {
        this.gameRegistry = gameRegistry;
    }
    
    public List<CodeBlock> getGameRegistryList()
    {
        return gameRegistryList;
    }
    
    public abstract void initializeGameRegistry();
    
    public JavaGameRegistry compile()
    {
        this.initializeGameRegistry();
        
        for(int i = 0; i < this.gameRegistryList.size(); i++)
        {
            this.gameRegistry.add(this.gameRegistryList.get(i));
        }
        return this;
    }
    
}