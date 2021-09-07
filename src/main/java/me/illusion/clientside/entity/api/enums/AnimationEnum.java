package me.illusion.clientside.entity.api.enums;

public enum AnimationEnum {
    SWING_MAIN_ARM(0),
    TAKE_DAMAGE(1),
    LEAVE_BED(2),
    SWING_OFFHAND(3),
    @Deprecated EAT_FOOD(3),
    CRITICAL_EFFECT(4),
    MAGICAL_CRITICAL_EFFECT(5);

    private byte value;

    AnimationEnum(int value) {
        this.value = (byte) value;
    }

    public byte getValue() {
        return value;
    }
}
