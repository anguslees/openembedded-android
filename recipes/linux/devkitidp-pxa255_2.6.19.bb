SECTION = "kernel"
DESCRIPTION = "Linux kernel for the BSQUARE PXA255 DevKitIDP"
LICENSE = "GPLv2"
PR = "r4"
DEPENDS = "u-boot"

SRC_URI = "${KERNELORG_MIRROR}/pub/linux/kernel/v2.6/linux-2.6.19.tar.bz2 \
	   file://linux-2.6.19_devkitidp1.patch \
	   file://defconfig"

S = "${WORKDIR}/linux-2.6.19"

COMPATIBLE_MACHINE = "devkitidp-pxa255"

inherit kernel
inherit package

ARCH = "arm"
KERNEL_IMAGETYPE = "uImage"
#CMDLINE_CONSOLE ?= "ttyS0,115200n8"
#CMDLINE_ROOT = "root=/dev/slug rootfstype=ext2,jffs2 initrd=0x01000000,10M mem=32M@0x00000000"
#CMDLINE_ROOT = "root=/dev/mtdblock4 rootfstype=jffs2 mem=32M@0x00000000"
#CMDLINE_ROOT = "root=/dev/ram0 rw rootfstype=ext2,jffs2 initrd=0x01000000,10M init=/linuxrc mem=32M@0x00000000"
#CMDLINE = "${CMDLINE_ROOT} ${CMDLINE_CONSOLE}"
CMDLINE = "root=/dev/mtdblock2 rootfstype=jffs2 console=ttyS0,115200 mtdparts=phys_mapped_flash:256k(boot)ro,0x1C0000(kernel),-(root)"

do_configure_prepend() {
	install -m 0644 ${WORKDIR}/defconfig ${S}/.config
#	echo "CONFIG_CMDLINE=\"${CMDLINE}\"" >> ${S}/.config
}

SRC_URI[md5sum] = "443c265b57e87eadc0c677c3acc37e20"
SRC_URI[sha256sum] = "c2fd6bcd2b7c1b3d37d64e4d1825703792a75474830a3db7d2dc603a8d392d58"
