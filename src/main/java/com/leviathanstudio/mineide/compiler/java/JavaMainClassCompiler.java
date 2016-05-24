package com.leviathanstudio.mineide.compiler.java;

import java.io.IOException;

import javax.lang.model.element.Modifier;

import com.leviathanstudio.mineide.util.Util;
import com.squareup.javapoet.*;

public class JavaMainClassCompiler
{
    private String mainClassPackage, mainClassName;
    private JavaFile mainClassJavaFile;
    
    private MethodSpec preInitMethod, initMethod, postInitMethod;
    
    String commonPackageForge = "net.minecraftforge.fml.common";
    
    public ClassName eventHandlerClass = ClassName.get(this.commonPackageForge + ".Mod", "EventHandler");
    public ClassName preInitEventClass = ClassName.get(this.commonPackageForge + ".event", "FMLPreInitializationEvent");
    public ClassName initEventClass = ClassName.get(this.commonPackageForge + ".event", "FMLInitializationEvent");
    public ClassName postInitEventClass = ClassName.get(this.commonPackageForge + ".event", "FMLPostInitializationEvent");
    
    public void compile() throws IOException
    {
        ClassName classMain = ClassName.get(this.getMainClassPackage(), this.getMainClassName());
        
        ClassName loggerClass = ClassName.get("org.apache.logging.log4j", "Logger");
        
        ClassName clientProxyClass = ClassName.get(this.getMainClassPackage() + ".proxy", "ClientProxy");
        ClassName commonProxyClass = ClassName.get(this.getMainClassPackage() + ".proxy", "CommonProxy");
        ClassName serverProxyClass = ClassName.get(this.getMainClassPackage() + ".proxy", "ServerProxy");
        
        ClassName instanceClass = ClassName.get(this.commonPackageForge + ".Mod", "Instance");
        AnnotationSpec instanceAnnotation = AnnotationSpec.builder(instanceClass).addMember("value", "$S", "MODID_TEST").build();
        
        ClassName modClass = ClassName.get(this.commonPackageForge, "Mod");
        AnnotationSpec modAnnotation = AnnotationSpec.builder(modClass).addMember("modid", "$S", "MODID_TEST").addMember("name", "$S", "NAME_TEST").addMember("version", "$S", "VERSION_TEST").build();
        
        ClassName sidedProxyClass = ClassName.get(this.commonPackageForge, "SidedProxy");
        AnnotationSpec sidedProxyAnnotation = AnnotationSpec.builder(sidedProxyClass).addMember("clientSide", "$S", clientProxyClass).addMember("serverSide", "$S", serverProxyClass).build();
        
        FieldSpec instanceField = FieldSpec.builder(classMain, "instance", Modifier.PUBLIC, Modifier.STATIC).addAnnotation(instanceAnnotation).build();
        
        FieldSpec proxyField = FieldSpec.builder(commonProxyClass, "proxy", Modifier.PUBLIC, Modifier.STATIC).addAnnotation(sidedProxyAnnotation).build();
        
        FieldSpec loggerField = FieldSpec.builder(loggerClass, "logger", Modifier.PUBLIC, Modifier.STATIC).build();
        
        TypeSpec mainClass = TypeSpec.classBuilder(classMain).addAnnotation(modAnnotation).addModifiers(Modifier.PUBLIC).addField(instanceField).addField(proxyField).addField(loggerField).addMethod(this.getPreInitMethod()).addMethod(this.getInitMethod()).addMethod(this.getPostInitMethod()).build();
        
        this.setMainClassJavaFile(JavaFile.builder(classMain.packageName(), mainClass).build());
        this.getMainClassJavaFile().writeTo(Util.FORGE_SRC_JAVA_DIR);
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
        return this.preInitMethod;
    }
    
    public void setPreInitMethod(MethodSpec preInitMethod)
    {
        this.preInitMethod = preInitMethod;
    }
    
    public MethodSpec getInitMethod()
    {
        return this.initMethod;
    }
    
    public void setInitMethod(MethodSpec initMethod)
    {
        this.initMethod = initMethod;
    }
    
    public MethodSpec getPostInitMethod()
    {
        return this.postInitMethod;
    }
    
    public void setPostInitMethod(MethodSpec postInitMethod)
    {
        this.postInitMethod = postInitMethod;
    }
}