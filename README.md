# Red Mill

i tried this before last modfest but didn't finish. redmill 1's code is private and it never came out. this is a full rewrite that doesn't use the original code

## credits

`agency.highlysuspect.redmill.oldschool.cpw` obviously contains code adapted from FML, and `agency.highlysuspect.redmill.oldschool.net.minecraftforge` obviously contains code adapted from Minecraft Forge, which are both LGPL-2.1-or-later.

(however the code under `...net.minecraft.*` is... 💀)

`agency.highlysuspect.redmill.svc.mcp.*` contains code from Voldeloom. voldeloom itself is MIT, but I wrote all the mcp classes in that project by myself so i hold the copyright.

## todo

* my fucked up and evil jarjar isn't working
  * the -game jar isn't loaded in prod, neoforge isn't even picking up on the jarjar
  * you can just install both the regular jar and the game jar so it's not a huuuuge deal
* i'd like to make minecraft-1.4.7-hier.json with datagen
  * and some of the other data files mayb

# notes

the process:

* first remap according to srg (fields/methods first, then classes, just so we have the correct "owner" information when remapping the fields)
* apply some classname fixups (leftovers.srg; mainly bringing some forge classes out of the root package, replacing java.util.logging with a Log4j-based shim, remapping select Forge classes into NeoForge ones where they haven't changed)
* rewrite field reads/writes to getter/setter calls! this is done on every class (TODO: probably shouldn't apply to some forge stuff)
* "class hierarchy bending" (for lack of a better term). basically i introduce an "api/implementation split" into nearly every class from minecraft
  * `dontbend.txt` contains regexes for classes not to do this split on
  * which, atm, is just "all of forge", so uh, maybe regex is a little too much power
  * this is also where the `agency/highlysuspect/redmill/game/oldschool/` repackaging is applied
  * humm, i should do that somewhere else, maybe
* finally some cheeky mod specific patches
  * currently this patches Auto Third Person to disable the "emulate sneak-to-dismount" and the "fix misplaced first-person hand" code, because of course it is not relevant anymore (and it won't work)

the dev workflow:

* i have some debug code that runs forge.jar itself thru this pipeline (plucked from my voldeloom cache, so it also contains vanilla minecraft)
* when i need to port a new class i open the compiled class in intellij (just for convenience's sake) and copypaste the decompiled code into a new java file
* cue lots and lots and lots of manual fixups, commenting out things that aren't ready yet, sometimes reimplementing a method where it makes sense to do so, etc
  * for example (this might change) the Forge EventBus system was torn out and replaced with the NeoForge EventBus

when i need access to a new minecraft class, i'll typically make the api interface and implement it on a related vanilla class with a mixin. however some of the new classes im reaching are stuff like `Block` and `Item` where i will need to implement a shim, and kind of plug the registration process (constructor reg! :D) into the neoforge registration process

## neoforge layer system

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

`src/game/java` contains stuff to be loaded on the GAME layer, neoforge.mods.toml, and classes that milled mods need to see like the Forge shim. `src/plugin/java` contains stuff on the SERVICE/PLUGIN layer. stuff in `plugin` can't see the stuff in `game`. (if you ever see me talk about the "main" sourceset that's `plugin` before i renamed it)

the mod discovery process happens from code in `plugin` because it happens before the `GAME` layer even exists (afaik). in `IModLanguageLoader#loadMod`, neoforge gives me a reference to the `GAME` ModuleLayer. at that point i peer through the looking class, pull `agency.highlysuspect.redmill.game.Bastion` through it, and the rest of the modloading process happens in `GAME` (since i need to start referring to classes from the Forge shim, like the preinit event)

neoforge will ignore mods from locations it thinks it already loaded mods from; loading a transformation service counts for this. the `modFolders.properties` jank is to make neoforge think there are two separate mods that live in two separate folders (one containing the transformation services and one containing the neoforge fml mod) instead of one.

## mod construction process

* init the mod container (LanguageLoader#loadMod)
* validate the mod container (LanguageLoader#validate)
* ...after all mod containers have been validated...
  * i detect that in RedmillLanguageLoader and do some final setup stuff there
* in parallel (toposorted by inter-mod dependencies):
  * ModContainer#constructMod is called
  * FMLConstructModEvent is posted to the mod container's event bus
    * this is that "mod event bus" ive heard so much about :heart_eyes:
    * FMLModContainer gives each mod its own event bus
* buncha registry events get posted to the mod bus
* entity attribute creation events ... client reload listeners ... (and so on)
* *then* FMLCommonSetupEvent
  * ok so this is not a good place to fire preinit ;O
* more random events
* IMC events
* FMLLoadCompleteEvent
* and yet more events

thinking about the preinit/init/postinit system. i forget exactly when they're fired in old minecraft

but like, init is only dispatched after all mods received preinit, that sorta thing

### how should it worke

* RedmillBefore will prepare the FMLInjectionData and other stuff in the Loader, but stop short of actually initing the mods
* When each mod initializes (thru Bastion) i will load the class and dispatch the oldskool construction event
* After all mods have been constructed RedmillAfter will take care of the preinit/init/postinit/complete phase

## ?

~~jar metadata "pre" contains just strings. suitable for reading from a classfile or from a json file.  the non-pre versions are actually linked up to each other. this makes it much easier to, say, climb up a class hierarchy while resolving trueowners. still not sure if this is really needed..?~~ sure wasn't, i made it all strings