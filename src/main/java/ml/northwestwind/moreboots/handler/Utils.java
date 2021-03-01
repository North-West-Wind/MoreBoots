package ml.northwestwind.moreboots.handler;

import com.google.common.collect.Lists;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.World;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;
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

    public static boolean absorb(World worldIn, BlockPos pos) {
        Queue<Tuple<BlockPos, Integer>> queue = Lists.newLinkedList();
        queue.add(new Tuple<>(pos, 0));
        int i = 0;

        while(!queue.isEmpty()) {
            Tuple<BlockPos, Integer> tuple = queue.poll();
            BlockPos blockpos = tuple.getA();
            int j = tuple.getB();

            for(Direction direction : Direction.values()) {
                BlockPos blockpos1 = blockpos.offset(direction);
                BlockState blockstate = worldIn.getBlockState(blockpos1);
                FluidState fluidstate = worldIn.getFluidState(blockpos1);
                Material material = blockstate.getMaterial();
                if (fluidstate.isTagged(FluidTags.WATER)) {
                    if (blockstate.getBlock() instanceof IBucketPickupHandler && ((IBucketPickupHandler)blockstate.getBlock()).pickupFluid(worldIn, blockpos1, blockstate) != Fluids.EMPTY) {
                        ++i;
                        if (j < 6) {
                            queue.add(new Tuple<>(blockpos1, j + 1));
                        }
                    } else if (blockstate.getBlock() instanceof FlowingFluidBlock) {
                        worldIn.setBlockState(blockpos1, Blocks.AIR.getDefaultState(), 3);
                        ++i;
                        if (j < 6) {
                            queue.add(new Tuple<>(blockpos1, j + 1));
                        }
                    } else if (material == Material.OCEAN_PLANT || material == Material.SEA_GRASS) {
                        TileEntity tileentity = blockstate.hasTileEntity() ? worldIn.getTileEntity(blockpos1) : null;
                        Block.spawnDrops(blockstate, worldIn, blockpos1, tileentity);
                        worldIn.setBlockState(blockpos1, Blocks.AIR.getDefaultState(), 3);
                        ++i;
                        if (j < 6) {
                            queue.add(new Tuple<>(blockpos1, j + 1));
                        }
                    }
                }
            }

            if (i > 64) {
                break;
            }
        }

        return i > 0;
    }
}
