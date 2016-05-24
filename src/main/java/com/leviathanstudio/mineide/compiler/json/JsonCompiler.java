package com.leviathanstudio.mineide.compiler.json;

import java.io.File;
import java.util.HashMap;
import java.util.Map.Entry;

import com.leviathanstudio.mineide.util.Util;

import java.util.Set;

public abstract class JsonCompiler
{
    // key : #key -> value
    private HashMap<String, String> mapPattern = new HashMap<>();
    // name -> final location
    // ex : blockstate -> /assets/$modid/blockstates/$file_name
    private HashMap<String, String> fileLocation = new HashMap<>();
    // name -> pattern location
    private HashMap<String, String> patternLocation = new HashMap<>();
    
    private final String modid;
    private final String type;
    
    public JsonCompiler(String modid, String type)
    {
        this.modid = modid;
        this.type = type;
    }
    
    public void addBlockstate(String name, String fileName)
    {
        String base = "/assets/$modid/blockstates/$name_$type.json";
        String file_location = base.replace("$modid", this.modid).replace("$type", this.type).replace("$name", fileName);
        String pattern_location = Util.TEMPLATE_DIR + "/blocks/blockstates";
        this.fileLocation.put(name, file_location);
        this.patternLocation.put(name, pattern_location);
    }
    
    public void addModelBlock(String name)
    {
        
    }
    
    public void addModelItem(String name)
    {
        
    }
    
    public void process()
    {
        Set<Entry<String, String>> entries = this.fileLocation.entrySet();
        
        for(Entry<String, String> entry : entries)
        {
            // get Infos
            String name = entry.getKey();
            String file_location = entry.getValue();
            String pattern = this.patternLocation.get(name);
            
            // get pattern
            
            // replace pattern
            
            // create file
            File file = new File(file_location);
        }
    }
    
    public abstract void addPatternKeys();
    
    public abstract void addPatternFiles();
}