package com.example.sizemod.items;

import com.example.sizemod.SizeHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * An item that adjusts the using player's size by a fixed step on right-click.
 * Pass a positive delta to grow, negative to shrink.
 */
public class SizeChangerItem extends Item {

    private final float delta;

    public SizeChangerItem(Properties properties, float delta) {
        super(properties);
        this.delta = delta;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide) {
            return InteractionResultHolder.pass(player.getItemInHand(hand));
        }

        float newSize = SizeHelper.adjustSize(player, delta);
        if (newSize < 0) {
            player.sendSystemMessage(Component.literal("Scale attribute not available."));
            return InteractionResultHolder.fail(player.getItemInHand(hand));
        }

        String action = delta > 0 ? "grown" : "shrunk";
        player.sendSystemMessage(
            Component.literal("You have " + action + "! Size: " + String.format("%.2f", newSize) + "x")
        );
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }
}
