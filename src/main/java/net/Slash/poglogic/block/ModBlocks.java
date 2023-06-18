package net.Slash.poglogic.block;

import net.Slash.poglogic.PogLogic;
import net.Slash.poglogic.block.custom.*;
import net.Slash.poglogic.item.ModCreativeModeTab;
import net.Slash.poglogic.item.testItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, PogLogic.MOD_ID);
    
    public static final RegistryObject<Block> ANDGATE = registerBlock("andgate",
            () -> new AndGate(BlockBehaviour.Properties.of(Material.HEAVY_METAL).strength(0.5f).noOcclusion()), ModCreativeModeTab.POG_TAB);
    
    public static final RegistryObject<Block> ORGATE = registerBlock("orgate",
            () -> new OrGate(BlockBehaviour.Properties.of(Material.HEAVY_METAL).strength(0.5f).noOcclusion()), ModCreativeModeTab.POG_TAB);
    
    public static final RegistryObject<Block> NORGATE = registerBlock("norgate",
            () -> new NorGate(BlockBehaviour.Properties.of(Material.HEAVY_METAL).strength(0.5f).noOcclusion()), ModCreativeModeTab.POG_TAB);
    
    public static final RegistryObject<Block> NANDGATE = registerBlock("nandgate",
            () -> new NandGate(BlockBehaviour.Properties.of(Material.HEAVY_METAL).strength(0.5f).noOcclusion()), ModCreativeModeTab.POG_TAB);
    
    public static final RegistryObject<Block> DFF = registerBlock("dff",
            () -> new Dff(BlockBehaviour.Properties.of(Material.HEAVY_METAL).strength(0.5f).noOcclusion()), ModCreativeModeTab.POG_TAB);
    
    public static final RegistryObject<Block> JKFF = registerBlock("jkff",
            () -> new Jkff(BlockBehaviour.Properties.of(Material.HEAVY_METAL).strength(0.5f).noOcclusion()), ModCreativeModeTab.POG_TAB);
    
    public static final RegistryObject<Block> INVERTER = registerBlock("inverter",
            () -> new Inverter(BlockBehaviour.Properties.of(Material.HEAVY_METAL).strength(0.5f).noOcclusion()), ModCreativeModeTab.POG_TAB);
    
    public static final RegistryObject<Block> CLOCK = registerBlock("clock",
            () -> new Clock(BlockBehaviour.Properties.of(Material.HEAVY_METAL).strength(0.5f).noOcclusion()), ModCreativeModeTab.POG_TAB);
    
    public static final RegistryObject<Block> XOR = registerBlock("xorgate",
            () -> new Xor(BlockBehaviour.Properties.of(Material.HEAVY_METAL).strength(0.5f).noOcclusion()), ModCreativeModeTab.POG_TAB);
    
    
    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }
    
    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab) {
        return testItem.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }
    
    
    
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
    
    
}
