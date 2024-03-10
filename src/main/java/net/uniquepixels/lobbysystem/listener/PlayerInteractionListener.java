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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PlayerInteractionListener implements Listener {

  private final List<Player> playersInInteractionCooldown = new ArrayList<>();

  @EventHandler
  public void onPlayerInteraction(PlayerInteractEvent event) {
    Player player = event.getPlayer();
    if(!BuildCommand.playersInBuildMode.contains(player)) {
      event.setCancelled(true);
    }

    if(playersInInteractionCooldown.contains(player)) return; //TODO: CHANGE

    switch (event.getPlayer().getInventory().getHeldItemSlot()) {
      case 0 -> { //Navigation
        //TEMP
        player.openInventory(Bukkit.createInventory(player, 9*5, Component.text("Navigation")));
      }
      case 1 -> { //Cosmetics
        //TEMP
        player.openInventory(Bukkit.createInventory(player, 9*5, Component.text("Cosmetics")));
      }
      case 4 -> { //Gadget
        //TEMP
      }
      case 7 -> { //PlayerHider
        if (Objects.equals(event.getItem(), new DefaultItemStackBuilder<>(Material.LIME_DYE).displayName(Component.text("All players shown").color(TextColor.color(74, 235, 90))).applyItemMeta().buildItem())) {
          PlayerHider.changePlayerView(player, PlayerView.ONLY_FRIENDS_AND_VIPS);
          player.getInventory().setItem(7, new DefaultItemStackBuilder<>(Material.PURPLE_DYE).displayName(Component.text("Only friends and VIPs shown").color(TextColor.color(235, 44, 207))).applyItemMeta().buildItem());
          player.playSound(player.getLocation(), Sound.UI_TOAST_IN, 5, 0);
          startPlayerToggleCooldown(player);

        } else if (Objects.equals(event.getItem(), new DefaultItemStackBuilder<>(Material.PURPLE_DYE).displayName(Component.text("Only friends and VIPs shown").color(TextColor.color(235, 44, 207))).applyItemMeta().buildItem())) {
          PlayerHider.changePlayerView(player, PlayerView.NONE);
          player.getInventory().setItem(7, new DefaultItemStackBuilder<>(Material.GRAY_DYE).displayName(Component.text("Nobody shown").color(TextColor.color(168, 168, 167))).applyItemMeta().buildItem());
          player.playSound(player.getLocation(), Sound.UI_TOAST_IN, 5, 0);
          startPlayerToggleCooldown(player);

        } else if (Objects.equals(event.getItem(), new DefaultItemStackBuilder<>(Material.GRAY_DYE).displayName(Component.text("Nobody shown").color(TextColor.color(168, 168, 167))).applyItemMeta().buildItem())) {
          PlayerHider.changePlayerView(player, PlayerView.ALL);
          player.getInventory().setItem(7, new DefaultItemStackBuilder<>(Material.LIME_DYE).displayName(Component.text("All players shown").color(TextColor.color(74, 235, 90))).applyItemMeta().buildItem());
          player.playSound(player.getLocation(), Sound.UI_TOAST_IN, 5, 0);
          startPlayerToggleCooldown(player);
        }
      }
      case 8 -> { //Profile
        player.openInventory(Bukkit.createInventory(player, 9*5, Component.text("My profile")));
      }
    }
  }

  private void startPlayerToggleCooldown(Player player) {
    playersInInteractionCooldown.add(player);

    final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    executorService.scheduleWithFixedDelay(() -> {
      playersInInteractionCooldown.remove(player);
      executorService.shutdown();
    }, 1, 1, TimeUnit.SECONDS);
  }
}
