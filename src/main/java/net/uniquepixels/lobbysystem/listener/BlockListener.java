package net.uniquepixels.lobbysystem.listener;

import net.uniquepixels.lobbysystem.commands.BuildCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener {

  @EventHandler
  public void onBlockDestroy(BlockBreakEvent event) {
    Player player = event.getPlayer();
    if(!BuildCommand.playersInBuildMode.contains(player)) {
      event.setCancelled(true);
    }
  }

  @EventHandler
  public void onBlockPlace(BlockPlaceEvent event) {
    Player player = event.getPlayer();
    if(!BuildCommand.playersInBuildMode.contains(player)) {
      event.setCancelled(true);
    }
  }
}
