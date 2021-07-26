package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.handler.Utils;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.Tag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.event.entity.living.LivingEvent;

public class SpongeBootsItem extends BootsItem {
    private final Tag.Named<Fluid> absorb;

    public SpongeBootsItem(ArmorMaterial material, String registryName, Tag.Named<Fluid> absorb, boolean fireRes) {
        super(material, registryName, fireRes);
        this.absorb = absorb;
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
        boolean absorbed = Utils.absorb(entity.level, entity.blockPosition(), absorb);
        if (absorbed && entity.getRandom().nextInt(100) == 0)
            boots.hurtAndBreak(1, entity, ent -> ent.playSound(SoundEvents.GRASS_BREAK, 1, 1));
    }
}
