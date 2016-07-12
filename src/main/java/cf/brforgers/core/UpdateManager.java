package cf.brforgers.core;

import cf.brforgers.core.lib.IOHelper;
import cf.brforgers.core.lib.Utils;
import cf.brforgers.core.lib.batch.TickBatchExecutor;
import cf.brforgers.core.lib.utils.AsyncTask;
import cf.brforgers.core.lib.utils.PRunnable;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * A rewrite of the Update Manager. It's now a Lot cleaner.
 * @author TheFreeHigh
 *
 */
public class UpdateManager implements Runnable {
	//// //// --- BEGIN HELPERCLASSES --- //// ////

	public static final String[] DEFAULT_MESSAGES = {
			"Failed to connect to check if update is available.",
			"%MODNAME% is updated.",
			"%MODNAME% is outdated. You are running version \"%CURVERSION%\" and the latest available version is \"%NEWVERSION%\""
	};
	private static final TickBatchExecutor.Client batchExecutor = new TickBatchExecutor.Client();
	public static boolean enabled = true;

	//// //// --- END HELPERCLASSES --- //// ////
	//// //// ---  BEGIN MAIN CLASS  --- //// ////
	public static int timeout = 6000;
	private static List<UpdateEntry> updateEntries = new ArrayList<UpdateEntry>();

	static {
		if (Utils.isClient() && enabled) {
			Utils.addEventsToBus(batchExecutor);
			batchExecutor.AddRunnablesToThisTick(new UpdateManager());
			batchExecutor.runOnTickStart = false;

		}

	}

	private int runsInProgress = 0;
	private int threadCount = 0;
	private int tickCountdown = 0;

	private UpdateManager() {
	}

	private static String parseString(String str, UpdateEntry entry, boolean removeFormattingCodes) {
		str = str
				.replaceAll("%MODNAME%", entry.modname)
				.replaceAll("%CURVERSION%", entry.currentVersion)
				.replaceAll("%NEWVERSION%", entry.updatedVersion)
				.replaceAll("%URL%", entry.updateUrl);

		if (removeFormattingCodes)
			str = (str == null) ? null : Utils.removeFormatting(str);

		return str;
	}

	public static void addToUpdateChecker(String modid, String modname, String updateUrl, String currentVersion, String[] messages) {
		UpdateEntry newEntry = new UpdateEntry();
		newEntry.modname = modname;
		newEntry.modid = modid;
		newEntry.updateUrl = updateUrl;
		newEntry.currentVersion = currentVersion;
		newEntry.messages = messages;

		updateEntries.add(newEntry);
	}

	public static UpdateStatus retrieveStatus(String modid) {
		for (UpdateEntry eachEntry : updateEntries)
			if (eachEntry.modid.equals(modid))
				return eachEntry.status;

		return null;
	}

	//// //// ---  END MAIN CLASS  --- //// ////
	//// //// ---  BEGIN REGISTER METHODS  --- //// ////

	public static void addToUpdateChecker(String modid, String modname, String updateUrl, String currentVersion) {
		addToUpdateChecker(modid, modname, updateUrl, currentVersion, DEFAULT_MESSAGES);
	}

	public void run() {
		if (tickCountdown == 0) {
			tickCountdown = timeout;
			threadCount++;
			batchExecutor.AddRunnablesToNextTick(new Runnable() {
				@Override
				public void run() {
					for (final UpdateEntry eachEntry : updateEntries) {
						runsInProgress++;
						batchExecutor.AddRunnablesToThisTick(new Runnable() {
							@Override
							public void run() {
								new AsyncTask<String>(new Callable<String>() {
									@Override
									public String call() throws Exception {
										return IOHelper.toString(eachEntry.updateUrl);
									}
								}).SetFinishTrigger(new PRunnable<String>() {
									@Override
									public void run(final String parameter) {
										synchronized (batchExecutor) {
											batchExecutor.AddRunnablesToThisTick(new Runnable() {
												@Override
												public void run() {
													if (parameter == null) {
														eachEntry.status = UpdateStatus.CANTCONNECT;
													} else {
										/* Nice little Fix for removing Tabs and New Lines */
														String newestVersion = parameter.replaceAll("[\\\t|\\\n|\\\r]", "");

														eachEntry.updatedVersion = newestVersion;

														if (newestVersion.equalsIgnoreCase(eachEntry.currentVersion)) {
															eachEntry.status = UpdateStatus.UPDATED;
														} else if (eachEntry.status != UpdateStatus.OUTDATED) {
															eachEntry.status = UpdateStatus.OUTDATED;
														}
													}
													runsInProgress--;
													if (runsInProgress == 0) {
														batchExecutor.AddRunnablesToThisTick(new Runnable() {
															@Override
															public void run() {
																if (Minecraft.getMinecraft().thePlayer == null) {
																	batchExecutor.AddRunnablesToNextTick(this);
																	return;
																}
																boolean outdatedMods = false;
																for (UpdateEntry entry : updateEntries) {
																	if (!entry.announced && entry.status == UpdateStatus.OUTDATED) {
																		entry.announced = true;
																		if (!outdatedMods) {
																			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "The Following mods are Outdated:"));
																			outdatedMods = true;
																		}
																		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(
																				EnumChatFormatting.GRAY + "> "
																						+ EnumChatFormatting.WHITE + entry.modname
																						+ EnumChatFormatting.WHITE + ": v" + EnumChatFormatting.BOLD + entry.updatedVersion
																						+ EnumChatFormatting.GRAY + " (Currently v" + EnumChatFormatting.BOLD + entry.currentVersion + EnumChatFormatting.RESET + ")"
																		));
																	}
																}
															}
														});
													}
												}
											});
										}
									}
								});
							}
						});
					}
				}
			});
		} else {
			tickCountdown--;
		}
		batchExecutor.AddRunnablesToThisTick(this);
	}
	
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
		public final boolean updated;

		/**
		 * Constructor
		 * @param updated Updated Variable
		 */
		UpdateStatus(boolean updated) {
			this.updated = updated;
		}

		public boolean isUpdated()
		{
			return this.updated;
		}
	}

	//// //// ---  END REGISTER METHODS  --- //// ////
	//// //// ---  BEGIN SHORTER METHODS  --- //// ////
	
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
		public String updatedVersion = null;
		public UpdateStatus status = UpdateStatus.UNCHECKED;
		public String[] messages = DEFAULT_MESSAGES;
		public boolean announced = false;
	}

	//// //// ---  END SHORTER METHODS  --- //// ////
}
