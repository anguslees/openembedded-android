DESCRIPTION = "Library for reading some sort of media format."
SECTION = "libs"
LICENSE = "LGPL"
PRIORITY = "optional"
DEPENDS = ""
do_unpack[depends] += "unzip-native:do_populate_sysroot"
PR ="r2"

inherit autotools

SRC_URI = "${SOURCEFORGE_MIRROR}/faac/${PN}-${PV}.zip \
           file://faad2-gcc-fix.patch \
 	   file://Makefile.am"

S="${WORKDIR}/${PN}"

do_configure_prepend() {
        mv ${WORKDIR}/Makefile.am ${S}/
}

PACKAGES =+ "libfaad libfaad-dev libmp4ff libmp4ff-dev"

FILES_${PN} = "${bindir}/faad"

FILES_libfaad = "${libdir}/libfaad.so.0 ${libdir}/libfaad.so.0.0.0"
FILES_libfaad-dev = "${libdir}/libfaad.so ${libdir}/libfaad.la ${libdir}/libfaad.a ${includedir}/faad.h"

FILES_libmp4ff = "${libdir}/libmp4ff.so.0 ${libdir}/libmp4ff.so.0.0.0"
FILES_libmp4ff-dev = "${libdir}/libmp4ff.so ${libdir}/libmp4ff.la ${libdir}/libmp4ff.a ${includedir}/mp4ff.h"

do_stage() {
	oe_libinstall -a -so -C libfaad libfaad ${STAGING_LIBDIR}
	install -m 0644 ${S}/include/faad.h ${STAGING_INCDIR}/
	oe_libinstall -a -so -C common/mp4ff libmp4ff ${STAGING_LIBDIR}
	install -m 0644 ${S}/common/mp4ff/mp4ff.h ${STAGING_INCDIR}/
}

SRC_URI[md5sum] = "b6807fba02d52747ebb92097daee71e8"
SRC_URI[sha256sum] = "50cf7188f6f99b75566f14d417915dcff1446d36d05982971c126893ae74a97a"
