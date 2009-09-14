NAME=javax.inject

# Clear and recreate build directory.
rm -r build
mkdir -p build/classes
mkdir -p build/dist

# Compile classes.
javac -g -d build/classes `find src -name *.java`

FOOTER="<font size='-1'>Copyright (C) 2009 <a href='http://code.google.com/p/atinject/'>\
The JSR-330 Expert Group</a>. \
Licensed under the <a href='http://www.apache.org/licenses/LICENSE-2.0'>Apache \
License</a>, Version 2.0.</font>"

# Generate Javadocs.
javadoc -protected -bottom "$FOOTER" \
	-sourcepath src -d build/javadoc javax.inject

# Generate jars.
cp -R src build
find build/src -name .svn -type d | xargs rm -r
jar cfM build/dist/$NAME-src.zip -C build/src .

jar cfM build/dist/$NAME-javadoc.zip -C build/javadoc .
jar cfM build/dist/$NAME.jar -C build/classes .

jar cfM build/$NAME.zip -C build/dist .
