package net.uniquepixels.lobbysystem;

import net.uniquepixels.core.paper.chat.chatinput.ChatInputManager;
import net.uniquepixels.core.paper.gui.backend.UIHolder;
import net.uniquepixels.coreapi.database.MongoDatabase;
import net.uniquepixels.lobbysystem.commands.BuildCommand;
import net.uniquepixels.lobbysystem.commands.SetupCommand;
import net.uniquepixels.lobbysystem.database.LobbydataCollection;
import net.uniquepixels.lobbysystem.database.UserdataCollection;
import net.uniquepixels.lobbysystem.listener.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class LobbySystem extends JavaPlugin {

  public static JavaPlugin javaPlugin;
  private UIHolder uiHolder;

  @Override
  public void onEnable() {

    setupMongoDB();

    RegisteredServiceProvider<UIHolder> uiProvider = Bukkit.getServicesManager().getRegistration(UIHolder.class);

    if (uiProvider == null)
      return;

    /*
     * UI workflow to open and manage current ui's (extend ChestUI for custom inventories)
     * */
    uiHolder = uiProvider.getProvider();

    RegisteredServiceProvider<ChatInputManager> chatProvider = Bukkit.getServicesManager().getRegistration(ChatInputManager.class);

    if (chatProvider == null)
      return;

    /*
    * Use ChatInputManager to get the next chat message from player and add actions after a message has been sent
    * */
    ChatInputManager chatInputManager = chatProvider.getProvider();

    javaPlugin = this;

    registerEvents();
    registerCommands();

  }

  @Override
  public void onDisable() {

  }

  private void registerEvents() {
    getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
    getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
    getServer().getPluginManager().registerEvents(new EntitySpawnListener(), this);
    getServer().getPluginManager().registerEvents(new EntityDamageListener(), this);
    getServer().getPluginManager().registerEvents(new FoodLevelChangeListener(), this);
    getServer().getPluginManager().registerEvents(new BlockListener(), this);
    getServer().getPluginManager().registerEvents(new ItemListener(), this);
    getServer().getPluginManager().registerEvents(new PlayerInteractionListener(), this);
    getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
  }

  private void registerCommands() {
    getCommand("build").setExecutor(new BuildCommand());
    getCommand("setup").setExecutor(new SetupCommand(uiHolder));
  }

  private void setupMongoDB() {
    String connectionString = "mongodb+srv://kaiblack:AtCliOmh8XAuLE3R@testcluster.kuszsdk.mongodb.net/?retryWrites=true&w=majority&appName=TestCluster";
    MongoDatabase database = new MongoDatabase(connectionString, "lobbysystem");

    new UserdataCollection().setup(database);

    LobbydataCollection lobbydataCollection = new LobbydataCollection();
    lobbydataCollection.setup(database);
    lobbydataCollection.checkLocations();
  }
}
