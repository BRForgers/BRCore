package cf.brforgers.core.internal;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class InternalHelper {
    /**
     * This will return the player armor, where [0]=> Boots and [3]=> Helmet
     *
     * @param player the Player
     * @return the Armor Array
     */
    public static ItemStack[] getCurrentArmor(EntityPlayer player) {
        return player.inventory.armorInventory;
    }

    /**
     * Check if the Entity is the EnderDragon (Lot's of Checking btw)
     *
     * @param entity the Entity that might be the Dragon
     * @return if is the Dragon or not
     */
    public static boolean isTheDragon(Entity entity) {
        return ((entity instanceof EntityDragon) || (
                !EntityList.getEntityString(entity).isEmpty() && (
                        EntityList.getEntityString(entity).equals("HardcoreEnderExpansion.Dragon") ||
                                EntityList.getEntityString(entity).equals("DraconicEvolution.EnderDragon") ||
                                EntityList.getEntityString(entity).equals("DraconicEvolution.ChaosGuardian")
                )
        )
        );
    }
}
