package cf.brforgers.core.lib;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

/**
 * Item/Block/ItemStack Converter and Item NBT Helper
 * @author TheFreeHigh
 */
public class ItemHelper
{
	/**
	 * Convert Block to Item
	 * @param block The Block
	 * @return The Converted ItemBlock
	 */
	public static Item toItem(Block block)
	{
		return Item.getItemFromBlock(block);
	}
	
	/**
	 * Get Item from ItemStack
	 * @param stack The ItemStack
	 * @return Item from it
	 */
	public static Item toItem(ItemStack stack)
	{
		return stack.getItem();
	}
	
	/**
	 * Get block from Item.
	 * @param item The ItemBlock
	 * @return The Converted Block
	 */
	public static Block toBlock(Item item)
	{
		return Block.getBlockFromItem(item);
	}
	
	/**
	 * Get block from Item
	 * @param stack The ItemStack
	 * @return Block from it
	 */
	public static Block toBlock(ItemStack stack)
	{
		return toBlock(stack.getItem());
	}
	
	/**
	 * Get ItemStack from the Item
	 * @param item The Item
	 * @return ItemStack created with it
	 */
	public static ItemStack toStack(Item item)
	{
		return new ItemStack(item);
	}
	
	/**
	 * Get ItemStack from the Block
	 * @param block The Block
	 * @return ItemStack created with it
	 */
	public static ItemStack toStack(Block block)
	{
		return new ItemStack(block);
	}
	
	/**
	 * Tests if an {@link ItemStack} has a {@link NBTTagCompound}
	 * @param stack {@link ItemStack} with {@link NBTTagCompound}
	 */
	public static boolean detectNBT(ItemStack stack) {
		return stack.hasTagCompound();
	}
	
	/**
	 * Tries to initialize an {@link NBTTagCompound} in an {@link ItemStack}.
	 * This won't do anything if the {@link ItemStack} already has a {@link NBTTagCompound}.
	 * @param stack {@link ItemStack} with {@link NBTTagCompound}
	 */
	public static void initNBT(ItemStack stack) {
		if(!detectNBT(stack))
			injectNBT(stack, new NBTTagCompound());
	}
	
	/**
	 * Injects an {@link NBTTagCompound} to an {@link ItemStack}.
	 * (No checks are made previously)
	 * @param stack {@link ItemStack} with {@link NBTTagCompound}
	 * @param nbt {@link NBTTagCompound} to be Injected
	 */
	public static void injectNBT(ItemStack stack, NBTTagCompound nbt) {
		stack.setTagCompound(nbt);
	}
	
	/**
	 * Gets the {@link NBTTagCompound} in an {@link ItemStack}.
	 * (Tries to init it previously in case there isn't one present)
	 * @param stack {@link ItemStack} with {@link NBTTagCompound}
	 * @return {@link NBTTagCompound} of the {@link ItemStack}
	 */
	public static NBTTagCompound getNBT(ItemStack stack) {
		initNBT(stack);
		return stack.getTagCompound();
	}

	/**
	 * Verify the Existence of a value in the {@link NBTTagCompound}
	 * @param stack {@link ItemStack} with {@link NBTTagCompound}
	 * @param tag {@link String} Key
	 */
	public static boolean verifyExistance(ItemStack stack, String tag) {
		return stack != null && getNBT(stack).hasKey(tag);
	}

		/**
	 * Set an {@link Boolean} in the {@link NBTTagCompound} with the tag Key
	 * @param stack {@link ItemStack} with {@link NBTTagCompound}
	 * @param tag {@link String} Key
	 * @param b {@link Boolean} Value
	 */
	public static void setBoolean(ItemStack stack, String tag, boolean b) {
		getNBT(stack).setBoolean(tag, b);
	}

	/**
	 * Get the {@link Boolean} in the {@link NBTTagCompound} with the tag Key
	 * @param stack {@link ItemStack} with {@link NBTTagCompound}
	 * @param tag {@link String} Key
	 * @param defaultExpected Value Returned if don't exist
	 * @return {@link Boolean} Value
	 */
	public static boolean getBoolean(ItemStack stack, String tag, boolean defaultExpected) {
		return verifyExistance(stack, tag) ? getNBT(stack).getBoolean(tag) : defaultExpected;
	}



