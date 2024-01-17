package io.github.thecsdev.isiwiw.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.fabric.impl.client.registry.sync.FabricRegistryClientInit;

/**
 * Client-sided Mixin.
 * Prevents 'Fabric API' from forcing users to install mods present on the server.
 */
@Mixin(value = FabricRegistryClientInit.class, remap = false)
public abstract class MixinFabricRegistryClientInit
{
	@Inject(method = "onInitializeClient", at = @At("HEAD"), cancellable = true, require = 0)
	private void onOnInitializeClient(CallbackInfo ci)
	{
		//stop it Fabric, get some help...
		ci.cancel();
	}
}