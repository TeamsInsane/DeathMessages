package me.TeamsInsane.DeathMessages.listeners;

import me.TeamsInsane.DeathMessages.messages.Message;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * This class contains lister for detecting player deaths.
 * @author TeamsInsane
 * @version 1.0
 */
public final class PlayerDeathListener implements Listener {

    /**
     * This method contains player death event listener.
     * @param event bukkit event
     */
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Message message = new Message(event);
        message.checkForMessages();
        event.setDeathMessage(message.getNewMessage());
    }
}
