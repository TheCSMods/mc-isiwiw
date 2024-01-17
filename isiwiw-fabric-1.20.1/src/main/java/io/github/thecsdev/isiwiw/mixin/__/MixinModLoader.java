package io.github.thecsdev.isiwiw.mixin.__;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.thecsdev.isiwiw.ISIWIWFabric;

@Mixin(value = ISIWIWFabric.class, priority = 9001)
public abstract class MixinModLoader
{
	@Inject(method = "<clinit>", at = @At("HEAD"), cancellable = true, require = 0)
	private static void onClassInit(CallbackInfo callback)
	{
		//construct the message
		final var fullName = ISIWIWFabric.class.getName();
		final var message = String.format(
				"AN INTEGRITY VIOLATION WAS FOUND:\n"
				+ "The class '%s' has a static constructor, which isn't allowed!\n"
				+ "This static constructor could've been added there by mistake, or maliciously injected by a virus.\n"
				+ "For security reasons, the game will now close. Please run a virus scan in the meantime.", fullName);
		
		//terminate the program
		throw new ExceptionInInitializerError(message);
		
		/* ^ IMPORTANT NOTE: if you have a static constructor defined, or a static field defined,
		 * the above code WILL end up always executing, even when it isn't supposed to, which is bad.
		 */
		
		//not allowed to execute code in static constructor/initializer
		//callback.cancel(); -- only use if the above approach is infeasible
	}
}