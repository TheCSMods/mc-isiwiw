package io.github.thecsdev.isiwiw;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.DedicatedServerModInitializer;

/**
 * Fabric Mod Loader entry-points for this mod.
 */
public final class ISIWIWFabric implements ClientModInitializer, DedicatedServerModInitializer
{
	// ==================================================
	public @Override void onInitializeClient() { new io.github.thecsdev.isiwiw.client.ISIWIWClient(); }
	public @Override void onInitializeServer() { new io.github.thecsdev.isiwiw.server.ISIWIWServer(); }
	// ==================================================
}