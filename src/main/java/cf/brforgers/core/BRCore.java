package cf.brforgers.core;

import cf.brforgers.core.lib.ModRegister;
import cf.brforgers.core.lib.Utils;
import cf.brforgers.core.lib.client.Armor3DRenderer;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.MetadataCollection;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BRCore extends DummyModContainer
{
	public static Logger logger = LogManager.getLogger("BRCore");
	private static BRCore instance = null;

	public BRCore() {
		super(MetadataCollection.from(MetadataCollection.class.getResourceAsStream("/brcore.info"), "BRCore").getMetadataForId("BRCore", Utils.asMap(Lib.class, null)));
		ModRegister.AddFancyModname(this.getMetadata().modId, Lib.FANCYNAME);
		logger.info("CoreMod registered");
		instance = this;
	}

	@Subscribe
	public static void preInit(FMLPreInitializationEvent e)
	{
		Configuration config = Utils.getConfig(e);
		config.load();

		if (Utils.isClient())
		{
			if (config.getBoolean("vanilla3DArmors", "ClientModules", false, "(Experimental) Vanilla Armors will be rendered 3D in Inventory"))
				Armor3DRenderer.RegisterVanillaArmors();
		}

		if(config.hasChanged())
			config.save();
	}

	@Override
	public boolean registerBus(EventBus bus, LoadController controller) {
		bus.register(this);
		return true;
	}
}