	/**
	 * Set an {@link Byte} in the {@link NBTTagCompound} with the tag Key
	 * @param stack {@link ItemStack} with {@link NBTTagCompound}
	 * @param tag {@link String} Key
	 * @param b {@link Byte} Value
	 */
	public static void setByte(ItemStack stack, String tag, byte b) {
		getNBT(stack).setByte(tag, b);
	}

	/**
	 * Get the {@link Byte} in the {@link NBTTagCompound} with the tag Key
	 * @param stack {@link ItemStack} with {@link NBTTagCompound}
	 * @param tag {@link String} Key
	 * @param defaultExpected Value Returned if don't exist
	 * @return {@link Byte} Value
	 */
	public static byte getByte(ItemStack stack, String tag, byte defaultExpected) {
		return verifyExistance(stack, tag) ? getNBT(stack).getByte(tag) : defaultExpected;
	}



	/**
	 * Set an {@link Short} in the {@link NBTTagCompound} with the tag Key
	 * @param stack {@link ItemStack} with {@link NBTTagCompound}
	 * @param tag {@link String} Key
	 * @param s {@link Short} Value
	 */
	public static void setShort(ItemStack stack, String tag, short s) {
		getNBT(stack).setShort(tag, s);
	}

	/**
	 * Get the {@link Short} in the {@link NBTTagCompound} with the tag Key
	 * @param stack {@link ItemStack} with {@link NBTTagCompound}
	 * @param tag {@link String} Key
	 * @param defaultExpected The Value Returned if don't exist
	 * @return {@link Short} Value
	 */
	public static short getShort(ItemStack stack, String tag, short defaultExpected) {
		return verifyExistance(stack, tag) ? getNBT(stack).getShort(tag) : defaultExpected;
	}



	/**
	 * Set an {@link Integer} in the {@link NBTTagCompound} with the tag Key
	 * @param stack {@link ItemStack} with {@link NBTTagCompound}
	 * @param tag {@link String} Key
	 * @param i {@link Integer} Value
	 */
	public static void setInt(ItemStack stack, String tag, int i) {
		getNBT(stack).setInteger(tag, i);
	}

	/**
	 * Get the {@link Integer} in the {@link NBTTagCompound} with the tag Key
	 * @param stack {@link ItemStack} with {@link NBTTagCompound}
	 * @param tag {@link String} Key
	 * @param defaultExpected The Value Returned if don't exist
	 * @return {@link Integer} Value
	 */
	public static int getInt(ItemStack stack, String tag, int defaultExpected) {
		return verifyExistance(stack, tag) ? getNBT(stack).getInteger(tag) : defaultExpected;
	}



	/**
	 * Set an {@link Long} in the {@link NBTTagCompound} with the tag Key
	 * @param stack {@link ItemStack} with {@link NBTTagCompound}
	 * @param tag {@link String} Key
	 * @param l {@link Long} Value
	 */
	public static void setLong(ItemStack stack, String tag, long l) {
		getNBT(stack).setLong(tag, l);
	}

	/**
	 * Get the {@link Long} in the {@link NBTTagCompound} with the tag Key
	 * @param stack {@link ItemStack} with {@link NBTTagCompound}
	 * @param tag {@link String} Key
	 * @param defaultExpected The Value Returned if don't exist
	 * @return {@link Long} Value
	 */
	public static long getLong(ItemStack stack, String tag, long defaultExpected) {
		return verifyExistance(stack, tag) ? getNBT(stack).getLong(tag) : defaultExpected;
	}



	/**
	 * Set an {@link Float} in the {@link NBTTagCompound} with the tag Key
	 * @param stack {@link ItemStack} with {@link NBTTagCompound}
	 * @param tag {@link String} Key
	 * @param f {@link Float} Value
	 */
	public static void setFloat(ItemStack stack, String tag, float f) {
		getNBT(stack).setFloat(tag, f);
	}

	/**
	 * Get the {@link Float} in the {@link NBTTagCompound} with the tag Key
	 * @param stack {@link ItemStack} with {@link NBTTagCompound}
	 * @param tag {@link String} Key
	 * @param defaultExpected The Value Returned if don't exist
	 * @return {@link Float} Value
	 */
	public static float getFloat(ItemStack stack, String tag, float defaultExpected) {
		return verifyExistance(stack, tag) ? getNBT(stack).getFloat(tag) : defaultExpected;
	}



