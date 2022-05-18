package ml.northwestwind.moreboots.init.block;

import ml.northwestwind.moreboots.init.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class ViscousBlock extends Block {
    public ViscousBlock(Properties p_49795_) {
        super(p_49795_);
    }

    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            ItemStack boots = livingEntity.getItemBySlot(EquipmentSlot.FEET);
            if (boots.getItem().equals(ItemInit.RAINBOW_SOCKS_BOOTS.get()) && livingEntity.isCrouching()) {
                CompoundTag tag = boots.getOrCreateTag();
                long tickInside = tag.getLong("tickInside");
                if (tickInside < 100) {
                    tickInside += 1;
                    tag.putLong("tickInside", tickInside);
                    boots.setTag(tag);
                } else {
                    ItemStack stack = new ItemStack(ItemInit.BOMBERFEET.get());
                    stack.setDamageValue(boots.getDamageValue());
                    EnchantmentHelper.setEnchantments(EnchantmentHelper.getEnchantments(boots), stack);
                    livingEntity.setItemSlot(EquipmentSlot.FEET, stack);
                }
            }
        }
        BlockState blockstate = level.getBlockState(pos.above());
        if (blockstate.isAir()) {
            entity.onAboveBubbleCol(false);
            if (!level.isClientSide) {
                ServerLevel serverlevel = (ServerLevel)level;

                for(int i = 0; i < 2; ++i) {
                    serverlevel.sendParticles(ParticleTypes.SPLASH, (double)pos.getX() + level.random.nextDouble(), (double)(pos.getY() + 1), (double)pos.getZ() + level.random.nextDouble(), 1, 0.0D, 0.0D, 0.0D, 1.0D);
                    serverlevel.sendParticles(ParticleTypes.BUBBLE, (double)pos.getX() + level.random.nextDouble(), (double)(pos.getY() + 1), (double)pos.getZ() + level.random.nextDouble(), 1, 0.0D, 0.01D, 0.0D, 0.2D);
                }
            }
        } else {
            entity.onInsideBubbleColumn(false);
        }
    }
}
