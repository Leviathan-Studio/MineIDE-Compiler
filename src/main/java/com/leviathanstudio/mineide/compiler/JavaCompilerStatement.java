package com.leviathanstudio.mineide.compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;

public class JavaCompilerStatement
{
    private CodeBlock unlocalizedNameStatement, creativeTabStatement, hardnessStatement, resistanceStatement, stepSoundStatement, harvestLevelStatement, lightLevelStatement, lightOpacityStatement, unbreakableStatement, tickRandomlyStatement, maxStackSizeStatement, maxDamageStatement, noRepairStatement;
    
    public CodeBlock getUnlocalizedNameStatement()
    {
        return unlocalizedNameStatement;
    }
    
    public void setUnlocalizedNameStatement(String unlocalizedName)
    {
        this.unlocalizedNameStatement = CodeBlock.builder().addStatement("this.$N(\"$L\")", "setUnlocalizedName", unlocalizedName).build();
    }
    
    public CodeBlock getCreativeTabStatement()
    {
        return creativeTabStatement;
    }
    
    public void setCreativeTabStatement(ClassName creativeTabClass, String creativeTab)
    {
        this.creativeTabStatement = CodeBlock.builder().addStatement("this.$N($T.$L)", "setCreativeTab", creativeTabClass, creativeTab).build();
    }
    
    public CodeBlock getHardnessStatement()
    {
        return hardnessStatement;
    }
    
    public void setHardnessStatement(CodeBlock hardnessStatement)
    {
        this.hardnessStatement = hardnessStatement;
    }
    
    public CodeBlock getResistanceStatement()
    {
        return resistanceStatement;
    }
    
    public void setResistanceStatement(CodeBlock resistanceStatement)
    {
        this.resistanceStatement = resistanceStatement;
    }
    
    public CodeBlock getStepSoundStatement()
    {
        return stepSoundStatement;
    }
    
    public void setStepSoundStatement(CodeBlock stepSoundStatement)
    {
        this.stepSoundStatement = stepSoundStatement;
    }
    
    public CodeBlock getHarvestLevelStatement()
    {
        return harvestLevelStatement;
    }
    
    public void setHarvestLevelStatement(CodeBlock harvestLevelStatement)
    {
        this.harvestLevelStatement = harvestLevelStatement;
    }
    
    public CodeBlock getLightLevelStatement()
    {
        return lightLevelStatement;
    }
    
    public void setLightLevelStatement(float ligthLevel)
    {
        this.lightLevelStatement = CodeBlock.builder().addStatement("this.$N($LF)", "setLightLevel", ligthLevel).build();
    }
    
    public CodeBlock getLightOpacityStatement()
    {
        return lightOpacityStatement;
    }
    
    public void setLightOpacityStatement(CodeBlock lightOpacityStatement)
    {
        this.lightOpacityStatement = lightOpacityStatement;
    }
    
    public CodeBlock getUnbreakableStatement()
    {
        return unbreakableStatement;
    }
    
    public void setUnbreakableStatement(CodeBlock unbreakableStatement)
    {
        this.unbreakableStatement = unbreakableStatement;
    }
    
    public CodeBlock getTickRandomlyStatement()
    {
        return tickRandomlyStatement;
    }
    
    public void setTickRandomlyStatement(CodeBlock tickRandomlyStatement)
    {
        this.tickRandomlyStatement = tickRandomlyStatement;
    }
    
    public CodeBlock getMaxStackSizeStatement()
    {
        return maxStackSizeStatement;
    }
    
    public void setMaxStackSizeStatement(CodeBlock maxStackSizeStatement)
    {
        this.maxStackSizeStatement = maxStackSizeStatement;
    }
    
    public CodeBlock getMaxDamageStatement()
    {
        return maxDamageStatement;
    }
    
    public void setMaxDamageStatement(CodeBlock maxDamageStatement)
    {
        this.maxDamageStatement = maxDamageStatement;
    }
    
    public CodeBlock getNoRepairStatement()
    {
        return noRepairStatement;
    }
    
    public void setNoRepairStatement(CodeBlock noRepairStatement)
    {
        this.noRepairStatement = noRepairStatement;
    }
}
