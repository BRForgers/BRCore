package cf.brforgers.core.lib;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockFactory {
	public static class FactoryBlock extends Block {protected FactoryBlock(Material mat) {super(mat);}}
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
	
	public static BlockFactory newFactory(CreativeTabs defaultTab, String texturePrefix, Material defaultMaterial) {
		BlockFactory f = new BlockFactory();
		f.defaultTab = defaultTab;
		f.texturePrefix = texturePrefix;
		f.defaultMaterial = defaultMaterial;
		return f;
	}
	private BlockFactory() {}
}
