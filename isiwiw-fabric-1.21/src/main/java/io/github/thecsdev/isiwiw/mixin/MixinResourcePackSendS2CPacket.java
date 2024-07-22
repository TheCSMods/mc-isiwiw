package io.github.thecsdev.isiwiw.mixin;

import java.util.Optional;
import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.network.packet.s2c.common.ResourcePackSendS2CPacket;
import net.minecraft.text.Text;

/**
 * Common-sided Mixin.
 * This Mixin prevents Minecraft servers from marking their own resource packs as "required".
 * Note that this renders the "require-resource-pack" server property useless.
 */
@Mixin(value = ResourcePackSendS2CPacket.class, priority = 0)
public abstract class MixinResourcePackSendS2CPacket
{
	// ==================================================
	private @Mutable @Shadow boolean required;
	// ==================================================
	@Inject(
			method = "<init>(Ljava/util/UUID;Ljava/lang/String;Ljava/lang/String;ZLjava/util/Optional;)V",
			at = @At("RETURN"),
			require = 0
		)
	private void onConstructor(UUID uUID, String url, String hash, boolean required, Optional<Text> prompt, CallbackInfo ci)
	{
		this.required = false;
	}
	// --------------------------------------------------
	@Inject(method = "required()Z", at = @At(value = "HEAD"), cancellable = true, require = 0)
	private void onRequired(CallbackInfoReturnable<Boolean> ci)
	{
		ci.setReturnValue(false);
		ci.cancel();
	}
	// ==================================================
}