package com.leviathanstudio.mineide.main;

import com.leviathanstudio.mineide.compiler.java.JavaMainClassCompiler;
import com.leviathanstudio.mineide.compiler.java.JavaProxiesCompiler;
import com.leviathanstudio.mineide.compiler.java.block.JavaBlockCompiler;
import com.leviathanstudio.mineide.compiler.json.JsonMCModInfoCompiler;
import com.leviathanstudio.mineide.utils.Utils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;

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
                
                this.getConstructorSpecList().add(CodeBlock.builder().addStatement("super($L)", materialClass.simpleName().toLowerCase()).build());
                this.getConstructorSpecList().add(this.getUnlocalizedNameStatement());
                this.getConstructorSpecList().add(this.getCreativeTabStatement());
                this.getConstructorSpecList().add(this.getHardnessStatement());
                this.getConstructorSpecList().add(this.getResistanceStatement());
                this.getConstructorSpecList().add(this.getStepSoundStatement());
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
                
                this.getConstructorSpecList().add(CodeBlock.builder().addStatement("super($L)", materialClass.simpleName().toLowerCase()).build());
                this.getConstructorSpecList().add(this.getUnlocalizedNameStatement());
                this.getConstructorSpecList().add(this.getCreativeTabStatement());
                this.getConstructorSpecList().add(this.getHardnessStatement());
                this.getConstructorSpecList().add(this.getResistanceStatement());
                this.getConstructorSpecList().add(this.getStepSoundStatement());
            }
        };
        testBlock2Compiler.compile();
        testBlock2Compiler.getBlockClassJavaFile().writeTo(System.out);
    }
}
