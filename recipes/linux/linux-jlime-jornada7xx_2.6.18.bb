SECTION = "kernel"
DESCRIPTION = "JLime Linux kernel for Arm based Jornada 7xx"
LICENSE = "GPLv2"
PR = "r0"

COMPATIBLE_HOST = "arm.*-linux"
COMPATIBLE_MACHINE = "jornada7xx"

SRC_URI = "${KERNELORG_MIRROR}/pub/linux/kernel/v2.6/linux-2.6.18.tar.gz \
           file://defconf_jlime \
	   file://linux-2.6.18-jornada7xx.patch"

S = "${WORKDIR}/linux-${PV}"

inherit kernel

#Lets let 3.4.x handle the compilation of this one
KERNEL_CCSUFFIX = "-3.4.4"

ARCH = "arm"
FILES_kernel-image = "/boot/${KERNEL_IMAGETYPE}*"

do_configure_prepend() {
	install -m 0644 ${WORKDIR}/defconf_jlime ${S}/.config
}

SRC_URI[md5sum] = "bc483723670bda09198d72293e712d42"
SRC_URI[sha256sum] = "eae56a8a9c788518e88604fff343ce6139cecbc7e44356bf1ff4dc7aaf4e9b33"
