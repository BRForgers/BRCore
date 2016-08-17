package cf.brforgers.core.lib.ez.mods;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModRegister {
    public final GeneralRegistry REGISTRY = GeneralRegistry.getPersonal();
    public final ModDefinition MOD;

    private ModRegister(ModDefinition mod) {
        MOD = mod;
    }

    public static ModRegister fromMod(ModDefinition mod) {
        return new ModRegister(mod);
    }

    public static ModRegister fromMod(String modid) {
        return fromMod(ModDefinition.get(modid));
    }

    public FastFactory getFactory(CreativeTabs tab, Material defaultMaterial) {
        return FastFactory.newFactory(MOD, tab, defaultMaterial);
    }

    public <T extends IForgeRegistryEntry<?>> void register(T object) {
        GameRegistry.register(object);
    }

    @SideOnly(Side.CLIENT)
    public void registerItemRenderer(Item item) {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(MOD.getLocation(item.getUnlocalizedName().substring(5)), "inventory"));
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockRenderer(Block block) {
        registerItemRenderer(Item.getItemFromBlock(block));
    }
}