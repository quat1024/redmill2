# Red Mill

redmill 1 never came out, this is a full rewrite

## todo

* can't seem to make a redmill2 mod container load, does nf not want a jar to have transformation services & also be a mod
* i'd like to make minecraft-1.4.7-hier.json with datagen

## notes

jar metadata "pre" contains just strings.suitable for reading from a classfile or from a json file.

the non-pre versions are actually linked up to each other. this makes it much easier to, say, climb up a class hierarchy while resolving trueowners.

still not sure if this is really needed..?