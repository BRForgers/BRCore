package cf.brforgers.core.lib;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IEventArmor {
    void onArmorWorn(World world, EntityPlayer player, ItemStack stack);

    void onArmorUnworn(World world, EntityPlayer player, ItemStack stack);
}