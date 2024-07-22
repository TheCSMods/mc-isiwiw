package io.github.thecsdev.isiwiw.mixin.security;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.thecsdev.isiwiw.Isiwiw;
import io.github.thecsdev.isiwiw.IsiwiwFabric;

/**
 * This {@link Mixin} is responsible for detecting static initializers in {@link IsiwiwFabric}.
 * Upon detection, this {@link Mixin} will crash the game, preventing initializer code from executing.<br/>
 * <br/>
 * This is done for security purposes, as there are many viruses out there that spread by injecting
 * code into static initializers. This {@link Mixin}'s goal is to prevent that code from executing.
 */
@Mixin(value = IsiwiwFabric.class, priority = 9001)
public abstract class MixinModLoader
{
	@Inject(method = "<clinit>", at = @At("HEAD"), cancellable = true, require = 0)
	private static void onClassInit(CallbackInfo callback)
	{
		//construct the message
		final var fullName = IsiwiwFabric.class.getName();
		final var message = String.format(
				"AN INTEGRITY VIOLATION WAS FOUND:\n"
				+ "The class '%s' has a static initializer, which isn't allowed!\n"
				+ "This static initializer could've been added there by mistake, or maliciously injected by a virus.\n"
				+ "For security reasons, the game will now crash. Please run a virus scan in the meantime.\n"
				+ "The affected mod's ID is '%s'.", fullName, Isiwiw.getModID());
		
		//terminate the program
		throw new ExceptionInInitializerError(message);
		
		/* ^ IMPORTANT NOTE: if you have a static constructor defined, or a static field defined,
		 * the above code WILL end up always executing, even when it isn't supposed to, which is bad.
		 */
	}
}