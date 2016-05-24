package com.leviathanstudio.mineide.main.test;

import javax.lang.model.element.Modifier;

import com.leviathanstudio.mineide.compiler.java.JavaMainClassCompiler;
import com.leviathanstudio.mineide.compiler.java.JavaProxiesCompiler;
import com.leviathanstudio.mineide.compiler.java.block.JavaBlockCompiler;
import com.leviathanstudio.mineide.compiler.java.block.JavaBlockManagerCompiler;
import com.leviathanstudio.mineide.compiler.java.registry.JavaGameRegistry;
import com.leviathanstudio.mineide.compiler.json.JsonMCModInfoCompiler;
import com.leviathanstudio.mineide.util.Util;
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
        Util.checkDir(Util.FORGE_SRC_RESOURCE_DIR);
        JavaMainClassCompiler mainClassCompiler = new JavaMainClassCompiler();
        
        mainClassCompiler.setMainClassPackage("fr.zeamateis.test");
        mainClassCompiler.setMainClassName("testMainClass");
        
        ClassName blockSuperclass = ClassName.get("net.minecraft.block", "Block");
        ClassName itemSuperclass = ClassName.get("net.minecraft.item", "Item");
        
        ClassName materialClass = ClassName.get("net.minecraft.block.material", "Material");
        ClassName creativeTabsClass = ClassName.get("net.minecraft.creativetab", "CreativeTabs");
        
        new JavaProxiesCompiler()
        {
            @Override
            public void setProxies()
            {
                this.setProxiesPackage(mainClassCompiler.getMainClassPackage() + ".proxy");
            }
        }.compile();
        
        new JsonMCModInfoCompiler().compile();
        
        JavaBlockCompiler blockTest2 = new JavaBlockCompiler()
        {
            @Override
            public void setConstructor()
            {
                this.setBlockConstructor(MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC).addCode(this.getConstructorSpec().build()).build());
            }
            
            @Override
            public void setBlockInformation()
            {
                this.setBlockClassPackage(mainClassCompiler.getMainClassPackage() + ".block");
                this.setBlockClassName("BlockTesting2");
                this.setCreativeTab("MISC");
                this.setUnlocalizedName("thisIsATest_block_number_two");
                this.setLightLevel(1.0F);
            }
            
            @Override
            public void initializeBlockClass()
            {
                this.setUnlocalizedNameStatement(this.getUnlocalizedName());
                this.setCreativeTabStatement(creativeTabsClass, this.getCreativeTab());
                this.setLightLevelStatement(0.5F);
                this.setSuperClass(blockSuperclass);
                this.getConstructorSpecList().add(CodeBlock.builder().addStatement("super($T.$N)", materialClass, "WOOD").build());
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
                this.getGameRegistryList().add(CodeBlock.builder().addStatement("$T.registerBlock(" + "$N" + ", " + "\"$L\"" + ")", this.gameRegistryClass, "blockTest", "BlockTest").build());
                // this.getGameRegistryList().add(CodeBlock.builder().addStatement("$T.registerItem("
                // + "$N" + ", " + "\"$L\"" + ")", this.gameRegistryClass,
                // "itemTest", "ItemTest").build());
            }
        }.compile();
        
        JavaBlockManagerCompiler blocksManagerCompiler = new JavaBlockManagerCompiler()
        {
            
            @Override
            public void setClassCompiler()
            {
                this.setBlocksManagerClass(ClassName.get(mainClassCompiler.getMainClassPackage() + ".block", "BlocksManager"));
            }
            
            @Override
            public void initializeBlocks()
            {
                this.initBlocksMethod.addCode(CodeBlock.builder().addStatement("$N = new $L()", "blockTest", blockTest2.getBlockClassName()).build());
            }
            
            @Override
            public void setRenderBlocks()
            {
                
            }
            
            @Override
            public void registerBlocks()
            {
                this.registerBlocksMethod.addCode(gameRegistry.getGameRegistry().build());
            }
            
        }.compile();
        
        // JavaItemCompiler itemTest = new JavaItemCompiler()
        // {
        //
        // @Override
        // public void setConstructor()
        // {
        // this.setItemConstructor(MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC).addCode(this.getConstructorSpec().build()).build());
        // }
        //
        // @Override
        // public void setItemInformation()
        // {
        // this.setItemClassPackage(mainClassCompiler.getMainClassPackage() +
        // ".item");
        // this.setItemClassName("ItemTest");
        // this.setUnlocalizedName("itemTest");
        // this.setCreativeTab("MISC");
        // }
        //
        // @Override
        // public void initializeItemClass()
        // {
        // this.setUnlocalizedNameStatement(CodeBlock.builder().addStatement("this.$N(\"$L\")",
        // "setUnlocalizedName", this.getUnlocalizedName()).build());
        // this.setCreativeTabStatement(CodeBlock.builder().addStatement("this.$N($T.$L)",
        // "setCreativeTab", creativeTabsClass, this.getCreativeTab()).build());
        // this.setSuperClass(itemSuperclass);
        // this.getConstructorSpecList().add(this.getUnlocalizedNameStatement());
        // this.getConstructorSpecList().add(this.getCreativeTabStatement());
        // }
        // }.compile();
        
        mainClassCompiler.setPreInitMethod(MethodSpec.methodBuilder("preInit").addModifiers(Modifier.PUBLIC).addAnnotation(mainClassCompiler.eventHandlerClass).addParameter(mainClassCompiler.preInitEventClass, "event").addStatement("logger = event.getModLog()").addStatement("proxy.preInit(event.getSuggestedConfigurationFile())").addStatement("$T.init()", blocksManagerCompiler.getBlocksManagerClass()).build());
        mainClassCompiler.setInitMethod(MethodSpec.methodBuilder("init").addModifiers(Modifier.PUBLIC).addAnnotation(mainClassCompiler.eventHandlerClass).addParameter(mainClassCompiler.initEventClass, "event").addStatement("proxy.init()").build());
        mainClassCompiler.setPostInitMethod(MethodSpec.methodBuilder("postInit").addModifiers(Modifier.PUBLIC).addAnnotation(mainClassCompiler.eventHandlerClass).addParameter(mainClassCompiler.postInitEventClass, "event").build());
        
        mainClassCompiler.compile();
    }
}