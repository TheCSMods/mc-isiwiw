package io.github.thecsdev.isiwiw.client.mixin;

import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.thecsdev.isiwiw.ISIWIW;
import net.fabricmc.fabric.api.client.networking.v1.ClientConfigurationNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.impl.client.registry.sync.FabricRegistryClientInit;
import net.fabricmc.fabric.impl.registry.sync.FabricRegistryInit;
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
	protected abstract @Shadow Text getText(Throwable e);
	// ==================================================
	@Inject(method = "registerSyncPacketReceiver", at = @At("HEAD"), cancellable = true, require = 0)
	private void onRegisterSyncPacketReceiver(RegistryPacketHandler packetHandler, CallbackInfo ci)
	{
		//register the configuration channel handler
		ClientConfigurationNetworking.registerGlobalReceiver(packetHandler.getPacketId(), (client, handler, buf, responseSender) ->
		{
			RegistrySyncManager.receivePacket(client, packetHandler, buf, RegistrySyncManager.DEBUG || !client.isInSingleplayer())
				.whenComplete((complete, throwable) ->
				{
					//if there's a throwable, then the synch failed. just log it
					if (throwable != null)
					{
						//log the registry synchronization failure
						LOGGER.error("Registry remapping failed!", throwable);
						ISIWIW.LOGGER.warn("Preventing 'Fabric API' from kicking the client...");
						
						//do not kick!
						/*client.execute(() -> ((ClientCommonNetworkHandlerAccessor) handler)
								.getConnection().disconnect(getText(throwable))); -- THIS IS TO BE REMOVED!*/
					}
					
					//i guess lie to the server about sync completion?
					handler.sendPacket(ClientConfigurationNetworking.createC2SPacket(
							FabricRegistryInit.SYNC_COMPLETE_ID, PacketByteBufs.create()));
				});
		});

		//DO NOT use Fabric's mechanism.
		ci.cancel();
	}
	// ==================================================
}