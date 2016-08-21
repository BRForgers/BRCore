package cf.brforgers.core;

import cf.brforgers.core.internal.InternalEventHandler;
import cf.brforgers.core.lib.ez.mods.GeneralRegistry;
import cf.brforgers.core.lib.ez.mods.ModDefinition;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.MetadataCollection;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

import static net.minecraft.util.text.TextFormatting.*;

//import cf.brforgers.core.lib.client.Armor3DRenderer;

public class BRCore extends DummyModContainer
{
    public static final String FANCYNAME = DARK_GREEN.toString() + BOLD.toString() + "BR" + GOLD.toString() + BOLD.toString() + "Core";

	public static Logger logger = LogManager.getLogger("BRCore");
	private static BRCore instance = null;

	public BRCore() {
        super(
                MetadataCollection.from(
                        MetadataCollection.class.getResourceAsStream("/brcore.info"), "BRCore"
                ).getMetadataForId(
                        "BRCore",
                        new HashMap<String, Object>() {{
                            put("name", "brcore");
                            put("version", "1.0");
                        }}
                )
        );

		logger.info("CoreMod registered");

		GeneralRegistry.getGlobal(ModDefinition.class).put("brcore", new ModDefinition(getModId(), getName(), FANCYNAME));

		instance = this;
	}

	@Subscribe
	public static void preInit(FMLPreInitializationEvent e)
	{
		MinecraftForge.EVENT_BUS.register(new InternalEventHandler());
	}

	@Override
	public boolean registerBus(EventBus bus, LoadController controller) {
		bus.register(this);
		return true;
	}
}
