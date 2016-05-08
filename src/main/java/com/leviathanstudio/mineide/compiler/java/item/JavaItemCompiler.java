package com.leviathanstudio.mineide.compiler.java.item;

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

public abstract class JavaItemCompiler extends JavaCompilerInformation
{
    private String itemClassPackage, itemClassName;
    private JavaFile itemClassJavaFile;
    
    private ClassName itemClass, superClass;
    
    // private List<ParameterSpec> parametersList = new ArrayList<ParameterSpec>();
    // private MethodSpec.Builder parameters = MethodSpec.constructorBuilder();
    private List<CodeBlock> constructorSpecList = new ArrayList<CodeBlock>();
    private CodeBlock.Builder constructorSpec = CodeBlock.builder();
    
    private MethodSpec itemConstructor;
    private TypeSpec itemBuilder;
    
    public abstract void setConstructor();
    
    public abstract void setItemInformation();
    
    public abstract void initializeItemClass();
    
    public JavaItemCompiler compile() throws IOException
    {
        // First Phase
        this.setItemInformation();
        
        // Second Phase
        this.setItemClass(ClassName.get(this.getItemClassPackage(), this.getItemClassName()));
        this.initializeItemClass();
        
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
        this.setItemBuilder(TypeSpec.classBuilder(this.getItemClass()).superclass(this.getSuperClass()).addModifiers(Modifier.PUBLIC).addMethod(this.getItemConstructor()).build());
        this.setItemClassJavaFile(JavaFile.builder(this.getItemClass().packageName(), getItemBuilder()).build());
        
        this.getItemClassJavaFile().writeTo(Utils.FORGE_SRC_JAVA_DIR);
        
        return this;
    }
    
    public String getItemClassPackage()
    {
        return itemClassPackage;
    }
    
    public void setItemClassPackage(String blockPackage)
    {
        this.itemClassPackage = blockPackage;
    }
    
    public String getItemClassName()
    {
        return itemClassName;
    }
    
    public void setItemClassName(String blockName)
    {
        this.itemClassName = blockName;
    }
    
    public JavaFile getItemClassJavaFile()
    {
        return itemClassJavaFile;
    }
    
    protected void setItemClassJavaFile(JavaFile blockClassJavaFile)
    {
        this.itemClassJavaFile = blockClassJavaFile;
    }
    
    public MethodSpec getItemConstructor()
    {
        return itemConstructor;
    }
    
    public void setItemConstructor(MethodSpec basicBlockConstructor)
    {
        this.itemConstructor = basicBlockConstructor;
    }
    
    public TypeSpec getItemBuilder()
    {
        return itemBuilder;
    }
    
    public void setItemBuilder(TypeSpec blockBuilder)
    {
        this.itemBuilder = blockBuilder;
    }
    
    protected ClassName getItemClass()
    {
        return itemClass;
    }
    
    protected void setItemClass(ClassName blockClass)
    {
        this.itemClass = blockClass;
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