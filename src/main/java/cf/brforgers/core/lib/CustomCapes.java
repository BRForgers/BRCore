package cf.brforgers.core.lib;

import com.google.gson.Gson;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

//import net.minecraft.client.network.NetworkPlayerInfo; //1.8+

/**
 * You'd never thought CustomCapes fits in a single class
 * @author Hyghlander
 *
 */
public class CustomCapes {
	public static Logger log = LogManager.getLogger("CustomCapes");
	private static boolean preInit = false;
	private static Map<String,User> users = new ConcurrentHashMap<String,User>();
	private static Map<String,String> cache = new ConcurrentHashMap<String,String>();
	private static Map<String,CustomCape> capeBuffer = new ConcurrentHashMap<String,CustomCape>();
	
	public static void preInit()
	{
		if (!preInit)
		{
			if (FMLCommonHandler.instance().getEffectiveSide().isClient()) MinecraftForge.EVENT_BUS.register(new RenderEventHandler());
			preInit = true;
		}
	}
	
	public static User get(String username)
	{
		return users.get(username);
	}

	public static void add(User user, boolean doOverride)
	{
		preInit();
		if (!users.containsKey(user.username) || doOverride)
		{
			users.put(user.username, user);
			cache.put(user.username, ((user.cape != null && user.cape.url != null) ? user.cape.url.toString() : "null"));
		}
	}

	public static boolean parse(String jsonString, boolean doOverrideUsers, boolean doOverrideCapes)
	{
		try{
			preInit();
			Map<String, Object> entries = new Gson().fromJson(jsonString, Map.class);
			if (entries != null) {
				for (Map.Entry<String, Object> entry : entries.entrySet()) {
					final String nodeName = entry.getKey();
					final Object obj = entry.getValue();
					if (obj instanceof String) {
						String capeUrl = (String) obj;
						User userInstance = new User(nodeName);
						CustomCape cape = null;
						if (!doOverrideCapes && capeBuffer.containsKey(capeUrl))
						{
							cape = capeBuffer.get(capeUrl);
						} else {
							URL url = Utils.newURL(capeUrl);
							cape = new CustomCape(url);
							capeBuffer.put(capeUrl, cape);
						}

						userInstance.cape = cape;

						add(userInstance, doOverrideUsers);
					}
				}
				return true;
        	}
		} catch(Exception e) {
			log.error("Error Parsing \""+jsonString+"\"", e);
		}
		return false;
	}

