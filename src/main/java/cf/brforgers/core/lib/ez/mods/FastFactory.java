package cf.brforgers.core.lib.ez.mods;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

@SuppressWarnings("unchecked")
public class FastFactory {
	public CreativeTabs defaultTab;
	public String texturePrefix;
	public Material defaultMaterial;

	private FastFactory() {
	}

    public static FastFactory newFactory(ModDefinition mod, CreativeTabs defaultTab, Material defaultMaterial) {
        FastFactory f = new FastFactory();
		f.defaultTab = defaultTab;
        f.texturePrefix = mod.PATH;
        f.defaultMaterial = defaultMaterial;
		return f;
	}

	public Block newBlock(String name)
	{
		return new FactoryBlock(defaultMaterial).setUnlocalizedName(name).setRegistryName(texturePrefix + name).setCreativeTab(defaultTab);
	}

	public Block newBlock(String name, Material material) {
		return new FactoryBlock(material).setUnlocalizedName(name).setRegistryName(texturePrefix + name).setCreativeTab(defaultTab);
	}
	
	public <BlockType extends Block> BlockType processBlock(BlockType block, String name)
	{
		return (BlockType) block.setUnlocalizedName(name).setRegistryName(texturePrefix + name).setCreativeTab(defaultTab);
	}
	
	public Item newItem(String name)
	{
		return new FactoryItem().setUnlocalizedName(name).setRegistryName(texturePrefix + name).setCreativeTab(defaultTab);
	}
	
	public <ItemType extends Item> ItemType processItem(ItemType item, String name)
	{
		return (ItemType) item.setUnlocalizedName(name).setRegistryName(texturePrefix + name).setCreativeTab(defaultTab);
	}

    private static class FactoryBlock extends Block {
        FactoryBlock(Material mat) {
            super(mat);
		}
	}

    private static class FactoryItem extends Item {
        FactoryItem() {
        }
	}
}
