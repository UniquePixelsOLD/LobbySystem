package net.uniquepixels.lobbysystem.listener;

import net.kyori.adventure.text.Component;
import net.uniquepixels.lobbysystem.LobbySystem;
import net.uniquepixels.lobbysystem.commands.BuildCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.scheduler.BukkitRunnable;

public class InventoryClickListener implements Listener {

  @EventHandler
  public void onInventoryClick(InventoryClickEvent event) {
    Player player = (Player) event.getWhoClicked();
    if(!BuildCommand.playersInBuildMode.contains(player)) {
      event.setCancelled(true);

      if(event.getInventory().getType() == InventoryType.CRAFTING && event.getSlot() == 22) {
        new BukkitRunnable() {
          @Override
          public void run() {
            player.openInventory(Bukkit.createInventory(player, 9*3, Component.text("Lobby Switcher")));
          }
        }.runTaskLater(LobbySystem.javaPlugin, 1L); // if not delayed, there will be a bug where the item is still stuck to the mouse after the player opens the crafting inventory again
      }
    }


  }
}
