package com.byteflipper.zmod.util;

import net.minecraft.server.world.ServerWorld;

/**
 * Управляет изменением времени суток в Minecraft.
 */
public class DayNightManager {

    // Длительность дня в тиках (по умолчанию 10 минут)
    private static long dayLength = 12000L;
    // Длительность ночи в тиках (по умолчанию 10 минут)
    private static long nightLength = 12000L;
    // Включён ли день
    private static boolean dayEnabled = true;
    // Включена ли ночь
    private static boolean nightEnabled = true;

    /**
     * Устанавливает длительность дня.
     *
     * @param length Длительность дня в тиках.
     */
    public static void setDayLength(long length) {
        dayLength = length;
    }

    /**
     * Устанавливает длительность ночи.
     *
     * @param length Длительность ночи в тиках.
     */
    public static void setNightLength(long length) {
        nightLength = length;
    }

    /**
     * Возвращает длительность дня в тиках.
     *
     * @return Длительность дня.
     */
    public static long getDayLength() {
        return dayLength;
    }

    /**
     * Возвращает длительность ночи в тиках.
     *
     * @return Длительность ночи.
     */
    public static long getNightLength() {
        return nightLength;
    }

    /**
     * Устанавливает время суток в мире.
     *
     * @param world Мир, в котором устанавливается время.
     * @param time Время суток в тиках.
     */
    public static void setTime(ServerWorld world, long time) {
        world.setTimeOfDay(time % (dayLength + nightLength));
    }

    /**
     * Устанавливает время на утро.
     *
     * @param world Мир, в котором устанавливается время.
     */
    public static void setDay(ServerWorld world) {
        if (dayEnabled) {
            setTime(world, 1000L); // Утро
        }
    }

    /**
     * Устанавливает время на вечер.
     *
     * @param world Мир, в котором устанавливается время.
     */
    public static void setNight(ServerWorld world) {
        if (nightEnabled) {
            setTime(world, dayLength + 1000L); // Вечер
        }
    }

    /**
     * Замораживает или возобновляет смену дня и ночи.
     *
     * @param world Мир, в котором нужно заморозить или возобновить время.
     * @param freeze Если true, время замораживается. Если false, смена времени возобновляется.
     */
    /*public static void freezeTime(ServerWorld world, boolean freeze) {
        if (freeze) {
            world.getWorldInfo().setDayTime(world.getTime());
        } else {
            world.getWorldInfo().setDayTime(-1); // Возобновляет смену дня и ночи
        }
    }*/

    /**
     * Увеличивает или уменьшает длительность дня.
     *
     * @param delta Изменение длительности дня в тиках. Может быть отрицательным для уменьшения.
     */
    public static void adjustDayLength(long delta) {
        dayLength = Math.max(0, dayLength + delta); // Предотвращает отрицательные значения
    }

    /**
     * Увеличивает или уменьшает длительность ночи.
     *
     * @param delta Изменение длительности ночи в тиках. Может быть отрицательным для уменьшения.
     */
    public static void adjustNightLength(long delta) {
        nightLength = Math.max(0, nightLength + delta); // Предотвращает отрицательные значения
    }

    /**
     * Отключает или включает день.
     *
     * @param enabled Если true, день включается. Если false, день отключается.
     */
    public static void setDayEnabled(boolean enabled) {
        dayEnabled = enabled;
        if (!enabled) {
            // Если день отключен, установите время на начало ночи
            ServerWorld world = getWorld(); // Получение мира, может потребоваться другой метод
            setTime(world, dayLength + 1000L);
        }
    }

    /**
     * Отключает или включает ночь.
     *
     * @param enabled Если true, ночь включается. Если false, ночь отключается.
     */
    public static void setNightEnabled(boolean enabled) {
        nightEnabled = enabled;
        if (!enabled) {
            // Если ночь отключена, установите время на начало дня
            ServerWorld world = getWorld(); // Получение мира, может потребоваться другой метод
            setTime(world, 1000L);
        }
    }

    /**
     * Проверяет, является ли текущее время днем.
     *
     * @param world Мир, в котором проверяется время.
     * @return true, если текущее время днем. Иначе false.
     */
    public static boolean isDay(ServerWorld world) {
        long time = world.getTimeOfDay();
        return dayEnabled && time >= 0L && time < dayLength;
    }

    /**
     * Проверяет, является ли текущее время ночью.
     *
     * @param world Мир, в котором проверяется время.
     * @return true, если текущее время ночью. Иначе false.
     */
    public static boolean isNight(ServerWorld world) {
        long time = world.getTimeOfDay();
        return nightEnabled && time >= dayLength && time < (dayLength + nightLength);
    }

    /**
     * Метод для получения мира. Реализуйте получение текущего мира в зависимости от контекста вашего мода.
     *
     * @return Мир, который будет использоваться для установки времени. Возвращает null по умолчанию.
     */
    private static ServerWorld getWorld() {
        // Реализуйте получение текущего мира здесь
        return null; // Замените на реальный метод получения мира
    }
}