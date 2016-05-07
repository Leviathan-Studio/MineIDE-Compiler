package com.leviathanstudio.mineide.main.test;

import javax.lang.model.element.Modifier;

import com.leviathanstudio.mineide.compiler.java.JavaMainClassCompiler;
import com.leviathanstudio.mineide.compiler.java.JavaProxiesCompiler;
import com.leviathanstudio.mineide.compiler.java.block.JavaBlockCompiler;
import com.leviathanstudio.mineide.compiler.java.item.JavaItemCompiler;
import com.leviathanstudio.mineide.compiler.java.registry.JavaGameRegistry;
import com.leviathanstudio.mineide.compiler.json.JsonMCModInfoCompiler;
import com.leviathanstudio.mineide.utils.Utils;
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
        Utils.checkDir(Utils.FORGE_SRC_RESOURCE_DIR);
        JavaMainClassCompiler mainClassCompiler = new JavaMainClassCompiler();
        
        mainClassCompiler.setMainClassPackage("fr.zeamateis.test");
        mainClassCompiler.setMainClassName("testMainClass");
        
        ClassName blockSuperclass = ClassName.get("net.minecraft.block", "Block");
        ClassName itemSuperclass = ClassName.get("net.minecraft.item", "Item");
        
        ClassName materialClass = ClassName.get("net.minecraft.block.material", "Material");
        
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
                this.setUnlocalizedName("thisIsATest_block_number_two");
                this.setLightLevel(1.0F);
            }
            
            @Override
            public void initializeBlockClass()
            {
                this.setUnlocalizedNameStatement(CodeBlock.builder().addStatement("this.$N(\"$L\")", "setUnlocalizedName", this.getUnlocalizedName()).build());
                this.setLightLevelStatement(CodeBlock.builder().addStatement("this.$N($LF)", "setLightLevel", this.getLightLevel()).build());
                this.setSuperClass(blockSuperclass);
                this.getConstructorSpecList().add(CodeBlock.builder().addStatement("super($L.$N)", materialClass, "WOOD").build());
                this.getConstructorSpecList().add(this.getUnlocalizedNameStatement());
                this.getConstructorSpecList().add(this.getLightLevelStatement());
            }
        }.compile();
        
        JavaItemCompiler itemTest = new JavaItemCompiler()
        {
            
            @Override
            public void setConstructor()
            {
                this.setItemConstructor(MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC).addCode(this.getConstructorSpec().build()).build());
            }
            
            @Override
            public void setItemInformation()
            {
                this.setItemClassPackage(mainClassCompiler.getMainClassPackage() + ".item");
                this.setItemClassName("ItemTest");
                this.setUnlocalizedName("itemTest");
                this.setCreativeTab("net.minecraft.creativetab.CreativeTabs.MISC");
            }
            
            @Override
            public void initializeItemClass()
            {
                this.setUnlocalizedNameStatement(CodeBlock.builder().addStatement("this.$N(\"$L\")", "setUnlocalizedName", this.getUnlocalizedName()).build());
                this.setCreativeTabStatement(CodeBlock.builder().addStatement("this.$N($L)", "setCreativeTab", this.getCreativeTab()).build());
                this.setSuperClass(itemSuperclass);
                this.getConstructorSpecList().add(this.getUnlocalizedNameStatement());
                this.getConstructorSpecList().add(this.getCreativeTabStatement());
            }
        }.compile();
        
        JavaGameRegistry gameRegistry = new JavaGameRegistry()
        {
            @Override
            public void initializeGameRegistry()
            {
                this.getGameRegistryList().add(CodeBlock.builder().addStatement(this.gameRegistryClass + ".registerBlock(" + "$N" + ", " + "\"$L\"" + ")", "blockTest", "BlockTest").build());
                this.getGameRegistryList().add(CodeBlock.builder().addStatement(this.gameRegistryClass + ".registerItem(" + "$N" + ", " + "\"$L\"" + ")", "itemTest", "ItemTest").build());
            }
        }.compile();
        
        mainClassCompiler.setPreInitMethod(MethodSpec.methodBuilder("preInit").addModifiers(Modifier.PUBLIC).addAnnotation(mainClassCompiler.eventHandlerClass).addParameter(mainClassCompiler.preInitEventClass, "event").addStatement("logger = event.getModLog()").addStatement("proxy.preInit(event.getSuggestedConfigurationFile())").addCode(CodeBlock.builder().addStatement("$L blockTest = new $N()", blockSuperclass, blockTest2.getBlockClassPackage() + "." + blockTest2.getBlockClassName()).build()).addCode(CodeBlock.builder().addStatement("$L itemTest = new $N()", itemSuperclass, itemTest.getItemClassPackage() + "." + itemTest.getItemClassName()).build()).addCode(gameRegistry.getGameRegistry().build()).build());
        mainClassCompiler.setInitMethod(MethodSpec.methodBuilder("init").addModifiers(Modifier.PUBLIC).addAnnotation(mainClassCompiler.eventHandlerClass).addParameter(mainClassCompiler.initEventClass, "event").addStatement("proxy.init()").build());
        mainClassCompiler.setPostInitMethod(MethodSpec.methodBuilder("postInit").addModifiers(Modifier.PUBLIC).addAnnotation(mainClassCompiler.eventHandlerClass).addParameter(mainClassCompiler.postInitEventClass, "event").build());
        
        mainClassCompiler.compile();
    }
}