	/**
	 * Set an {@link Double} in the {@link NBTTagCompound} with the tag Key
	 * @param stack {@link ItemStack} with {@link NBTTagCompound}
	 * @param tag {@link String} Key
	 * @param d {@link Double} Value
	 */
	public static void setDouble(ItemStack stack, String tag, double d) {
		getNBT(stack).setDouble(tag, d);
	}

	/**
	 * Get the {@link Double} in the {@link NBTTagCompound} with the tag Key
	 * @param stack {@link ItemStack} with {@link NBTTagCompound}
	 * @param tag {@link String} Key
	 * @param defaultExpected The Value Returned if don't exist
	 * @return {@link Double} Value
	 */
	public static double getDouble(ItemStack stack, String tag, double defaultExpected) {
		return verifyExistance(stack, tag) ? getNBT(stack).getDouble(tag) : defaultExpected;
	}



	/**
	 * Set an {@link NBTTagCompound} in the {@link NBTTagCompound} with the tag Key
	 * @param stack {@link ItemStack} with {@link NBTTagCompound}
	 * @param tag {@link String} Key
	 * @param cmp The {@link NBTTagCompound}
	 */
	public static void setCompound(ItemStack stack, String tag, NBTTagCompound cmp) {
		if(!tag.equalsIgnoreCase("ench")) // not override the enchantments
			getNBT(stack).setTag(tag, cmp);
	}

	/**
	 * Get the {@link NBTTagCompound} in the {@link NBTTagCompound} with the tag Key
	 * @param stack {@link ItemStack} with {@link NBTTagCompound}
	 * @param tag {@link String} Key
	 * @param nullifyOnFail the Value If true it'll return null if it doesn't find any compounds, otherwise it'll return a new one.
	 * @return The {@link NBTTagCompound}
	 */
	public static NBTTagCompound getCompound(ItemStack stack, String tag, boolean nullifyOnFail) {
		return verifyExistance(stack, tag) ? getNBT(stack).getCompoundTag(tag) : nullifyOnFail ? null : new NBTTagCompound();
	}



	/**
	 * Set an {@link String} in the {@link NBTTagCompound} with the tag Key
	 * @param stack {@link ItemStack} with {@link NBTTagCompound}
	 * @param tag {@link String} Key
	 * @param s {@link String} Value
	 */
	public static void setString(ItemStack stack, String tag, String s) {
		getNBT(stack).setString(tag, s);
	}

	/**
	 * Get the {@link String} in the {@link NBTTagCompound} with the tag Key
	 * @param stack {@link ItemStack} with {@link NBTTagCompound}
	 * @param tag {@link String} Key
	 * @param defaultExpected The Value Returned if don't exist
	 * @return {@link String} Value
	 */
	public static String getString(ItemStack stack, String tag, String defaultExpected) {
		return verifyExistance(stack, tag) ? getNBT(stack).getString(tag) : defaultExpected;
	}



	/**
	 * Set an {@link NBTTagList} in the {@link NBTTagCompound} with the tag Key
	 * @param stack {@link ItemStack} with {@link NBTTagCompound}
	 * @param tag {@link String} Key
	 * @param list The {@link NBTTagList}
	 */
	public static void setList(ItemStack stack, String tag, NBTTagList list) {
		getNBT(stack).setTag(tag, list);
	}

	/**
	 * Get the {@link NBTTagList} in the {@link NBTTagCompound} with the tag Key
	 * @param stack {@link ItemStack} with {@link NBTTagCompound}
	 * @param tag {@link String} Key
	 * @param objtype
	 * @param nullifyOnFail the Value If true it'll return null if it doesn't find any compounds, otherwise it'll return a new one.
	 * @return The {@link NBTTagList}
	 */
	public static NBTTagList getList(ItemStack stack, String tag, int objtype, boolean nullifyOnFail) {
		return verifyExistance(stack, tag) ? getNBT(stack).getTagList(tag, objtype) : nullifyOnFail ? null : new NBTTagList();
	}
}

