package ml.northwestwind.moreboots.init.item.boots;

import com.google.common.collect.Lists;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.sound.PlaySoundEvent;

import java.util.List;

public class MusicBootsItem extends BootsItem {
    private static final List<SoundEvent> INSTRUMENTS = Lists.newArrayList(SoundEvents.NOTE_BLOCK_BANJO, SoundEvents.NOTE_BLOCK_BASS, SoundEvents.NOTE_BLOCK_BELL, SoundEvents.NOTE_BLOCK_BIT, SoundEvents.NOTE_BLOCK_CHIME, SoundEvents.NOTE_BLOCK_COW_BELL, SoundEvents.NOTE_BLOCK_DIDGERIDOO, SoundEvents.NOTE_BLOCK_FLUTE, SoundEvents.NOTE_BLOCK_GUITAR, SoundEvents.NOTE_BLOCK_HARP, SoundEvents.NOTE_BLOCK_XYLOPHONE, SoundEvents.NOTE_BLOCK_PLING, SoundEvents.NOTE_BLOCK_IRON_XYLOPHONE);

    public MusicBootsItem() {
        super(ItemInit.ModArmorMaterial.MUSIC, "music_boots");
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void onPlaySound(PlaySoundEvent event) {
        net.minecraft.client.entity.player.ClientPlayerEntity player = Minecraft.getInstance().player;
        if (player == null) return;
        ISound sound = event.getSound();
        event.setResultSound(new SimpleSound(INSTRUMENTS.get(player.getRandom().nextInt(INSTRUMENTS.size())), SoundCategory.RECORDS, 1, (float) Math.pow(2.0D, (double) (player.getRandom().nextInt(24) - 12) / 12.0D), sound.getX(), sound.getY(), sound.getZ()));
    }
}
