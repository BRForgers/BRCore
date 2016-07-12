package cf.brforgers.core.lib;

import cf.brforgers.core.lib.utils.PRunnable;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.EventBus;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;

/**
 * A Common Helper Library for Mods
 * @author TheFreeHigh
 */
public class Utils {
	public static final Pattern formattingRemover = Pattern.compile("(?i)" + String.valueOf('\u00a7') + "[0-9A-FK-OR]");

	public static <T> Map<String, Object> asMap(Class<? extends T> clazz, T inst) {
		Map<String, Object> result = new HashMap<String, Object>();
		Field[] declaredFields = getAllFields(clazz);
		for (Field field : declaredFields) {
			try {
				if (!Modifier.isTransient(field.getModifiers())) {
					field.setAccessible(true);
					result.put(field.getName(), field.get(inst));
				}
			} catch (Exception e) {
			}
		}
		return result;
	}

	public static Field[] getAllFields(Class aClass) {
		List<Field> fields = new ArrayList<Field>();
		do {
			Collections.addAll(fields, aClass.getDeclaredFields());
			aClass = aClass.getSuperclass();
		} while (aClass != null && aClass != Object.class);
		return fields.toArray(new Field[fields.size()]);
	}

	public static <P> PRunnable<P> toPRunnable(final java.lang.Runnable runnable, Class<P> type) {
		return new PRunnable<P>() {
			@Override
			public void run(P parameter) {
				runnable.run();
			}
		};
	}
	
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
