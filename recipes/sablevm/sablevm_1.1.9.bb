DESCRIPTION = "A highly-portable Java virtual machine implementing the Java virtual machine specification, second edition."
HOMEPAGE = "http://sablevm.org"
LICENSE = "LGPL"
PRIORITY = "optional"
SECTION = "interpreters"
PR = "r2"

DEPENDS = "libffi libtool popt \
           sablevm-classpath"
#          unzip"
RRECOMMENDS_${PN} = "sablevm-classpath (${PV})"

SRC_URI = "http://sablevm.org/download/release/${PV}/${PN}-${PV}.tar.gz"

inherit autotools update-alternatives

EXTRA_OECONF = "--enable-real-life-brokenness \
                --disable-errors-on-warnings --disable-signals-for-exceptions"

PROVIDES = "virtual/java"
ALTERNATIVE_NAME = "java"
ALTERNATIVE_PATH = "${bindir}/java-sablevm"
ALTERNATIVE_PRIORITY = "350"

PACKAGES = "${PN}-dbg ${PN} ${PN}-doc lib${PN} lib${PN}-dev"

FILES_${PN} = "${bindir} \
	       ${libdir}/${PN}/bin"

FILES_lib${PN} = "${libdir}/lib${PN}-*.so"

FILES_lib${PN}-dev = "${includedir}/jni* \
	              ${libdir}/lib${PN}.so \
		      ${libdir}/lib${PN}.la"

do_install_append() {
	install -d ${D}${docdir}
	mv ${D}${datadir}/${PN} ${D}${docdir}/

	# symlink only present in the deb...
	install -d ${D}${libdir}/${PN}/bin
	cd ${D}${libdir}/${PN}/bin && ln -sf ../../../bin/java-sablevm java
}
