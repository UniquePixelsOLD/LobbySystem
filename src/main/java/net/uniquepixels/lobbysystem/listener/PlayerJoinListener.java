package net.uniquepixels.lobbysystem.listener;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.uniquepixels.core.paper.item.DefaultItemStackBuilder;
import net.uniquepixels.core.paper.item.firework.FireworkEffectItemStackBuilder;
import net.uniquepixels.core.paper.item.skull.SkullItemStackBuilder;
import net.uniquepixels.lobbysystem.LobbySystem;
import net.uniquepixels.lobbysystem.database.UserdataCollection;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

import static com.mongodb.client.model.Filters.eq;

public class PlayerJoinListener implements Listener {

  private static Player player;

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    player = event.getPlayer();

    UserdataCollection.checkUserData(player, true);

    player.setGameMode(GameMode.SURVIVAL);
    player.setFoodLevel(20);
    player.setHealth(20);
    player.getInventory().setHeldItemSlot(0);

    teleport();
    resetInventory();

    event.joinMessage(Component.empty()); //Adjust with Velocity

    event.getPlayer().getServer().getOnlinePlayers().forEach(player1 -> {

      Component component = Component.text(player.getName() + " has joined this lobby.").color(TextColor.color(233, 232, 77));

      switch (UserdataCollection.collection.find(eq("player_uuid", player1.getUniqueId().toString())).first().getString("playerhider_status")) {
        case "only_selected" -> {
          //TODO: Check if player1 is a friend of player
          if(!player.hasPermission("lobbysystem.vip")) {
            player1.hidePlayer(LobbySystem.javaPlugin, player);
          } else {
            player1.sendMessage(component);
          }
        }
        case "none" -> player1.hidePlayer(LobbySystem.javaPlugin, player);
        case "all" -> player1.sendMessage(component);
      }
    });

    player.sendMessage("""
      §e§l---------------------------------------------
      \s
                     \s\s§eWelcome to §6UniquePixels§e!
      \s
      \s\s§eLinks
      §e§l---------------------------------------------""");

    if(player.hasPermission("lobbysystem.fly")) {
      player.setAllowFlight(true);
    }
  }

  public static void resetInventory() {
    player.getInventory().clear();
    player.getInventory().setItem(0, new DefaultItemStackBuilder<>(Material.RECOVERY_COMPASS).displayName(Component.text("Navigation").color(TextColor.color(30, 165, 173)).append(Component.text(" (right click)").color(TextColor.color(168, 168, 167)))).buildItem());
    player.getInventory().setItem(1, new DefaultItemStackBuilder<>(Material.CHEST).displayName(Component.text("Cosmetics").color(TextColor.color(30, 165, 173)).append(Component.text(" (right click)").color(TextColor.color(168, 168, 167)))).buildItem());

    String playerhider = UserdataCollection.collection.find(eq("player_uuid", player.getUniqueId().toString())).first().getString("playerhider_status");
    FireworkEffectItemStackBuilder itemBuilder = new FireworkEffectItemStackBuilder();
    Component name = null;
    switch (playerhider) {
      case "all" -> {
        itemBuilder.setEffect(FireworkEffect.builder().withColor(Color.LIME).build());
        name = Component.text("All players shown").color(TextColor.color(74, 235, 90));
      }
      case "only_selected" -> {
        itemBuilder.setEffect(FireworkEffect.builder().withColor(Color.PURPLE).build());
        name = Component.text("Only Friends and VIPs shown").color(TextColor.color(235, 44, 207));
      }
      case "none" -> {
        itemBuilder.setEffect(FireworkEffect.builder().withColor(Color.GRAY).build());
        name = Component.text("Nobody shown").color(TextColor.color(168, 168, 167));
      }
    }
    name = name.append(Component.text(" (right click)").color(TextColor.color(168, 168, 167)));
    itemBuilder.displayName(name);

    player.getInventory().setItem(7, itemBuilder.buildItem());
    player.getInventory().setItem(8, new SkullItemStackBuilder(Material.PLAYER_HEAD).setSkullOwner(player.getPlayer()).displayName(Component.text("My profile").color(TextColor.color(74, 235, 90)).append(Component.text(" (right click)").color(TextColor.color(168, 168, 167)))).buildItem());

    player.getInventory().setItem(22, new DefaultItemStackBuilder<>(Material.END_CRYSTAL).displayName(Component.text("Lobby Switcher").color(TextColor.color(30, 165, 173)).append(Component.text(" (right click)").color(TextColor.color(168, 168, 167)))).buildItem());
  }

  private void teleport() {
    //TODO: Add MongoDB database integration
  }
}
