package com.northwestwind.moreboots.handler.packet;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.UUID;
import java.util.function.IntFunction;
import java.util.stream.Stream;

public class CRemoveNegativeEffectsPacket implements Serializable {
    private UUID uuid;
    public CRemoveNegativeEffectsPacket(LivingEntity entity) {
        uuid = entity.getUniqueID();
    }

    public UUID getUUID() {
        return uuid;
    }

    public void handle(final NetworkEvent.Context ctx) {
        if(ctx.getSender() == null) return;
        Stream<Entity> entities = ctx.getSender().getServerWorld().getEntities();
        LivingEntity target = null;
        for(Entity entity : entities.toArray(Entity[]::new)) {
            if(entity.getUniqueID().equals(getUUID())) {
                target = (LivingEntity) entity;
            }
        }
        if(target == null) return;
        Field[] fields = Effects.class.getDeclaredFields();
        for(Field f : fields) {
            Class<?> cl = f.getType();
            if (cl.equals(Effects.class) && Modifier.isStatic(f.getModifiers())) {
                try {
                    Effect effect = (Effect) f.get(null);
                    if(!effect.isBeneficial()) target.removePotionEffect(effect);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
