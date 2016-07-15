package cf.brforgers.core.lib;

import cf.brforgers.core.launch.BRCorePlugin;
import cf.brforgers.core.lib.utils.Function;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * A Common Helper Library for Mods
 * @author AdrianTodt
 */
public class Utils {
	public static final Pattern formattingRemover = Pattern.compile("(?i)" + String.valueOf('\u00a7') + "[0-9A-FK-OR]");
    public static final boolean debugFlag = BRCorePlugin.getDebugFlag();

    public static <P> Function<P> toFunction(final java.lang.Runnable runnable, Class<P> type) {
        return new Function<P>() {
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
		return FMLCommonHandler.instance().getSide().isClient();
	}

	/**
	 * Get if we're on Server-side
	 * @return true when in server-side, and false if client-side
	 */
	public static boolean isServer()
	{
		return FMLCommonHandler.instance().getSide().isServer();
	}

	/**
	 * Get the Client Player Name (or a empty string if we're on server)
	 * @return Client Player Name
	 */
	@SideOnly(Side.CLIENT)
	public static String getPlayerName()
	{
		return isClient() ? Minecraft.getMinecraft().thePlayer.getDisplayNameString() : "";
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

	public static Map<String, Object> getInjectionData() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.putAll(BRCorePlugin.injectedData);
        return map;
    }

	public static File getMinecraftDir() {
		return BRCorePlugin.getMinecraftDir();
	}
}
