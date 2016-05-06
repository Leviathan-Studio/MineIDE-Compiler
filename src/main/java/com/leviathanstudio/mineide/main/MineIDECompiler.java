package com.leviathanstudio.mineide.main;

import javax.lang.model.element.Modifier;

import com.leviathanstudio.mineide.compiler.java.JavaMainClassCompiler;
import com.leviathanstudio.mineide.compiler.java.JavaProxiesCompiler;
import com.leviathanstudio.mineide.compiler.java.block.JavaBlockCompiler;
import com.leviathanstudio.mineide.compiler.json.JsonMCModInfoCompiler;
import com.leviathanstudio.mineide.utils.Utils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

public class MineIDECompiler
{
    /**
     * Debug Main Class for testing compiler
     */
    public static void main(String[] args) throws Exception
    {
        JavaMainClassCompiler mainClassCompiler = new JavaMainClassCompiler();
        
        mainClassCompiler.setMainClassPackage("fr.zeamateis.test");
        mainClassCompiler.setMainClassName("testMainClass");
        mainClassCompiler.compile();
        mainClassCompiler.getMainClassJavaFile().writeTo(Utils.FORGE_SRC_JAVA_DIR);
        
        JavaProxiesCompiler proxiesCompiler = new JavaProxiesCompiler();
        proxiesCompiler.setProxiesPackage(mainClassCompiler.getMainClassPackage() + ".proxy");
        proxiesCompiler.compile();
        proxiesCompiler.getClientProxyJavaFile().writeTo(Utils.FORGE_SRC_JAVA_DIR);
        proxiesCompiler.getCommonProxyJavaFile().writeTo(Utils.FORGE_SRC_JAVA_DIR);
        proxiesCompiler.getServerProxyJavaFile().writeTo(Utils.FORGE_SRC_JAVA_DIR);
        
        JsonMCModInfoCompiler mcModInfoCompiler = new JsonMCModInfoCompiler();
        mcModInfoCompiler.compile();
        
        JavaBlockCompiler testBlock1Compiler = new JavaBlockCompiler()
        {
            @Override
            public void setBlockInformation()
            {
                this.setBlockPackage(mainClassCompiler.getMainClassPackage() + ".block");
                this.setBlockName("BlockTesting1");
                this.setUnlocalizedName("thisIsATest_block");
                this.setCreativeTab("CreativeTab.tabBlock");
                this.setHardness(1.0F);
                this.setResistance(11.0F);
                this.setStepSound("StepSound.soundStone");
            }
            
            @Override
            public void initializeBlockClass()
            {
                this.setUnlocalizedNameStatement(CodeBlock.builder().addStatement("this.$N(\"$L\")", "setUnlocalizedName", this.getUnlocalizedName()).build());
                this.setCreativeTabStatement(CodeBlock.builder().addStatement("this.$N($L)", "setCreativeTab", this.getCreativeTab()).build());
                this.setHardnessStatement(CodeBlock.builder().addStatement("this.$N($LF)", "setHardness", this.getHardness()).build());
                this.setResistanceStatement(CodeBlock.builder().addStatement("this.$N($LF)", "setResistance", this.getResistance()).build());
                this.setStepSoundStatement(CodeBlock.builder().addStatement("this.$N($L)", "setStepSound", this.getStepSound()).build());
                
                this.setBlockClass(ClassName.get(this.getBlockPackage(), this.getBlockName()));
                this.setSuperClass(this.blockSuperclass);
            }
            
            @Override
            public void initCompiler()
            {
                
                this.getConstructorSpecList().add(CodeBlock.builder().addStatement("super($L)", materialClass.simpleName().toLowerCase()).build());
                this.getConstructorSpecList().add(this.getUnlocalizedNameStatement());
                this.getConstructorSpecList().add(this.getCreativeTabStatement());
                this.getConstructorSpecList().add(this.getHardnessStatement());
                this.getConstructorSpecList().add(this.getResistanceStatement());
                this.getConstructorSpecList().add(this.getStepSoundStatement());
                
                for(int i = 0; i < this.getConstructorSpecList().size(); i++)
                {
                    this.constructorSpec.add(this.getConstructorSpecList().get(i));
                }
                
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
                
                this.setBasicBlockConstructor(MethodSpec.constructorBuilder().addParameter(materialClass, "material").addModifiers(Modifier.PUBLIC).addCode(this.constructorSpec.build()).build());
                
                this.setBlockBuilder(TypeSpec.classBuilder(this.getBlockClass()).superclass(this.getSuperClass()).addModifiers(Modifier.PUBLIC).addMethod(this.getBasicBlockConstructor()).build());
                this.setBlockClassJavaFile(JavaFile.builder(this.getBlockClass().packageName(), getBlockBuilder()).build());
            }
        };
        testBlock1Compiler.compile();
        testBlock1Compiler.getBlockClassJavaFile().writeTo(System.out);
        
        JavaBlockCompiler testBlock2Compiler = new JavaBlockCompiler()
        {
            @Override
            public void setBlockInformation()
            {
                this.setBlockPackage(mainClassCompiler.getMainClassPackage() + ".block");
                this.setBlockName("BlockTesting2");
                this.setUnlocalizedName("thisIsATest_block_number_two");
                this.setCreativeTab("CreativeTab.tabMisc");
                this.setHardness(1.0F);
                this.setResistance(11.0F);
                this.setStepSound("StepSound.soundWood");
            }
            
            @Override
            public void initializeBlockClass()
            {
                this.setUnlocalizedNameStatement(CodeBlock.builder().addStatement("this.$N(\"$L\")", "setUnlocalizedName", this.getUnlocalizedName()).build());
                this.setCreativeTabStatement(CodeBlock.builder().addStatement("this.$N($L)", "setCreativeTab", this.getCreativeTab()).build());
                this.setHardnessStatement(CodeBlock.builder().addStatement("this.$N($LF)", "setHardness", this.getHardness()).build());
                this.setResistanceStatement(CodeBlock.builder().addStatement("this.$N($LF)", "setResistance", this.getResistance()).build());
                this.setStepSoundStatement(CodeBlock.builder().addStatement("this.$N($L)", "setStepSound", this.getStepSound()).build());
                
                this.setBlockClass(ClassName.get(this.getBlockPackage(), this.getBlockName()));
                this.setSuperClass(this.blockSuperclass);
            }
            
            @Override
            public void initCompiler()
            {
                
                this.getConstructorSpecList().add(CodeBlock.builder().addStatement("super($L)", materialClass.simpleName().toLowerCase()).build());
                this.getConstructorSpecList().add(this.getUnlocalizedNameStatement());
                this.getConstructorSpecList().add(this.getCreativeTabStatement());
                this.getConstructorSpecList().add(this.getHardnessStatement());
                this.getConstructorSpecList().add(this.getResistanceStatement());
                this.getConstructorSpecList().add(this.getStepSoundStatement());
                
                for(int i = 0; i < this.getConstructorSpecList().size(); i++)
                {
                    this.constructorSpec.add(this.getConstructorSpecList().get(i));
                }
                
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
                
                this.setBasicBlockConstructor(MethodSpec.constructorBuilder().addParameter(materialClass, "material").addModifiers(Modifier.PUBLIC).addCode(this.constructorSpec.build()).build());
                
                this.setBlockBuilder(TypeSpec.classBuilder(this.getBlockClass()).superclass(this.getSuperClass()).addModifiers(Modifier.PUBLIC).addMethod(this.getBasicBlockConstructor()).build());
                this.setBlockClassJavaFile(JavaFile.builder(this.getBlockClass().packageName(), getBlockBuilder()).build());
            }
        };
        testBlock2Compiler.compile();
        testBlock2Compiler.getBlockClassJavaFile().writeTo(System.out);
    }
}
