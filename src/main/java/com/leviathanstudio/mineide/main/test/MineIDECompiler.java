package com.leviathanstudio.mineide.main.test;

import javax.lang.model.element.Modifier;

import com.leviathanstudio.mineide.compiler.java.JavaMainClassCompiler;
import com.leviathanstudio.mineide.compiler.java.JavaProxiesCompiler;
import com.leviathanstudio.mineide.compiler.java.block.JavaBlockCompiler;
import com.leviathanstudio.mineide.compiler.java.registry.JavaGameRegistry;
import com.leviathanstudio.mineide.compiler.json.JsonMCModInfoCompiler;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;

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
        
        ClassName blockSuperclass = ClassName.get("net.minecraft.block", "Block");
        ClassName materialClass = ClassName.get("net.minecraft.block", "Material");
        
        new JavaProxiesCompiler()
        {
            @Override
            public void setProxies()
            {
                this.setProxiesPackage(mainClassCompiler.getMainClassPackage() + ".proxy");
            }
        }.compile();
        
        new JsonMCModInfoCompiler().compile();
        
        new JavaBlockCompiler()
        {
            @Override
            public void setConstructor()
            {
                this.setBasicBlockConstructor(MethodSpec.constructorBuilder().addParameter(materialClass, "material").addModifiers(Modifier.PUBLIC).addCode(this.getConstructorSpec().build()).build());
            }
            
            @Override
            public void setBlockInformation()
            {
                this.setBlockClassPackage(mainClassCompiler.getMainClassPackage() + ".block");
                this.setBlockClassName("BlockTesting1");
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
                
                this.setSuperClass(blockSuperclass);
                
                this.getConstructorSpecList().add(CodeBlock.builder().addStatement("super($L)", materialClass.simpleName().toLowerCase()).build());
                this.getConstructorSpecList().add(this.getUnlocalizedNameStatement());
                this.getConstructorSpecList().add(this.getCreativeTabStatement());
                this.getConstructorSpecList().add(this.getHardnessStatement());
                this.getConstructorSpecList().add(this.getResistanceStatement());
                this.getConstructorSpecList().add(this.getStepSoundStatement());
            }
        }.compile();
        
        new JavaBlockCompiler()
        {
            @Override
            public void setConstructor()
            {
                this.setBasicBlockConstructor(MethodSpec.constructorBuilder().addParameter(materialClass, "material").addModifiers(Modifier.PUBLIC).addCode(this.getConstructorSpec().build()).build());
            }
            
            @Override
            public void setBlockInformation()
            {
                this.setBlockClassPackage(mainClassCompiler.getMainClassPackage() + ".block");
                this.setBlockClassName("BlockTesting2");
                
                this.setUnlocalizedName("thisIsATest_block_number_two");
                this.setCreativeTab("CreativeTab.tabMisc");
                this.setLightLevel(1.0F);
            }
            
            @Override
            public void initializeBlockClass()
            {
                this.setUnlocalizedNameStatement(CodeBlock.builder().addStatement("this.$N(\"$L\")", "setUnlocalizedName", this.getUnlocalizedName()).build());
                this.setCreativeTabStatement(CodeBlock.builder().addStatement("this.$N($L)", "setCreativeTab", this.getCreativeTab()).build());
                this.setLightLevelStatement(CodeBlock.builder().addStatement("this.$N($LF)", "setLightLevel", this.getLightLevel()).build());
                this.setSuperClass(blockSuperclass);
                
                this.getConstructorSpecList().add(CodeBlock.builder().addStatement("super($L)", materialClass.simpleName().toLowerCase()).build());
                this.getConstructorSpecList().add(this.getUnlocalizedNameStatement());
                this.getConstructorSpecList().add(this.getCreativeTabStatement());
                this.getConstructorSpecList().add(this.getLightLevelStatement());
            }
        }.compile();
        
        JavaGameRegistry gameRegistry = new JavaGameRegistry()
        {
            @Override
            public void initializeGameRegistry()
            {
                this.getGameRegistryList().add(CodeBlock.builder().addStatement("GameRegistry.registerBlock(" + "\"$N\"" + "," + " $L" + ")", "BlockTest", "BlockTest").build());
                this.getGameRegistryList().add(CodeBlock.builder().addStatement("GameRegistry.registerItem(" + "\"$N\"" + "," + " $L" + ")", "ItemTest", "ItemTest").build());
            }
        }.compile();
        
        mainClassCompiler.setPreInitMethod(MethodSpec.methodBuilder("preInit").addModifiers(Modifier.PUBLIC).addAnnotation(mainClassCompiler.eventHandlerClass).addParameter(mainClassCompiler.preInitEventClass, "event").addStatement("logger = event.getModLog()").addStatement("proxy.preInit(event.getSuggestedConfigurationFile())").addCode(gameRegistry.getGameRegistry().build()).build());
        mainClassCompiler.setInitMethod(MethodSpec.methodBuilder("init").addModifiers(Modifier.PUBLIC).addAnnotation(mainClassCompiler.eventHandlerClass).addParameter(mainClassCompiler.initEventClass, "event").addStatement("proxy.init()").build());
        mainClassCompiler.setPostInitMethod(MethodSpec.methodBuilder("postInit").addModifiers(Modifier.PUBLIC).addAnnotation(mainClassCompiler.eventHandlerClass).addParameter(mainClassCompiler.postInitEventClass, "event").build());
        
        mainClassCompiler.compile();
    }
}
