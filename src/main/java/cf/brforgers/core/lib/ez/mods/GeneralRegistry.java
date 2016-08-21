package cf.brforgers.core.lib.ez.mods;

import java.util.HashMap;
import java.util.Map;

public class GeneralRegistry {
    private static final GeneralRegistry instance = new GeneralRegistry();
    private Map<Class, Map> internal = new HashMap<Class, Map>();

    private GeneralRegistry() {
    }

    @SuppressWarnings("unchecked")
    public static <T> Map<String, T> getGlobal(Class<T> register) {
        Map<String, T> r = instance.get(register);
        if (r == null) {
            r = new HashMap<String, T>();
            instance.internal.put(register, r);
        }

        return r;
    }

    public static GeneralRegistry getInstance() {
        return instance;
    }

    public static GeneralRegistry getPersonal() {
        return new GeneralRegistry();
    }

    @SuppressWarnings("unchecked")
    public <T> Map<String, T> get(Class<T> register) {
        Map<String, T> r = internal.get(register);
        if (r == null) {
            r = new HashMap<String, T>();
            internal.put(register, r);
        }

        return r;
    }

//    public <T extends IForgeRegistryEntry<T>> GeneralRegistry boundRegistryWithForge(final Class<T> type, int max, ResourceLocation registryName, ResourceLocation optionalDefaultKey) {
//        //IForgeRegistry<T> FORGE = //nothing to do with this variable
//        PersistentRegistryManager.createRegistry(
//                registryName,
//                type,
//                optionalDefaultKey,
//                0,
//                max,
//                false,
//                new IForgeRegistry.AddCallback<T>() {
//                    @Override
//                    public void onAdd(T obj, int id, Map<ResourceLocation, ?> slaveset) {
//                        get(type).put(
//                                obj.getRegistryName().toString(),
//                                obj
//                        );
//                    }
//                },
//                new IForgeRegistry.ClearCallback<T>() {
//                    @Override
//                    public void onClear(Map<ResourceLocation, ?> slaveset) {
//                        internal.remove(get(type));
//                    }
//                },
//                null
//        );
//        return this;
//    }
}
