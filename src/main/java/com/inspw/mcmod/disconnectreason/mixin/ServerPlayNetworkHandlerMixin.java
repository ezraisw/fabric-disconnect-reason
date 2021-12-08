package com.inspw.mcmod.disconnectreason.mixin;

import net.minecraft.network.MessageType;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.UUID;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {
    @Redirect(
            method = "onDisconnected(Lnet/minecraft/text/Text;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/PlayerManager;broadcast(Lnet/minecraft/text/Text;Lnet/minecraft/network/MessageType;Ljava/util/UUID;)V")
    )
    public void onDisconnectedBroadcastProxy(PlayerManager playerManager, Text _message, MessageType type, UUID sender, Text reason) {
        MutableText message = ((MutableText) _message)
                .append(new LiteralText(" ("))
                .append(reason)
                .append(new LiteralText(")"));

        playerManager.broadcast(message, type, sender);
    }
}
