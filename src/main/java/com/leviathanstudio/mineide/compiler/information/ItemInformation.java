package com.leviathanstudio.mineide.compiler.information;

public class ItemInformation
{
    private String unlocalizedName, creativeTab;
    private int maxStackSize, maxDamage;
    private boolean noRepair;
    
    public String getUnlocalizedName()
    {
        return unlocalizedName;
    }
    
    public void setUnlocalizedName(String unlocalizedName)
    {
        this.unlocalizedName = unlocalizedName;
    }
    
    public String getCreativeTab()
    {
        return creativeTab;
    }
    
    public void setCreativeTab(String creativeTab)
    {
        this.creativeTab = creativeTab;
    }
    
    public int getMaxStackSize()
    {
        return maxStackSize;
    }
    
    public void setMaxStackSize(int maxStackSize)
    {
        this.maxStackSize = maxStackSize;
    }
    
    public int getMaxDamage()
    {
        return maxDamage;
    }
    
    public void setMaxDamage(int maxDamage)
    {
        this.maxDamage = maxDamage;
    }
    
    public boolean isNoRepair()
    {
        return noRepair;
    }
    
    public void setNoRepair(boolean noRepair)
    {
        this.noRepair = noRepair;
    }
    
}