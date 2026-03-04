package com.example.sizemod.commands;

import com.example.sizemod.SizeHelper;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class SizeCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
            Commands.literal("size")
                .requires(source -> source.hasPermission(0))
                .then(Commands.literal("set")
                    .then(Commands.argument("value", FloatArgumentType.floatArg(SizeHelper.MIN_SIZE, SizeHelper.MAX_SIZE))
                        .executes(ctx -> setSize(ctx, FloatArgumentType.getFloat(ctx, "value")))))
                .then(Commands.literal("grow")
                    .executes(ctx -> adjustSize(ctx, SizeHelper.STEP)))
                .then(Commands.literal("shrink")
                    .executes(ctx -> adjustSize(ctx, -SizeHelper.STEP)))
                .then(Commands.literal("reset")
                    .executes(ctx -> setSize(ctx, 1.0f)))
                .then(Commands.literal("get")
                    .executes(SizeCommand::getSize))
        );
    }

    private static int setSize(CommandContext<CommandSourceStack> ctx, float size) {
        if (!(ctx.getSource().getEntity() instanceof ServerPlayer player)) {
            ctx.getSource().sendFailure(Component.literal("This command can only be used by players."));
            return 0;
        }

        float newSize = SizeHelper.setSize(player, size);
        if (newSize < 0) {
            ctx.getSource().sendFailure(Component.literal("Scale attribute not available."));
            return 0;
        }

        ctx.getSource().sendSuccess(() -> Component.literal("Size set to " + newSize + "x"), false);
        return 1;
    }

    private static int adjustSize(CommandContext<CommandSourceStack> ctx, float delta) {
        if (!(ctx.getSource().getEntity() instanceof ServerPlayer player)) {
            ctx.getSource().sendFailure(Component.literal("This command can only be used by players."));
            return 0;
        }

        float newSize = SizeHelper.adjustSize(player, delta);
        if (newSize < 0) {
            ctx.getSource().sendFailure(Component.literal("Scale attribute not available."));
            return 0;
        }

        String action = delta > 0 ? "grown" : "shrunk";
        ctx.getSource().sendSuccess(() ->
            Component.literal("You have " + action + "! Size: " + String.format("%.2f", newSize) + "x"), false);
        return 1;
    }

    private static int getSize(CommandContext<CommandSourceStack> ctx) {
        if (!(ctx.getSource().getEntity() instanceof ServerPlayer player)) {
            ctx.getSource().sendFailure(Component.literal("This command can only be used by players."));
            return 0;
        }

        float size = SizeHelper.getSize(player);
        if (size < 0) {
            ctx.getSource().sendFailure(Component.literal("Scale attribute not available."));
            return 0;
        }

        ctx.getSource().sendSuccess(() ->
            Component.literal("Your current size: " + String.format("%.2f", size) + "x"), false);
        return 1;
    }
}
