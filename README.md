# GraphTree
A DSL for defining trees

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