package com.leviathanstudio.mineide.compiler.json;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.stream.JsonWriter;
import com.leviathanstudio.mineide.utils.Utils;

public class JsonMCModInfoCompiler
{
    private String modid, name, description, version, mcversion, url, updateUrl, credits, logoFile;
    private List<String> authorList = new ArrayList<String>();
    private List<String> screenshots = new ArrayList<String>();
    private List<String> dependencies = new ArrayList<String>();
    
    public void compile() throws IOException
    {
        JsonWriter writer = new JsonWriter(new FileWriter(Utils.FORGE_SRC_RESOURCE_DIR + "/mcmod.info"));
        
        writer.setIndent("  ");
        
        writer.beginArray().beginObject();
        
        writer.name("modid").value(this.getModid());
        writer.name("name").value(this.getName());
        writer.name("description").value(this.getDescription());
        writer.name("version").value(this.getVersion());
        writer.name("mcversion").value(this.getMCVersion());
        writer.name("url").value(this.getUrl());
        writer.name("updateUrl").value(this.getUpdateUrl());
        
        writer.name("authorList").beginArray();
        for(int i = 0; i < authorList.size(); i++)
        {
            writer.value(authorList.get(i));
        }
        writer.endArray();
        
        writer.name("credits").value(this.getCredits());
        writer.name("logoFile").value(this.getLogoFile());
        
        writer.name("screenshots").beginArray();
        for(int i = 0; i < screenshots.size(); i++)
        {
            writer.value(screenshots.get(i));
        }
        writer.endArray();
        
        writer.name("dependencies").beginArray();
        for(int i = 0; i < dependencies.size(); i++)
        {
            writer.value(dependencies.get(i));
        }
        writer.endArray();
        
        writer.endObject().endArray();
        
        writer.close();
    }
    
    public String getModid()
    {
        return modid;
    }
    
    public void setModid(String modid)
    {
        this.modid = modid;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public String getVersion()
    {
        return version;
    }
    
    public void setVersion(String version)
    {
        this.version = version;
    }
    
    public String getMCVersion()
    {
        return mcversion;
    }
    
    public void setMCVersion(String mcversion)
    {
        this.mcversion = mcversion;
    }
    
    public String getUrl()
    {
        return url;
    }
    
    public void setUrl(String url)
    {
        this.url = url;
    }
    
    public String getUpdateUrl()
    {
        return updateUrl;
    }
    
    public void setUpdateUrl(String updateUrl)
    {
        this.updateUrl = updateUrl;
    }
    
    public String getCredits()
    {
        return credits;
    }
    
    public void setCredits(String credits)
    {
        this.credits = credits;
    }
    
    public String getLogoFile()
    {
        return logoFile;
    }
    
    public void setLogoFile(String logoFile)
    {
        this.logoFile = logoFile;
    }
    
    public List<String> getAuthorList()
    {
        return authorList;
    }
    
    public List<String> getScreenshots()
    {
        return screenshots;
    }
    
    public List<String> getDependencies()
    {
        return dependencies;
    }
}