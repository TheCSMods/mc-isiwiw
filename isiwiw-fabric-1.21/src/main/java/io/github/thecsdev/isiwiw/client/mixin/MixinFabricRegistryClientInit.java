package io.github.thecsdev.isiwiw.client.mixin;

import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.fabric.api.client.networking.v1.ClientConfigurationNetworking;
import net.fabricmc.fabric.impl.client.registry.sync.FabricRegistryClientInit;
import net.fabricmc.fabric.impl.registry.sync.RegistrySyncManager;
import net.fabricmc.fabric.impl.registry.sync.SyncCompletePayload;
import net.fabricmc.fabric.impl.registry.sync.packet.RegistryPacketHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

/**
 * Client-sided Mixin.
 * Prevents 'Fabric API' from forcing users to install mods present on the server.
 */
@Mixin(value = FabricRegistryClientInit.class, remap = false, priority = 0)
public abstract class MixinFabricRegistryClientInit
{
	// ==================================================
	private   static   @Shadow Logger LOGGER;
	protected abstract @Shadow Text   getText(Throwable e);
	// ==================================================
	@Inject(method = "registerSyncPacketReceiver", at = @At("HEAD"), cancellable = true, require = 0)
	private <T extends RegistryPacketHandler.RegistrySyncPayload> void onRegisterSyncPacketReceiver(
			RegistryPacketHandler<T> packetHandler,
			CallbackInfo ci)
	{
		//modified ISIWIW behavior
		ClientConfigurationNetworking.registerGlobalReceiver(packetHandler.getPacketId(), (payload, context) ->
		{
			final var client = MinecraftClient.getInstance();
			RegistrySyncManager
				.receivePacket(client, packetHandler, payload, RegistrySyncManager.DEBUG || !client.isInSingleplayer())
				.whenComplete((complete, throwable) ->
				{
					if(throwable != null)
					{
						LOGGER.error("Registry remapping failed!", throwable);
						//client.execute(() -> context.responseSender().disconnect(getText(throwable))); -- DO NOT KICK!
						return;
					}
					if(complete) context.responseSender().sendPacket(SyncCompletePayload.INSTANCE);
				});
		});
		
		//DO NOT use Fabric's mechanism.
		ci.cancel();
	}
	// ==================================================
}