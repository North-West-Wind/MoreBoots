package ml.northwestwind.moreboots.init.block;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.ArrayList;
import java.util.Random;

public class RainbowWoolBlock extends Block {
    private static final ArrayList<Item> dyes = Lists.newArrayList();

    static {
        dyes.add(Items.BLACK_DYE);
        dyes.add(Items.WHITE_DYE);
        dyes.add(Items.RED_DYE);
        dyes.add(Items.BROWN_DYE);
        dyes.add(Items.GREEN_DYE);
        dyes.add(Items.YELLOW_DYE);
        dyes.add(Items.BLUE_DYE);
        dyes.add(Items.CYAN_DYE);
        dyes.add(Items.LIME_DYE);
        dyes.add(Items.LIGHT_GRAY_DYE);
        dyes.add(Items.GRAY_DYE);
        dyes.add(Items.MAGENTA_DYE);
        dyes.add(Items.PINK_DYE);
        dyes.add(Items.LIGHT_BLUE_DYE);
        dyes.add(Items.ORANGE_DYE);
        dyes.add(Items.PURPLE_DYE);
    }

    public RainbowWoolBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if(player.getItemInHand(hand).getItem() instanceof DyeItem) {
            if (!world.isClientSide) player.setItemInHand(hand, new ItemStack(dyes.get(new Random().nextInt(dyes.size()))));
            return InteractionResult.SUCCESS;
        }
        return super.use(state, world, pos, player, hand, result);
    }
}
