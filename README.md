# ISIWIW
'I shall install whatever I want' is a Minecraft mod that aims to allow clients to bypass some installation requirements made by servers, such as installing certain mods and resource packs. This mod is optional on both sides, meaning, you do not have to have it installed on the server in order to use it on the client, and vice-versa.

#### When installed on a client, this mod will:
- Disable (Fabric API)'s "registry synchronization" mechanism, effectively preventing Fabric from forcing the client to install certain mods.
    - Note that this will not work out for mods that add modded features such as blocks, items, and entities, because for obvious reasons... how on Earth are you supposed to play on a modded Minecraft server with modded features if your client doesn't even know what said feature are or what they do or what they look like and so on? The simple answer is, you can't. In those cases, you will have no choice but to install the mods.
	- The goal here is to allow "optional" server-sided mods to actually behave like "optional" mods, without having Fabric interfering and unnecessarily forcing clients to install said mods.
- Prevent servers from forcing the client to install a resource pack before joining.
    - The user will be prompted to download the resource pack as usual, however, if the user selects "No", rather than getting kicked out by the server, the client will instad lie to the server by telling the server that the user selected "Yes", even though the user actually selected "No". This will effectively trick the server into thinking the client has the resource pack applied, even though they don't.

#### When installed on a server, this mod will:
- Disable (Fabric API)'s "registry synchronization" mechanism, effectively preventing Fabric from forcing the client to install certain mods.
	- Same effect as when installed on a client, except in this case, clients will not have to download this mod on their own, as the server-sided version of this mod will do the job for them.
- Disable the effect of the "require-resource-pack" server property.
    - You can still set the property to whichever value you want, but it will have no effect. This mod will ensure no client is forced to download a resource pack.

### Requirements (v1.0+):
- [Installing this mod: Client (Optional) - Server (Optional)
- Installing literally anything else, anywhere: Not required

### Compatibility notice
As a client, attempting to ignore installing mods that add modded features (such as blocks, items, and mobs) could result crashes, data loss/corruption, and a bunch of other bad side-effects. Be absolutely sure you know what you're doing here when using this mod. Although player data is handled on the server-side, it is still recommended to exercise caution.

If however, a server has mods that add server-sided features such as commands and worldgen stuff for example, then this should be 100% safe. The only difference here will be that Fabric will not attempt to force you to install those mods when you don't even need them.
Fun fact: This is the main reason I made this mod in the first place. A friend of mine kept being forced to install [BSS](modrinth.com/mod/n6PXGAoM), despite the fact that one of the only server-sided features it added was the `/stats` command, and for some reason that was triggering Fabric. So with this mod present, that issue turned from "is" to "was".

If there are any mods out there that break when used alongside this mod, feel free to report those issues, and we'll see if resolutions are possible.

#### Quilt notice for v1.0
As of v1.0, I did not test this with Quilt. I never even worked with Quilt before. If however, Quilt has their own mechanisms that force clients to install mods and stuff, then I am willing to look into them and see if it's possible to do something about them as well (If possible; I will not make any promises about Quilt).