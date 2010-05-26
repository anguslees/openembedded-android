SECTION = "kernel"
DESCRIPTION = "Linux kernel for SH4 based TITAN (NP51R/LinkGear Series 100) router appliance"
LICENSE = "GPLv2"
PR = "r2"

SRC_URI = "${KERNELORG_MIRROR}/pub/linux/kernel/v2.6/linux-${PV}.tar.bz2 \
	   file://titan-flash.patch \
	   file://titan-pcibios-scan-update.patch \
           file://no-mm-mutex.patch \
	   file://linux-2.6-limits.patch \
	   file://linux-sh-__sdivsi3_i4i.patch \
	   file://titan-config"
S = "${WORKDIR}/linux-${PV}"

COMPATIBLE_HOST = 'sh4.*-linux'
COMPATIBLE_MACHINE = "titan"

inherit kernel

ARCH = "sh"
KERNEL_OUTPUT = "arch/${ARCH}/boot/${KERNEL_IMAGETYPE}"

#
# Use an updated defconfig which includes the flash driver
# The flash driver quality doesn't allow it to be a part of the main kernel
#
do_configure_prepend() {
	install -m 0644 ${WORKDIR}/titan-config ${S}/arch/sh/configs/titan_defconfig
	yes '' | oe_runmake titan_defconfig
}

SRC_URI[md5sum] = "1b515f588078dfa7f4bab2634bd17e80"
SRC_URI[sha256sum] = "f187b12d70e0a48ce81f0472dfe9504fb5f0f966be339ac9d57dd2b991a74942"
