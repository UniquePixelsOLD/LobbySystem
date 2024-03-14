package net.uniquepixels.lobbysystem.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {

  @EventHandler
  public void onEntityDamage(EntityDamageEvent event) {
    event.setCancelled(true);

    if(!(event.getEntity() instanceof Player player)) return;

    if(event.getCause() == EntityDamageEvent.DamageCause.VOID) PlayerJoinListener.teleportToSpawn(player);
  }
}
