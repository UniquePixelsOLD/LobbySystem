package net.uniquepixels.lobbysystem.ui;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.uniquepixels.core.paper.gui.UIRow;
import net.uniquepixels.core.paper.gui.UISlot;
import net.uniquepixels.core.paper.gui.backend.UIHolder;
import net.uniquepixels.core.paper.gui.background.UIBackground;
import net.uniquepixels.core.paper.gui.exception.OutOfInventoryException;
import net.uniquepixels.core.paper.gui.item.UIItem;
import net.uniquepixels.core.paper.gui.types.chest.ChestUI;
import net.uniquepixels.core.paper.item.DefaultItemStackBuilder;
import net.uniquepixels.lobbysystem.database.LobbydataCollection;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class SetupUI extends ChestUI {
  private final UIHolder uiHolder;
  public SetupUI(UIHolder uiHolder) {
    super(Component.text("Setup menu").color(NamedTextColor.BLACK), UIRow.CHEST_ROW_3);
    this.uiHolder = uiHolder;
  }

  @Override
  protected void initItems(Player player) throws OutOfInventoryException {
    item(new UIItem(new DefaultItemStackBuilder<>(Material.END_CRYSTAL).displayName(Component.text("Set Lobby Spawn").color(NamedTextColor.WHITE)).addLoreLine(Component.text("This is the position").color(NamedTextColor.GRAY)).addLoreLine(Component.text("where the player spawns.").color(NamedTextColor.GRAY)).buildItem(), UISlot.SLOT_10), ((clicker, clickedItem, action, event) -> {
      setLocationAndCloseInventory(player, "lobby_spawn");
      return true;
    }));

    item(new UIItem(new DefaultItemStackBuilder<>(Material.NETHER_STAR).displayName(Component.text("Set Lobby Pos1").color(NamedTextColor.WHITE)).addLoreLine(Component.text("This is the first position").color(NamedTextColor.GRAY)).addLoreLine(Component.text("of the lobby boundary.").color(NamedTextColor.GRAY)).buildItem(), UISlot.SLOT_11), ((clicker, clickedItem, action, event) -> {
      setLocationAndCloseInventory(player, "lobby_pos1");
      return true;
    }));

    item(new UIItem(new DefaultItemStackBuilder<>(Material.NETHER_STAR).displayName(Component.text("Set Lobby Pos2").color(NamedTextColor.WHITE)).addLoreLine(Component.text("This is the second position").color(NamedTextColor.GRAY)).addLoreLine(Component.text("of the lobby boundary.").color(NamedTextColor.GRAY)).buildItem(), UISlot.SLOT_12), ((clicker, clickedItem, action, event) -> {
      setLocationAndCloseInventory(player, "lobby_pos2");
      return true;
    }));

    item(new UIItem(new DefaultItemStackBuilder<>(Material.WHITE_WOOL).displayName(Component.text("Set Jump and Run start plate").color(NamedTextColor.WHITE)).addLoreLine(Component.text("Sets the block/plate the player").color(NamedTextColor.GRAY)).addLoreLine(Component.text("starts the Jump & Run with").color(NamedTextColor.GRAY)).buildItem(), UISlot.SLOT_14), ((clicker, clickedItem, action, event) -> {
      setLocationAndCloseInventory(player, "jumpandrun_start");
      return true;
    }));

    item(new UIItem(new DefaultItemStackBuilder<>(Material.WHITE_WOOL).displayName(Component.text("Set Jump and Run Pos1").color(NamedTextColor.WHITE)).addLoreLine(Component.text("This is the first position").color(NamedTextColor.GRAY)).addLoreLine(Component.text("of the JnR boundary.").color(NamedTextColor.GRAY)).buildItem(), UISlot.SLOT_15), ((clicker, clickedItem, action, event) -> {
      setLocationAndCloseInventory(player, "jumpandrun_pos1");
      return true;
    }));

    item(new UIItem(new DefaultItemStackBuilder<>(Material.WHITE_WOOL).displayName(Component.text("Set Jump and Run Pos2").color(NamedTextColor.WHITE)).addLoreLine(Component.text("This is the second position").color(NamedTextColor.GRAY)).addLoreLine(Component.text("of the JnR boundary.").color(NamedTextColor.GRAY)).buildItem(), UISlot.SLOT_16), ((clicker, clickedItem, action, event) -> {
      setLocationAndCloseInventory(player, "jumpandrun_pos2");
      return true;
    }));

    setBackground(UIBackground.createDefaultBackground());
  }

  @Override
  public void onClose(Player player) {

  }

  @Override
  public boolean allowItemMovementInOtherInventories() {
    return false;
  }

  private void setLocationAndCloseInventory(Player player, String locationName) {
    LobbydataCollection.setLocation(player.getLocation(), locationName);
    player.sendMessage(Component.text("You have set '").color(NamedTextColor.WHITE).append(Component.text(locationName).color(NamedTextColor.GOLD)).append(Component.text("'.").color(NamedTextColor.WHITE)));
    player.closeInventory();
  }
}
