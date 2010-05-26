DEFAULT_PREFERENCE = "-1"

require libtool.inc
PR = "${INC_PR}.0"

SRC_URI = "${GNU_MIRROR}/libtool/libtool-${PV}.tar.gz \
           file://autotools.patch \
	   file://uclibc.patch"
#	   file://3figures.patch"
S = "${WORKDIR}/libtool-${PV}"

PACKAGES = "${PN}-dbg libltdl libltdl-dev ${PN}"
FILES_${PN} += "${datadir}/aclocal*"
FILES_libltdl = "${libdir}/libltdl.so.*"
FILES_libltdl-dev = "${libdir}/libltdl.* ${includedir}/ltdl.h"

inherit autotools

EXTRA_AUTORECONF = "--exclude=libtoolize"

do_configure () {
	find ${S} -name acinclude.m4 | for m4 in `cat`; do
		cat ${S}/libtool.m4 ${S}/ltdl.m4 > $m4
	done
	autotools_do_configure
}

do_stage () {
	oe_libinstall -a -so -C libltdl libltdl ${STAGING_LIBDIR}
	install -m 0644 libltdl/ltdl.h ${STAGING_INCDIR}/
}

SRC_URI[md5sum] = "8e0ac9797b62ba4dcc8a2fb7936412b0"
SRC_URI[sha256sum] = "88e9ffaaade21c1ddaf6297723dd2fb4ca18ccaef0499a28b6e672f02c8ceb5d"
