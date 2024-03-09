package net.uniquepixels.lobbysystem.listener;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.uniquepixels.core.paper.item.DefaultItemStackBuilder;
import net.uniquepixels.core.paper.item.skull.SkullItemStackBuilder;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

  private static Player player;

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    player = event.getPlayer();

    player.setGameMode(GameMode.SURVIVAL);
    player.setFoodLevel(20);
    player.setHealth(20);
    player.getInventory().setHeldItemSlot(0);

    teleport();
    resetInventory();

    event.joinMessage(Component.empty()); //Adjust with Velocity

    event.getPlayer().getServer().getOnlinePlayers().forEach(player1 -> {
      //TODO: Check if player1 has hidden other players -> doesn't want to receive messages from them
      player1.sendMessage(Component.text(player.getName() + " has joined this lobby.").color(TextColor.color(233, 232, 77)));
    });

    player.sendMessage("""
      §e§l---------------------------------------------
      \s
                     \s\s§eWelcome to §6UniquePixels§e!
      \s
      \s\s§eLinks
      §e§l---------------------------------------------"""); //TODO: Add links

    if(player.hasPermission("lobbysystem.fly")) {
      player.setAllowFlight(true);
    }
  }

  public static void resetInventory() {
    player.getInventory().clear();
    player.getInventory().setItem(0, new DefaultItemStackBuilder<>(Material.RECOVERY_COMPASS).displayName(Component.text("Navigation").color(TextColor.color(30, 165, 173))).applyItemMeta().buildItem());
    player.getInventory().setItem(1, new DefaultItemStackBuilder<>(Material.CHEST).displayName(Component.text("Cosmetics").color(TextColor.color(30, 165, 173))).applyItemMeta().buildItem());
    player.getInventory().setItem(4, new SkullItemStackBuilder(Material.PLAYER_HEAD).setSkullOwner(player.getPlayer()).displayName(Component.text("My profile").color(TextColor.color(74, 235, 90))).applyItemMeta().buildItem());
    player.getInventory().setItem(7, new DefaultItemStackBuilder<>(Material.LIME_DYE).displayName(Component.text("All players shown").color(TextColor.color(74, 235, 90))).applyItemMeta().buildItem());
    player.getInventory().setItem(8, new DefaultItemStackBuilder<>(Material.END_CRYSTAL).displayName(Component.text("Lobby Switcher").color(TextColor.color(30, 165, 173))).applyItemMeta().buildItem());
  }

  private void teleport() {
    //TODO: Add MongoDB database integration
  }
}
