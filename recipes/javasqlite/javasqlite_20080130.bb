DESCRIPTION = "JDBC driver for sqlite and sqlite3"
HOMEPAGE = "http://www.ch-werner.de/javasqlite"
LICENSE  = "BSD"

PR = "r2"

SRC_URI = "\
  http://www.ch-werner.de/javasqlite/javasqlite-${PV}.tar.gz \
  file://build-fix.patch"

DEPENDS = "virtual/javac-native fastjar-native classpath-tools-native javasqlite-mkconst-native classpath sqlite sqlite3"
RDEPENDS_${JPN} = "lib${PN}-jni"

inherit java-library autotools

JARFILENAME = "sqlite.jar"

# jamvm-native unfortunately contains non-generificed java/lang/reflect classes
# which are accessed in this package. Work around this by setting the bootclasspath
# explicitly.
export JAVACFLAGS="-bootclasspath ${STAGING_DATADIR}/classpath/glibj.zip -source 5.0"

EXTRA_OECONF = " \
  --with-jni-incdir=${STAGING_INCDIR}/classpath \
  --with-jardir=${datadir_java} \
  --with-target-native-libdir=${libdir_jni} \
  --with-sqlite-incdir=${STAGING_INCDIR} \
  --with-sqlite-libdir=${STAGING_LIBDIR} \
  --with-sqlite3-incdir=${STAGING_INCDIR} \
  --with-sqlite3-libdir=${STAGING_LIBDIR} \
  --with-java=${STAGING_BINDIR_NATIVE}/java \
  --with-javac=${STAGING_BINDIR_NATIVE}/javac \
  --with-jar=${STAGING_BINDIR_NATIVE}/gjar \
  --with-javah=${STAGING_BINDIR_NATIVE}/gjavah \
  --with-javadoc=true \
  "

do_configurepre() {
  sed -i -e "s|wrong-libtool|${TARGET_SYS}-libtool|" Makefile.in
}

addtask configurepre after do_patch before do_configure

do_compile_prepend() {
  # Injects a cross-compiled mkconst binary into the build
  cp ${STAGING_BINDIR_NATIVE}/javasqlite-mkconst-${PV} native/mkconst
  touch native/mkconst
}

do_install_append() {
  rm ${D}${libdir_jni}/libsqlite_jni.la
  rm ${D}${libdir_jni}/libsqlite_jni.a
}

PACKAGES += "lib${PN}-jni lib${PN}-jni-dbg"

FILES_lib${PN}-jni = "${libdir_jni}/lib*.so*"
FILES_lib${PN}-jni-dbg = "${libdir_jni}/.debug/lib*.so*"

SRC_URI[md5sum] = "842932cfb22d44d8ebf85d1e184b4c55"
SRC_URI[sha256sum] = "ea216b2fa82e784fe7572074246fb0bbaaeac46e655f554d44da827f82b95231"
