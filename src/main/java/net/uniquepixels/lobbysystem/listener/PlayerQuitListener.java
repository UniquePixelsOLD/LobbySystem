package net.uniquepixels.lobbysystem.listener;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Objects;

public class PlayerQuitListener implements Listener {

  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent event) {

    Player player = event.getPlayer();

    event.quitMessage(Component.empty());

    event.getPlayer().getServer().getOnlinePlayers().forEach(player1 -> {

      Component component = Component.text(player.getName() + " has left this lobby.").color(TextColor.color(233, 232, 77));

      switch (Objects.requireNonNull(player1.getInventory().getItem(7)).getType()) { //TODO: Add MongoDB Database integration
        case PURPLE_DYE -> {
          //TODO: Check if player1 is a friend of player
          if(player.hasPermission("lobbysystem.vip")) {
            player1.sendMessage(component);
          }
        }
        case LIME_DYE -> player1.sendMessage(component);
      }
    });
  }
}