	public static boolean parse(Map<String,String> users, boolean doOverrideUsers, boolean doOverrideCapes)
	{
		preInit();

		if (users != null) {
			for (Iterator iterator = users.entrySet().iterator(); iterator.hasNext();) {
				Entry<String, String> entry = (Entry<String, String>) iterator.next();
				String userName = entry.getKey();
	            String capeUrl = entry.getValue();
	        	User userInstance = new User(userName);

				CustomCape cape = null;
	        	if (capeBuffer.containsKey(capeUrl) && !doOverrideCapes)
	        	{
	        		cape = capeBuffer.get(cape);
	        	} else {
	        		cape = new CustomCape(Utils.newURL(capeUrl));
	        		capeBuffer.put(capeUrl, cape);
	        	}

				add(userInstance, doOverrideUsers);
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Clear the cache of each parse() called
	 */
	public static void clearCache()
	{
		cache = new ConcurrentHashMap<String,String>();
	}
	
	/**
	 * Clear the HashMaps of Users and Capes
	 * <br> (It does NOT remove the capes, only reset the Maps)
	 */
	public static void clearCapes()
	{
		users = new ConcurrentHashMap<String,User>();
		capeBuffer = new ConcurrentHashMap<String,CustomCape>();
	}
	
	public static void reparse(boolean clearCacheAfter, boolean doOverrideUsers, boolean doOverrideCapes)
	{
		//Get old
		Map<String,String> oldCache = cache;

		if (!clearCacheAfter) clearCache();

		parse(oldCache, doOverrideUsers, doOverrideCapes);

		if (clearCacheAfter) clearCache();
	}
	
	/**
	 * Create a Map that Links the User and the Cape URL
	 * @return
	 */
	public static Map<String,String> generateStringMap()
	{
		Map<String,String> map = new ConcurrentHashMap<String,String>();

		for (Iterator iterator = users.entrySet().iterator(); iterator.hasNext();) {
			Entry<String, User> entry = (Entry<String, User>) iterator.next();
			if (entry.getValue() != null)
			{
				User user = entry.getValue();
				if(user.username != null && user.cape != null)
				{
					CustomCape cape = user.cape;
					if(cape.url != null)
					{
						map.put(user.username, cape.url.toString());
					}
				}
			}
		}

		return map;
	}

	public static class Helper {
		public static int roundToPowerOf2(int v) {
			v--;
			v |= v >> 1;
			v |= v >> 2;
			v |= v >> 4;
			v |= v >> 8;
			return v++;
		}
	}

	public static class RenderEventHandler {

		@SubscribeEvent
		public void renderPlayer(RenderPlayerEvent.Pre event) {

			AbstractClientPlayer player = (AbstractClientPlayer) event.entityPlayer;//getEntityPlayer();

			User user = CustomCapes.get(player.getCommandSenderName());//getDisplayNameString());
			if (user == null) return;

			CustomCape cape = user.cape;
			if (cape == null) return;

			boolean flag = cape.isTextureLoaded(player);
			if (!flag) {
				cape.loadTexture(player);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public static class HDImageBuffer implements IImageBuffer {
		@Override
		public BufferedImage parseUserSkin(final BufferedImage texture) {
			if (texture == null)
				return null;
			int imageWidth = texture.getWidth(null) <= 32 ? 32 : Math.min(512, Helper.roundToPowerOf2(texture.getWidth(null)));
			int imageHeight = texture.getHeight(null) <= 16 ? 16 : Math.min(256, Helper.roundToPowerOf2(texture.getHeight(null)));

			BufferedImage capeImage = new BufferedImage(imageWidth, imageHeight, 2);

			Graphics graphics = capeImage.getGraphics();
			graphics.drawImage(texture, 0, 0, null);
			graphics.dispose();

			return capeImage;
		}

		//@Override
		public void skinAvailable() {
		}

		@Override
		public void func_152634_a() {
		}
	}

	public static class User {
		public CustomCape cape = null;
		public String username = null;

		public User(String username) {
			this.username = username;
		}
	}

	public static class CustomCape {
		protected URL url;
		protected String name;
		protected ITextureObject texture;
		protected ResourceLocation location;

		public CustomCape(URL url) {
			//this.setName("cape-" + url.hashCode());
			this.setURL(url);
		}

		public String getName() {
			return name;
		}

		private void setName(String name) {
			this.name = name;
			this.location = new ResourceLocation("CustomCapes/" + name);
		}

		public ITextureObject getTexture() {
			return this.texture;
		}

		public ResourceLocation getLocation() {
			return this.location;
		}

		public void loadTexture(AbstractClientPlayer player) {
			if (FMLCommonHandler.instance().getEffectiveSide().isServer()) return;
			ResourceLocation location = this.getLocation();

			player.func_152121_a(Type.CAPE, location);
			//player.playerInfo.playerTextures.put(Type.CAPE, location);
			//player.playerInfo.playerTextures.put(Type.ELYTRA, location);

			Minecraft.getMinecraft().renderEngine.loadTexture(location, this.getTexture());
		}

		public boolean isTextureLoaded(AbstractClientPlayer player) {
			return player.getLocationCape() == getLocation();
		}

		public URL getURL() {
			return this.url;
		}

		public void setURL(URL url) {
			this.url = url;
			setName("cape-" + ((url != null) ? url.toString().hashCode() : "null"));

			if (FMLCommonHandler.instance().getEffectiveSide().isClient())
				if (url == null)
					this.texture = null;
				else
					this.texture = new ThreadDownloadImageData(null, url.toString(), null, new HDImageBuffer());
		}
	}
}