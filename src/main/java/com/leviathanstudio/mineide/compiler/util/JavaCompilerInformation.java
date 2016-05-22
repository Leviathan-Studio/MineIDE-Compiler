package com.leviathanstudio.mineide.compiler.util;

public class JavaCompilerInformation extends JavaCompilerStatement
{
    private String  unlocalizedName, stepSound, creativeTab;
    private float   hardness, resistance, lightLevel;
    private int     lightOpacity, harvestLevel, maxStackSize, maxDamage;
    private boolean unbreakable, tickRandom, noRepair;

    public String getUnlocalizedName()
    {
        return this.unlocalizedName;
    }

    public void setUnlocalizedName(String unlocalizedName)
    {
        this.unlocalizedName = unlocalizedName;
    }

    public String getStepSound()
    {
        return this.stepSound;
    }

    public void setStepSound(String stepSound)
    {
        this.stepSound = stepSound;
    }

    public String getCreativeTab()
    {
        return this.creativeTab;
    }

    public void setCreativeTab(String creativeTab)
    {
        this.creativeTab = creativeTab;
    }

    public float getHardness()
    {
        return this.hardness;
    }

    public void setHardness(float hardness)
    {
        this.hardness = hardness;
    }

    public float getResistance()
    {
        return this.resistance;
    }

    public void setResistance(float resistance)
    {
        this.resistance = resistance;
    }

    public float getLightLevel()
    {
        return this.lightLevel;
    }

    public void setLightLevel(float lightLevel)
    {
        this.lightLevel = lightLevel;
    }

    public int getLightOpacity()
    {
        return this.lightOpacity;
    }

    public void setLightOpacity(int lightOpacity)
    {
        this.lightOpacity = lightOpacity;
    }

    public int getHarvestLevel()
    {
        return this.harvestLevel;
    }

    public void setHarvestLevel(int harvestLevel)
    {
        this.harvestLevel = harvestLevel;
    }

    public boolean isUnbreakable()
    {
        return this.unbreakable;
    }

    public void setUnbreakable(boolean unbreakable)
    {
        this.unbreakable = unbreakable;
    }

    public boolean isTickRandom()
    {
        return this.tickRandom;
    }

    public void setTickRandom(boolean tickRandom)
    {
        this.tickRandom = tickRandom;
    }

    public int getMaxStackSize()
    {
        return this.maxStackSize;
    }

    public void setMaxStackSize(int maxStackSize)
    {
        this.maxStackSize = maxStackSize;
    }

    public int getMaxDamage()
    {
        return this.maxDamage;
    }

    public void setMaxDamage(int maxDamage)
    {
        this.maxDamage = maxDamage;
    }

    public boolean isNoRepair()
    {
        return this.noRepair;
    }

    public void setNoRepair(boolean noRepair)
    {
        this.noRepair = noRepair;
    }
}