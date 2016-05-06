package com.leviathanstudio.mineide.compiler.java.block;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;

import com.leviathanstudio.mineide.compiler.information.BlockInformation;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

public abstract class JavaBlockCompiler extends BlockInformation
{
    private String blockPackage, blockName;
    private JavaFile blockClassJavaFile;
    
    // private List<ParameterSpec> parametersList = new ArrayList<ParameterSpec>();
    // private MethodSpec.Builder parameters = MethodSpec.constructorBuilder();
    
    private ClassName blockClass, superClass;
    
    private List<CodeBlock> constructorSpecList = new ArrayList<CodeBlock>();
    public CodeBlock.Builder constructorSpec = CodeBlock.builder();
    
    private MethodSpec blockConstructor;
    private TypeSpec blockBuilder;
    
    private CodeBlock unlocalizedNameStatement, creativeTabStatement, hardnessStatement, resistanceStatement, stepSoundStatement;
    
    public ClassName blockSuperclass = ClassName.get("net.minecraft.block", "Block");
    public ClassName materialClass = ClassName.get("net.minecraft.block", "Material");
    
    public abstract void setBlockInformation();
    
    public abstract void initializeBlockClass();
    
    public void compile()
    {
        this.setBlockInformation();
        this.initializeBlockClass();
        
        // TODO Exemple: If needed for constructor declaration
        // ParameterSpec unlocalizedName = ParameterSpec.builder(String.class, "unlocalizedName").build();
        //
        // ParameterSpec material = ParameterSpec.builder(materialClass, "material").build();
        // ParameterSpec hardness = ParameterSpec.builder(Float.class, "hardness").build();
        // ParameterSpec resistance = ParameterSpec.builder(Float.class, "resistance").build();
        //
        // parametersList.add(unlocalizedName);
        // parametersList.add(material);
        // parametersList.add(hardness);
        // parametersList.add(resistance);
        //
        // for(int i = 0; i < parametersList.size(); i++)
        // {
        // this.parameters.addParameter(parametersList.get(i).type, parametersList.get(i).name);
        // }
        
        for(int i = 0; i < this.getConstructorSpecList().size(); i++)
        {
            this.constructorSpec.add(this.getConstructorSpecList().get(i));
        }
        
        this.setBasicBlockConstructor(MethodSpec.constructorBuilder().addParameter(materialClass, "material").addModifiers(Modifier.PUBLIC).addCode(this.constructorSpec.build()).build());
        this.setBlockBuilder(TypeSpec.classBuilder(this.getBlockClass()).superclass(this.getSuperClass()).addModifiers(Modifier.PUBLIC).addMethod(this.getBasicBlockConstructor()).build());
        this.setBlockClassJavaFile(JavaFile.builder(this.getBlockClass().packageName(), getBlockBuilder()).build());
    }
    
    public CodeBlock getUnlocalizedNameStatement()
    {
        return unlocalizedNameStatement;
    }
    
    public void setUnlocalizedNameStatement(CodeBlock unlocalizedNameStatement)
    {
        this.unlocalizedNameStatement = unlocalizedNameStatement;
    }
    
    public CodeBlock getCreativeTabStatement()
    {
        return creativeTabStatement;
    }
    
    public void setCreativeTabStatement(CodeBlock creativeTabStatement)
    {
        this.creativeTabStatement = creativeTabStatement;
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
    
    public ClassName getSuperClass()
    {
        return superClass;
    }
    
    public void setSuperClass(ClassName superClass)
    {
        this.superClass = superClass;
    }
    
    public List<CodeBlock> getConstructorSpecList()
    {
        return constructorSpecList;
    }
    
    public void setConstructorSpecList(List<CodeBlock> constructorSpecList)
    {
        this.constructorSpecList = constructorSpecList;
    }
}