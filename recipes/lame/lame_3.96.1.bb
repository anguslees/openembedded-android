SECTION = "console/utils"
DESCRIPTION = "Not an MP3 encoder"
LICENSE = "LGPL"
PR = "r5"

SRC_URI = "${SOURCEFORGE_MIRROR}/lame/lame-${PV}.tar.gz \
	file://no-gtk1.patch \
	file://Makefile-lm.patch \
	file://ldflags-qa.patch"

inherit autotools

PACKAGES += "libmp3lame libmp3lame-dev"
FILES_${PN} = "${bindir}/lame"
FILES_libmp3lame = "${libdir}/libmp3lame.so.*"
FILES_libmp3lame-dev = "${includedir} ${libdir}/*"
FILES_${PN}-dev = ""

do_configure() {
	# no autoreconf please
	aclocal
	autoconf
	libtoolize --force
	gnu-configize --force
	oe_runconf
}

do_stage() {
	install -d ${STAGING_LIBDIR}
	oe_libinstall -C libmp3lame -so -a libmp3lame ${STAGING_LIBDIR}
	install -d ${STAGING_INCDIR}/lame
	install -m 0644 include/lame.h ${STAGING_INCDIR}/lame/
}

SRC_URI[md5sum] = "e1206c46a5e276feca11a7149e2fc6ac"
SRC_URI[sha256sum] = "f4f093e371c999a2a079607b74582a8ef5c1c3c9b322e3e997a47c1ea2afe2a5"
