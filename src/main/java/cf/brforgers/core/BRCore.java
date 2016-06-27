package cf.brforgers.core;

import static cf.brforgers.core.Lib.*;

import org.apache.logging.log4j.Logger;

import cf.brforgers.core.lib.*;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

/**
 * BRForgersCore: Where a Lot of ~magic~ Definitions happen!
 */
@Mod(modid = MODID , version = VERSION , name = MODNAME)
public class BRCore
{
	@Instance(MODID)
	/**
	 * Instance of the Mod
	 */
	public static BRCore instance;
	
	/**
	 * The Mod Logger. Until pre-init, it will be a SilentLogger. Then it will be the Proper Mod Logger.
	 */
	public static Logger logger = new SilentLogger();
	
	@EventHandler
	/**
	 * Mod PreInit Event.
	 * Used only by Forge
	 * @param e Forge Event
	 */
	public static void preInit(FMLPreInitializationEvent e)
	{
		/* Get Logger */
		logger = e.getModLog();
		
		/* Startup Log */
		logger.info("I'm Alive?");
		FMLLog.info("Yes, You ARE. Now Load!");
		logger.info("Okay! Start Loading...");
		
		/* Get Configs */
		Configuration config = Utils.getConfig(e);
		config.load();
		
		/// Start Modules ///
		
		/* If we're in Client, load Client Modules */
		if (Utils.isClient())
		{			
			boolean updaterEnabled = config.getBoolean("enabledUpdater", "ClientModules", true, "Enable or Disable the Mod Updater Indicator.");
			UpdateManager.timeout = config.getInt("timecycleUpdater", "ClientModules", 300, 60, 1200, "Set the Timeout (in seconds) to the updater check to updates.") * 20;
		}
		
		/* Register Update Checker */
		UpdateManager.addToUpdateChecker(Lib.MODID, Lib.FANCYNAME, Lib.UPDATEURL, Lib.VERSION, logger, true);
		
		/// End Modules ///
		
		/* Ending PreInit */
		
		if(config.hasChanged())
			config.save();
		
		logger.info("I think " + (Utils.isClient() ? "we're" : "I'm") + " done for now.");
		if (Utils.isClient())
			logger.info("Also, Thanks "+ Utils.getPlayerName() +", for playing with BRForgers Mods!");
		
		// I can't resist a Easter Egg..
		FMLLog.info("Uhh, such a Strange Core Mod. Okay, next Mods...");
	}
	
	@EventHandler
	/**
	 * Mod Init Event.
	 * Used only by Forge
	 * @param e Forge Event
	 */
	public static void init(FMLInitializationEvent e)
	{
		logger.info("Look, it's Initialization Time!");
		logger.info("I just have nothing to do!");
	}
		
	@EventHandler
	/**
	 * Mod PostInit Event.
	 * Used only by Forge
	 * @param e Forge Event
	 */
	public static void postInit(FMLPostInitializationEvent e)
	{
		logger.info("Oh, it's Post Initialization. Let's Process the Updates!");
		logger.info("Oh, wait! I've forgot that the Update Check is now Parallel. I guess I'll just sit down and watch the whole world burn.");
	}
}
