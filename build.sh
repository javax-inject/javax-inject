VERSION=EDR
NAME=atinject

# Clear and recreate build directory.
rm -r build
mkdir -p build/classes
mkdir -p build/dist

# Compile classes.
javac -g -d build/classes `find src -name *.java`

# Generate Javadocs.
javadoc -protected \
            -header "<font color='red'><b>This is a DRAFT specification.</b></font>" \
            -sourcepath src -d build/javadoc javax.inject

# Generate jars.
cp -R src build
find build/src -name .svn -type d | xargs rm -r
jar cfM build/dist/$NAME-$VERSION-src.zip -C build/src .

jar cfM build/dist/$NAME-$VERSION-javadoc.zip -C build/javadoc .
jar cfM build/dist/$NAME-$VERSION.jar -C build/classes .

jar cfM build/$NAME-$VERSION.zip -C build/dist .

# Pending license footer.
#-bottom <font size='-1'>Copyright (C) 2009 The JSR-330 Expert Group. Licensed 
# under the <a href='http://www.apache.org/licenses/LICENSE-2.0'>Apache License</a>,
#            Version 2.0.</font>
