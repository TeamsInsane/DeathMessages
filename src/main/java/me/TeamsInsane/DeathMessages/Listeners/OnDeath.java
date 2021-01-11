package me.TeamsInsane.DeathMessages.Listeners;

import me.TeamsInsane.DeathMessages.Core;
import me.TeamsInsane.DeathMessages.deathmessages.DeathMessages;
import me.TeamsInsane.DeathMessages.utils.Color;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Arrays;

public class OnDeath implements Listener {

    private PlayerDeathEvent event;
    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        this.event = e;
        String[] message = e.getDeathMessage().split(" ");
        Arrays.copyOfRange(message,1, message.length);
        String formatedMessage = String.join(" ", message).toLowerCase();
        System.out.println(formatedMessage);
        for(DeathMessages deathMessages : DeathMessages.values()){
            if(formatedMessage.contains(deathMessages.message.toLowerCase())){
                String configMessage = Core.configuration.getConfig().getString(deathMessages.key);
                if(configMessage == null) return;
                configMessage = applyPlaceHolders(configMessage);
                e.setDeathMessage(Color.format(configMessage));
            }
        }
    }

    private String applyPlaceHolders(String message){
        message = message.replace("%player%",event.getEntity().getName());
        message = message.replace("%nickPlayer%",event.getEntity().getDisplayName());
        message = message.replace("%world%",event.getEntity().getLocation().getWorld().getName());
        Player killer = event.getEntity().getKiller();
        if(killer != null) message = message.replace("%killer%",killer.getName());
        return message;
    }
}
