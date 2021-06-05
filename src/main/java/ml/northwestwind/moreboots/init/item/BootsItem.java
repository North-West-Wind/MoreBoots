package ml.northwestwind.moreboots.init.item;

import ml.northwestwind.moreboots.MoreBoots;
import ml.northwestwind.moreboots.Reference;
import ml.northwestwind.moreboots.handler.MoreBootsPacketHandler;
import ml.northwestwind.moreboots.handler.packet.CPlayerKAPacket;
import ml.northwestwind.moreboots.init.ItemInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderNameplateEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.List;

public class BootsItem extends ArmorItem {
    private final String registryName;

    public BootsItem(IArmorMaterial material, String registryName, boolean isNetherite) {
        super(material, EquipmentSlotType.FEET, isNetherite ? new Item.Properties().tab(MoreBoots.MoreBootsItemGroup.INSTANCE).fireResistant() : new Item.Properties().tab(MoreBoots.MoreBootsItemGroup.INSTANCE));
        setRegistryName(Reference.MODID, registryName);
        this.registryName = registryName;
    }

    public BootsItem(IArmorMaterial material, String registryName) {
        this(material, registryName, false);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (!(stack.getItem() instanceof BootsItem)) return;
        tooltip.add(new TranslationTextComponent("tooltip.moreboots." + registryName));
    }

    @OnlyIn(Dist.CLIENT)
    public void onPlaySound(final PlaySoundEvent event) { }
    public void onPlayerLeftClick(final PlayerInteractEvent.LeftClickEmpty event) { }
    public void onLivingFall(final LivingFallEvent event) { }
    public void onLivingJump(final LivingEvent.LivingJumpEvent event) { }
    public void onLivingDamage(final LivingDamageEvent event) { }
    public void onLivingAttack(final LivingDamageEvent event) { }
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
    public void renderNameplate(final RenderNameplateEvent event) { }

    public void getCollisionShape(IBlockReader worldIn, BlockPos pos, ISelectionContext context, CallbackInfoReturnable<VoxelShape> cir) { }
}
