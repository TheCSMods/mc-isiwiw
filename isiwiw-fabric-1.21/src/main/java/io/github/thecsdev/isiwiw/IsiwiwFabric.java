package io.github.thecsdev.isiwiw;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.DedicatedServerModInitializer;

/**
 * Fabric Mod Loader entry-points for this mod.
 * @apiNote Do not change anything in here. Leave it as-is.
 * @apiNote Do not under any circumstances put static initializers or variables in here!
 */
public final class IsiwiwFabric implements ClientModInitializer, DedicatedServerModInitializer
{
	// ==================================================
	public @Override void onInitializeClient() { new io.github.thecsdev.isiwiw.client.IsiwiwClient(); }
	public @Override void onInitializeServer() { new io.github.thecsdev.isiwiw.server.IsiwiwServer(); }
	// ==================================================
}