package net.uniquepixels.lobbysystem.commands;

import net.uniquepixels.lobbysystem.listener.PlayerJoinListener;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BuildCommand implements CommandExecutor {

  public static List<Player> playersInBuildMode = new ArrayList<>();
  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
    if(!(sender instanceof Player player)) return true;

    if(playersInBuildMode.contains(player)) {
      playersInBuildMode.remove(player);
      player.sendMessage("§eYou have §6deactivated §ethe §6Build Mode§e.");
      player.setGameMode(GameMode.SURVIVAL);
      PlayerJoinListener.resetInventory();
      if(player.hasPermission("lobbysystem.fly")) {
        player.setAllowFlight(true);
      }
    } else {
      playersInBuildMode.add(player);
      player.sendMessage("§eYou have §6activated §ethe §6Build Mode§e.");
      player.setGameMode(GameMode.CREATIVE);
      player.getInventory().clear();
    }

    return true;
  }
}
