#!/bin/sh
mvn clean install -Dmaven.test.skip=true
echo "Build Mos-Lnk Server Success ."
echo "Install Lnk Server... "
echo "copy build/lib/*.jar lnk/lib/" && cp build/lib/*.jar lnk/lib/
echo "copy etc/*.xml lnk/etc/" && cp etc/*.xml lnk/etc/
echo "copy etc/*.sh lnk/bin/" && cp etc/*.sh lnk/bin/
echo "copy target/mos-lnk.jar lnk/bin/" && cp target/mos-lnk.jar lnk/bin/
echo "Install Lnk Server Success."
