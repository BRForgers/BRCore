package cf.brforgers.core.lib;

import java.io.File;
import java.util.regex.Pattern;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

/**
 * Anything that don't have enough to be a separated Helper
 * @author TheFreeHigh
 *
 */
public class RandomUtils {
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
}
