//package cf.brforgers.core.ee;
//
//import cf.brforgers.core.BRCore;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.resources.I18n;
//import net.minecraft.client.resources.Locale;
//import net.minecraft.init.Blocks;
//import net.minecraft.util.text.translation.LanguageMap;
//import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
//import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
//import net.minecraftforge.fml.common.gameevent.TickEvent;
//import net.minecraftforge.fml.relauncher.Side;
//import net.minecraftforge.fml.relauncher.SideOnly;
//
//import java.util.Map;
//
//public class EEEventHandler {
//    public static boolean isAppliable(String name) {
//        if (name == null || name.isEmpty()) return false;
//        boolean appliable = true;
//        appliable |= name.equalsIgnoreCase("PotterCraft_");
//        appliable |= name.equalsIgnoreCase("Aruka_Alter");
//        appliable |= name.equalsIgnoreCase("LorenzoDCC");
//        appliable |= name.equalsIgnoreCase("AdrianTodt");
//        return appliable;
//    }
//
//    @SideOnly(Side.CLIENT)
//    @SubscribeEvent
//    public void easterEggsClient(TickEvent.ClientTickEvent event) {
//        if (Blocks.CACTUS.getLocalizedName().equalsIgnoreCase("Kektus")) return;
//        if (Minecraft.getMinecraft().thePlayer != null && !isAppliable(Minecraft.getMinecraft().thePlayer.getDisplayNameString()))
//            return;
//
//        BRCore.logger.info("KEKing Translations...");
//
//        try {
//            Locale loc = (Locale) ObfuscationReflectionHelper.getPrivateValue(I18n.class, null, "i18nLocale");
//            Map<String, String> langMap = (Map<String, String>) ObfuscationReflectionHelper.getPrivateValue(Locale.class, loc, "properties");
//            for (Map.Entry<String, String> entry : langMap.entrySet()) {
//                entry.setValue(entry.getValue().replaceAll("Cact", "Kekt"));
//            }
//        } catch (Exception ignored) {
//        }
//
//        try {
//            LanguageMap map = (LanguageMap) ObfuscationReflectionHelper.getPrivateValue(LanguageMap.class, null, "instance");
//            Map<String, String> langMap = (Map<String, String>) ObfuscationReflectionHelper.getPrivateValue(LanguageMap.class, map, "languageList");
//            for (Map.Entry<String, String> entry : langMap.entrySet()) {
//                entry.setValue(entry.getValue().replaceAll("Cact", "Kekt"));
//            }
//        } catch (Exception ignored) {
//        }
//    }
//}
