package com.byteflipper.zmod.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.MapColor;
import net.minecraft.component.type.SuspiciousStewEffectsComponent;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.BlockSoundGroup;

public class FlowerFlameBlock extends FlowerBlock {

    public FlowerFlameBlock() {
        super(
                RegistryEntry.of(StatusEffects.FIRE_RESISTANCE.value()),  // Эффект FIRE_RESISTANCE
                10.0f,  // Длительность эффекта - 10 секунд
                AbstractBlock.Settings
                        .create()
                        .noCollision()
                        .breakInstantly()
                        .mapColor(MapColor.YELLOW)
                        .sounds(BlockSoundGroup.GRASS)
        );
    }}
