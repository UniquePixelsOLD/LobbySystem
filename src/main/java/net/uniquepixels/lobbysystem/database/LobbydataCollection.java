package net.uniquepixels.lobbysystem.database;

import com.mongodb.client.MongoCollection;
import net.uniquepixels.coreapi.database.MongoDatabase;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.mongodb.client.model.Updates.set;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;

public class LobbydataCollection {

  public static MongoCollection<Document> collection;

  public void setup(MongoDatabase database) {
    collection = database.collection("lobbydata", Document.class);
  }


  boolean allLocationsFound = true;
  public void checkLocations() {

    List<String> locations = new ArrayList<>();

    locations.add("lobby_spawn");
    locations.add("lobby_pos1");
    locations.add("lobby_pos2");
    locations.add("jumpandrun_start");
    locations.add("jumpandrun_pos1");
    locations.add("jumpandrun_pos2");

    locations.forEach(name -> {
      if(getLocation(name) != null) return;

      allLocationsFound = false;
      System.out.println("Location '" + name + "' could not be found. Please set it ingame using /setup.");
    });
  }

  public static void setLocation(Location location, String name) {
    if(getLocation(name) == null) {
      collection.insertOne(new Document("location", name).append("world", location.getWorld().getName()).append("x", location.getX()).append("y", location.getY()).append("z", location.getZ()).append("yaw", (double) location.getYaw()).append("pitch", (double) location.getPitch()));
    } else {
      collection.updateOne(eq("location", name), combine(set("world", location.getWorld().getName()), set("x", location.getX()), set("y", location.getY()), set("z", location.getZ()), set("yaw", (double) location.getYaw()), set("pitch", (double) location.getPitch())));
    }
  }

  public static Location getLocation(String name) {
    Document doc = collection.find(eq("location", name)).first();

    if(doc == null) return null;

    World world = Bukkit.getWorld(doc.getString("world"));
    double x = doc.getDouble("x");
    double y = doc.getDouble("y");
    double z = doc.getDouble("z");
    double yaw = doc.getDouble("yaw");
    double pitch = doc.getDouble("pitch");

    float new_yaw = (float) yaw;
    float new_pitch = (float) pitch;

    return new Location(world, x, y, z, new_yaw, new_pitch);
  }

}
