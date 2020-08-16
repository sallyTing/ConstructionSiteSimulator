
# Instructions

## Design and structure
Each process will have a simulator,

a simulator has a construction site, and store all valid commands from user input,

a construction site has a map read from provided file and a bulldozer running on it,

a bulldozer needs to remember its position, direction, also cost related like fuel usage, damage numbers and whether got penalty or not.

- `io` package stores all the IO related actions (reading file, reading user input, print to console)
- `exception` package stores customised exceptions, all caught in code, either ignore or stop process
- `commands` package stores 4 different user commands and parent class
- `costs` package stores different cost items required to be listed at the end
- `models` package stores all domain related class mentioned at the top

At the start, this program will read the construction map from a provided file, and then form a construction site with an initialised bulldozer,

then create simulator to wait for user command and parse it to the corresponding command, 

and then process this command to the site and bulldozer until `Quit` command to end the process.

The site map will be updated if moving the bulldozer forward (update to cleared land), 

the bulldozer will be updated based on command:

if turning left or right, then update direction only, 

if moving forward, then update location on the map, and accumulate fuel usage,

if next passing square is a tree, and not stopping on it, then accumulate damage number, 

if next passing square is a protected tree, then throw `OutOfMapException` with the current updated site,

if next passing square is out of map, then throw `ProtectedTreePenaltyException` with the current updated site.

When the simulator caught an exception, then throw `TerminateException` with the current updated simulator (with latest command and latest site) 
and termination reason to end this process.

## To run this project:
go to this folder and enter command:

`./gradlew run -q --console=plain --args="src/test/resources/input.txt"`

so there is one map file `src/test/resources/input.txt` already, if you want to use other files, can simple put under the main folder and use command

`./gradlew run -q --console=plain --args="[filename under main folder]"`

## To test:
enter command:

`./gradlew test -i`

# Assumptions

1. the map in input file is always M * N.
2. the bulldozer can be damaged multiple times
3. The site will never have a ring of unremovable trees that surround one or more squares that could otherwise be cleared.
