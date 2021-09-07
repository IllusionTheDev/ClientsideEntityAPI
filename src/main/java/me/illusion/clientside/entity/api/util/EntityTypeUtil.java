package me.illusion.clientside.entity.api.util;

import lombok.SneakyThrows;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public class EntityTypeUtil {

    private static List<String> types;

    public static void load(List<String> list) {
        types = list;
    }

    @SneakyThrows
    public static int adapt(EntityType type) {
        if(types == null)
            throw new IllegalAccessException("Attempting to spawn entity before API is loaded");

        return types.indexOf(type.name());
    }
}
