package ml.northwestwind.moreboots.init.item;

import ml.northwestwind.moreboots.MoreBoots;
import ml.northwestwind.moreboots.Reference;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderNameplateEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.List;

public class BootsItem extends ArmorItem {
    protected final String registryName;

    public BootsItem(ArmorMaterial material, String registryName, boolean isNetherite) {
        super(material, EquipmentSlot.FEET, isNetherite ? new Item.Properties().tab(MoreBoots.MoreBootsItemGroup.INSTANCE).fireResistant() : new Item.Properties().tab(MoreBoots.MoreBootsItemGroup.INSTANCE));
        this.registryName = registryName;
    }

    public BootsItem(ArmorMaterial material, String registryName) {
        this(material, registryName, false);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        if (!(stack.getItem() instanceof BootsItem)) return;
        tooltip.add(new TranslatableComponent("tooltip.moreboots." + registryName));
    }

    @OnlyIn(Dist.CLIENT)
    public void onPlaySound(final PlaySoundEvent event) { }
    public void onPlayerLeftClick(final PlayerInteractEvent.LeftClickEmpty event) { }
    public void onLivingFall(final LivingFallEvent event) { }
    public void onLivingJump(final LivingEvent.LivingJumpEvent event) { }
    public void onLivingHurt(final LivingHurtEvent event) { }
    public void onLivingAttack(final LivingHurtEvent event) { }
    public void onPlayerXpChange(final PlayerXpEvent.XpChange event) { }
    public void onLivingEquipmentChange(final LivingEquipmentChangeEvent event) { }
    public void onLivingUpdate(final LivingEvent.LivingUpdateEvent event) { }
    @OnlyIn(Dist.CLIENT)
    public void activateBoots() { }
    @OnlyIn(Dist.CLIENT)
    public void onShift() { }
    @OnlyIn(Dist.CLIENT)
    public void onJump() { }
    @OnlyIn(Dist.CLIENT)
    public void preRenderLiving(final RenderLivingEvent.Pre<?, ?> event) { }
    @OnlyIn(Dist.CLIENT)
    public void postRenderLiving(final RenderLivingEvent.Post<?, ?> event) { }
    @OnlyIn(Dist.CLIENT)
    public void renderNameplate(final RenderNameplateEvent event) { }

    public void getCollisionShape(BlockGetter worldIn, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) { }
}
