package io.github.thecsdev.isiwiw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ISIWIW extends Object
{
	// ==================================================
	public static final Logger LOGGER = LoggerFactory.getLogger(getModID());
	// --------------------------------------------------
	static final String ModName = "I shall install whatever I want";
	static final String ModID = "isiwiw";
	private static ISIWIW Instance;
	// --------------------------------------------------
	//protected final ModContainer modInfo; - avoid platform-specific APIs
	// ==================================================
	public ISIWIW()
	{
		//validate instance first
		if(isModInitialized())
			throw new IllegalStateException(getModID() + " has already been initialized.");
		else if(!isInstanceValid(this))
			throw new UnsupportedOperationException("Invalid " + getModID() + " type: " + this.getClass().getName());
		
		//assign instance
		Instance = this;
		//modInfo = FabricLoader.getInstance().getModContainer(getModID()).get();
		
		//log stuff
		/*LOGGER.info("Initializing '" + getModName() + "' " + modInfo.getMetadata().getVersion() +
				" as '" + getClass().getSimpleName() + "'.");*/
		LOGGER.info("Initializing '" + getModName() + "' as '" + getClass().getSimpleName() + "'.");
		
		//init stuff
		//TODO - Initialize common stuff here (client/dedicated-server/internal-server)
	}
	// ==================================================
	public static ISIWIW getInstance() { return Instance; }
	//public ModContainer getModInfo() { return modInfo; }
	// --------------------------------------------------
	public static String getModName() { return ModName; }
	public static String getModID() { return ModID; }
	// --------------------------------------------------
	public static boolean isModInitialized() { return isInstanceValid(Instance); }
	private static boolean isInstanceValid(ISIWIW instance) { return isServer(instance) || isClient(instance); }
	// --------------------------------------------------
	public static boolean isServer() { return isServer(Instance); }
	public static boolean isClient() { return isClient(Instance); }
	
	private static boolean isServer(ISIWIW arg0) { return arg0 instanceof io.github.thecsdev.isiwiw.server.ISIWIWServer; }
	private static boolean isClient(ISIWIW arg0) { return arg0 instanceof io.github.thecsdev.isiwiw.client.ISIWIWClient; }
	// ==================================================
}