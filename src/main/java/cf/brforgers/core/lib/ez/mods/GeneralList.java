package cf.brforgers.core.lib.ez.mods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneralList {
    private static final GeneralList instance = new GeneralList();
    private Map<Class, List> internal = new HashMap<Class, List>();

    private GeneralList() {
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> getGlobal(Class<T> register) {
        List<T> r = instance.get(register);
        if (r == null) {
            r = new ArrayList<T>();
            instance.internal.put(register, r);
        }

        return r;
    }

    public static GeneralList getInstance() {
        return instance;
    }

    public static GeneralList getPersonal() {
        return new GeneralList();
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> get(Class<T> register) {
        List<T> r = internal.get(register);
        if (r == null) {
            r = new ArrayList<T>();
            internal.put(register, r);
        }

        return r;
    }

}
