package net.Slash.poglogic.item;

import net.Slash.poglogic.block.ModBlocks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
    public static final CreativeModeTab POG_TAB = new CreativeModeTab("pogtab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModBlocks.ANDGATE.get());
        }
    };
}
