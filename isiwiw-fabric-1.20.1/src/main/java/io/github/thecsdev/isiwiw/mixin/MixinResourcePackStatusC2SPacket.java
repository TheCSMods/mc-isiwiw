package io.github.thecsdev.isiwiw.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.ResourcePackStatusC2SPacket;

/**
 * Common-sided Mixin.
 * This Mixin works alongside {@link MixinResourcePackSendS2CPacket}, and prevents
 * the client from telling the server that it ever rejected the resource pack.
 * 
 * TLDR;
 * - In vanilla, if you reject the pack, the client will tell the server you rejected it.
 * - In here, if you reject the pack, the client will lie to the server that you accepted it and successfully loaded it.
 */
@Mixin(ResourcePackStatusC2SPacket.class)
public abstract class MixinResourcePackStatusC2SPacket
{
	private @Shadow @Mutable ResourcePackStatusC2SPacket.Status status;

	@Inject(method = "<init>(Lnet/minecraft/network/packet/c2s/play/ResourcePackStatusC2SPacket$Status;)V", at = @At("RETURN"))
	private void onConstructor(ResourcePackStatusC2SPacket.Status status, CallbackInfo ci)
	{
		if (this.status == ResourcePackStatusC2SPacket.Status.DECLINED)
			this.status = ResourcePackStatusC2SPacket.Status.SUCCESSFULLY_LOADED;
	}

	@Inject(method = "<init>(Lnet/minecraft/network/PacketByteBuf;)V", at = @At("RETURN"))
	private void onConstructor(PacketByteBuf buf, CallbackInfo ci)
	{
		if (this.status == ResourcePackStatusC2SPacket.Status.DECLINED)
			this.status = ResourcePackStatusC2SPacket.Status.SUCCESSFULLY_LOADED;
	}
}