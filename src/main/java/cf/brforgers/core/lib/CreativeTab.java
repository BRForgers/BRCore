package cf.brforgers.core.lib;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * A non-Abstract Creative Tab
 * @author TheFreeHigh
 *
 */
public class CreativeTab extends CreativeTabs {
	public Item displayItem = null;
	public CreativeTab(String label) {
		super(label);
	}
	
	public CreativeTab(int arg0, String arg1, Item displayItem) {
		super(arg0, arg1);
	}
	
	public CreativeTab setIcon(Item displayItem) {
		this.displayItem = displayItem;
		return this;
	}
	
	public CreativeTab setIcon(Block displayItem) {
		this.displayItem = ItemHelper.toItem(displayItem);
		return this;
	}

	@Override
	public Item getTabIconItem() {
		return displayItem;
	}
}