DESCRIPTION = "Linux kernel for Micrel KS8695(P) based devices"
SECTION = "kernel"
LICENSE = "GPLv2"
PR = "r0"

SRC_URI = "${KERNELORG_MIRROR}/pub/linux/kernel/v2.6/linux-${PV}.tar.bz2 \
           file://ks8695-headers-r0.patch \
           file://ks8695-base-r0.patch \
           file://defconfig-ks8695"

S = "${WORKDIR}/linux-${PV}"

COMPATIBLE_HOST = 'arm.*-linux'
COMPATIBLE_MACHINE = "ks8695"

inherit kernel
inherit package

ARCH = "arm"
CMDLINE = "ttyS0,115200n8 root=/dev/mtdblock2 init=/linuxrc"

do_configure_prepend() {
	install -m 0644 ${WORKDIR}/defconfig-${MACHINE} ${S}/.config
	echo "CONFIG_CMDLINE=\"${CMDLINE}\"" >> ${S}/.config
}

SRC_URI[md5sum] = "9a91b2719949ff0856b40bc467fd47be"
SRC_URI[sha256sum] = "1200dcc7e60fcdaf68618dba991917a47e41e67099e8b22143976ec972e2cad7"
