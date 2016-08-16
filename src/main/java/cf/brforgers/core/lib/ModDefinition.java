package cf.brforgers.core.lib;

public class ModDefinition {
    public final String modid, modname, fancyname;

    public ModDefinition(String modid, String modname, String fancyname) {
        this.modid = modid;
        this.modname = modname;
        this.fancyname = fancyname;
    }

    public ModDefinition(String modid, String modname) {
        this(modid, modname, modname);
    }

    public ModDefinition(String modid) {
        this(modid, modid);
    }
}
