package cf.brforgers.core.lib.ez;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EasterEggManager {
    private static final Map<String, List<WeightedItemStack>> drops = new HashMap<String, List<WeightedItemStack>>();
    private static final List<WeightedItemStack> dragondrops = new ArrayList<WeightedItemStack>();

    public static List<WeightedItemStack> getRaw(String player) {
        List<WeightedItemStack> s = drops.get(player);

        if (s == null) {
            s = new ArrayList<WeightedItemStack>();
            drops.put(player, s);
        }

        return s;
    }

    public static List<WeightedItemStack> getDragonRaw() {
        return dragondrops;
    }

    public static void addDragonDropItemStack(ItemStack stack) {
        addDragonDropItemStackWithChance(stack, 100);
    }

    public static void addDragonDropItemStackWithChance(ItemStack stack, int chance) {
        getDragonRaw().add(new WeightedItemStack(stack, chance));
    }

    public static void addDropItemStack(String player, ItemStack stack) {
        addDropItemStackWithChance(player, stack, 100);
    }

    public static void addDropItemStackWithChance(String player, ItemStack stack, int chance) {
        getRaw(player).add(new WeightedItemStack(stack, chance));
    }

    public static class WeightedItemStack {
        public final ItemStack stack;
        public final int weight;

        public WeightedItemStack(ItemStack stack, int weight) {
            this.stack = stack;
            this.weight = weight;
        }
    }
}
