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

		DayNightManager.setDayLength(12000L); // Длительность дня в тиках
		DayNightManager.setNightLength(36000L); // Длительность ночи в тиках (60 минут реального времени)

		// Периодическое обновление времени
		ServerTickEvents.END_SERVER_TICK.register(server -> {
			for (ServerWorld world : server.getWorlds()) {
				long time = world.getTimeOfDay();
				long dayLength = DayNightManager.getDayLength();
				long nightLength = DayNightManager.getNightLength();

				// Пример проверки времени и изменения (добавьте вашу логику)
				if (DayNightManager.isDay(world)) {
					// Пример выполнения действий для дня
				} else if (DayNightManager.isNight(world)) {
					// Пример выполнения действий для ночи
				}
			}
		});

		LOGGER.info("Hello Fabric world!");
	}
}