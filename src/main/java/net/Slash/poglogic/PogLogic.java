package net.Slash.poglogic;

import com.mojang.logging.LogUtils;
import net.Slash.poglogic.block.ModBlocks;
import net.Slash.poglogic.block.entity.ModBlockEntities;
import net.Slash.poglogic.item.testItem;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

@Mod(PogLogic.MOD_ID)
public class PogLogic {
    public static final String MOD_ID = "poglogic";
    private static final Logger LOGGER = LogUtils.getLogger();

    public PogLogic() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
    
    
        testItem.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
        
        }
    }
}
