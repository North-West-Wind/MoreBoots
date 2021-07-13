package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.handler.Utils;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITag;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.entity.living.LivingEvent;

public class SpongeBootsItem extends BootsItem {
    private final ITag.INamedTag<Fluid> absorb;
    public SpongeBootsItem(IArmorMaterial material, String registryName, ITag.INamedTag<Fluid> absorb, boolean fireRes) {
        super(material, registryName, fireRes);
        this.absorb = absorb;
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        ItemStack boots = entity.getItemBySlot(EquipmentSlotType.FEET);
        boolean absorbed = Utils.absorb(entity.level, entity.blockPosition(), absorb);
        if (absorbed && entity.getRandom().nextInt(100) == 0)
            boots.hurtAndBreak(1, entity, ent -> ent.playSound(SoundEvents.GRASS_BREAK, 1, 1));
    }
}
