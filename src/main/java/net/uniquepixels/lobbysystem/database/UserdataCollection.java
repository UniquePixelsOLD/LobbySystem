package net.uniquepixels.lobbysystem.database;

import com.mongodb.client.MongoCollection;
import net.uniquepixels.coreapi.database.MongoDatabase;
import org.bson.Document;

public class UserdataCollection {

  public static MongoCollection<Document> collection;

  public void setup(MongoDatabase database) {
    collection = database.collection("userdata", Document.class);
  }
}
