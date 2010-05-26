# bootsplash OE build file
# Copyright (C) 2004, Advanced Micro Devices, Inc.  All Rights Reserved
# Released under the MIT license (see packages/COPYING)

DESCRIPTION="Bootsplash shows pretty pictures during boot"
HOMEPAGE="http://www.bootsplash.org"
SECTION = "media-gfx"
LICENSE = "GPL"
SRC_URI="ftp://ftp.openbios.org/pub/bootsplash/rpm-sources/bootsplash/bootsplash-${PV}.tar.bz2 \
         file://freetype2.patch \
	 file://gcc-issues.patch"

DEPENDS="freetype libmng"
PR = "r1"

EXTRA_OEMAKE += "-C Utilities \
		CFLAGS='${CFLAGS} -I${STAGING_INCDIR}/freetype2/ -I${STAGING_INCDIR}' \
		LDFLAGS='${LDFLAGS} -L${STAGING_LIBDIR} -ljpeg'"

do_compile() {
	oe_runmake
}

do_install() {
	install -d ${D}${bindir}/
	install -m 0755 ${S}/Utilities/fbtruetype ${D}${bindir}
	install -m 0755 ${S}/Utilities/splash ${D}${bindir}
}

SRC_URI[md5sum] = "d7c7cdab692fb2edc5cf5ebb554f20a1"
SRC_URI[sha256sum] = "9b20c37f2ae2247354b580e080bf0c3b650d3e63bf39c3d5573ef3b9c75fe0f0"
