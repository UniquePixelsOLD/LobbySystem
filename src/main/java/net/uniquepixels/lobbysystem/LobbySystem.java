package net.uniquepixels.lobbysystem;

import net.uniquepixels.core.paper.item.DefaultItemStackBuilder;
import net.uniquepixels.core.paper.item.ItemStackBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import net.uniquepixels.core.paper.gui.backend.UIHolder;
import net.uniquepixels.core.paper.chat.chatinput.ChatInputManager;

public class LobbySystem extends JavaPlugin {

  @Override
  public void onEnable() {

    RegisteredServiceProvider<UIHolder> uiProvider = Bukkit.getServicesManager().getRegistration(UIHolder.class);

    if (uiProvider == null)
      return;

    /*
     * UI workflow to open and manage current ui's (extend ChestUI for custom inventories)
     * */
    UIHolder uiHolder = uiProvider.getProvider();

    RegisteredServiceProvider<ChatInputManager> chatProvider = Bukkit.getServicesManager().getRegistration(ChatInputManager.class);

    if (chatProvider == null)
      return;

    /*
    * Use ChatInputManager to get the next chat message from player and add actions after a message has been sent
    * */
    ChatInputManager chatInputManager = chatProvider.getProvider();

    ItemStack item = new DefaultItemStackBuilder<>(Material.MAGENTA_GLAZED_TERRACOTTA).applyItemMeta().buildItem();

  }

  @Override
  public void onDisable() {

  }
}
