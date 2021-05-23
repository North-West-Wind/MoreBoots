package ml.northwestwind.moreboots.handler;

import com.google.common.collect.Lists;
import ml.northwestwind.moreboots.handler.packet.IPacket;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
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
        BlockPos pos = new BlockPos(player.position());
        BlockPos pos1 = pos.offset(1, 0, 1);
        BlockPos pos2 = pos.offset(-1, 0, -1);
        Iterator<BlockPos> iterator = BlockPos.betweenClosedStream(pos1, pos2).iterator();
        while (iterator.hasNext()) {
            BlockPos blockPos = iterator.next();
            Block block = player.level.getBlockState(blockPos).getBlock();
            Fluid fluid = player.level.getFluidState(blockPos).getType();
            if (!player.level.isEmptyBlock(blockPos) && !(fluid instanceof FlowingFluid) && !block.getCollisionShape(player.level.getBlockState(blockPos), player.level, blockPos, ISelectionContext.empty()).equals(VoxelShapes.empty()))
                return false;
        }
        return true;
    }

    public static void changeGroundBlocks(LivingEntity living, World worldIn, BlockPos pos, int level, ArrayList<Block> target, ArrayList<Block> to) {
        float f = (float) Math.min(16, 2 + level);
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
        for (BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-f, -1.0D, -f), pos.offset(f, -1.0D, f))) {
            if (blockpos.closerThan(living.position(), f)) {
                blockpos$mutable.set(blockpos.getX(), blockpos.getY() + 1, blockpos.getZ());
                BlockState blockstate1 = worldIn.getBlockState(blockpos$mutable);
                if (blockstate1.isAir(worldIn, blockpos$mutable)) {
                    BlockState blockstate2 = worldIn.getBlockState(blockpos);
                    boolean shouldChange = target.contains(blockstate2.getBlock());
                    if (shouldChange)
                        worldIn.setBlockAndUpdate(blockpos, to.get(new Random().nextInt(to.size())).defaultBlockState());
                }
            }
        }
    }

    public static <P extends IPacket> byte[] packetToBytes(P obj) {
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

    public static <P extends IPacket> P bytesToObj(byte[] bytes) {
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
        return (P) obj;
    }

    public static boolean absorb(World worldIn, BlockPos pos, ITag.INamedTag<Fluid> tag) {
        Queue<Tuple<BlockPos, Integer>> queue = Lists.newLinkedList();
        queue.add(new Tuple<>(pos, 0));
        int i = 0;

        while(!queue.isEmpty()) {
            Tuple<BlockPos, Integer> tuple = queue.poll();
            BlockPos blockpos = tuple.getA();
            int j = tuple.getB();

            for(Direction direction : Direction.values()) {
                BlockPos blockpos1 = blockpos.relative(direction);
                BlockState blockstate = worldIn.getBlockState(blockpos1);
                FluidState fluidstate = worldIn.getFluidState(blockpos1);
                Material material = blockstate.getMaterial();
                if (fluidstate.is(tag)) {
                    if (blockstate.getBlock() instanceof IBucketPickupHandler && ((IBucketPickupHandler)blockstate.getBlock()).takeLiquid(worldIn, blockpos1, blockstate) != Fluids.EMPTY) {
                        ++i;
                        if (j < 6) {
                            queue.add(new Tuple<>(blockpos1, j + 1));
                        }
                    } else if (blockstate.getBlock() instanceof FlowingFluidBlock) {
                        worldIn.setBlockAndUpdate(blockpos1, Blocks.AIR.defaultBlockState());
                        ++i;
                        if (j < 6) {
                            queue.add(new Tuple<>(blockpos1, j + 1));
                        }
                    } else if (material == Material.WATER_PLANT || material == Material.REPLACEABLE_WATER_PLANT) {
                        TileEntity tileentity = blockstate.hasTileEntity() ? worldIn.getBlockEntity(blockpos1) : null;
                        Block.dropResources(blockstate, worldIn, blockpos1, tileentity);
                        worldIn.setBlockAndUpdate(blockpos1, Blocks.AIR.defaultBlockState());
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

    public static void jumpFromGround(LivingEntity entity) {
        float factor = entity.level.getBlockState(entity.blockPosition()).getBlock().getJumpFactor();
        float factor1 = entity.level.getBlockState(new BlockPos(entity.position().x, entity.getBoundingBox().minY - 0.5000001D, entity.position().z)).getBlock().getJumpFactor();
        float f = 0.42f * ((double)factor == 1.0D ? factor1 : factor);
        if (entity.hasEffect(Effects.JUMP)) f += 0.1F * (float)(entity.getEffect(Effects.JUMP).getAmplifier() + 1);

        Vector3d vector3d = entity.getDeltaMovement();
        entity.setDeltaMovement(vector3d.x, f, vector3d.z);
        if (entity.isSprinting()) {
            float f1 = entity.yRot * ((float)Math.PI / 180F);
            entity.setDeltaMovement(entity.getDeltaMovement().add(-MathHelper.sin(f1) * 0.2F, 0.0D, MathHelper.cos(f1) * 0.2F));
        }

        entity.hasImpulse = true;
        entity.setDeltaMovement(entity.getDeltaMovement().add(0, 1, 0));
    }
}
