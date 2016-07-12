package cf.brforgers.core.lib.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Reflection done more easily
 */
public class ReflectionHelper {
    public static <T> Map<String, Object> asMap(Class<? extends T> clazz, T inst) {
        Map<String, Object> result = new HashMap<String, Object>();
        Field[] declaredFields = getAllFields(clazz);
        for (Field field : declaredFields) {
            try {
                if (!Modifier.isTransient(field.getModifiers())) {
                    field.setAccessible(true);
                    result.put(field.getName(), field.get(inst));
                }
            } catch (Exception e) {
            }
        }
        return result;
    }

    public static <T> Map<String, String> asStringMap(Class<? extends T> clazz, T inst) {
        Map<String, String> result = new HashMap<String, String>();
        Field[] declaredFields = getAllFields(clazz);
        for (Field field : declaredFields) {
            try {
                if (!Modifier.isTransient(field.getModifiers())) {
                    field.setAccessible(true);
                    result.put(field.getName(), field.get(inst).toString());
                }
            } catch (Exception e) {
            }
        }
        return result;
    }

    public static Field[] getAllFields(Class aClass) {
        List<Field> fields = new ArrayList<Field>();
        do {
            Collections.addAll(fields, aClass.getDeclaredFields());
            aClass = aClass.getSuperclass();
        } while (aClass != null && aClass != Object.class);
        return fields.toArray(new Field[fields.size()]);
    }
}
