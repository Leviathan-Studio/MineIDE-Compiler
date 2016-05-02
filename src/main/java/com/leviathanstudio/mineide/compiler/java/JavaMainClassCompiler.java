package com.leviathanstudio.mineide.compiler.java;

import java.io.IOException;

import javax.lang.model.element.Modifier;

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
    
    public void compile() throws IOException
    {
        String commonPackageForge = "net.minecraftforge.fml.common";
        ClassName classMain = ClassName.get(getMainClassPackage(), getMainClassName());
        
        ClassName loggerClass = ClassName.get("org.apache.logging.log4j", "Logger");
        
        ClassName eventHandlerClass = ClassName.get(commonPackageForge + ".Mod", "EventHandler");
        ClassName preInitEventClass = ClassName.get(commonPackageForge + ".event", "FMLPreInitializationEvent");
        ClassName initEventClass = ClassName.get(commonPackageForge + ".event", "FMLInitializationEvent");
        ClassName postInitEventClass = ClassName.get(commonPackageForge + ".event", "FMLPostInitializationEvent");
        
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
        
        MethodSpec preInitMethod = MethodSpec.methodBuilder("preInit").addModifiers(Modifier.PUBLIC).addAnnotation(eventHandlerClass).addParameter(preInitEventClass, "event").addStatement("logger = event.getModLog()").addStatement("proxy.preInit(event.getSuggestedConfigurationFile())").build();
        MethodSpec initMethod = MethodSpec.methodBuilder("init").addModifiers(Modifier.PUBLIC).addAnnotation(eventHandlerClass).addParameter(initEventClass, "event").addStatement("proxy.init()").build();
        MethodSpec postInitMethod = MethodSpec.methodBuilder("postInit").addModifiers(Modifier.PUBLIC).addAnnotation(eventHandlerClass).addParameter(postInitEventClass, "event").build();
        
        TypeSpec mainClass = TypeSpec.classBuilder(classMain).addAnnotation(modAnnotation).addModifiers(Modifier.PUBLIC).addField(instanceField).addField(proxyField).addField(loggerField).addMethod(preInitMethod).addMethod(initMethod).addMethod(postInitMethod).build();
        
        this.setMainClassJavaFile(JavaFile.builder(classMain.packageName(), mainClass).build());
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
}