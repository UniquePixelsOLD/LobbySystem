package net.uniquepixels.lobbysystem.listener;

import net.uniquepixels.lobbysystem.commands.BuildCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

  @EventHandler
  public void onInventoryClick(InventoryClickEvent event) {
    Player player = (Player) event.getWhoClicked();
    if(!BuildCommand.playersInBuildMode.contains(player)) {
      event.setCancelled(true);
    }
  }
}
