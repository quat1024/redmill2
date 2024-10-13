# Red Mill

i tried this before last modfest but didn't finish. redmill 1's code is private and it never came out. this is a full rewrite that doesn't use the original code

## credits

obviously contains lots of code adapted from FML and Minecraft Forge, which are both LGPL-2.1-or-later.

`agency.highlysuspect.redmill.svc.mcp.*` contains code from Voldeloom. voldeloom itself is MIT, but I wrote all the mcp classes in that project by myself so i hold the copyright anyway.

## todo

* ~~can't seem to make a redmill2 mod container load, does nf not want a jar to have transformation services & also be a mod~~
  * fixed with some horrible gradle shit involving the `fml.modFoldersFile` system property
  * making a "real" subproject is probably the correct way to fix it
  * WILL NEED some bullshit to happen in production!! Probably need to play with jarinjar!
* i'd like to make minecraft-1.4.7-hier.json with datagen

## notes

see `cpw.mods.modlauncher.api.IModuleLayerManager$Layer`.

there's four "layers":

* BOOT is normally not modifiable
  * can load ITransformerDiscoveryService (mod discovery process stuff)
  * can load ILaunchPluginService (which are super handy, you can apply class transformation to any class)
  * probably more stuff
* SERVICE and PLUGIN, not sure exactly the distinction
  * SERVICE can load ITransformationService (which is a more restricted and *much* safer implementation of coremod stuff)
  * PLUGIN can load IModLanguageLoader (about-equal to language providers from old fml) and IModFileReader (customizability of what stuff "counts as a mod")
* GAME contains mods and the minecraft code

the classloading is such that BOOT can see nothing else, SERVICE and PLUGIN can see BOOT, and GAME can see everything (i.e. if you load a class from PLUGIN you can refer to it from BOOT and it'll be the same class)

very cool that stuff like IModFileReader is exposed in the first place! I haven't looked at Sinytra Connector's code but i imagine they're big fans of that

the main drawback of ITransformationService is that i believe you have to specify the complete set of classes you want to transform up-front, but the transformation services get initialized way before imodfilereaders get to load mods. so unless i want to make my own shitty copy of the neoforge mod discovery process, in order to transform classes from milled mods, i need to use an ILaunchPluginService (just do the regular mod discovery process, enumerate mod classes there, and pass the list off to the ILaunchPluginService). the workaround is loading an ITransformationService but not actually using it for doing any class transformation, instead just reflecting into the list of ILaunchPluginServices and adding my own. you're definitely not supposed to do that.

## so what does this mean for the mod

`src/game/java` contains stuff to be loaded on the GAME layer, neoforge.mods.toml, and classes that milled mods need to see like the Forge shim. `src/main/java` contains stuff on the SERVICE/PLUGIN layer. stuff in `main` can't see the stuff in `game`. 

most of the mod discovery process happens from code in `main` because it happens before the `GAME` layer even exists (afaik). in `IModLanguageLoader#loadMod` neoforge gives me the `GAME` ModuleLayer. i peer through the looking class, pull `agency.highlysuspect.redmill.game.Bastion` through it, and the rest of the modloading process happens in `GAME` (since i need to start referring to classes from the Forge shim, like the preinit event)

neoforge will ignore mods from locations it thinks it already loaded mods from; loading a transformation service counts for this. the `modFolders.properties` jank is to make neoforge think there are two separate mods that live in two separate folders (one containing the transformation services and one containing the neoforge fml mod) instead of one.

~~jar metadata "pre" contains just strings. suitable for reading from a classfile or from a json file.  the non-pre versions are actually linked up to each other. this makes it much easier to, say, climb up a class hierarchy while resolving trueowners. still not sure if this is really needed..?~~ sure wasn't, i made it all strings