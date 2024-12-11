package com.inspw.mcmod.disconnectreason.mixin;

import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {
    @Redirect(method = "onDisconnected(Lnet/minecraft/text/Text;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/PlayerManager;broadcast(Lnet/minecraft/text/Text;Z)V"))
    public void onDisconnectedBroadcastProxy(PlayerManager playerManager, Text _message, boolean overlay, Text reason) {
        MutableText message = ((MutableText) _message)
                .append(" (")
                .append(reason)
                .append(")");

        playerManager.broadcast(message, overlay);
    }
}
