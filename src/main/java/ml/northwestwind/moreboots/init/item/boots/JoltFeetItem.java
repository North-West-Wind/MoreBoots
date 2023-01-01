package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.Reference;
import ml.northwestwind.moreboots.handler.MoreBootsPacketHandler;
import ml.northwestwind.moreboots.handler.Utils;
import ml.northwestwind.moreboots.handler.packet.CStrikeAreaPacket;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.IReverseTag;

import java.util.Optional;

public class JoltFeetItem extends BootsItem {
    private static final TagKey<Block> ELECTRIC_TAG = BlockTags.create(new ResourceLocation(Reference.MODID, "wools"));

    public JoltFeetItem() {
        super(ItemInit.ModArmorMaterial.JOLT, "jolt_feet");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        BlockPos pos = entity.blockPosition();
        Optional<IReverseTag<Block>> thisBlockTag = ForgeRegistries.BLOCKS.tags().getReverseTag(entity.level.getBlockState(pos).getBlock());
        Optional<IReverseTag<Block>> downBlockTag = ForgeRegistries.BLOCKS.tags().getReverseTag(entity.level.getBlockState(pos.below()).getBlock());
        if ((thisBlockTag.isPresent() && thisBlockTag.get().containsTag(ELECTRIC_TAG)) || (downBlockTag.isPresent() && downBlockTag.get().containsTag(ELECTRIC_TAG))) {
            if (!entity.getDeltaMovement().equals(Vec3.ZERO) && entity.level.getRandom().nextDouble() < 0.005) {
                LightningBolt lightning = new LightningBolt(EntityType.LIGHTNING_BOLT, entity.level);
                lightning.setPos(entity.getX(), entity.getY(), entity.getZ());
                entity.level.addFreshEntity(lightning);
            }
        }
    }

    @Override
    public void activateBoots() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;
        if (player.getInventory().countItem(Items.SAND) == 0) return;
        boolean shouldStrike = player.isCreative();
        if (!shouldStrike) {
            int slot = Utils.getStackSlot(player.getInventory(), Items.LIGHTNING_ROD);
            if (slot > -1) {
                ItemStack stack = player.getInventory().getItem(slot);
                stack.shrink(1);
                shouldStrike = true;
            }
        }
        if (shouldStrike && player.level.isClientSide) MoreBootsPacketHandler.INSTANCE.sendToServer(new CStrikeAreaPacket());
    }
}
