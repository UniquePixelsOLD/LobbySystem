package net.uniquepixels.lobbysystem.listener;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.uniquepixels.core.paper.item.DefaultItemStackBuilder;
import net.uniquepixels.core.paper.item.skull.SkullItemStackBuilder;
import net.uniquepixels.lobbysystem.commands.BuildCommand;
import net.uniquepixels.lobbysystem.other.PlayerHider;
import net.uniquepixels.lobbysystem.other.PlayerView;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;

public class PlayerInteractionListener implements Listener {

  @EventHandler
  public void onPlayerInteraction(PlayerInteractEvent event) {
    Player player = event.getPlayer();
    if(!BuildCommand.playersInBuildMode.contains(player)) {
      event.setCancelled(true);
    }

    if(Objects.equals(event.getItem(), new DefaultItemStackBuilder<>(Material.RECOVERY_COMPASS).displayName(Component.text("Navigation").color(TextColor.color(30, 165, 173))).applyItemMeta().buildItem())) {
      player.openInventory(Bukkit.createInventory(player, 9*5, Component.text("Navigation")));

    } else if (Objects.equals(event.getItem(), new DefaultItemStackBuilder<>(Material.CHEST).displayName(Component.text("Cosmetics").color(TextColor.color(30, 165, 173))).applyItemMeta().buildItem())) {
      player.openInventory(Bukkit.createInventory(player, 9*5, Component.text("Cosmetics")));

    } else if (Objects.equals(event.getItem(), new SkullItemStackBuilder(Material.PLAYER_HEAD).setSkullOwner(player.getPlayer()).displayName(Component.text("My profile").color(TextColor.color(74, 235, 90))).applyItemMeta().buildItem())) {
      player.openInventory(Bukkit.createInventory(player, 9*5, Component.text("My profile")));

    } else if (Objects.equals(event.getItem(), new DefaultItemStackBuilder<>(Material.LIME_DYE).displayName(Component.text("All players shown").color(TextColor.color(74, 235, 90))).applyItemMeta().buildItem())) {

      PlayerHider.changePlayerView(player, PlayerView.ONLY_FRIENDS_AND_VIPS);
      player.getInventory().setItem(7, new DefaultItemStackBuilder<>(Material.PURPLE_DYE).displayName(Component.text("Only friends and VIPs shown").color(TextColor.color(235, 44, 207))).applyItemMeta().buildItem());
      player.playSound(player.getLocation(), Sound.UI_TOAST_IN, 5, 0);

    } else if (Objects.equals(event.getItem(), new DefaultItemStackBuilder<>(Material.PURPLE_DYE).displayName(Component.text("Only friends and VIPs shown").color(TextColor.color(235, 44, 207))).applyItemMeta().buildItem())) {

      PlayerHider.changePlayerView(player, PlayerView.NONE);
      player.getInventory().setItem(7, new DefaultItemStackBuilder<>(Material.GRAY_DYE).displayName(Component.text("Nobody shown").color(TextColor.color(168, 168, 167))).applyItemMeta().buildItem());
      player.playSound(player.getLocation(), Sound.UI_TOAST_IN, 5, 0);

    } else if (Objects.equals(event.getItem(), new DefaultItemStackBuilder<>(Material.GRAY_DYE).displayName(Component.text("Nobody shown").color(TextColor.color(168, 168, 167))).applyItemMeta().buildItem())) {

      PlayerHider.changePlayerView(player, PlayerView.ALL);
      player.getInventory().setItem(7, new DefaultItemStackBuilder<>(Material.LIME_DYE).displayName(Component.text("All players shown").color(TextColor.color(74, 235, 90))).applyItemMeta().buildItem());
      player.playSound(player.getLocation(), Sound.UI_TOAST_IN, 5, 0);

    } else if (Objects.equals(event.getItem(), new DefaultItemStackBuilder<>(Material.END_CRYSTAL).displayName(Component.text("Lobby Switcher").color(TextColor.color(30, 165, 173))).applyItemMeta().buildItem())) {
      player.openInventory(Bukkit.createInventory(player, 9*3, Component.text("Lobby Switcher")));
    }
  }
}
