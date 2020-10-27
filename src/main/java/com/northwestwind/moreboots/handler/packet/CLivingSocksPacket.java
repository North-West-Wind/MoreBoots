package com.northwestwind.moreboots.handler.packet;

import com.northwestwind.moreboots.init.ItemInit;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Explosion;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.network.NetworkEvent;

import java.io.Serializable;
import java.util.UUID;

public class CLivingSocksPacket implements Serializable {
    private UUID uuid;
    public CLivingSocksPacket(LivingEntity entity) {
        uuid = entity.getUniqueID();
    }

    public void handle(final NetworkEvent.Context ctx) {
        ServerPlayerEntity player = ctx.getSender();
        if(player == null) return;
        LivingEntity entity = (LivingEntity) ByteBufUtils.findEntityByUUID(uuid, player.getServerWorld());
        if(entity == null) return;
        ItemStack boots = entity.getItemStackFromSlot(EquipmentSlotType.FEET);
        if(!boots.getItem().equals(ItemInit.socks_boots)) return;
        CompoundNBT tag = boots.getOrCreateTag();
        if (entity.isInWater() && !entity.isSpectator()) {
            if (!tag.getBoolean("wet")) tag.putBoolean("wet", true);
            tag.putLong("wetTick", 1);
            boots.setTag(tag);
            boots.setDisplayName(new TranslationTextComponent("item.moreboots.socks_boots_wet"));
        }
        if (tag.getBoolean("wet")) entity.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 1, 1));
        else {
            Vector3d pos = entity.getPositionVec();
            Biome biome = entity.world.getBiome(new BlockPos(pos));
            float temperature = biome.getTemperature(new BlockPos(pos));
            if (temperature < 0.2) entity.addPotionEffect(new EffectInstance(Effects.SPEED, 1));
            else if (temperature > 1) entity.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 1));
        }
        if (tag.getLong("wetTick") >= 6000) {
            tag.putBoolean("wet", false);
            tag.putLong("wetTick", 0);
            boots.setDisplayName(new TranslationTextComponent("item.moreboots.socks_boots"));
        }
        boots.setTag(tag);
    }
}
