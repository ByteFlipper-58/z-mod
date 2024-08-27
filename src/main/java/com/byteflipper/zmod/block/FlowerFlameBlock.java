package com.byteflipper.zmod.block;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class FlowerFlameBlock extends FlowerBlock {

    public FlowerFlameBlock() {
        super(
                RegistryEntry.of(StatusEffects.FIRE_RESISTANCE.value()),
                10.0f,
                FlowerBlock.Settings.copy(Blocks.DANDELION)
                        .nonOpaque()
                        .noCollision()
                        .breakInstantly()
                        .luminance((state) -> {return 15;})
                        .mapColor(MapColor.YELLOW)
                        .sounds(BlockSoundGroup.GRASS)
        );
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);

        // Generate particles circling around the block
        double radius = 0.5;
        int particleCount = 10;
        for (int i = 0; i < particleCount; i++) {
            double angle = random.nextDouble() * Math.PI * 2;
            double offsetX = Math.cos(angle) * radius;
            double offsetZ = Math.sin(angle) * radius;

            // Particle slightly above the bottom of the block
            world.addParticle(
                    ParticleTypes.FLAME,
                    pos.getX() + 0.5 + offsetX,
                    pos.getY() + 0.5, // Slightly above the center of the block
                    pos.getZ() + 0.5 + offsetZ,
                    0.0, 0.0, 0.0
            );
        }
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        super.onEntityCollision(state, world, pos, entity);

        // Ignite entities (players and mobs) that collide with the block
        if (entity instanceof LivingEntity livingEntity && !world.isClient) {
            livingEntity.setOnFireFor(5); // Set entities on fire for 5 seconds
        }
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        super.afterBreak(world, player, pos, state, blockEntity, tool);
    }
}
