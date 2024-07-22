package io.github.thecsdev.isiwiw;

import static io.github.thecsdev.isiwiw.Isiwiw.getModID;

import java.io.File;
import java.nio.file.InvalidPathException;

import io.github.thecsdev.tcdcommons.api.config.AutoConfig;

/**
 * A default config {@link File} for {@link Isiwiw}.<br/>
 * @apiNote This mod's developer may add their own custom config properties for this mod here.
 */
public final class IsiwiwConfig extends AutoConfig
{
	// ==================================================
	/* 
	 * This is how you define your own custom config properties.
	 * Just simply define a primitive-type variable, and the config
	 * module will handle the saving and loading for you.
	 * 
	 * Note that the field has to be public, and must not be static or final.
	 */
	// public boolean enableSomeRandomFeature = true;
	
	/* 
	 * You may also use the 'SerializedAs' annotation to tell the
	 * config module what json key to use when saving a given property.
	 */
	// public @SerializedAs("my-custom-json-property-name") int number = 5;
	
	/* 
	 * Additionally, you may use the `NonSerialized` annotation to tell
	 * the config module to not save and load a given property at all.
	 */
	// public @NonSerialized String unsavedProperty = "Hello world!";
	// ==================================================
	public IsiwiwConfig() throws InvalidPathException { super(getModID()); }
	// ==================================================
}