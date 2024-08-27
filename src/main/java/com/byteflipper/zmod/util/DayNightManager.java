package com.byteflipper.zmod.util;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.GameRules;

/**
 * Класс DayNightManager отвечает за управление временем суток на сервере Minecraft.
 * Он позволяет настраивать длительность дня и ночи, переключать время суток,
 * а также управлять конфигурацией мода.
 * <p>
 * © ByteFlipper
 */
public class DayNightManager {

    private static long dayLength = 24000; // Длина дня в тиках (по умолчанию 24000 тиков = 20 минут)
    private static long nightLength = 24000; // Длина ночи в тиках (по умолчанию 24000 тиков = 20 минут)
    private static boolean isDay = true; // Текущая фаза времени суток (true - день, false - ночь)
    private static boolean dayEnabled = true; // Флаг для включения/отключения дня
    private static boolean nightEnabled = true; // Флаг для включения/отключения ночи
    private static boolean modConfigEnabled = false; // Опциональная настройка мода для автоматического переключения времени суток

    /**
     * Метод initialize() инициализирует DayNightManager и регистрирует обработчик тиков сервера.
     */
    public static void initialize() {
        // Регистрируем обработчик события тика сервера
        ServerTickEvents.END_SERVER_TICK.register(DayNightManager::onServerTick);
    }

    /**
     * Метод onServerTick(MinecraftServer server) обрабатывает тики сервера и управляет временем суток.
     *
     * @param server объект MinecraftServer, представляющий текущий сервер Minecraft.
     */
    private static void onServerTick(MinecraftServer server) {
        if (!dayEnabled && !nightEnabled) return; // Если день и ночь отключены, выходим из метода

        long timeOfDay = server.getOverworld().getTimeOfDay() % (dayLength + nightLength);

        // Проверяем, нужно ли переключить время суток (только если и день, и ночь включены)
        if (dayEnabled && nightEnabled) {
            if (isDay && timeOfDay >= dayLength) {
                isDay = false; // Переключаем на ночь
            } else if (!isDay && timeOfDay >= dayLength + nightLength) {
                isDay = true; // Переключаем на день
                server.getOverworld().setTimeOfDay(0); // Сбрасываем время дня
            }
        }

        // Если включена только ночь, устанавливаем полночь
        if (!dayEnabled && nightEnabled) {
            server.getOverworld().setTimeOfDay(18000); // Полночь
            return;
        }

        // Если включён только день, устанавливаем полдень
        if (!nightEnabled && dayEnabled) {
            server.getOverworld().setTimeOfDay(6000); // Полдень
            return;
        }

        // Управление временем суток в зависимости от текущей фазы дня
        if (isDay) {
            if (dayEnabled) {
                server.getOverworld().getGameRules().get(GameRules.DO_DAYLIGHT_CYCLE).set(true, server);
                server.getOverworld().setTimeOfDay(server.getOverworld().getTimeOfDay() + (24000 / dayLength));
            }
        } else {
            if (nightEnabled) {
                server.getOverworld().getGameRules().get(GameRules.DO_DAYLIGHT_CYCLE).set(false, server);
                server.getOverworld().setTimeOfDay(server.getOverworld().getTimeOfDay() + (24000 / nightLength));
            }
        }
    }

    /**
     * Метод toggleDayNight(MinecraftServer server, boolean isDayCommand, ServerPlayerEntity player)
     * отвечает за переключение времени суток через команду пользователя.
     *
     * @param server      объект MinecraftServer, представляющий текущий сервер Minecraft.
     * @param isDayCommand логическое значение, указывающее, была ли вызвана команда для дня (true) или ночи (false).
     * @param player      объект ServerPlayerEntity, представляющий игрока, вызвавшего команду.
     */
    public static void toggleDayNight(MinecraftServer server, boolean isDayCommand, ServerPlayerEntity player) {
        // Логика переключения времени суток через команду с учётом настройки мода
        if (modConfigEnabled) {
            if (isDayCommand && nightEnabled) {
                setDayEnabled(false);
                setNightEnabled(true);
                server.getOverworld().setTimeOfDay(18000); // Полночь
                sendMessage(player, "День отключен модом. Включена ночь.");
            } else if (!isDayCommand && dayEnabled) {
                setDayEnabled(true);
                setNightEnabled(false);
                server.getOverworld().setTimeOfDay(6000); // Полдень
                sendMessage(player, "Ночь отключена модом. Включен день.");
            }
        }
    }

    /**
     * Метод sendMessage(ServerPlayerEntity player, String message) отправляет сообщение в чат игрока.
     *
     * @param player  объект ServerPlayerEntity, представляющий игрока, которому нужно отправить сообщение.
     * @param message строка с текстом сообщения, которое будет отправлено игроку.
     */
    private static void sendMessage(ServerPlayerEntity player, String message) {
        if (player != null) {
            player.sendMessage(Text.literal(message), false);
        }
    }

    /**
     * Метод setDayLength(long length) устанавливает длину дня в тиках.
     *
     * @param length длина дня в тиках (1 тик = 1/20 секунды).
     */
    public static void setDayLength(long length) {
        dayLength = length;
    }

    /**
     * Метод setNightLength(long length) устанавливает длину ночи в тиках.
     *
     * @param length длина ночи в тиках (1 тик = 1/20 секунды).
     */
    public static void setNightLength(long length) {
        nightLength = length;
    }

    /**
     * Метод getDayLength() возвращает текущую длину дня в тиках.
     *
     * @return длина дня в тиках.
     */
    public static long getDayLength() {
        return dayLength;
    }

    /**
     * Метод getNightLength() возвращает текущую длину ночи в тиках.
     *
     * @return длина ночи в тиках.
     */
    public static long getNightLength() {
        return nightLength;
    }

    /**
     * Метод setDayEnabled(boolean enabled) включает или отключает день.
     *
     * @param enabled логическое значение, указывающее, нужно ли включить (true) или отключить (false) день.
     */
    public static void setDayEnabled(boolean enabled) {
        dayEnabled = enabled;
    }

    /**
     * Метод setNightEnabled(boolean enabled) включает или отключает ночь.
     *
     * @param enabled логическое значение, указывающее, нужно ли включить (true) или отключить (false) ночь.
     */
    public static void setNightEnabled(boolean enabled) {
        nightEnabled = enabled;
    }

    /**
     * Метод setModConfigEnabled(boolean enabled) включает или отключает настройку мода для автоматического
     * переключения времени суток при использовании команд.
     *
     * @param enabled логическое значение, указывающее, нужно ли включить (true) или отключить (false) автоматическую конфигурацию мода.
     */
    public static void setModConfigEnabled(boolean enabled) {
        modConfigEnabled = enabled;
    }

    /**
     * Метод isModConfigEnabled() возвращает текущее состояние настройки мода для автоматического переключения времени суток.
     *
     * @return true, если настройка мода включена, false в противном случае.
     */
    public static boolean isModConfigEnabled() {
        return modConfigEnabled;
    }
}
