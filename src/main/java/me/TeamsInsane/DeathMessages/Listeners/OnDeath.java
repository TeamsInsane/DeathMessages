package me.TeamsInsane.DeathMessages.Listeners;

import me.TeamsInsane.DeathMessages.Core;
import me.TeamsInsane.DeathMessages.deathmessages.DeathMessages;
import me.TeamsInsane.DeathMessages.utils.Color;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Arrays;

/**
 * This class contains lister for detecting player deaths.
 * @author TeamsInsane
 * @version 1.0
 */
public class OnDeath implements Listener {

    private PlayerDeathEvent event;

    /**
     * This method contains player death event listener.
     * @param e bukkit event
     */
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

    /**
     * This method applies place holders to the original message.
     * @param message death message
     * @return message with applied place holders
     */
    private String applyPlaceHolders(String message){
        message = message.replace("%player%",event.getEntity().getName());
        message = message.replace("%nickPlayer%",event.getEntity().getDisplayName());
        message = message.replace("%world%",event.getEntity().getLocation().getWorld().getName());
        Player killer = event.getEntity().getKiller();
        if(killer != null) message = message.replace("%killer%",killer.getName());

        Player player = event.getEntity();
        if(player.getLastDamageCause() instanceof EntityDamageByEntityEvent){
            EntityDamageByEntityEvent diedByEntity = (EntityDamageByEntityEvent) player.getLastDamageCause();
            if (!(diedByEntity.getDamager() instanceof Player)) {
                if(diedByEntity.getDamager() instanceof Arrow){
                    Arrow arrow = (Arrow) diedByEntity.getDamager();
                    message = message.replace("%killer%", getProjectilePlayer(arrow));
                }else {
                    message = message.replace("%killer%", diedByEntity.getDamager().getName());
                }
            }
        }
        return message;
    }

    private String getProjectilePlayer(Arrow arrow){
        if (arrow.getShooter() instanceof Skeleton) return "skeleton";
        if (arrow.getShooter() instanceof Blaze) return "žarko";
        return "UNIMPLEMENTED";
    }
}
