package net.uniquepixels.lobbysystem.listener;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.uniquepixels.core.paper.item.firework.FireworkEffectItemStackBuilder;
import net.uniquepixels.lobbysystem.commands.BuildCommand;
import net.uniquepixels.lobbysystem.database.UserdataCollection;
import net.uniquepixels.lobbysystem.other.PlayerHider;
import net.uniquepixels.lobbysystem.other.PlayerView;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

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
        String playerhider = UserdataCollection.collection.find(eq("player_uuid", player.getUniqueId().toString())).first().getString("playerhider_status");
        FireworkEffectItemStackBuilder itemBuilder = new FireworkEffectItemStackBuilder();
        Component name = null;

        String newStatus = "";
        switch (playerhider) {
          case "all" -> {
            PlayerHider.changePlayerView(player, PlayerView.ONLY_SELECTED);

            newStatus = "only_selected";

            itemBuilder.setEffect(FireworkEffect.builder().withColor(Color.PURPLE).build());
            name = Component.text("Only Friends and VIPs shown").color(TextColor.color(235, 44, 207));
          }
          case "only_selected" -> {
            PlayerHider.changePlayerView(player, PlayerView.NONE);

            newStatus = "none";

            itemBuilder.setEffect(FireworkEffect.builder().withColor(Color.GRAY).build());
            name = Component.text("Nobody shown").color(TextColor.color(168, 168, 167));
          }
          case "none" -> {
            PlayerHider.changePlayerView(player, PlayerView.ALL);

            newStatus = "all";

            itemBuilder.setEffect(FireworkEffect.builder().withColor(Color.LIME).build());
            name = Component.text("All players shown").color(TextColor.color(74, 235, 90));
          }
        }

        UserdataCollection.collection.updateOne(eq("player_uuid", player.getUniqueId().toString()), combine(set("playerhider_status", newStatus)));

        name = name.append(Component.text(" (right click)").color(TextColor.color(168, 168, 167)));
        itemBuilder.displayName(name);

        player.getInventory().setItem(7, itemBuilder.buildItem());

        player.playSound(player.getLocation(), Sound.UI_TOAST_IN, 5, 0);
        startPlayerToggleCooldown(player);
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
    }, 500, 500, TimeUnit.MILLISECONDS);
  }
}
