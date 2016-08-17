package cf.brforgers.core.lib.ez.mods;

public class ModDefinition {
    public final String MODID, MODNAME, FANCYNAME; //Need to be supplied
    public final String PATH; //Calculated
    public ModDefinition(String modid, String modname, String fancyname) {
        this.MODID = modid;
        this.MODNAME = modname;
        this.FANCYNAME = fancyname;
        this.PATH = modid + ":";

        GeneralRegistry.getGlobal(ModDefinition.class).putObject(MODID, this);
    }

    public ModDefinition(String modid, String modname) {
        this(modid, modname, modname);
    }

    public ModDefinition(String modid) {
        this(modid, modid);
    }

    public static ModDefinition get(String modid) {
        return GeneralRegistry.getGlobal(ModDefinition.class).getObject(modid);
    }

    public String getLocation(String relPath) {
        return PATH + relPath;
    }
}
