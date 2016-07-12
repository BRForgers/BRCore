package cf.brforgers.core.lib;

import java.util.HashMap;
import java.util.Map;

public class ModRegister {
    private static Map<String, String> fancyNames = new HashMap<String, String>();

    /**
     * Mainly used by CLiMI, you can register a Fancy Colored Name to be used by CLiMI and appear the ModName as Colored.
     *
     * @param modid
     * @param fancyName
     */
    public static void AddFancyModname(String modid, String fancyName) {
        fancyNames.put(modid, fancyName);
    }
}
