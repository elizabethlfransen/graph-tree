# GraphTree
A DSL for defining trees

## Installation
*If building from source please see [Building](#building)*

GraphTree requires java 11 to run

Installation Directions:
* Download the latest release from [Releases](https://github.com/elizabethlfransen/graph-tree/releases)
* Extract the zip to an appropriate location
* **Optional:** add bin folder to path
* Run `bin/graph-tree` *(If bin folder has been added to path you should be able to use `graph-tree`)
    * For windows run `bin/graph-tree.bat`

## Running

```
Usage: graph-tree [OPTIONS]

  Base command for graph-tree

Options:
  -f, --format [PNG|SVG|SVG_STANDALONE|DOT|XDOT|PLAIN|PLAIN_EXT|PS|PS2|JSON|JSON0|IMAP|CMAPX]
                                   Resulting format for generating (default:
                                   PNG)
  -i, --input FILE                 Input file path or "-" for stdin (default:
                                   -)
  -o, --output FILE                Output file path or "-" for stdout
                                   (default: -)
  --generate-completion [bash|zsh|fish]
                                   Generates autocomplete script for the
                                   command. Example Usage: graph-tree
                                   --generate-autocomplete zsh >
                                   ~/my-program-completion.sh Then source
                                   ~/my-program-completion.sh
  -h, --help                       Show this message and exit
            Show this message and exit

```

## Building

If running on Windows replace `./gradlew` with `./gradlew.bat`

Start a new build
```shell
./gradlew build
```

Create a distribution
```shell
./gradlew installDist
```

Resulting distribution will be in
```
build/distributions/graph-tree-1.0.0.zip
```

## Development

### TDD
All modules listed below should be implemented via test driven development. Any new feature should be added using TDD
principals.

### Modules
The development of GraphTree is broken into modules for ease of TDD

### Internal / Parsing

Internal antlr parsing for reading the language. These classes are found in the `internal` subpackage and should not be
used in the public ap

### Documents

Classes found in the root package and are considered the open api. These mainly hold data classes representing GraphTree
documents and constructing them.

### Graphviz

Graphviz is a great language for describing graphs and there are many great tools used for rending these graphs, 
therefore this library will include a way of transpiling GraphTree to Graphviz

### App

A cli application to transpile or render graphs