package ml.northwestwind.moreboots.container.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import ml.northwestwind.moreboots.container.StorageBootsContainer;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class StorageBootsScreen extends AbstractContainerScreen<StorageBootsContainer>
{
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation("minecraft", "textures/gui/container/generic_54.png");
    private final int containerRows;

    public StorageBootsScreen(StorageBootsContainer container, Inventory playerInventory, Component titleIn)
    {
        super(container, playerInventory, titleIn);
        this.passEvents = false;
        this.containerRows = container.getContainerRows();
        this.imageHeight = 114 + this.containerRows * 18;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(matrixStack); //Draw background
        super.render(matrixStack, mouseX, mouseY, partialTicks); //Super
        this.renderTooltip(matrixStack, mouseX, mouseY); //Render hovered tooltips
    }

    //Render
    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY)
    {
        RenderSystem.clearColor(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindForSetup(GUI_TEXTURE);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.blit(matrixStack, i, j, 0, 0, this.imageWidth, this.containerRows * 18 + 17);
        this.blit(matrixStack, i, j + this.containerRows * 18 + 17, 0, 126, this.imageWidth, 96);
    }
}