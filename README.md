# Chaos Game
This JavaFX application lets you draw 2d fractals using the [Chaos Game](https://en.wikipedia.org/wiki/Chaos_game) method, using a (hopefully) user-friendly GUI. The basic principles are as follows:
- Place a certain number of points onto the plane, called *anchor points*.
- Choose an arbitrary starting point
- In each iteration, choose one of the anchor points uniformly at random and move half the distance towards it. Draw a small dot at the new location.
- Repeat many times until a fractal shape appears.

With changes to the rules, e.g. number and location of anchors, proportion to move instead of a half, or not allowing the same anchor to be repeated twice, many types of fractals can be generated.

This started as a hobby project back in 2017 due to my fascination with fractals, after seeing the [Numberphile video](https://youtu.be/kbKtFN71Lfs) on the topic. The rudimentary version was then polished up with extra features as part of a course project at uni. This explains some of the things very uncharacteristic for a hobby project, like thorough unit testing, test coverage reports and an unnecessary database feature for saving drawing settings (added to satisfy a checklist for course grading more than anything).

## Usage
Install Java on your system, if you have not done so already: https://www.oracle.com/java/technologies/javase-downloads.html

Download the Chaos Game jar [here](https://github.com/haxrober/otm-harjoitustyo/releases/tag/1.0) and double click to run.

In Java version 11 and newer versions, JavaFX is no longer included by default. To run the Jar file, you may need to provide the JVM with these missing libraries:
- Download the JavaFX SDK for your platform: https://gluonhq.com/products/javafx/
- Extract the archive anywhere
- Open up your terminal of choice and move to the directory where ChaosGame.jar is located. Provide the path to the /lib directory of the extracted archive, and the necessary module Javafx.controls, using the following command:
```
java -jar --module-path "path/to/javafx-sdk/lib/" --add-modules javafx.controls ChaosGame.jar
```

[Explanation of the GUI elements](https://github.com/RobertHarkonen/Chaos-Game-fractal-generator/blob/master/Documentation/GUI explanation.md)

## Basic maven commands

- Run tests: `mvn test`
- Test coverage report: `mvn test jacoco:report`
- Generate a runnable .jar: `mvn package`
- Checkstyle: `mvn jxr:jxr checkstyle:checkstyle`
- Generate Javadoc: `mvn javadoc:javadoc`
