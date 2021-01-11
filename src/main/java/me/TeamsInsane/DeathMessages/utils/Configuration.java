package me.TeamsInsane.DeathMessages.utils;

import me.TeamsInsane.DeathMessages.Core;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;

public class Configuration {
    private Core core;
    public Configuration(Core core){
        this.core = core;
    }

    private File configFile = null;
    private FileConfiguration fileConfiguration = null;

    public void saveConfig(){
        if(configFile == null){
            configFile = new File(core.getDataFolder(),"config.yml");
        }
        if(!configFile.exists()){
            core.saveResource("config.yml", false);
        }
    }

    public void reloadConfig(){
        if(configFile == null){
            saveConfig();
        }
        fileConfiguration = YamlConfiguration.loadConfiguration(configFile);
        Reader configStream = new InputStreamReader(core.getResource("config.yml"));
        if(configStream != null) {
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(configStream);
            fileConfiguration.setDefaults(yamlConfiguration);
        }
    }

    public FileConfiguration getConfig(){
        if(fileConfiguration == null){
            reloadConfig();
        }
        return fileConfiguration;
    }
}
