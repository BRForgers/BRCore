package cf.brforgers.core.lib;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemFactory {
	public static class FactoryItem extends Item {protected FactoryItem(){}}
	public CreativeTabs defaultTab;
	public String texturePrefix;
	public Item newItem(String name)
	{
		return new FactoryItem().setUnlocalizedName(name).setTextureName(texturePrefix + name).setCreativeTab(defaultTab);
	}
	
	public Item processBlock(Item item, String name)
	{
		return item.setUnlocalizedName(name).setTextureName(texturePrefix + name).setCreativeTab(defaultTab);
	}
	
	public static ItemFactory newFactory(CreativeTabs defaultTab, String texturePrefix) {
		ItemFactory f = new ItemFactory();
		f.defaultTab = defaultTab;
		f.texturePrefix = texturePrefix;
		return f;
	}
	private ItemFactory() {}
}
