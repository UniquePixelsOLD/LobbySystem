package net.uniquepixels.lobbysystem.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class EntitySpawnListener implements Listener {

  @EventHandler
  public void onEntitySpawn(EntitySpawnEvent event) {
    if(!(event.getEntity() instanceof Player)) {
      event.setCancelled(true);
      //TODO: configure for cosmetics
    }
  }
}
