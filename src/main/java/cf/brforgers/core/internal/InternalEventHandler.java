package cf.brforgers.core.internal;

import cf.brforgers.core.lib.ez.hooks.DropHooks;
import cf.brforgers.core.lib.ez.hooks.IEventArmor;
import cf.brforgers.core.lib.ez.hooks.KeyBinder;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static cf.brforgers.core.internal.InternalHelper.getCurrentArmor;
import static cf.brforgers.core.internal.InternalHelper.isTheDragon;

public class InternalEventHandler {
    @SuppressWarnings("unchecked")
    private Map<UUID, ItemStack>[] armorMaps = new Map[]{new HashMap<UUID, ItemStack>(), new HashMap<UUID, ItemStack>(), new HashMap<UUID, ItemStack>(), new HashMap<UUID, ItemStack>()};

    @SubscribeEvent
    public void event_DropHooks_dragonDrops(LivingDropsEvent event) {
        if (!event.getEntity().worldObj.isRemote && isTheDragon(event.getEntity())) {
            for (DropHooks.WeightedItemStack stack : DropHooks.getDragonRaw()) {
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
    public void hook_IEventArmor_playerTick(TickEvent.PlayerTickEvent event) {
        if (event.player.worldObj.isRemote) return;
        EntityPlayer player = event.player;
        UUID uuid = EntityPlayer.getUUID(player.getGameProfile());
        ItemStack[] cur = getCurrentArmor(player), reg = getRegisteredArmor(player);

        for (int i = 0; i < 4; i++) {
            if (cur[i] != reg[i]) {
                if (reg[i] != null && reg[i].getItem() != null && reg[i].getItem() instanceof IEventArmor) {
                    ((IEventArmor) reg[i].getItem()).onArmorUnworn(player.worldObj, player, reg[i]);
                }
                if (cur[i] != null && cur[i].getItem() != null && cur[i].getItem() instanceof IEventArmor) {
                    ((IEventArmor) cur[i].getItem()).onArmorWorn(player.worldObj, player, cur[i]);
                }
                armorMaps[i].put(uuid, cur[i]);
            }
        }
    }

    @SubscribeEvent
    public void hook_IEventArmor_playerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.player.worldObj.isRemote) return;

        ItemStack[] armor = getCurrentArmor(event.player);
        UUID uuid = EntityPlayer.getUUID(event.player.getGameProfile());
        for (int i = 0; i < 4; i++) armorMaps[i].put(uuid, armor[i]);
    }


    @SubscribeEvent
    public void hook_DropHooks_playerDrops(LivingDeathEvent e) {
        if (e.getEntity() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) e.getEntity();
            List<DropHooks.WeightedItemStack> list = DropHooks.getRaw(player.getDisplayNameString());

            for (DropHooks.WeightedItemStack wstack : list) {
                if (player.worldObj.rand.nextInt(100) < wstack.weight) {
                    player.dropItem(wstack.stack, true, false);
                }
            }
        }
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        for (KeyBinder.KeyBind keyBind : KeyBinder.getKeyBinds()) {
            if (keyBind.mapping.isPressed() != keyBind.state) {
                keyBind.state = keyBind.mapping.isPressed();
                keyBind.run();
            }
        }
    }
}
