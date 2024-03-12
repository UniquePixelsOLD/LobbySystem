package net.uniquepixels.lobbysystem.database;

import com.mongodb.client.MongoCollection;
import net.uniquepixels.coreapi.database.MongoDatabase;
import org.bson.Document;
import org.bukkit.entity.Player;

import static com.mongodb.client.model.Filters.eq;

public class UserdataCollection {

  public static MongoCollection<Document> collection;

  public void setup(MongoDatabase database) {
    collection = database.collection("userdata", Document.class);
  }

  public static void checkUserData(Player player, boolean generateUserdataIfNotFound) {
    //sends true if userdata found, false if not

    boolean dataAvailable = collection.find(eq("player_uuid", player.getUniqueId().toString())).first() != null;
    if(generateUserdataIfNotFound && !dataAvailable) {
      System.out.println("Userdata for " + player.getName() + " not found. Generating data...");
      collection.insertOne(new Document("player_uuid", player.getUniqueId().toString()).append("playerhider_status", "all"));
      System.out.println("Userdata for " + player.getName() + " has been inserted into the database.");
    }
  }
}
