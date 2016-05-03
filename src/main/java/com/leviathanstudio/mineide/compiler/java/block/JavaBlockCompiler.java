package com.leviathanstudio.mineide.compiler.java.block;

import javax.lang.model.element.Modifier;

import com.leviathanstudio.mineide.compiler.information.BlockInformation;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

public class JavaBlockCompiler extends BlockInformation
{
    private String blockPackage, blockName;
    private JavaFile blockClassJavaFile;
    
    ClassName minecraftBlockClass = ClassName.get("net.minecraft.block", "Block");
    ClassName meterialClass = ClassName.get("net.minecraft.block", "Material");
    private ClassName blockClass;
    
    private CodeBlock constructorSpec;
    private MethodSpec blockConstructor;
    private TypeSpec blockBuilder;
    
    /**
     * Needed parameter for simple block before <u>{@code addBasicBlock()}</u> <br>
     * <i>{@code setBlockPackage(String)} <br>
     * {@code setBlockName(String)}<br>
     * {@code setUnlocalizedName(String)}<br>
     * {@code setHardness(float)}<br>
     * {@code setResistance}<br>
     * {@code setStepSound((String) StepSound)}</i></b>
     */
    public void addBasicBlock()
    {
        this.setBlockClass(ClassName.get(this.getBlockPackage(), this.getBlockName()));
        this.setConstructorSpec(CodeBlock.builder()
                .addStatement("this.$N(\"$N\")", "setUnlocalizedName", this.getUnlocalizedName())
                .addStatement("this.$N($N)", "setCreativeTab", "CreativeTabs.tabBlock")
                .addStatement("this.$N($LF)", "setHardness", this.getHardness())
                .addStatement("this.$N($LF)", "setResistance", this.getResistance())
                .addStatement("this.$N($N)", "setStepSound", this.getStepSound())
                .build());
        this.setBasicBlockConstructor(MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC).addParameter(String.class, "unlocalizedName").addParameter(meterialClass, "material").addParameter(Float.class, "hardness").addParameter(Float.class, "resistance").addCode(constructorSpec).build());
        
        this.setBlockBuilder(TypeSpec.classBuilder(this.getBlockClass()).superclass(minecraftBlockClass).addModifiers(Modifier.PUBLIC).addMethod(blockConstructor).build());
        this.setBlockClassJavaFile(JavaFile.builder(this.getBlockClass().packageName(), getBlockBuilder()).build());
    }
    
    public String getBlockPackage()
    {
        return blockPackage;
    }
    
    public void setBlockPackage(String blockPackage)
    {
        this.blockPackage = blockPackage;
    }
    
    public String getBlockName()
    {
        return blockName;
    }
    
    public void setBlockName(String blockName)
    {
        this.blockName = blockName;
    }
    
    public JavaFile getBlockClassJavaFile()
    {
        return blockClassJavaFile;
    }
    
    public void setBlockClassJavaFile(JavaFile blockClassJavaFile)
    {
        this.blockClassJavaFile = blockClassJavaFile;
    }
    
    public CodeBlock getConstructorSpec()
    {
        return constructorSpec;
    }
    
    public void setConstructorSpec(CodeBlock constructorSpec)
    {
        this.constructorSpec = constructorSpec;
    }
    
    public MethodSpec getBasicBlockConstructor()
    {
        return blockConstructor;
    }
    
    public void setBasicBlockConstructor(MethodSpec basicBlockConstructor)
    {
        this.blockConstructor = basicBlockConstructor;
    }
    
    public TypeSpec getBlockBuilder()
    {
        return blockBuilder;
    }
    
    public void setBlockBuilder(TypeSpec blockBuilder)
    {
        this.blockBuilder = blockBuilder;
    }
    
    public ClassName getBlockClass()
    {
        return blockClass;
    }
    
    public void setBlockClass(ClassName blockClass)
    {
        this.blockClass = blockClass;
    }
}