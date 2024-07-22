package io.github.thecsdev.isiwiw.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.impl.registry.sync.FabricRegistryInit;
import net.fabricmc.fabric.impl.registry.sync.SyncCompletePayload;
import net.fabricmc.fabric.impl.registry.sync.packet.DirectRegistryPacketHandler;

/**
 * Common-sided Mixin.
 * Prevents 'Fabric API' from forcing users to install mods present on the server.
 */
@Mixin(value = FabricRegistryInit.class, remap = false)
public class MixinFabricRegistryInit
{
	// ==================================================
	@Inject(method = "onInitialize", at = @At("HEAD"), cancellable = true, require = 0)
	private void onOnInitialize(CallbackInfo ci)
	{
		//register required stuffs
		PayloadTypeRegistry.configurationC2S().register(SyncCompletePayload.ID, SyncCompletePayload.CODEC);
		PayloadTypeRegistry.configurationS2C().register(DirectRegistryPacketHandler.Payload.ID, DirectRegistryPacketHandler.Payload.CODEC);
		
		//and from there... stop it Fabric
		ci.cancel();
	}
	// ==================================================
}