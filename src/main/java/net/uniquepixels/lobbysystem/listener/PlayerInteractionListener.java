package net.uniquepixels.lobbysystem.listener;

import net.uniquepixels.lobbysystem.commands.BuildCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractionListener implements Listener {

  @EventHandler
  public void onPlayerInteraction(PlayerInteractEvent event) {
    Player player = event.getPlayer();
    if(!BuildCommand.playersInBuildMode.contains(player)) {
      event.setCancelled(true);
    }
  }
}
