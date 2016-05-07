package com.leviathanstudio.mineide.compiler.java;

import java.io.IOException;

import javax.lang.model.element.Modifier;

import com.leviathanstudio.mineide.utils.Utils;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

public class JavaMainClassCompiler
{
    private String mainClassPackage, mainClassName;
    private JavaFile mainClassJavaFile;
    
    private MethodSpec preInitMethod, initMethod, postInitMethod;
    
    String commonPackageForge = "net.minecraftforge.fml.common";
    
    public ClassName eventHandlerClass = ClassName.get(commonPackageForge + ".Mod", "EventHandler");
    public ClassName preInitEventClass = ClassName.get(commonPackageForge + ".event", "FMLPreInitializationEvent");
    public ClassName initEventClass = ClassName.get(commonPackageForge + ".event", "FMLInitializationEvent");
    public ClassName postInitEventClass = ClassName.get(commonPackageForge + ".event", "FMLPostInitializationEvent");
    
    public void compile() throws IOException
    {
        ClassName classMain = ClassName.get(getMainClassPackage(), getMainClassName());
        
        ClassName loggerClass = ClassName.get("org.apache.logging.log4j", "Logger");
        
        ClassName clientProxyClass = ClassName.get(getMainClassPackage() + ".proxy", "ClientProxy");
        ClassName commonProxyClass = ClassName.get(getMainClassPackage() + ".proxy", "CommonProxy");
        ClassName serverProxyClass = ClassName.get(getMainClassPackage() + ".proxy", "ServerProxy");
        
        ClassName instanceClass = ClassName.get(commonPackageForge + ".Mod", "Instance");
        AnnotationSpec instanceAnnotation = AnnotationSpec.builder(instanceClass).addMember("value", "$S", "MODID_TEST").build();
        
        ClassName modClass = ClassName.get(commonPackageForge, "Mod");
        AnnotationSpec modAnnotation = AnnotationSpec.builder(modClass).addMember("modid", "$S", "MODID_TEST").addMember("name", "$S", "NAME_TEST").addMember("version", "$S", "VERSION_TEST").build();
        
        ClassName sidedProxyClass = ClassName.get(commonPackageForge, "SidedProxy");
        AnnotationSpec sidedProxyAnnotation = AnnotationSpec.builder(sidedProxyClass).addMember("clientSide", "$S", clientProxyClass).addMember("serverSide", "$S", serverProxyClass).build();
        
        FieldSpec instanceField = FieldSpec.builder(classMain, "instance", Modifier.PUBLIC, Modifier.STATIC).addAnnotation(instanceAnnotation).build();
        
        FieldSpec proxyField = FieldSpec.builder(commonProxyClass, "proxy", Modifier.PUBLIC, Modifier.STATIC).addAnnotation(sidedProxyAnnotation).build();
        
        FieldSpec loggerField = FieldSpec.builder(loggerClass, "logger", Modifier.PUBLIC, Modifier.STATIC).build();
        
        TypeSpec mainClass = TypeSpec.classBuilder(classMain).addAnnotation(modAnnotation).addModifiers(Modifier.PUBLIC).addField(instanceField).addField(proxyField).addField(loggerField).addMethod(this.getPreInitMethod()).addMethod(this.getInitMethod()).addMethod(this.getPostInitMethod()).build();
        
        this.setMainClassJavaFile(JavaFile.builder(classMain.packageName(), mainClass).build());
        this.getMainClassJavaFile().writeTo(Utils.FORGE_SRC_JAVA_DIR);
    }
    
    public JavaFile getMainClassJavaFile()
    {
        return this.mainClassJavaFile;
    }
    
    public void setMainClassJavaFile(JavaFile javaFile)
    {
        this.mainClassJavaFile = javaFile;
    }
    
    public String getMainClassPackage()
    {
        return this.mainClassPackage;
    }
    
    public void setMainClassPackage(String packageName)
    {
        this.mainClassPackage = packageName;
    }
    
    public String getMainClassName()
    {
        return this.mainClassName;
    }
    
    public void setMainClassName(String className)
    {
        this.mainClassName = className;
    }
    
    public MethodSpec getPreInitMethod()
    {
        return preInitMethod;
    }
    
    public void setPreInitMethod(MethodSpec preInitMethod)
    {
        this.preInitMethod = preInitMethod;
    }
    
    public MethodSpec getInitMethod()
    {
        return initMethod;
    }
    
    public void setInitMethod(MethodSpec initMethod)
    {
        this.initMethod = initMethod;
    }
    
    public MethodSpec getPostInitMethod()
    {
        return postInitMethod;
    }
    
    public void setPostInitMethod(MethodSpec postInitMethod)
    {
        this.postInitMethod = postInitMethod;
    }
}