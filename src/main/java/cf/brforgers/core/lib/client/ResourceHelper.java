package cf.brforgers.core.lib.client;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.Map;


@SideOnly(Side.CLIENT)
public class ResourceHelper {
    private static Map<String, ResourceLocation> cachedResources = new HashMap<String, ResourceLocation>();
    
	public static void bindTexture(ResourceLocation texture) {
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
    }

    public static ResourceLocation getResource(String rs) {
        if (!cachedResources.containsKey(rs))
            cachedResources.put(rs, new ResourceLocation(rs));
        return cachedResources.get(rs);
    }

    public static void bindResource(String rs) {
        bindTexture(getResource(rs));
    }


}