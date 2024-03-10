package net.uniquepixels.lobbysystem.listener;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.uniquepixels.core.paper.item.DefaultItemStackBuilder;
import net.uniquepixels.core.paper.item.firework.FireworkEffectItemStackBuilder;
import net.uniquepixels.core.paper.item.skull.SkullItemStackBuilder;
import net.uniquepixels.lobbysystem.LobbySystem;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Objects;

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

      Component component = Component.text(player.getName() + " has joined this lobby.").color(TextColor.color(233, 232, 77));

      switch (Objects.requireNonNull(player1.getInventory().getItem(7)).getType()) { //TODO: Add MongoDB Database integration
        case PURPLE_DYE -> {
          //TODO: Check if player1 is a friend of player
          if(!player.hasPermission("lobbysystem.vip")) {
            player1.hidePlayer(LobbySystem.javaPlugin, player);
          } else {
            player1.sendMessage(component);
          }
        }
        case GRAY_DYE -> player1.hidePlayer(LobbySystem.javaPlugin, player);
        case LIME_DYE -> player1.sendMessage(component);
      }
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
    player.getInventory().setItem(7, new DefaultItemStackBuilder<>(Material.LIME_DYE).displayName(Component.text("All players shown").color(TextColor.color(74, 235, 90))).applyItemMeta().buildItem()); //TODO: Save player hider choice
    //player.getInventory().setItem(7, new FireworkEffectItemStackBuilder().setEffect(FireworkEffect.builder().withColor(Color.LIME).build()).displayName(Component.text("All players shown").color(TextColor.color(74, 235, 90)).append(Component.text(" (right click)").color(TextColor.color(168, 168, 167)))).applyItemMeta().buildItem()); //TODO: Save player hider choice
    player.getInventory().setItem(8, new SkullItemStackBuilder(Material.PLAYER_HEAD).setSkullOwner(player.getPlayer()).displayName(Component.text("My profile").color(TextColor.color(74, 235, 90))).applyItemMeta().buildItem());

    player.getInventory().setItem(22, new DefaultItemStackBuilder<>(Material.END_CRYSTAL).displayName(Component.text("Lobby Switcher").color(TextColor.color(30, 165, 173))).applyItemMeta().buildItem());
  }

  private void teleport() {
    //TODO: Add MongoDB database integration
  }
}
