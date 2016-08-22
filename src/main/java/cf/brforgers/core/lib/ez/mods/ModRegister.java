package cf.brforgers.core.lib.ez.mods;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModRegister {
    public final GeneralRegistry REGISTRY = GeneralRegistry.getPersonal();
    public final GeneralList LISTS = GeneralList.getPersonal();
    public final ModDefinition MOD;
    private final GeneralList INTERNAL = GeneralList.getPersonal();

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

    public <T extends IForgeRegistryEntry<? super T>> void register(T object) {
        GameRegistry.register(object);
        INTERNAL.get(object.getRegistryType()).add(object);
    }

    public void registerItemBlock(Block block) {
        register(new ItemBlock(block).setRegistryName(block.getRegistryName()).setUnlocalizedName(block.getUnlocalizedName().substring(5)));
    }

    @SideOnly(Side.CLIENT)
    public void automagicallyRegisterRenderers() {
        for (Item item : INTERNAL.get(Item.class)) registerRenderer(item);
        for (Block block : INTERNAL.get(Block.class)) registerRenderer(block);
    }

    @SideOnly(Side.CLIENT)
    public void registerRenderer(Item item) {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(MOD.getLocation(item.getUnlocalizedName().substring(5)), "inventory"));
    }

    @SideOnly(Side.CLIENT)
    public void registerRenderer(Block block) {
        if (Item.getItemFromBlock(block) != null)
        registerRenderer(Item.getItemFromBlock(block));
    }
}