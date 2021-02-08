package ml.northwestwind.moreboots.container.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import ml.northwestwind.moreboots.container.StorageBootsContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class StorageBootsScreen extends ContainerScreen<StorageBootsContainer>
{
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation("minecraft", "textures/gui/container/generic_54.png");

    private final int rows;

    public StorageBootsScreen(StorageBootsContainer container, PlayerInventory playerInventory, ITextComponent titleIn)
    {
        super(container, playerInventory, titleIn);
        this.rows = container.getRows();
        this.ySize = 114 + this.rows * 18;
    }

    @Override
    public void func_230430_a_(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        this.func_230446_a_(matrixStack); //Draw background
        super.func_230430_a_(matrixStack, mouseX, mouseY, partialTicks); //Super
        this.func_230459_a_(matrixStack, mouseX, mouseY); //Render hovered tooltips
    }

    //Render
    @Override
    protected void func_230450_a_(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY)
    {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.field_230706_i_.getTextureManager().bindTexture(GUI_TEXTURE);
        this.func_238474_b_(matrixStack, this.guiLeft, this.guiTop, 0, 0, this.xSize, this.rows * 18 + 17);
        this.func_238474_b_(matrixStack, this.guiLeft, this.guiTop + this.rows * 18 + 17, 0, 126, this.xSize, 96);
    }

    @Override
    protected void func_230451_b_(MatrixStack matrixStack, int mouseX, int mouseY)
    {
        this.field_230712_o_.func_243248_b(matrixStack, this.field_230704_d_, 8.0F, 6.0F, 0x404040);
        this.field_230712_o_.func_243248_b(matrixStack, this.playerInventory.getDisplayName(), 8.0F, (float) (this.ySize - 96 + 2), 0x404040);
    }
}