package me.illusion.clientside.entity.api.util;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import me.illusion.clientside.entity.api.ClientsideEntity;
import me.illusion.clientside.entity.api.ClientsideLivingEntity;
import me.illusion.clientside.entity.api.enums.AnimationEnum;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public final class PacketUtils {

    private PacketUtils() {

    }

    public static void sendPacket(PacketContainer packet, Player... targets) {
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();

        for(Player player : targets) {
            try {
                manager.sendServerPacket(player, packet);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public static PacketContainer createAnimationPacket(ClientsideLivingEntity entity, AnimationEnum value) {
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.ANIMATION);

        packet.getIntegers()
                .writeSafely(0, entity.getEntityId()) // 1.8 - 1.17.1 so far
                .writeSafely(1, (int) value.getValue()); // Should work on 1.17

        packet.getBytes().writeSafely(0, value.getValue()); // In case some legacy version does funky stuff

        return packet;
    }

    public static PacketContainer createDestroyPacket(ClientsideEntity entity) {
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_DESTROY);

        packet.getIntegerArrays().writeSafely(0, new int[]{entity.getEntityId()});
        packet.getIntegers().writeSafely(0, entity.getEntityId());

        return packet;
    }

    public static PacketContainer createSpawnPacket(ClientsideEntity entity) {
        if(entity instanceof ClientsideLivingEntity)
            return createSpawnLivingEntityPacket((ClientsideLivingEntity) entity);

        PacketContainer packet = new PacketContainer(PacketType.Play.Server.SPAWN_ENTITY);

        populate(packet, entity);

        return packet;
    }

    public static PacketContainer createSpawnLivingEntityPacket(ClientsideLivingEntity entity) {
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.SPAWN_ENTITY_LIVING);

        populate(packet, entity);

        return packet;
    }

    public static PacketContainer createMovementPacket(ClientsideEntity entity, Location oldLocation, Location newLocation) {
        if(oldLocation.distanceSquared(newLocation) > 64)
            return createTeleportPacket(entity, newLocation);

        PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.REL_ENTITY_MOVE);

        packet.getIntegers().write(0, entity.getEntityId());

        packet.getShorts()
                .write(0, (short) ((newLocation.getX() * 32 - oldLocation.getX() * 32) * 128)) // Copied straight from wiki.vg/Protocol
                .write(1, (short) ((newLocation.getY() * 32 - oldLocation.getY() * 32) * 128))
                .write(2, (short) ((newLocation.getZ() * 32 - oldLocation.getZ() * 32) * 128));

        packet.getModifier().writeDefaults();

        return packet;
    }

    public static PacketContainer createTeleportPacket(ClientsideEntity entity, Location location) {
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_TELEPORT);

        packet.getIntegers()
                .writeSafely(0, entity.getEntityId());

        packet.getDoubles()
                .writeSafely(0, location.getX()) // X
                .writeSafely(1, location.getY()) // Y
                .writeSafely(2, location.getZ()); // Z

        packet.getFloat()
                .writeSafely(0, location.getYaw()) // Yaw
                .writeSafely(1, location.getPitch()); // Pitch

        packet.getModifier().writeDefaults();

        return packet;
    }

    private static void populate(PacketContainer packet, ClientsideEntity entity) {
        packet.getIntegers()
                .writeSafely(0, entity.getEntityId()) // Entity ID
                .writeSafely(1, entity.getEntityTypeId()); // EntityType ID, usually index of alphabetically sorted list of EntityTypes

        Location location = entity.getLocation();

        packet.getDoubles()
                .writeSafely(0, location.getX()) // X
                .writeSafely(1, location.getY()) // Y
                .writeSafely(2, location.getZ()); // Z

        packet.getFloat()
                .writeSafely(0, location.getYaw()) // Yaw
                .writeSafely(1, location.getPitch()); // Pitch

        packet.getUUIDs()
                .writeSafely(0, entity.getUuid()); // UUID

        packet.getModifier().writeDefaults();
    }

}
