package com.northwestwind.moreboots.handler.packet;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.io.*;
import java.util.UUID;
import java.util.stream.Stream;

public class ByteBufUtils
{
    public static byte[] objToBytes(Object obj)
    {
        byte[] bytes = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        try
        {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally
        {
            try {
                if(oos != null)oos.close();
                if(bos != null)bos.close();
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        }
        return bytes;
    }

    public static Object bytesToObj(byte[] bytes)
    {
        Object obj = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try
        {
            bis = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bis);
            obj = ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally
        {
            try {
                if (ois != null) ois.close();
                if (bis != null) bis.close();
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        }
        return obj;
    }

    @Nullable
    public static Entity findEntityByUUID(UUID uuid, ServerWorld world) {
        Stream<Entity> entities = world.getEntities();
        LivingEntity entity = null;
        for(Entity ent : entities.toArray(Entity[]::new)) {
            if(ent.getUniqueID().equals(uuid)) {
                entity = (LivingEntity) ent;
            }
        }
        return entity;
    }
}