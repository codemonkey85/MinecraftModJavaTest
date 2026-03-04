package com.example.sizemod;

import com.example.sizemod.commands.SizeCommand;
import com.example.sizemod.items.SizeChangerItem;
import com.mojang.logging.LogUtils;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

@Mod(SizeMod.MOD_ID)
public class SizeMod {

    public static final String MOD_ID = "sizemod";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);

    public static final DeferredItem<Item> GROW_CRYSTAL = ITEMS.register("grow_crystal",
        () -> new SizeChangerItem(new Item.Properties(), SizeHelper.STEP));

    public static final DeferredItem<Item> SHRINK_CRYSTAL = ITEMS.register("shrink_crystal",
        () -> new SizeChangerItem(new Item.Properties(), -SizeHelper.STEP));

    public SizeMod(IEventBus modEventBus, ModContainer modContainer) {
        ITEMS.register(modEventBus);
        NeoForge.EVENT_BUS.register(this);
        LOGGER.info("Size Mod loaded!");
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        SizeCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public void onBuildCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS) {
            event.accept(GROW_CRYSTAL);
            event.accept(SHRINK_CRYSTAL);
        }
    }
}
