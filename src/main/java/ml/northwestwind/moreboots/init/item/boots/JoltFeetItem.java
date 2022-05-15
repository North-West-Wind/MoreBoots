package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.Reference;
import ml.northwestwind.moreboots.handler.MoreBootsPacketHandler;
import ml.northwestwind.moreboots.handler.packet.CStrikeAreaPacket;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class JoltFeetItem extends BootsItem {
    private static final ResourceLocation ELECTRIC_TAG = new ResourceLocation(Reference.MODID, "wools");

    public JoltFeetItem() {
        super(ItemInit.ModArmorMaterial.JOLT, "jolt_feet");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        BlockPos pos = entity.blockPosition();
        if (entity.level.getBlockState(pos).getBlock().getTags().contains(ELECTRIC_TAG) || entity.level.getBlockState(pos.below()).getBlock().getTags().contains(ELECTRIC_TAG)) {
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
        MoreBootsPacketHandler.INSTANCE.sendToServer(new CStrikeAreaPacket());
    }
}
