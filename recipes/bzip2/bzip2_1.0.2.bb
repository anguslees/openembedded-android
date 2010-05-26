DESCRIPTION = "Very high-quality data compression program."
SECTION = "console/utils"
PR = "r2"

LICENSE = "bzip2"
SRC_URI = "ftp://sources.redhat.com/pub/bzip2/v102/bzip2-${PV}.tar.gz \
	   file://installpaths.patch \
	   file://ldflags.patch"

CFLAGS_append = " -fPIC -fpic -Winline -fno-strength-reduce -D_FILE_OFFSET_BITS=64"

do_compile () {
	oe_runmake -f Makefile-libbz2_so
	ln -sf libbz2.so.1.0.2 libbz2.so.1.0
	ln -sf libbz2.so.1.0.2 libbz2.so.1
	ln -sf libbz2.so.1.0.2 libbz2.so
	oe_runmake libbz2.a bzip2 bzip2recover
}

do_stage () {
	install -m 0644 bzlib.h ${STAGING_INCDIR}/
	oe_libinstall -a -so libbz2 ${STAGING_LIBDIR}
}

do_install () {
	oe_runmake 'DESTDIR=${D}' install
	oe_libinstall -a -so libbz2 ${D}${libdir}
	mv ${D}${bindir}/bunzip2 ${D}${bindir}/bunzip2.${PN}
	mv ${D}${bindir}/bzcat ${D}${bindir}/bzcat.${PN}
}


pkg_postinst_${PN} () {
	update-alternatives --install ${bindir}/bunzip2 bunzip2 bunzip2.${PN} 100
	update-alternatives --install ${bindir}/bzcat bzcat bzcat.${PN} 100
}


pkg_prerm_${PN} () {
	update-alternatives --remove bunzip2 bunzip2.${PN}
	update-alternatives --remove bzcat bzcat.${PN}
}

SRC_URI[md5sum] = "ee76864958d568677f03db8afad92beb"
SRC_URI[sha256sum] = "4b526afa73ca1ccd6f5f1f5fd23813f159f715c3d0e00688f1df54b51f443cdd"
