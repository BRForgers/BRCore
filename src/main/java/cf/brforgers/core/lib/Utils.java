package cf.brforgers.core.lib;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.EventBus;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

/**
 * A Common Helper Library for Mods
 * @author TheFreeHigh
 */
public class Utils {
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
	
	public static final Pattern formattingRemover = Pattern.compile("(?i)" + String.valueOf('\u00a7') + "[0-9A-FK-OR]");
	
	/**
	 * Just in case anyone doesn't know where, I found this on {@link MinecraftForge} class
	 * @param seed The Seed {@link ItemStack} to Be Dropped
	 * @param chance The Chance to be Dropped (Wheat Seeds is 10)
	 */
	public static void addGrassSeed(ItemStack seed, int chance)
	{
		MinecraftForge.addGrassSeed(seed, chance);
	}
	
	/**
	 * Remove the Minecraft formatting
	 * @param str Input String
	 * @return Output String
	 */
	public static String removeFormatting(String str)
	{
		return formattingRemover.matcher(str).replaceAll("");
	}
	
	public static URL newURL(String url) {
		try {
			return new URL(url);
		} catch (MalformedURLException ignored) {}
		return null;
	}
}
