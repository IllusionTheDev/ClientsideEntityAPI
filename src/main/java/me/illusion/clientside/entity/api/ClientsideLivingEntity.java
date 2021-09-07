package me.illusion.clientside.entity.api;

import me.illusion.clientside.entity.api.enums.AnimationEnum;
import me.illusion.clientside.entity.api.util.PacketUtils;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class ClientsideLivingEntity extends ClientsideEntity {

    public ClientsideLivingEntity(EntityType type, Location location) {
        super(type, location);
    }

    public void displayAnimation(AnimationEnum animation, Player... players) {
        PacketUtils.sendPacket(PacketUtils.createAnimationPacket(this, animation), players);
    }
}
