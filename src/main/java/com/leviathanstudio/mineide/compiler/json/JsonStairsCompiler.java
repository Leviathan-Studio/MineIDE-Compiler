package com.leviathanstudio.mineide.compiler.json;

public class JsonStairsCompiler extends JsonCompiler
{

    public JsonStairsCompiler(String modid)
    {
        super(modid, "stairs");
    }

    @Override
    public void addPatternKeys()
    {

    }

    @Override
    public void addPatternFiles()
    {
        this.addBlockstate("test", "default");
    }

}
