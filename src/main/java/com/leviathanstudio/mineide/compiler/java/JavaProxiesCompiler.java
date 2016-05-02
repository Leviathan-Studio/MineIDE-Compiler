package com.leviathanstudio.mineide.compiler.java;

import java.io.File;
import java.io.IOException;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.MethodSpec.Builder;
import com.squareup.javapoet.TypeSpec;

public class JavaProxiesCompiler
{
    private static String proxiesPackage;
    
    public static String getProxiesPackage()
    {
        return proxiesPackage;
    }
    
    public static void setProxiesPackage(String proxiesPackage)
    {
        JavaProxiesCompiler.proxiesPackage = proxiesPackage;
    }
    
    private static JavaFile clientProxyJavaFile, commonProxyJavaFile, serverProxyJavaFile;
    private static ClassName clientProxyClass, commonProxyClass, serverProxyClass;
    private static Builder initMethod, preInitMethod;
    
    public static void generateProxiesClasses() throws IOException
    {
        clientProxyClass = ClassName.get(getProxiesPackage(), "ClientProxy");
        commonProxyClass = ClassName.get(getProxiesPackage(), "CommonProxy");
        serverProxyClass = ClassName.get(getProxiesPackage(), "ServerProxy");
        
        preInitMethod = MethodSpec.methodBuilder("preInit").addModifiers(Modifier.PUBLIC).addParameter(File.class, "configFile");
        initMethod = MethodSpec.methodBuilder("init").addModifiers(Modifier.PUBLIC);
        
        initCommonProxy();
        initClientProxy();
        initServerProxy();
    }
    
    private static void initClientProxy() throws IOException
    {
        TypeSpec clientProxyBuilder = TypeSpec.classBuilder(clientProxyClass).superclass(commonProxyClass).addModifiers(Modifier.PUBLIC).addMethod(preInitMethod.addAnnotation(Override.class).addStatement("super.preInit(configFile)").build()).addMethod(initMethod.addAnnotation(Override.class).addStatement("super.init()").build()).build();
        
        setClientProxyJavaFile(JavaFile.builder(clientProxyClass.packageName(), clientProxyBuilder).build());
    }
    
    private static void initCommonProxy() throws IOException
    {
        
        TypeSpec commonProxyBuilder = TypeSpec.classBuilder(commonProxyClass).addModifiers(Modifier.PUBLIC).addMethod(preInitMethod.build()).addMethod(initMethod.build()).build();
        
        setCommonProxyJavaFile(JavaFile.builder(commonProxyClass.packageName(), commonProxyBuilder).build());
    }
    
    private static void initServerProxy() throws IOException
    {
        TypeSpec serverProxyBuilder = TypeSpec.classBuilder(serverProxyClass).superclass(commonProxyClass).addModifiers(Modifier.PUBLIC).addMethod(preInitMethod.build()).addMethod(initMethod.build()).build();
        
        setServerProxyJavaFile(JavaFile.builder(serverProxyClass.packageName(), serverProxyBuilder).build());
    }
    
    public static JavaFile getClientProxyJavaFile()
    {
        return clientProxyJavaFile;
    }
    
    public static void setClientProxyJavaFile(JavaFile clientProxyJavaFile)
    {
        JavaProxiesCompiler.clientProxyJavaFile = clientProxyJavaFile;
    }
    
    public static JavaFile getCommonProxyJavaFile()
    {
        return commonProxyJavaFile;
    }
    
    public static void setCommonProxyJavaFile(JavaFile commonProxyJavaFile)
    {
        JavaProxiesCompiler.commonProxyJavaFile = commonProxyJavaFile;
    }
    
    public static JavaFile getServerProxyJavaFile()
    {
        return serverProxyJavaFile;
    }
    
    public static void setServerProxyJavaFile(JavaFile serverProxyJavaFile)
    {
        JavaProxiesCompiler.serverProxyJavaFile = serverProxyJavaFile;
    }
    
}