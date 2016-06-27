package cf.brforgers.core.lib;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class FastFactory {
	public static class FactoryBlock extends Block {protected FactoryBlock(Material mat) {super(mat);}}
	public static class FactoryItem extends Item {protected FactoryItem(){}}
	
	public CreativeTabs defaultTab;
	public String texturePrefix;
	public Material defaultMaterial;

	public Block newBlock(String name)
	{
		return new FactoryBlock(defaultMaterial).setBlockName(name).setBlockTextureName(texturePrefix + name).setCreativeTab(defaultTab);
	}
	
	public Block processBlock(Block block, String name)
	{
		return block.setBlockName(name).setBlockTextureName(texturePrefix + name).setCreativeTab(defaultTab);
	}
	
	public Item newItem(String name)
	{
		return new FactoryItem().setUnlocalizedName(name).setTextureName(texturePrefix + name).setCreativeTab(defaultTab);
	}
	
	public Item processItem(Item item, String name)
	{
		return item.setUnlocalizedName(name).setTextureName(texturePrefix + name).setCreativeTab(defaultTab);
	}
	
	public static FastFactory newFactory(CreativeTabs defaultTab, String texturePrefix, Material defaultMaterial) {
		FastFactory f = new FastFactory();
		f.defaultTab = defaultTab;
		f.texturePrefix = texturePrefix;
		f.defaultMaterial = defaultMaterial;
		return f;
	}
	private FastFactory() {}
}
