package net.uniquepixels.lobbysystem.other;

import net.uniquepixels.lobbysystem.LobbySystem;
import org.bukkit.entity.Player;

public class PlayerHider {

  public static void changePlayerView(Player player, PlayerView playerView) {
    switch (playerView) {
      case ALL -> player.getServer().getOnlinePlayers().forEach(player1 -> player.showPlayer(LobbySystem.javaPlugin, player1));
      case ONLY_FRIENDS_AND_VIPS -> player.getServer().getOnlinePlayers().forEach(player1 -> {
        //TODO: Check if player1 is a friend of player
        if(player1.hasPermission("lobbysystem.vip")) {
          player.showPlayer(LobbySystem.javaPlugin, player1);
        } else player.hidePlayer(LobbySystem.javaPlugin, player1);
      });
      case NONE -> player.getServer().getOnlinePlayers().forEach(player1 -> player.hidePlayer(LobbySystem.javaPlugin, player1));
    }
  }
}
