package cf.brforgers.core;

import cf.brforgers.core.lib.ModRegister;
import cf.brforgers.core.lib.Utils;
import cf.brforgers.core.lib.reflect.ReflectionHelper;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.MetadataCollection;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//import cf.brforgers.core.lib.client.Armor3DRenderer;

public class BRCore extends DummyModContainer
{
	public static Logger logger = LogManager.getLogger("BRCore");
	private static BRCore instance = null;

	public BRCore() {
		super(MetadataCollection.from(MetadataCollection.class.getResourceAsStream("/brcore.info"), "BRCore").getMetadataForId("BRCore", ReflectionHelper.asMap(Lib.class, null)));
		ModRegister.AddFancyModname(this.getMetadata().modId, Lib.FANCYNAME);
		logger.info("CoreMod registered");
		instance = this;
	}

	@Subscribe
	public static void preInit(FMLPreInitializationEvent e)
	{
		Configuration config = new Configuration(e.getSuggestedConfigurationFile());
		config.load();

		if (Utils.isClient())
		{
			//if (config.getBoolean("vanilla3DArmors", "ClientModules", false, "(Experimental) Vanilla Armors will be rendered 3D in Inventory"))
			//Armor3DRenderer.registerVanillaArmors();
		}

		if(config.hasChanged())
			config.save();

		//MinecraftForge.EVENT_BUS.register(new EEEventHandler());
	}

	@Override
	public boolean registerBus(EventBus bus, LoadController controller) {
		bus.register(this);
		return true;
	}
}
