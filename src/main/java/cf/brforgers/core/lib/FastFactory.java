package cf.brforgers.core.lib;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class FastFactory {
	public CreativeTabs defaultTab;
	public String texturePrefix;
	public Material defaultMaterial;

	private FastFactory() {
	}

	public static FastFactory newFactory(CreativeTabs defaultTab, String modId, Material defaultMaterial) {
		FastFactory f = new FastFactory();
		f.defaultTab = defaultTab;
		f.texturePrefix = modId + ":";
		f.defaultMaterial = defaultMaterial;
		return f;
	}

	public Block newBlock(String name)
	{
		return new FactoryBlock(defaultMaterial).setRegistryName(texturePrefix + name).setUnlocalizedName(name).setCreativeTab(defaultTab);
	}

	public Block newBlock(String name, Material material) {
		return new FactoryBlock(material).setRegistryName(texturePrefix + name).setUnlocalizedName(name).setCreativeTab(defaultTab);
	}
	
	public <BlockType extends Block> BlockType processBlock(BlockType block, String name)
	{
		return (BlockType) block.setUnlocalizedName(name).setRegistryName(texturePrefix + name).setCreativeTab(defaultTab);
	}
	
	public Item newItem(String name)
	{
		return new FactoryItem().setUnlocalizedName(name).setCreativeTab(defaultTab);
	}
	
	public <ItemType extends Item> ItemType processItem(ItemType item, String name)
	{
		return (ItemType) item.setUnlocalizedName(name).setRegistryName(texturePrefix + name).setCreativeTab(defaultTab);
	}

	public static class FactoryBlock extends Block {
		protected FactoryBlock(Material mat) {
			super(mat);
		}
	}

	public static class FactoryItem extends Item {
		protected FactoryItem() {
		}
	}
}
