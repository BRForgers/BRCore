//package cf.brforgers.core.lib.client;
//
//import net.minecraft.client.renderer.ItemRenderer;
//import net.minecraftforge.fml.relauncher.Side;
//import net.minecraftforge.fml.relauncher.SideOnly;
//import net.minecraft.client.model.ModelBiped;
//import net.minecraft.client.renderer.entity.RenderBiped;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemArmor;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.ResourceLocation;
//import net.minecraftforge.client.IItemRenderer;
//import net.minecraftforge.client.MinecraftForgeClient;
//import org.lwjgl.opengl.GL11;
//
//import static net.minecraft.init.Items.*;
//
//@SideOnly(Side.CLIENT)
//public class Armor3DRenderer extends ItemRenderer {
//	private static ModelBiped default1 = new ModelBiped(0.45f), default2 = new ModelBiped(0.9f);
//    private ItemArmor armor;
//
//    private Armor3DRenderer(ItemArmor armor) {
//        this.armor = armor;
//    }
//
//    public static void registerVanillaArmors() {
//        Item[] armors = new Item[]{
//				leather_boots, leather_chestplate, leather_helmet, leather_leggings,
//				golden_boots, golden_chestplate, golden_helmet, golden_leggings,
//				chainmail_boots, chainmail_chestplate, chainmail_helmet, chainmail_leggings,
//				iron_boots, iron_chestplate, iron_helmet, iron_leggings,
//				diamond_boots, diamond_chestplate, diamond_helmet, diamond_leggings
//		};
//
//		for (Item item : armors) {
//            register((ItemArmor) item);
//        }
//	}
//
//    public static void register(ItemArmor armor) {
//        MinecraftForgeClient.registerItemRenderer(armor, new Armor3DRenderer(armor));
//	}
//
//    @Override
//    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
//        return true;
//    }
//
//    @Override
//    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
//        return true;
//    }
//
//    @Override
//    public void renderItem(ItemRenderType type, ItemStack stack, Object... data) {
//        GL11.glPushMatrix();
//        ResourceHelper.bindTexture(getArmorResource(stack));//.bindResource(armor.getArmorTexture(stack, null, armor.armorType, null));
//
//        if (type == ItemRenderType.EQUIPPED_FIRST_PERSON || type == ItemRenderType.EQUIPPED) {
//            GL11.glTranslated(0.5, 0.5, 0.5);
//            GL11.glRotated(180, 0, 1, 0);
//        }
//        GL11.glTranslated(0, armor.armorType == 0 ? -0.25 : armor.armorType == 1 ? 0.42 : armor.armorType == 2 ? 1.05 : 1.5, 0);
//        GL11.glRotated(180, -1, 0, 1);
//        getArmorModel(stack).render(null, 0f, 0f, 0f, 0f, 0f, 0.0625f);
//
//        GL11.glPopMatrix();
//    }
//
//    private ResourceLocation getArmorResource(ItemStack stack) {
//    	return RenderBiped.getArmorResource(null, stack, armor.armorType, null);
//    }
//
//    private ModelBiped getArmorModel(ItemStack stack) {
//    	ModelBiped m = armor.getArmorModel(null, stack, armor.armorType);
//    	if (m!=null) return m;
//    	m = (armor.armorType == 2) ? default1 : default2;
//    	m.bipedHead.showModel = armor.armorType == 0;
//		m.bipedHeadwear.showModel = armor.armorType == 0;
//		m.bipedBody.showModel = armor.armorType == 1 || armor.armorType == 2;
//		m.bipedRightArm.showModel = armor.armorType == 1;
//		m.bipedLeftArm.showModel = armor.armorType == 1;
//		m.bipedRightLeg.showModel = armor.armorType == 2 || armor.armorType == 3;
//		m.bipedLeftLeg.showModel = armor.armorType == 2 || armor.armorType == 3;
//		return m;
//    }
//}