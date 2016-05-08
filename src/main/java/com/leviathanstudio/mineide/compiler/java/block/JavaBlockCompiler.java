package com.leviathanstudio.mineide.compiler.java.block;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;

import com.leviathanstudio.mineide.compiler.JavaCompilerInformation;
import com.leviathanstudio.mineide.utils.Utils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

public abstract class JavaBlockCompiler extends JavaCompilerInformation
{
    private String blockClassPackage, blockClassName;
    private JavaFile blockClassJavaFile;
    
    private ClassName blockClass, superClass;
    
    // private List<ParameterSpec> parametersList = new ArrayList<ParameterSpec>();
    // private MethodSpec.Builder parameters = MethodSpec.constructorBuilder();
    private List<CodeBlock> constructorSpecList = new ArrayList<CodeBlock>();
    private CodeBlock.Builder constructorSpec = CodeBlock.builder();
    
    private MethodSpec blockConstructor;
    private TypeSpec blockBuilder;
    
    public abstract void setConstructor();
    
    public abstract void setBlockInformation();
    
    public abstract void initializeBlockClass();
    
    public JavaBlockCompiler compile() throws IOException
    {
        // First Phase
        this.setBlockInformation();
        
        // Second Phase
        this.setBlockClass(ClassName.get(this.getBlockClassPackage(), this.getBlockClassName()));
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
        
        // Third Phase
        for(int i = 0; i < this.getConstructorSpecList().size(); i++)
        {
            this.constructorSpec.add(this.getConstructorSpecList().get(i));
        }
        this.setConstructor();
        this.setBlockBuilder(TypeSpec.classBuilder(this.getBlockClass()).superclass(this.getSuperClass()).addModifiers(Modifier.PUBLIC).addMethod(this.getBlockConstructor()).build());
        this.setBlockClassJavaFile(JavaFile.builder(this.getBlockClass().packageName(), getBlockBuilder()).build());
        
        this.getBlockClassJavaFile().writeTo(Utils.FORGE_SRC_JAVA_DIR);
        
        return this;
    }
    
    public String getBlockClassPackage()
    {
        return blockClassPackage;
    }
    
    public void setBlockClassPackage(String blockPackage)
    {
        this.blockClassPackage = blockPackage;
    }
    
    public String getBlockClassName()
    {
        return blockClassName;
    }
    
    public void setBlockClassName(String blockName)
    {
        this.blockClassName = blockName;
    }
    
    public JavaFile getBlockClassJavaFile()
    {
        return blockClassJavaFile;
    }
    
    protected void setBlockClassJavaFile(JavaFile blockClassJavaFile)
    {
        this.blockClassJavaFile = blockClassJavaFile;
    }
    
    public MethodSpec getBlockConstructor()
    {
        return blockConstructor;
    }
    
    public void setBlockConstructor(MethodSpec basicBlockConstructor)
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
    
    protected ClassName getBlockClass()
    {
        return blockClass;
    }
    
    protected void setBlockClass(ClassName blockClass)
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
    
    public CodeBlock.Builder getConstructorSpec()
    {
        return constructorSpec;
    }
    
    public void setConstructorSpec(CodeBlock.Builder constructorSpec)
    {
        this.constructorSpec = constructorSpec;
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