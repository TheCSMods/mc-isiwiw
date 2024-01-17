package io.github.thecsdev.isiwiw.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.common.ResourcePackSendS2CPacket;
import net.minecraft.text.Text;

/**
 * Common-sided Mixin.
 * This Mixin prevents Minecraft servers from marking their own resource packs as "required".
 * Note that this renders the "require-resource-pack" server property useless.
 */
@Mixin(ResourcePackSendS2CPacket.class)
public abstract class MixinResourcePackSendS2CPacket
{
	private @Mutable @Shadow boolean required;
	
	@Inject(method = "<init>(Ljava/lang/String;Ljava/lang/String;ZLnet/minecraft/text/Text;)V", at = @At("RETURN"))
	private void onConstructor(String url, String hash, boolean required, Text prompt, CallbackInfo ci) { this.required = false; }

	@Inject(method = "<init>(Lnet/minecraft/network/PacketByteBuf;)V", at = @At("RETURN"))
	private void onConstructor(PacketByteBuf buf, CallbackInfo ci) { this.required = false; }
	
	@Redirect(method = "isRequired()Z", at = @At(value = "FIELD", target = "Lnet/minecraft/network/packet/s2c/common/ResourcePackSendS2CPacket;required:Z"))
	private boolean redirectIsRequired(ResourcePackSendS2CPacket instance) { return false; }
}