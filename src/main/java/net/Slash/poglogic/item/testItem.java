package net.Slash.poglogic.item;

import net.Slash.poglogic.PogLogic;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class testItem {
    public  static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PogLogic.MOD_ID);
    
    
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
    
}
