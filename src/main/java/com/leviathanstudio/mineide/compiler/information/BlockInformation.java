package com.leviathanstudio.mineide.compiler.information;

public class BlockInformation
{
    private String unlocalizedName, stepSound;
    private float hardness, resistance, lightLevel;
    private int lightOpacity, harvestLevel;
    private boolean unbreakable, tickRandom;
    
    public String getUnlocalizedName()
    {
        return unlocalizedName;
    }
    
    public void setUnlocalizedName(String unlocalizedName)
    {
        this.unlocalizedName = unlocalizedName;
    }
    
    public String getStepSound()
    {
        return stepSound;
    }
    
    public void setStepSound(String stepSound)
    {
        this.stepSound = stepSound;
    }
    
    public float getHardness()
    {
        return hardness;
    }
    
    public void setHardness(float hardness)
    {
        this.hardness = hardness;
    }
    
    public float getResistance()
    {
        return resistance;
    }
    
    public void setResistance(float resistance)
    {
        this.resistance = resistance;
    }
    
    public float getLightLevel()
    {
        return lightLevel;
    }
    
    public void setLightLevel(float lightLevel)
    {
        this.lightLevel = lightLevel;
    }
    
    public int getLightOpacity()
    {
        return lightOpacity;
    }
    
    public void setLightOpacity(int lightOpacity)
    {
        this.lightOpacity = lightOpacity;
    }
    
    public int getHarvestLevel()
    {
        return harvestLevel;
    }
    
    public void setHarvestLevel(int harvestLevel)
    {
        this.harvestLevel = harvestLevel;
    }
    
    public boolean isUnbreakable()
    {
        return unbreakable;
    }
    
    public void setUnbreakable(boolean unbreakable)
    {
        this.unbreakable = unbreakable;
    }
    
    public boolean isTickRandom()
    {
        return tickRandom;
    }
    
    public void setTickRandom(boolean tickRandom)
    {
        this.tickRandom = tickRandom;
    }
}
