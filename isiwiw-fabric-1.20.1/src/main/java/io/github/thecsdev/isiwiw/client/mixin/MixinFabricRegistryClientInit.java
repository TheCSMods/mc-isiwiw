package io.github.thecsdev.isiwiw.client.mixin;

import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.thecsdev.isiwiw.ISIWIW;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking.PlayChannelHandler;
import net.fabricmc.fabric.impl.client.registry.sync.FabricRegistryClientInit;
import net.fabricmc.fabric.impl.registry.sync.RegistrySyncManager;
import net.fabricmc.fabric.impl.registry.sync.packet.RegistryPacketHandler;
import net.minecraft.text.Text;

/**
 * Client-sided Mixin.
 * Prevents 'Fabric API' from forcing users to install mods present on the server.
 */
@Mixin(value = FabricRegistryClientInit.class, remap = false, priority = Integer.MAX_VALUE)
public abstract class MixinFabricRegistryClientInit
{
	// ==================================================
	private static @Shadow Logger LOGGER;
	protected abstract @Shadow Text getText(Exception e);
	// ==================================================
	@Inject(method = "registerSyncPacketReceiver", at = @At("HEAD"), cancellable = true, require = 0)
	private void onRegisterSyncPacketReceiver(RegistryPacketHandler packetHandler, CallbackInfo ci)
	{
		//create a play channel handler
		final PlayChannelHandler pch = (client, handler, buf, responseSender) ->
		{
			RegistrySyncManager.receivePacket(
					client, packetHandler, buf,
					RegistrySyncManager.DEBUG || !client.isInSingleplayer(), e ->
			{
				LOGGER.error("Registry remapping failed!", e);
				ISIWIW.LOGGER.warn("Preventing 'Fabric API' from kicking the client...");
				//client.execute(() -> handler.getConnection().disconnect(getText(e))); -- THIS IS TO BE REMOVED!
			});
		};
		
		//register the play channel handler
		ClientPlayNetworking.registerGlobalReceiver(packetHandler.getPacketId(), pch);
		
		//DO NOT use Fabric's mechanism.
		ci.cancel();
	}
	// ==================================================
}