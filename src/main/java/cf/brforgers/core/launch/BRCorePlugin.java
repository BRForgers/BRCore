package cf.brforgers.core.launch;

import net.minecraftforge.fml.relauncher.FMLInjectionData;
import net.minecraftforge.fml.relauncher.IFMLCallHook;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

//import net.minecraftforge.fml.relauncher.CoreModManager;
//
//import net.minecraftforge.fml.common.versioning.DefaultArtifactVersion;
//import net.minecraftforge.fml.common.versioning.VersionParser;
//import net.minecraftforge.fml.relauncher.FMLInjectionData;
//import net.minecraftforge.fml.relauncher.IFMLCallHook;
//import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
//import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

@IFMLLoadingPlugin.MCVersion("1.10.2")
@TransformerExclusions(value = {"cf.brforgers.core.launch"})
public class BRCorePlugin implements IFMLLoadingPlugin, IFMLCallHook
{
    public static final Map<String, Object> injectedData = new HashMap<String, Object>();
    public static final Logger logger = LogManager.getLogger("BRCorePlugin");
    public static BRCorePlugin fmlPlugin, callHook;
    private static File minecraftDir;
    private static String currentMcVersion;
    public final boolean debugFlag;

    public BRCorePlugin() {
        if (minecraftDir != null) {
            debugFlag = fmlPlugin.debugFlag;
            callHook = this;
            return;//get called twice, once for IFMLCallHook
        }

        fmlPlugin = this;

        minecraftDir = (File) FMLInjectionData.data()[6];
        currentMcVersion = (String) FMLInjectionData.data()[4];
        DepLoader.load();

        debugFlag = new File(minecraftDir, ".debug").exists();
        logger.info("Debug Flag: " + debugFlag + " (" + (debugFlag ? "Disable deleting" : "Enable creating") + " file \".debug\" in Minecraft Directory)");
    }

    public static boolean getDebugFlag() {
        return fmlPlugin.debugFlag;
    }

    public static File getMinecraftDir() {
        return minecraftDir;
    }

    public static String getCurrentMcVersion() {
        return currentMcVersion;
    }

	@Override
	public Void call() throws Exception {
        if (!getDebugFlag()) return null;
        logger.info("Dumping InjectionData:");
        for (Map.Entry<String, Object> entry : injectedData.entrySet()) {
            logger.info(entry);
        }
        return null;
	}

	@Override
	public String[] getASMTransformerClass() {
		return null;
	}

	@Override
	public String getModContainerClass() {
		return "cf.brforgers.core.BRCore";
	}

    @Override
    public String getSetupClass() {
        return getClass().getName();
    }

	@Override
	public void injectData(Map<String, Object> data) {
        injectedData.putAll(data);
        injectedData.put("debugFlag", debugFlag);
    }

	@Override
	public String getAccessTransformerClass() {
		return null;
	}
}