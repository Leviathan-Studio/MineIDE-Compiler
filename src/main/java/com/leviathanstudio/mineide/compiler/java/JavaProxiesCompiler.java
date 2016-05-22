package com.leviathanstudio.mineide.compiler.java;

import java.io.File;
import java.io.IOException;

import javax.lang.model.element.Modifier;

import com.leviathanstudio.mineide.utils.Utils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.MethodSpec.Builder;
import com.squareup.javapoet.TypeSpec;

public abstract class JavaProxiesCompiler
{
    private String proxiesPackage;

    public String getProxiesPackage()
    {
        return this.proxiesPackage;
    }

    public void setProxiesPackage(String packageName)
    {
        this.proxiesPackage = packageName;
    }

    private JavaFile  clientProxyJavaFile, commonProxyJavaFile, serverProxyJavaFile;
    private ClassName clientProxyClass, commonProxyClass, serverProxyClass;
    private Builder   initMethod, preInitMethod;

    public abstract void setProxies();

    public JavaProxiesCompiler compile() throws IOException
    {
        this.setProxies();
        this.clientProxyClass = ClassName.get(this.getProxiesPackage(), "ClientProxy");
        this.commonProxyClass = ClassName.get(this.getProxiesPackage(), "CommonProxy");
        this.serverProxyClass = ClassName.get(this.getProxiesPackage(), "ServerProxy");

        this.preInitMethod = MethodSpec.methodBuilder("preInit").addModifiers(Modifier.PUBLIC).addParameter(File.class,
                "configFile");
        this.initMethod = MethodSpec.methodBuilder("init").addModifiers(Modifier.PUBLIC);

        this.initCommonProxy();
        this.initClientProxy();
        this.initServerProxy();

        this.getClientProxyJavaFile().writeTo(Utils.FORGE_SRC_JAVA_DIR);
        this.getCommonProxyJavaFile().writeTo(Utils.FORGE_SRC_JAVA_DIR);
        this.getServerProxyJavaFile().writeTo(Utils.FORGE_SRC_JAVA_DIR);
        return this;
    }

    private void initClientProxy() throws IOException
    {
        TypeSpec clientProxyBuilder = TypeSpec.classBuilder(this.clientProxyClass).superclass(this.commonProxyClass)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(this.preInitMethod.addAnnotation(Override.class).addStatement("super.preInit(configFile)")
                        .build())
                .addMethod(this.initMethod.addAnnotation(Override.class).addStatement("super.init()").build()).build();

        this.setClientProxyJavaFile(JavaFile.builder(this.clientProxyClass.packageName(), clientProxyBuilder).build());
    }

    private void initCommonProxy() throws IOException
    {

        TypeSpec commonProxyBuilder = TypeSpec.classBuilder(this.commonProxyClass).addModifiers(Modifier.PUBLIC)
                .addMethod(this.preInitMethod.build()).addMethod(this.initMethod.build()).build();

        this.setCommonProxyJavaFile(JavaFile.builder(this.commonProxyClass.packageName(), commonProxyBuilder).build());
    }

    private void initServerProxy() throws IOException
    {
        TypeSpec serverProxyBuilder = TypeSpec.classBuilder(this.serverProxyClass).superclass(this.commonProxyClass)
                .addModifiers(Modifier.PUBLIC).addMethod(this.preInitMethod.build()).addMethod(this.initMethod.build())
                .build();

        this.setServerProxyJavaFile(JavaFile.builder(this.serverProxyClass.packageName(), serverProxyBuilder).build());
    }

    public JavaFile getClientProxyJavaFile()
    {
        return this.clientProxyJavaFile;
    }

    public void setClientProxyJavaFile(JavaFile javaFile)
    {
        this.clientProxyJavaFile = javaFile;
    }

    public JavaFile getCommonProxyJavaFile()
    {
        return this.commonProxyJavaFile;
    }

    public void setCommonProxyJavaFile(JavaFile javaFile)
    {
        this.commonProxyJavaFile = javaFile;
    }

    public JavaFile getServerProxyJavaFile()
    {
        return this.serverProxyJavaFile;
    }

    public void setServerProxyJavaFile(JavaFile javaFile)
    {
        this.serverProxyJavaFile = javaFile;
    }

}