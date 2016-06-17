package cf.brforgers.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import org.apache.logging.log4j.Logger;

import cf.brforgers.core.lib.IOHelper;
import cf.brforgers.core.lib.ModHelper;
import cf.brforgers.core.lib.RandomUtils;
import cf.brforgers.core.lib.SilentLogger;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;

/**
 * A rewrite of the Update Manager. It's now a Lot cleaner.
 * @author TheFreeHigh
 *
 */
public class UpdateManager  implements Runnable {
	//// //// --- BEGIN HELPERCLASSES --- //// ////
	
	/**
	 * Update Status Enumerator
	 * <br> Retrieved by calling retrieveStatus(modid)
	 * @author TheFreeHigh
	 *
	 */
	public enum UpdateStatus {
		/**Mod not Checked yet.*/
		UNCHECKED(false),
		/**Error when Checking for Updates.*/
		ERRORED(false),
		/**The Mod is Updated.*/
		UPDATED(true),
		/**The Mod is Outdated.*/
		OUTDATED(false),
		/**Can't Connect for Checking for Updates.*/
		CANTCONNECT(true);

		/**Defines if the Mod is Updated.*/
		public boolean updated = false;
		
		/**Mod Version*/
		public String version = "";
		
		/**
		 * Constructor
		 * @param updated Updated Variable
		 */
		UpdateStatus(boolean updated) {
			this.updated = updated;
		}
		
		/**
		 * Set current Version
		 * @param version The Current Version found
		 * @return Itself, for Constructing
		 */
		public UpdateStatus setVersion(String version)
		{
			this.version = version;
			return this;
		}
		
		/**
		 * Get the Current Version
		 * @return Version variable
		 */
		public String getVersion()
		{
			return version;
		}
		
		public boolean isUpdated()
		{
			return this.updated;
		}
	}
	
	/**
	 * Don't care about it.
	 * Yep. Forget it.
	 * @author TheFreeHigh
	 *
	 */
	private static class UpdateEntry
	{
		public String modname = null;
		public String modid = null;
		public String updateUrl = null;
		public String currentVersion = null;
		public UpdateStatus status = UpdateStatus.UNCHECKED;
		public Logger modLogger = new SilentLogger();
		public boolean announce = false;
		public String[] messages = DEFAULT_MESSAGES;
	}
	
	
	private static String parseString(String str, UpdateEntry entry, boolean removeFormattingCodes)
	{
		str = str
				.replaceAll("%MODNAME%", entry.modname)
				.replaceAll("%CURVERSION%", entry.currentVersion)
				.replaceAll("%NEWVERSION%", entry.status.getVersion())
				.replaceAll("%URL%", entry.updateUrl);
		
		if (removeFormattingCodes)
			str = (str == null) ? null : RandomUtils.removeFormatting(str);
		
		return str;
	}
	
	//// //// --- END HELPERCLASSES --- //// ////
	//// //// ---  BEGIN MAIN CLASS  --- //// ////
	
	private UpdateManager() {}
	private static ArrayList<UpdateEntry> announceableEntries = new ArrayList<UpdateEntry>(), updateEntries = new ArrayList<UpdateEntry>();
	private boolean runFinished = false;
	public static int timeout = 6000;
	private int threadCount = 0;
	private int tickCountdown = 0;
	
