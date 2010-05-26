DESCRIPTION = "a small and fast cgi program to retrieve images from a V4L device."
SECTION = "console/network"
PRIORITY = "optional"
DEPENDS="jpeg"
LICENSE = "GPLV2"
PR = "r0"

SRC_URI = "http://mpx.freeshell.net/w3cam-0.7.2.tar.gz \
	   file://staticpaths.patch"

S = "${WORKDIR}/w3cam-0.7.2/"

inherit autotools

LDFLAGS += "-L${STAGING_LIBDIR}"
CFLAGS += "-L${STAGING_LIBDIR} -I${STAGING_INCDIR}"

EXTRA_OECONF = "--without-x --without-ttf-inc"

do_install() {
	install -d ${D}${sbindir}
	install -d ${D}${bindir}
	install -d ${D}${mandir}/man1
	install -d ${D}usr/cgi-bin
	install -m 0755 ${S}w3camd/w3camd ${D}${sbindir}/w3camd
	install -m 0755 ${S}w3cam.cgi ${D}usr/cgi-bin/w3camd.cgi
	install -m 0755 ${S}vidcat ${D}${bindir}/vidcat
	install -m 0644 ${S}vidcat.1 ${D}${mandir}/man1/vidcat.1
}


SRC_URI[md5sum] = "eec0b301b32bc8e9f65a4e54248c9868"
SRC_URI[sha256sum] = "96d659d916fafe555311068c9bf8662b93f10d099b12b17ed04d8a8fffbc72e0"
