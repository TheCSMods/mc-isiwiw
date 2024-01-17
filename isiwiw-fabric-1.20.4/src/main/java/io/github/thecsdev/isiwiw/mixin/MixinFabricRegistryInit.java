package io.github.thecsdev.isiwiw.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.fabric.impl.registry.sync.FabricRegistryInit;

/**
 * Common-sided Mixin.
 * Prevents 'Fabric API' from forcing users to install mods present on the server.
 */
@Mixin(value = FabricRegistryInit.class, remap = false)
public class MixinFabricRegistryInit
{
	@Inject(method = "onInitialize", at = @At("HEAD"), cancellable = true, require = 0)
	private void onOnInitialize(CallbackInfo ci)
	{
		//stop it Fabric, get some help...
		ci.cancel();
	}
}