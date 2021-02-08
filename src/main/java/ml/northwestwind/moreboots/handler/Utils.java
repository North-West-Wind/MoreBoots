package ml.northwestwind.moreboots.handler;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.World;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Utils {
    public static ArrayList<Block> target = new ArrayList<>();
    public static ArrayList<Block> to = new ArrayList<>();
    public static ArrayList<Block> grass = new ArrayList<>();
    public static ArrayList<Block> air = new ArrayList<>();

    public static void initialize() {
        target.add(Blocks.DIRT);
        target.add(Blocks.GRASS_BLOCK);
        target.add(Blocks.PODZOL);
        to.add(Blocks.MYCELIUM);
        grass.add(Blocks.GRASS);
        air.add(Blocks.AIR);
    }

    public static boolean isSurroundedByInvalidBlocks(LivingEntity player) {
        BlockPos pos = new BlockPos(player.getPositionVec());
        BlockPos pos1 = pos.add(1, 0, 1);
        BlockPos pos2 = pos.add(-1, 0, -1);
        Iterator<BlockPos> iterator = BlockPos.getAllInBox(pos1, pos2).iterator();
        while (iterator.hasNext()) {
            BlockPos blockPos = iterator.next();
            Block block = player.world.getBlockState(blockPos).getBlock();
            Fluid fluid = player.world.getFluidState(blockPos).getFluid();
            if (!player.world.isAirBlock(blockPos) && !(fluid instanceof FlowingFluid) && !block.getCollisionShape(player.world.getBlockState(blockPos), player.world, blockPos, ISelectionContext.dummy()).equals(VoxelShapes.empty()))
                return false;
        }
        return true;
    }

    public static void changeGroundBlocks(LivingEntity living, World worldIn, BlockPos pos, int level, ArrayList<Block> target, ArrayList<Block> to) {
        float f = (float) Math.min(16, 2 + level);
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
        for (BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-f, -1.0D, -f), pos.add(f, -1.0D, f))) {
            if (blockpos.withinDistance(living.getPositionVec(), f)) {
                blockpos$mutable.setPos(blockpos.getX(), blockpos.getY() + 1, blockpos.getZ());
                BlockState blockstate1 = worldIn.getBlockState(blockpos$mutable);
                if (blockstate1.isAir(worldIn, blockpos$mutable)) {
                    BlockState blockstate2 = worldIn.getBlockState(blockpos);
                    boolean shouldChange = target.contains(blockstate2.getBlock());
                    if (shouldChange)
                        worldIn.setBlockState(blockpos, to.get(new Random().nextInt(to.size())).getDefaultState());
                }
            }
        }
    }

    public static byte[] objToBytes(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (oos != null) oos.close();
                if (bos != null) bos.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return bytes;
    }

    public static Object bytesToObj(byte[] bytes) {
        Object obj = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            bis = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bis);
            obj = ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ois != null) ois.close();
                if (bis != null) bis.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return obj;
    }
}
