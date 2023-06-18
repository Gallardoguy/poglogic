package net.Slash.poglogic.block.entity;

import net.Slash.poglogic.PogLogic;
import net.Slash.poglogic.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, PogLogic.MOD_ID);
    
    public static final RegistryObject<BlockEntityType<ClockEntity>> CLOCK = BLOCK_ENTITIES.register("clock", () -> BlockEntityType.Builder.of(ClockEntity::new, ModBlocks.CLOCK.get()).build(null));
    
    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
    
}
