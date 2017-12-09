# ExtendedCLI
[![Build Status](https://travis-ci.org/JPDSousa/ExtendedCLI.svg?branch=master)](https://travis-ci.org/JPDSousa/ExtendedCLI)
[![codecov](https://codecov.io/gh/JPDSousa/ExtendedCLI/branch/master/graph/badge.svg)](https://codecov.io/gh/JPDSousa/ExtendedCLI)
[![](https://jitpack.io/v/JPDSousa/ExtendedCLI.svg)](https://jitpack.io/#JPDSousa/ExtendedCLI)
[![BCH compliance](https://bettercodehub.com/edge/badge/JPDSousa/ExtendedCLI?branch=master)](https://bettercodehub.com/)
[![Known Vulnerabilities](https://snyk.io/test/github/jpdsousa/extendedcli/badge.svg)](https://snyk.io/test/github/jpdsousa/extendedcli)

ExtendedCLI is a Command Line Interface framework that allows its users to build complex fully-customizable CLIs in Java. Through ExtendedCLI users are able:
 - Design a CLI with multiple commands, each command with a set of customizable options;
 - Each command follows the [command pattern](https://en.wikipedia.org/wiki/Command_pattern), providing an `execute()` and `undo()` methods;
 - Each option follows the classic format `-option_name`, and can be assigned with a value (in the form of `-option_name value`), a default value and a description;
 - For the most advanced and creative users, the CLI can be assigned with a custom input and output stream, allowing users to use the CLI from custom environments (from system consoles to files or even messaging applications such as [Slack](https://slack.com) or [Telegram](https://telegram.org/))
 
 ## Getting Started
 
 ExtendedCLI uses maven as a package manager, but it not yet available in the maven repository. However, while the first official version of ExtendedCLI is not released, you can download the stable version through [jitpack](https://jitpack.io/#JPDSousa/ExtendedCLI) (available for Maven, Graddle, sbt and leiningen).
 
 After including ExtendedCLI in your project, you're ready to build your first Command Line Interface!
 
 ## Basic Usage
 
 The whole framework works around the `ExtendedCLI` class. An `ExtendedCLI` instance works as regular CLI object: it stores all your custom commands and receives pieces of input (i.e. lines), which it processes. `ExtendedCLI` instances are built through `CLIBuilder` as such:
 
```java
boolean ignoreCase = true;
CLIBuilder builder = new CLIBuilder(ignoreCase);
builder.registerCommand("command1", new Command1());
builder.registerCommand("command2", new Command2());
// ... register all commands
ExtendedCLI cli = builder.build();
 ```
 
 The newly created `ExtendedCLI` instance stores all the registered commands, and is ready to start processing lines of input:
 
 ```java
 String line = "command1 -p path_to_something";
 try {
   cli.execute(line);
 } catch (NoSuchCommandException e) {
   System.out.println("Unkown Command.");
 }
 ```
 
 When executed, the line `"command1 -p path_to_something"` is processed by the `ExtendedCLI` instance:
  - The first word is matched to the registered command according to the name it was registered to (i.e. `"command1"` is matched to the `Command1` instance created above).
  - The matched command is executed with all the data extracted from the input line.
