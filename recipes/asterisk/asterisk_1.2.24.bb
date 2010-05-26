# Copyright (C) 2007, Stelios Koroneos - Digital OPSiS, All Rights Reserved
# Released under the MIT license (see packages/COPYING)
DESCRIPTION="The Asterisk open source software PBX"
HOMEPAGE="www.asterisk.org"
LICENSE="GPL"
DEPENDS="ncurses zlib openssl curl alsa-lib libogg libvorbis speex"
SECTION = "console/telephony"
PR = "r1"

SRC_URI="http://downloads.asterisk.org/pub/telephony/asterisk/releases/${P}.tar.gz \
	 file://uclibc-compat-getloadavg.patch \
	 file://uclibc-dns.patch \
#         file://uclibc-define-glob.patch \
         file://asterisk.patch \
         file://enable-speex.patch"

S = "${WORKDIR}/asterisk-${PV}"


export CROSS_COMPILE="${CCACHE}${HOST_PREFIX}"
export CROSS_COMPILE_BIN="${STAGING_BINDIR_CROSS}"
export CROSS_COMPILE_TARGET="${STAGING_DIR_HOST}"

export CROSS_ARCH="Linux"
export CROSS_PROC="${TARGET_ARCH}"

export MAKECMDGOALS="dont-optimize"

# We will probably have to edit the CFLAG in the Makefile

do_compile() {
        oe_runmake
}

do_install() {
        oe_runmake DESTDIR=${D} install
}

do_stage () {
        install -d ${STAGING_INCDIR}/asterisk
        install -m 0644 ${S}/include/asterisk/*.h ${STAGING_INCDIR}/asterisk/
}


FILES_${PN}-dbg += "${libdir}/asterisk/modules/.debug"
FILES_${PN}-dbg += "/var/lib/asterisk/agi-bin/.debug"


SRC_URI[md5sum] = "63dc8b7be4cd10375c5fbda893c780bc"
SRC_URI[sha256sum] = "9debaf410636fa477e1e1f09fe0b16a1c2814afaf7195f34f29e4ce5b8debbbd"
# CHECKSUMS.INI MISMATCH: I've got this instead:
#SRC_URI[md5sum] = "db7bcaaa494804af361157a37c224dfa"
#SRC_URI[sha256sum] = "eed3493b1409d7100e0f983af0486bd7f8965e9e47b7a6d5ab8539b2dd3609aa"
