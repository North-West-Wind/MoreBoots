package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class WarriorQueenFeet extends BootsItem {
    public WarriorQueenFeet() {
        super(ItemInit.ModArmorMaterial.WARRIOR_QUEEN, "warrior_queen_boots");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity.hasEffect(MobEffects.MOVEMENT_SPEED) && entity.getEffect(MobEffects.MOVEMENT_SPEED).getAmplifier() > 0) return;
        entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 10, 0, false, false, false));
    }

    @Override
    public void onLivingAttack(LivingHurtEvent event) {
        LivingEntity entity = event.getEntityLiving();
        Entity source = event.getSource().getEntity();
        Vec3 pos = source.position().add(source.getLookAngle());
        entity.level.explode(source, pos.x, pos.y, pos.z, 2, Explosion.BlockInteraction.NONE);
        event.setAmount(event.getAmount() * 1.5f);
    }
}
