package com.afforess.bukkit.minecartmaniacore;
import java.io.File;
import java.util.logging.Logger;
import org.bukkit.Server;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.java.JavaPlugin;

public class MinecartManiaCore extends JavaPlugin {
	
	public MinecartManiaCore(PluginLoader pluginLoader, Server instance,
			PluginDescriptionFile desc, File folder, File plugin,
			ClassLoader cLoader) {
		super(pluginLoader, instance, desc, folder, plugin, cLoader);
		server = instance;
		description = desc;
	}

	public final MinecartManiaCoreListener listener = new MinecartManiaCoreListener(this);
	public static Logger log;
	public static Server server;
	public static PluginDescriptionFile description;
	
	

	public void onEnable(){
		log = Logger.getLogger("Minecraft");
		Configuration.loadConfiguration();
		 // Register our events
     //   pm.registerEvent(Event.Type.REDSTONE_CHANGE, listener, Priority.Normal, this);
     //   pm.registerEvent(Event.Type.VEHICLE_COLLISION_ENTITY, listener, Priority.Normal, this);
     //   pm.registerEvent(Event.Type.VEHICLE_CREATE, listener, Priority.Normal, this);
    //    pm.registerEvent(Event.Type.VEHICLE_DAMAGE, listener, Priority.Normal, this);
    //    pm.registerEvent(Event.Type.VEHICLE_ENTER, listener, Priority.Normal, this);
     //   pm.registerEvent(Event.Type.VEHICLE_EXIT, listener, Priority.Normal, this);
        getServer().getPluginManager().registerEvent(Event.Type.VEHICLE_MOVE, listener, Priority.Highest, this);
        getServer().getPluginManager().registerEvent(Event.Type.VEHICLE_COLLISION_ENTITY, listener, Priority.Normal, this);
     //   pm.registerEvent(Event.Type.PLAYER_COMMAND, listener, Priority.Normal, this);
     //   pm.registerEvent(Event.Type.BLOCK_PLACED, listener, Priority.Normal, this);
		//etc.getLoader().addListener(PluginLoader.Hook.VEHICLE_DESTROYED, listener, this, PluginListener.Priority.MEDIUM);
		//etc.getLoader().addListener(PluginLoader.Hook.VEHICLE_UPDATE, listener, this, PluginListener.Priority.MEDIUM);
        //etc.getLoader().addListener(PluginLoader.Hook.OPEN_INVENTORY, listener, this, PluginListener.Priority.MEDIUM);
	//	etc.getLoader().addListener(PluginLoader.Hook.DISCONNECT, listener, this, PluginListener.Priority.MEDIUM);
        
        //getServer().getPluginManager().callEvent(arg0)

        PluginDescriptionFile pdfFile = this.getDescription();
        log.info( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
	}
	
	public void onDisable(){
		
	}
}
