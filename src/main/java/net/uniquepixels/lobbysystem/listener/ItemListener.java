package net.uniquepixels.lobbysystem.listener;

import io.papermc.paper.event.player.PlayerPickItemEvent;
import net.uniquepixels.lobbysystem.commands.BuildCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class ItemListener implements Listener {

  @EventHandler
  public void onItemDrop(PlayerDropItemEvent event) {
    Player player = event.getPlayer();
    if(!BuildCommand.playersInBuildMode.contains(player)) {
      event.setCancelled(true);
    }
  }

  @EventHandler
  public void onItemPickup(PlayerPickItemEvent event) {
    Player player = event.getPlayer();
    if(!BuildCommand.playersInBuildMode.contains(player)) {
      event.setCancelled(true);
    }
  }
}
