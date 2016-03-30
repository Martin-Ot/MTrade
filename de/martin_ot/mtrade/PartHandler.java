package de.martin_ot.mtrade;

import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Martin on 29.03.2016.
 */
public class PartHandler implements Listener {

    private Main m;

    public PartHandler(Main m) {
            this.m = m;
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent pqe) {
        m.proceedLogout(pqe.getPlayer());
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent pje) {
        m.proceedJoin(pje.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent apce){
        if (apce.getMessage().startsWith("$")) {
            apce.setCancelled(true);
            m.proceedChat(apce.getPlayer(), apce.getMessage().substring(1));
        }
    }

}
