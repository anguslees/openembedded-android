# beepmp OE build file
# Copyright (C) 2004, Advanced Micro Devices, Inc.  All Rights Reserved
# Released under the MIT license (see packages/COPYING)

DESCRIPTION = "A GTK2 based media player that looks alot like XMMS"
SECTION = "x11/multimedia"
HOMEPAGE = "http://beepmp.sourceforge.net"
LICENSE = "GPL"

SRC_URI = "${SOURCEFORGE_MIRROR}/beepmp/bmp-${PV}.tar.gz \
	   file://gnusource.patch"

inherit autotools pkgconfig

DEPENDS = "gtk+ libglade libogg libvorbis"
S = "${WORKDIR}/bmp-0.9.7"

FILES_${PN} += " ${libdir}/bmp/Output/*.so ${libdir}/bmp/Input/*.so \
	${libdir}/bmp/Visualization/*.so ${datadir}/bmp"

EXTRA_OECONF = "--disable-esd --disable-alsa --enable-simd"

do_configure() {
	rm -rf  m4/libtool.m4
	autotools_do_configure
}

SRC_URI[md5sum] = "5d74113f5de3d11a400d1d6c118d41c0"
SRC_URI[sha256sum] = "7b458b6ca51c5f1c01ce328aedaab81e71028c796d37a953582e0deb55980c51"
