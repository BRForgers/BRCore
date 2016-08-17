package cf.brforgers.core.internal;

import cf.brforgers.core.lib.EasterEggManager;
import cf.brforgers.core.lib.IEventArmor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class InternalEventHandler {
    @SuppressWarnings("unchecked")
    private Map<UUID, ItemStack>[] armorMaps = new Map[]{new HashMap<UUID, ItemStack>(), new HashMap<UUID, ItemStack>(), new HashMap<UUID, ItemStack>(), new HashMap<UUID, ItemStack>()};

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

    /**
     * This will return the player armor, where [0]=> Boots and [3]=> Helmet
     *
     * @param player the Player
     * @return the Armor Array
     */
    public static ItemStack[] getCurrentArmor(EntityPlayer player) {
        return player.inventory.armorInventory;
    }

    @SubscribeEvent
    public void dragonDrops(LivingDropsEvent event) {
        if (!event.getEntity().worldObj.isRemote && isTheDragon(event.getEntity())) {
            for (EasterEggManager.WeightedItemStack stack : EasterEggManager.getDragonRaw()) {
                if (event.getEntity().worldObj.rand.nextInt(100) < stack.weight) {
                    int count = stack.stack.stackSize;

                    for (int i = 0; i < count; i++) {
                        float mm = 0.3F;
                        EntityItem item = new EntityItem(event.getEntity().worldObj, event.getEntity().posX - 2 + event.getEntity().worldObj.rand.nextInt(4), event.getEntity().posY - 2 + event.getEntity().worldObj.rand.nextInt(4), event.getEntity().posZ - 2 + event.getEntity().worldObj.rand.nextInt(4), new ItemStack(stack.stack.getItem()));
                        item.motionX = mm * ((((float) event.getEntity().worldObj.rand.nextInt(100)) / 100F) - 0.5F);
                        item.motionY = mm * ((((float) event.getEntity().worldObj.rand.nextInt(100)) / 100F) - 0.5F);
                        item.motionZ = mm * ((((float) event.getEntity().worldObj.rand.nextInt(100)) / 100F) - 0.5F);
                        event.getEntity().worldObj.spawnEntityInWorld(item);
                    }
                }
            }
        }
    }

    private ItemStack[] getRegisteredArmor(EntityPlayer player) {
        UUID uuid = EntityPlayer.getUUID(player.getGameProfile());
        ItemStack[] stacks = new ItemStack[4];
        for (int i = 0; i < 4; i++) stacks[i] = armorMaps[i].get(uuid);
        return stacks;
    }

    @SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent event) {
        if (event.player.worldObj.isRemote) return;
        EntityPlayer player = event.player;
        UUID uuid = EntityPlayer.getUUID(player.getGameProfile());
        ItemStack[] cur = getCurrentArmor(player), reg = getRegisteredArmor(player);

        for (int i = 0; i < 4; i++) {
            if (cur[i] != reg[i]) {
                if (reg[i].getItem() instanceof IEventArmor) {
                    ((IEventArmor) reg[i].getItem()).onArmorUnworn(player.worldObj, player, reg[i]);
                }
                if (cur[i].getItem() instanceof IEventArmor) {
                    ((IEventArmor) cur[i].getItem()).onArmorWorn(player.worldObj, player, cur[i]);
                }
                armorMaps[i].put(uuid, cur[i]);
            }
        }
    }

    @SubscribeEvent
    public void playerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.player.worldObj.isRemote) return;

        ItemStack[] armor = getCurrentArmor(event.player);
        UUID uuid = EntityPlayer.getUUID(event.player.getGameProfile());
        for (int i = 0; i < 4; i++) armorMaps[i].put(uuid, armor[i]);
    }


    @SubscribeEvent
    public void easterEggDrops(LivingDeathEvent e) {
        if (e.getEntity() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) e.getEntity();
            List<EasterEggManager.WeightedItemStack> list = EasterEggManager.getRaw(player.getDisplayNameString());

            for (EasterEggManager.WeightedItemStack wstack : list) {
                if (player.worldObj.rand.nextInt(100) < wstack.weight) {
                    player.dropItem(wstack.stack, true, false);
                }
            }
        }
    }
}