	@SubscribeEvent
	public void onClientPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.side != Side.CLIENT) return;
		
		//BRForgersCore.logger.info("Ticks left: "+tickCount);
		if(tickCountdown == 0)
		{
			tickCountdown = timeout;
			threadCount++;
			new Thread (this, "UpdateThread-"+threadCount).start();
		} else {
			tickCountdown--;
		}
		
		if(!runFinished) return;
		
		runFinished = false;
		
		//BRForgersCore.logger.info("THREAD RUN FINISHED");
         
		boolean outdatedMods = false;
		for (UpdateEntry entry : announceableEntries)
		{
			if (entry.status == UpdateStatus.OUTDATED)
			{
				if (outdatedMods == false)
				{
					event.player.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "The Following mods are Outdated:"));
					outdatedMods = true;
				}
				
				event.player.addChatMessage(new ChatComponentText(
						EnumChatFormatting.GRAY + "> "
						+ EnumChatFormatting.WHITE + entry.modname
						+ EnumChatFormatting.WHITE + ": v" + EnumChatFormatting.BOLD + entry.status.version
						+ EnumChatFormatting.GRAY + " (Currently v" + EnumChatFormatting.BOLD + entry.currentVersion + EnumChatFormatting.RESET + ")"
				));
			}
		}
	}

	@Override
	public void run() {
		
		ArrayList<UpdateEntry> newEntries = new ArrayList<UpdateEntry>();
		ArrayList<UpdateEntry> newAnnounceableEntries = new ArrayList<UpdateEntry>();
		
		for (UpdateEntry eachEntry: updateEntries)
		{
			try {
				String newestVersion = IOHelper.toString(eachEntry.updateUrl);
				
				if (newestVersion == null)
				{
					eachEntry.modLogger.error(parseString(eachEntry.messages[0],eachEntry,true));
					eachEntry.status = UpdateStatus.CANTCONNECT;
					//newEntries.add(eachEntry);
				}
				else
				{
					/* Nice little Fix for removing Tabs and New Lines */
					newestVersion = newestVersion.replaceAll("[\\\t|\\\n|\\\r]","");
				
					if(newestVersion.equalsIgnoreCase(eachEntry.currentVersion))
					{
						eachEntry.status = UpdateStatus.UPDATED.setVersion(newestVersion);
						eachEntry.modLogger.info(parseString(eachEntry.messages[1],eachEntry,true));
						//newEntries.add(eachEntry);
					} else if(eachEntry.status != UpdateStatus.OUTDATED)
					{
						newAnnounceableEntries.add(eachEntry);
						eachEntry.status = UpdateStatus.OUTDATED.setVersion(newestVersion);
						eachEntry.modLogger.info(parseString(eachEntry.messages[2],eachEntry,true));
					}
				}
			}catch (IOException e)
			{
				eachEntry.status = UpdateStatus.CANTCONNECT;
				eachEntry.modLogger.error(parseString(eachEntry.messages[0],eachEntry,true), e);
				//newEntries.add(eachEntry);
			}
			
			newEntries.add(eachEntry);
		}
		
		updateEntries = newEntries;
		announceableEntries = newAnnounceableEntries;
		runFinished = true;
	}
	
	static
	{
		if (ModHelper.isClient())
			ModHelper.addEventsToBus(new UpdateManager());
	}
	
	//// //// ---  END MAIN CLASS  --- //// ////
	//// //// ---  BEGIN REGISTER METHODS  --- //// ////
	
	public static final String[] DEFAULT_MESSAGES = {
			"Failed to connect to check if update is available.",
			"%MODNAME% is updated.",
			"%MODNAME% is outdated. You are running version \"%CURVERSION%\" and the latest available version is \"%NEWVERSION%\""
	};
	
	public static void addToUpdateChecker(String modid, String modname, String updateUrl, String currentVersion, Logger logger, boolean announce, String[] messages)
	{
		UpdateEntry newEntry = new UpdateEntry();
		newEntry.modname = modname;
		newEntry.modid = modid;
		newEntry.updateUrl = updateUrl;
		newEntry.currentVersion = currentVersion;
		newEntry.modLogger = logger;
		newEntry.announce = announce;
		newEntry.messages = messages;
		
		updateEntries.add(newEntry);
	}
	
	public static UpdateStatus retrieveStatus(String modid)
	{
		for (UpdateEntry eachEntry : updateEntries)
			if (eachEntry.modid.equals(modid))
				return eachEntry.status;
		
		return null;
	}
	
	//// //// ---  END REGISTER METHODS  --- //// ////
	//// //// ---  BEGIN SHORTER METHODS  --- //// ////
	
	public static void addToUpdateChecker(String modid, String modname, String updateUrl, String currentVersion)
	{
		addToUpdateChecker(modid, modname, updateUrl, currentVersion, BRCore.logger, true, DEFAULT_MESSAGES);
	}
	
	public static void addToUpdateChecker(String modid, String modname, String updateUrl, String currentVersion, boolean announce)
	{
		addToUpdateChecker(modid, modname, updateUrl, currentVersion, BRCore.logger, announce, DEFAULT_MESSAGES);
	}
	
	public static void addToUpdateChecker(String modid, String modname, String updateUrl, String currentVersion, boolean announce, String[] messages)
	{
		addToUpdateChecker(modid, modname, updateUrl, currentVersion, BRCore.logger, announce, messages);
	}
	
	public static void addToUpdateChecker(String modid, String modname, String updateUrl, String currentVersion, Logger logger)
	{
		addToUpdateChecker(modid, modname, updateUrl, currentVersion, logger, true, DEFAULT_MESSAGES);
	}
	
	public static void addToUpdateChecker(String modid, String modname, String updateUrl, String currentVersion, Logger logger, String[] messages)
	{
		addToUpdateChecker(modid, modname, updateUrl, currentVersion, logger, true, messages);
	}
	
	public static void addToUpdateChecker(String modid, String modname, String updateUrl, String currentVersion, Logger logger, boolean announce)
	{
		addToUpdateChecker(modid, modname, updateUrl, currentVersion, logger, announce, DEFAULT_MESSAGES);
	}

	//// //// ---  END SHORTER METHODS  --- //// ////
}
