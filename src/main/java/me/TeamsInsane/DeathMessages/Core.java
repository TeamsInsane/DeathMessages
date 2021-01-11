package me.TeamsInsane.DeathMessages;

import me.TeamsInsane.DeathMessages.Listeners.OnDeath;
import me.TeamsInsane.DeathMessages.utils.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin {
    public static Configuration configuration;

    @Override
    public void onEnable(){
        this.getServer().getPluginManager().registerEvents(new OnDeath(), this);
        configuration = new Configuration(this);
        configuration.saveConfig();
        configuration.reloadConfig();
    }

}

