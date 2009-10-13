NAME=javax.inject
VERSION=SNAPSHOT

# Clear and recreate build directory.
rm -r build
mkdir -p build/classes
mkdir -p build/tck
mkdir -p build/dist
mkdir -p build/tck/classes
mkdir -p build/tck/dist

# Compile classes.
javac -g -d build/classes `find src -name *.java`
javac -g -classpath build/classes:lib/junit.jar -d build/tck/classes \
	`find tck -name *.java`

FOOTER="<font size='-1'>Copyright (C) 2009 <a href='http://code.google.com/p/atinject/'>\
The JSR-330 Expert Group</a>. \
Licensed under the <a href='http://www.apache.org/licenses/LICENSE-2.0'>Apache \
License</a>, Version 2.0.</font>"

# Generate Javadocs.
javadoc -protected -bottom "$FOOTER" \
    -header "<font color='red'><b>This is a DRAFT specification.</b></font>" \
	-sourcepath src -d build/javadoc javax.inject
javadoc -classpath build/classes:lib/junit.jar -protected -bottom "$FOOTER" \
	-sourcepath tck -d build/tck/javadoc org.atinject.tck \
	org.atinject.tck.auto org.atinject.tck.auto.accessories

# Generate jars.
cp -R src build
cp -R tck build/tck/src

rmSvn() {
  find $1 -name .svn -type d | xargs rm -r
}

rmSvn build/src
rmSvn build/tck/src
rm build/tck/src/tck.iml

jar cfM build/dist/$NAME-src.zip -C build/src .
jar cfM build/tck/dist/$NAME-tck-src.zip -C build/tck/src .

jar cfM build/dist/$NAME-javadoc.zip -C build/javadoc .
jar cfM build/tck/dist/$NAME-tck-javadoc.zip -C build/tck/javadoc .
jar cfM build/dist/$NAME.jar -C build/classes .
jar cfM build/tck/dist/$NAME-tck.jar -C build/tck/classes .

jar cfM build/$NAME.zip -C build/dist .
jar cfM build/$NAME-tck.zip -C build/tck/dist .

# Build Maven bundle.
mkdir build/maven
cp build/dist/$NAME.jar build/maven/$NAME-$VERSION.jar
cp build/dist/$NAME-src.zip build/maven/$NAME-$VERSION-sources.jar
cp build/dist/$NAME-javadoc.zip build/maven/$NAME-$VERSION-javadoc.jar
cp pom.xml build/maven
jar cfM build/$NAME-$VERSION-bundle.jar -C build/maven .
