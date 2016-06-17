package cf.brforgers.core.lib;

import java.io.File;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.EventBus;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

/**
 * A Common Helper Library for Mods
 * @author TheFreeHigh
 */
public class ModHelper {
	/**
	 * Get if we're on Client-side
	 * @return true when in client-side, and false if server-side
	 */
	public static boolean isClient()
	{
		return FMLCommonHandler.instance().getEffectiveSide().isClient();
	}
	
	/**
	 * Get if we're on Server-side
	 * @return true when in server-side, and false if client-side
	 */
	public static boolean isServer()
	{
		return FMLCommonHandler.instance().getEffectiveSide().isServer();
	}
	
	/**
	 * Get the Client Player Name (or a empty string if we're on server)
	 * @return Client Player Name
	 */
	public static String getPlayerName()
	{
		return isClient() ? Minecraft.getMinecraft().getSession().getUsername() : "";
	}
	
	/**
	 * Get an Config File from the Path
	 * @param pathname the Path
	 * @return a new Configuration instance
	 */
	public static Configuration getConfig(String pathname)
	{
		return new Configuration(new File(pathname));
	}
	
	/**
	 * Get an Config File from the File
	 * @param file the File
	 * @return a New Configuration instance
	 */
	public static Configuration getConfig(File file)
    {
    	return new Configuration(file);
    }
    
	/**
	 * Get an Config File from the Event
	 * @param event the PreInitEvent
	 * @return a New Configuration instance
	 */
    public static Configuration getConfig(FMLPreInitializationEvent event)
    {
    	return new Configuration(event.getSuggestedConfigurationFile());
    }
    
    /**
     * It add all events to the FML and Forge Bus (Lazy way)
     * @param events The Events
     */
	public static void addEventsToBus(Object... events)
	{
		EventBus fmlBus = FMLCommonHandler.instance().bus(), forgeBus = MinecraftForge.EVENT_BUS;
		for (Object event : events) {
			fmlBus.register(event);
			forgeBus.register(event);
		}
	}
}
