package cf.brforgers.core.lib;

import cf.brforgers.core.lib.utils.Function;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import net.minecraft.client.settings.KeyBinding;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class KeyMapper {
    private static KeyMapper instance = null;
    private List<Mapping> mappings = new ArrayList<Mapping>();

    private KeyMapper() {
    }

    private static KeyMapper getInstance() {
        if (instance == null) {
            instance = new KeyMapper();
            Utils.registerEvents(instance);
        }
        return instance;
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        for (Iterator mappingIterator = mappings.iterator(); mappingIterator.hasNext(); ) {
            Mapping mapping = (Mapping) mappingIterator.next();
            if (mapping.mapping.isPressed() != mapping.state) {
                mapping.state = mapping.mapping.isPressed();
                mapping.runOnTrigger.run(mapping.state);
            }
        }

    }

    public static class Mapping {
        public final Function<Boolean> runOnTrigger;
        public final KeyBinding mapping;
        private boolean state;

        public Mapping(String bindingName, String category, int key) {
            this(bindingName, category, key, (Function<Boolean>) null);
        }

        public Mapping(String bindingName, String category, int key, Runnable runOnTrigger) {
            this(bindingName, category, key, Utils.toFunction(runOnTrigger, Boolean.class));
        }

        public Mapping(String bindingName, String category, int key, Function<Boolean> runOnTrigger) {

            this.runOnTrigger = runOnTrigger;
            mapping = new KeyBinding(bindingName, key, category);
        }
    }
}
