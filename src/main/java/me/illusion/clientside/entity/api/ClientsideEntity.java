package me.illusion.clientside.entity.api;

import lombok.Getter;
import me.illusion.clientside.entity.api.util.EntityIDTracker;
import me.illusion.clientside.entity.api.util.EntityTypeUtil;
import me.illusion.clientside.entity.api.util.PacketUtils;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter
public class ClientsideEntity {

    private final int entityTypeId;
    private final int entityId = EntityIDTracker.getNextId();
    private final UUID uuid = UUID.randomUUID();

    private Location location;


    public ClientsideEntity(EntityType type, Location location) {
        this.entityTypeId = EntityTypeUtil.adapt(type);
        this.location = location;
    }

    public void show(Player... players) {
        PacketUtils.sendPacket(PacketUtils.createSpawnPacket(this), players);
    }
}
