package me.TeamsInsane.DeathMessages.messages;

import me.TeamsInsane.DeathMessages.Core;
import me.TeamsInsane.DeathMessages.deathmessages.DeathMessages;
import me.TeamsInsane.DeathMessages.utils.Color;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.*;

public class Message {

    private final PlayerDeathEvent event;
    private final String message;
    private String newMessage;

    public Message(PlayerDeathEvent event) {
        this.event = event;
        // This removes player name from the message and converts all chars to lower case
        message = String.join(" ", Arrays.copyOfRange(Objects.requireNonNull(
                event.getDeathMessage()).split(" "), 1, event.getDeathMessage().length())).toLowerCase();
    }

    public String getNewMessage() {
        if (newMessage == null) return message;
        return Color.format(newMessage);
    }

    public void checkForMessages() {
        for (DeathMessages deathMessage : DeathMessages.values()) {
            if (!message.contains(deathMessage.message.toLowerCase())) continue;

            String configMessage = Core.configuration.getConfig().getString(deathMessage.key);
            if(configMessage == null) return;
            newMessage = configMessage;
            applyPlaceholders();
        }
    }

    private void applyPlaceholders() {
        Player player = event.getEntity();

        newMessage = newMessage.replace("%player%", player.getName());
        newMessage = newMessage.replace("%nickPlayer%", player.getDisplayName());
        newMessage = newMessage.replace("%world%", player.getLocation().getWorld().getName());

        if (player.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
            killedByEntity(player);
            return;
        }
        killedByNature(player);
    }

    private void killedByEntity(Player player) {
        EntityDamageByEntityEvent diedByEntity = (EntityDamageByEntityEvent) player.getLastDamageCause();
        if (diedByEntity == null) return;

        Entity entity = diedByEntity.getDamager();
        // Killed by player
        if (entity instanceof Player) {
            Player killer = event.getEntity().getKiller();
            if (killer == null) return;
            newMessage = newMessage.replace("%killer%", killer.getName());
            newMessage = newMessage.replace("%nickKiller%", killer.getDisplayName());
            return;
        }
        // Killed by arrow
        if (diedByEntity.getDamager() instanceof Arrow) {
            Arrow arrow = (Arrow) diedByEntity.getDamager();
            if (arrow.getShooter() instanceof Skeleton) setKiller("Skeleton");
            if (arrow.getShooter() instanceof Pillager) setKiller("Pillager");
            if (arrow.getShooter() instanceof Piglin) setKiller("Piglin");
            return;
        }
        // Killed by fireball
        if (diedByEntity.getDamager() instanceof Fireball) {
            Fireball fireball = (Fireball) diedByEntity.getDamager();
            if (fireball.getShooter() instanceof Ghast) setKiller("Ghast");
            if (fireball.getShooter() instanceof Blaze) setKiller("Blaze");
            return;
        }
        newMessage = newMessage.replace("%killer%", diedByEntity.getDamager().getName());
    }

    private void killedByNature(Player player) {

    }


    private void setKiller(String replacement) {
        newMessage = newMessage.replace("%killer%", replacement);
    }


}
