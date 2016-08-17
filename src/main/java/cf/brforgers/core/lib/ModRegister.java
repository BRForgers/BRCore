package cf.brforgers.core.lib;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModRegister {
    public final GeneralRegistry REGISTRY = GeneralRegistry.getPersonal();
    private final ModDefinition MOD;

    private ModRegister(ModDefinition mod) {
        MOD = mod;
    }

    public static ModRegister fromMod(ModDefinition mod) {
        return new ModRegister(mod);
    }

    public <K extends IForgeRegistryEntry<?>> void register(K object) {
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