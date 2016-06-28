package cf.brforgers.core;

import static net.minecraft.util.EnumChatFormatting.*;
//import net.minecraft.util.text.TextFormatting;

/**
 * BRForgersCore's Strings Lib
 * @author TheFreeHigh
 */
public class Lib
{
	//Mod Identity
	/**
	 * MODID of the Core
	 */
	public static final String MODID = "BRCore";
	
	/**
	 * VERSION of the Core
	 */
	public static final String VERSION = "1.0-RC2";
	
	/**
	 * MODNAME of the Core
	 */
	public static final String MODNAME = "BRCore";
	
	/**
	 * Mod's FANCYNAME
	 * <br> Used in the Update Manager
	 */
	public static final String FANCYNAME = DARK_GREEN.toString() + BOLD.toString() + "BR" + GOLD.toString() + BOLD.toString() + "Core";
	///**
	// * Mod's FANCYNAME
	// * <br> Used in the Update Manager
	// */
	//public static final String FANCYNAME = TextFormatting.DARK_GREEN + "" + TextFormatting.BOLD + "BR" + TextFormatting.GOLD + "" + TextFormatting.BOLD + "Foundation";
	
	/**
	 * URL to Check for Updates
	 */
	public static final String UPDATEURL = "https://raw.githubusercontent.com/TheBrazillianForgersTeam/BRCore/master/latest.txt";
	
	/* Add dependences="required-after:BRForgersCore@[2.1,)" in @Mod(...) to make the mod dependent on BRForgersCore */
}
