package me.TeamsInsane.DeathMessages;

import me.TeamsInsane.DeathMessages.listeners.PlayerDeathListener;
import me.TeamsInsane.DeathMessages.utils.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

public final class Core extends JavaPlugin {
    public static Configuration configuration;

    @Override
    public void onEnable(){
        this.getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
        configuration = new Configuration(this);
        configuration.saveConfig();
        configuration.reloadConfig();
    }

}

