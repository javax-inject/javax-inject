NAME=javax.inject

# Clear and recreate build directory.
rm -r build
mkdir -p build/classes
mkdir -p build/tck
mkdir -p build/dist

# Compile classes.
javac -g -d build/classes `find src -name *.java`
javac -g -classpath build/classes -d build/tck \
	`find tck -name *.java`

FOOTER="<font size='-1'>Copyright (C) 2009 <a href='http://code.google.com/p/atinject/'>\
The JSR-330 Expert Group</a>. \
Licensed under the <a href='http://www.apache.org/licenses/LICENSE-2.0'>Apache \
License</a>, Version 2.0.</font>"

# Generate Javadocs.
javadoc -protected -bottom "$FOOTER" \
	-sourcepath src -d build/javadoc javax.inject

# Generate jars.
cp -R src build
cp -R tck build/tck-src
find build/src -name .svn -type d | xargs rm -r
find build/tck-src -name .svn -type d | xargs rm -r

jar cfM build/dist/$NAME-src.zip -C build/src .
jar cfM build/dist/$NAME-tck-src.zip -C build/tck-src .

jar cfM build/dist/$NAME-javadoc.zip -C build/javadoc .
jar cfM build/dist/$NAME.jar -C build/classes .
jar cfM build/dist/$NAME-tck.jar -C build/tck .

jar cfM build/$NAME.zip -C build/dist .
