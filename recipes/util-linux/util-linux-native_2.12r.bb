DESCRIPTION = "Util-linux is a suite of essential utilities for any Linux system."
SECTION = "base"
LICENSE = "GPL"
DEPENDS = "zlib-native ncurses-native"

inherit autotools native

SRC_URI = "${KERNELORG_MIRROR}/pub/linux/utils/util-linux/util-linux-${PV}.tar.bz2 \
           file://gcc34.patch \
           file://MCONFIG \
           file://make_include \
           file://swapargs.h \
	   file://fdiskbsdlabel_thumb.diff \
           file://defines.h"

S="${WORKDIR}/util-linux-${PV}"

EXTRA_OEMAKE="'OPT=${BUILD_CFLAGS}' 'CC=${BUILD_CC}' 'LD=${BUILD_LD}' 'LDFLAGS=${BUILD_LDFLAGS}' SBINDIR=${base_sbindir} USRSBINDIR=${base_sbindir} LOGDIR=${localstatedir}/log VARPATH=${localstatedir} LOCALEDIR=${datadir}/locale"

do_compile () {
	set -e
	install ${WORKDIR}/MCONFIG ${S}/MCONFIG
	install ${WORKDIR}/make_include ${S}/make_include
	install ${WORKDIR}/swapargs.h ${S}/mount/swapargs.h
	install ${WORKDIR}/defines.h ${S}/defines.h
	oe_runmake
}

do_stage () {
	autotools_stage_all
}


SRC_URI[md5sum] = "af9d9e03038481fbf79ea3ac33f116f9"
SRC_URI[sha256sum] = "b8e499b338ce9fbd1fb315194b26540ec823c0afc46c9e145ac7a3e38ad57e6b"
