package me.illusion.clientside.entity.api.util;

public class EntityIDTracker {

    private static int ENTITY_ID = 150000;

    public static int getNextId() {
        return ENTITY_ID++;
    }
}
