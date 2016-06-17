package cf.brforgers.core.lib;

import cf.brforgers.core.lib.DungeonManager.DungeonChests.Groups;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;

/**
 * A Manager for Dungeon Loot, with a Enum of all Chests
 * @author TheFreeHigh
 */
public class DungeonManager {
	/**
	 * Now all is in a Enum. Beautiful, huh?
	 * @author TheFreeHigh
	 *
	 */
	public enum DungeonChests
	{
		/**
		 * Starter Bonus Chest
		 */
		BONUS_CHEST(ChestGenHooks.BONUS_CHEST),
		/**
		 * The Village Blacksmith Chest
		 */
		VILLAGE_BLACKSMITH(ChestGenHooks.VILLAGE_BLACKSMITH),
		/**
		 * Chests found on Dungeons
		 */
		DUNGEON_CHEST(ChestGenHooks.DUNGEON_CHEST),
		/**
		 * Mineshaft Chests
		 */
		MINESHAFT_CORRIDOR(ChestGenHooks.MINESHAFT_CORRIDOR),
		/**
		 * Desert Pyramid Chests (The TNT Trap ones)
		 */
		PYRAMID_DESERT_CHEST(ChestGenHooks.PYRAMID_DESERT_CHEST),
		/**
		 * Jungle Pyramids Chests
		 */
		PYRAMID_JUNGLE_CHEST(ChestGenHooks.PYRAMID_JUNGLE_CHEST),
		/**
		 * Jungle Dispensers (It's a Trap)
		 */
		PYRAMID_JUNGLE_DISPENSER(ChestGenHooks.PYRAMID_JUNGLE_DISPENSER),
		/**
		 * Stronghold Corridor Chests.
		 */
		STRONGHOLD_CORRIDOR(ChestGenHooks.STRONGHOLD_CORRIDOR),
		/**
		 * Stronghold Crossing Chests.
		 */
		STRONGHOLD_CROSSING(ChestGenHooks.STRONGHOLD_CROSSING),
		/**
		 * Stronghold Library Chests.
		 */
		STRONGHOLD_LIBRARY(ChestGenHooks.STRONGHOLD_LIBRARY);
		
		/**
		 * What? Enum-ception?
		 * @author TheFreeHigh
		 *
		 */
		public enum Groups
		{
			/**
			 * All Overworld Chests
			 */
			OVERWORLD(BONUS_CHEST,VILLAGE_BLACKSMITH),
			/**
			 * All Underground Chests
			 */
			UNDERGROUND(DUNGEON_CHEST,MINESHAFT_CORRIDOR),
			/**
			 * All Pyramid Chests (Desert + Jungle)
			 */
			PYRAMIDS(PYRAMID_DESERT_CHEST,PYRAMID_JUNGLE_CHEST,PYRAMID_JUNGLE_DISPENSER),
			/**
			 * All Stronghold Chests
			 */
			STRONGHOLD(STRONGHOLD_CORRIDOR,STRONGHOLD_CROSSING,STRONGHOLD_LIBRARY),
			/**
			 * ALL CHESTS
			 */
			ALL(DungeonChests.values());
			
			DungeonChests[] dChests;
			
			Groups(DungeonChests... chests)
			{
				dChests = chests;
			}
			
			public DungeonChests[] getChests()
			{
				return dChests;
			}
		}
		
		String chestValue = null;
		
		DungeonChests(String value)
		{
			chestValue = value;
		}
		
		public String getValue()
		{
			return chestValue;
		}
	}
	
	/**
	 * Add Loot to a Dungeon Chests
	 * @param chest A Chest from DungeonChests
	 * @param item the ItemStack to be added
	 * @param minAmount Minimum Amount of the Loot
	 * @param maxAmount Maximum Amount of the Loot
	 * @param chance The Chance (1=Rarest,100=Commonest) of the Loot
	 */
	public static void addChestLoot(DungeonChests chest, ItemStack item, int minAmount, int maxAmount, int chance)
	{
		ChestGenHooks.getInfo(chest.getValue()).addItem(new WeightedRandomChestContent(item, minAmount, maxAmount, chance));
	}
	
	/**
	 * Add Loot to Multiple Dungeon Chests
	 * @param group One of the {@link Groups}. Add all Chests from the Group
	 * @param item the ItemStack to be added
	 * @param minAmount Minimum Amount of the Loot
	 * @param maxAmount Maximum Amount of the Loot
	 * @param chance The Chance (1=Rarest,100=Commonest) of the Loot
	 */
	public static void addChestLoot(Groups group, ItemStack item, int minAmount, int maxAmount, int chance)
	{
		WeightedRandomChestContent chest = new WeightedRandomChestContent(item, minAmount, maxAmount, chance);
		DungeonChests[] dungeonChests = group.getChests();
		
		for(DungeonChests dChest: dungeonChests)
		{
			ChestGenHooks.getInfo(dChest.getValue()).addItem(chest);
		}
	}
	
	/**
	 * Add Loot to Multiple Dungeon Chests
	 * @param group A Array of {@link DungeonChests}. All will get the Loot added.
	 * @param item the ItemStack to be added
	 * @param minAmount Minimum Amount of the Loot
	 * @param maxAmount Maximum Amount of the Loot
	 * @param chance The Chance (1=Rarest,100=Commonest) of the Loot
	 */
	public static void addChestLoot(DungeonChests[] dungeonChests, ItemStack item, int minAmount, int maxAmount, int chance)
	{
		WeightedRandomChestContent chest = new WeightedRandomChestContent(item, minAmount, maxAmount, chance);
		for(DungeonChests dChest: dungeonChests)
		{
			ChestGenHooks.getInfo(dChest.getValue()).addItem(chest);
		}
	}
	
	/**
	 * Add Loot to All Dungeon Chests
	 * @param item the ItemStack to be added
	 * @param minAmount Minimum Amount of the Loot
	 * @param maxAmount Maximum Amount of the Loot
	 * @param chance The Chance (1=Rarest,100=Commonest) of the Loot
	 */
	public static void addChestLootToAll(ItemStack item, int minAmount, int maxAmount, int chance)
	{
		WeightedRandomChestContent chest = new WeightedRandomChestContent(item, minAmount, maxAmount, chance);
		DungeonChests[] dungeonChests = DungeonChests.values();
		for(DungeonChests dChest: dungeonChests)
		{
			ChestGenHooks.getInfo(dChest.getValue()).addItem(chest);
		}
	}
}
