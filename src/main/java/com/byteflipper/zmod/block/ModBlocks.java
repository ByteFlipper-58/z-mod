package com.byteflipper.zmod.block;

import com.byteflipper.zmod.ZMod;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

/**
 * Класс для регистрации пользовательских блоков в моде.
 */
public class ModBlocks {

    public static final Block FLOWER_FLAME = registerBlock("flower_flame", new FlowerFlameBlock());

    /**
     * Регистрирует блок в реестре блоков и одновременно регистрирует соответствующий блоковый элемент.
     *
     * @param name  Строка, представляющая имя блока для регистрации.
     * @param block Экземпляр блока, который нужно зарегистрировать.
     * @return Возвращает зарегистрированный блок.
     */
    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(ZMod.MOD_ID, name), block);
    }

    /**
     * Регистрирует элемент для блока в реестре предметов.
     *
     * @param name  Строка, представляющая имя элемента, связанного с блоком.
     * @param block Экземпляр блока, для которого создается элемент.
     */
    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(ZMod.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    /**
     * Метод для регистрации всех блоков мода и их добавления в определенные группы предметов.
     * Этот метод вызывается при инициализации мода.
     */
    public static void registerModBlocks() {
        ZMod.LOGGER.info("Registering Mod Blocks for " + ZMod.MOD_ID);

        // Добавляет зарегистрированные блоки в группу NATURAL
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> {
            entries.add(FLOWER_FLAME);
        });
    }
}
