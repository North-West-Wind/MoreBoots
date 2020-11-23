package com.northwestwind.moreboots.init.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Random;

public class RainbowWoolBlock extends Block {
    private ArrayList<Item> dyes;

    public RainbowWoolBlock(Properties properties) {
        super(properties);
        dyes = new ArrayList<Item>();
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

    @Override
    public void onBlockClicked(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        if(player.getHeldItemMainhand().getItem() instanceof DyeItem) player.replaceItemInInventory(EquipmentSlotType.MAINHAND.getIndex(), new ItemStack(dyes.get(new Random().nextInt(dyes.size()))));
        super.onBlockClicked(state, worldIn, pos, player);
    }
}
