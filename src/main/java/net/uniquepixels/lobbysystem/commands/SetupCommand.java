package net.uniquepixels.lobbysystem.commands;

import net.uniquepixels.core.paper.gui.backend.UIHolder;
import net.uniquepixels.lobbysystem.ui.SetupUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetupCommand implements CommandExecutor {

  private final UIHolder uiHolder;

    public SetupCommand(UIHolder uiHolder) {
        this.uiHolder = uiHolder;
    }

    @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
      if(!(sender instanceof Player player)) {
        sender.sendMessage("This command is ingame-only!");
        return true;
      }

      uiHolder.open(new SetupUI(uiHolder), player);
    return true;
  }
}
