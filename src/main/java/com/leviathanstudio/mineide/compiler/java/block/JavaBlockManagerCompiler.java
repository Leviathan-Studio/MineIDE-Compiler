package com.leviathanstudio.mineide.compiler.java.block;

import java.io.IOException;

import javax.lang.model.element.Modifier;

import com.leviathanstudio.mineide.utils.Utils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.MethodSpec.Builder;
import com.squareup.javapoet.TypeSpec;

public abstract class JavaBlockManagerCompiler
{
    private ClassName blocksManagerClass;
    private JavaFile  blockManagerJavaFile;

    public Builder    initBlocksMethod, registerBlocksMethod, renderBlocksMethod;

    ClassName         blockSuperclass = ClassName.get("net.minecraft.block", "Block");

    public abstract void initializeBlocks();

    public abstract void registerBlocks();

    public abstract void setRenderBlocks();

    public abstract void setClassCompiler();

    public JavaBlockManagerCompiler compile() throws IOException
    {
        this.setClassCompiler();

        Builder initMethod = MethodSpec.methodBuilder("init").addModifiers(Modifier.PUBLIC, Modifier.STATIC);

        this.initBlocksMethod = MethodSpec.methodBuilder("initBlock").addModifiers(Modifier.PRIVATE, Modifier.STATIC);
        this.initializeBlocks();

        this.registerBlocksMethod = MethodSpec.methodBuilder("registerBlock").addModifiers(Modifier.PRIVATE,
                Modifier.STATIC);
        this.registerBlocks();

        this.renderBlocksMethod = MethodSpec.methodBuilder("renderBlock").addModifiers(Modifier.PRIVATE,
                Modifier.STATIC);
        this.setRenderBlocks();

        TypeSpec blocksManagerBuilder = TypeSpec.classBuilder(this.getBlocksManagerClass().simpleName())
                .addField(this.blockSuperclass, "blockTest", Modifier.PUBLIC, Modifier.STATIC)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(initMethod.addStatement("$N()", "initBlock").addStatement("$N()", "registerBlock")
                        .addStatement("$N()", "renderBlock").build())
                .addMethod(this.initBlocksMethod.build()).addMethod(this.registerBlocksMethod.build())
                .addMethod(this.renderBlocksMethod.build()).build();

        this.setBlockManagerJavaFile(
                JavaFile.builder(this.getBlocksManagerClass().packageName(), blocksManagerBuilder).build());

        this.getBlockManagerJavaFile().writeTo(Utils.FORGE_SRC_JAVA_DIR);

        return this;
    }

    public JavaFile getBlockManagerJavaFile()
    {
        return this.blockManagerJavaFile;
    }

    public void setBlockManagerJavaFile(JavaFile blockManagerJavaFile)
    {
        this.blockManagerJavaFile = blockManagerJavaFile;
    }

    public ClassName getBlocksManagerClass()
    {
        return this.blocksManagerClass;
    }

    public void setBlocksManagerClass(ClassName blocksManagerClass)
    {
        this.blocksManagerClass = blocksManagerClass;
    }
}
