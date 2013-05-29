Jzy3d - A Java API for 3d charts
================================

Project layout
--------------
Source organisation
- src/api holds the API sources
- src/bridge holds classes to ease injection of components from a given windowing toolkit to another one (awt, swing, swt, newt) 
- src/tests holds
 - ChartTest, a tool to compare a chart with a previously saved screenshot
 - Replay, a utility to record and validate a sequence of mouse and key interactions results on a chart (work in progress)
- src/awt and src/swing hold Windowing toolkit dependent classes. 
 - Will be moved soon to external module as jzy3d-swt. 

Build
- Eclipse: .project & .classpath files
- Ant: build.xml
- Maven: pom.xml
- Javadoc: javadoc.xml

Library dependencies
- jogl2
- jdt (currently copied in API but will be externalized soon)
- opencsv
- log4j
- junit

Windowing toolkit dependent modules
-----------------------------------
- jzy3d-swt
- jzy3d-awt (coming soon)
- jzy3d-swing (coming soon)


Satellite projects depending on Jzy3d
--------------
Satellite projects are extensions of the framework that remain external to the API.

- jzy3d-graphs
- jzy3d-depthpeeling
- jzy3d-svm-mapper (also depends on jzy3d-tools-libsvm)
- glredbook

License
--------------
New BSD

More information
--------------
http://www.jzy3d.org

