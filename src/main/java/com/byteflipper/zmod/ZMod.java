package com.byteflipper.zmod;

import com.byteflipper.zmod.block.ModBlocks;
import com.byteflipper.zmod.util.DayNightManager;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.world.ServerWorld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZMod implements ModInitializer {
	public static final String MOD_ID = "z-mod";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		ModBlocks.registerModBlocks();
		DayNightManager.initialize();
		DayNightManager.setDayLength(2000);
		DayNightManager.setNightLength(4000);
		DayNightManager.setNightEnabled(true);
		DayNightManager.setDayEnabled(false);
		DayNightManager.setModConfigEnabled(true);

		LOGGER.info("Hello Fabric world!");
	}
}