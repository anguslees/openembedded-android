DESCRIPTION = "Linux Kernel for x86 compatible machines"
SECTION = "kernel"
LICENSE = "GPLv2"
PR = "r5"

SRC_URI = "${KERNELORG_MIRROR}/pub/linux/kernel/v2.6/linux-${PV}.tar.bz2 " 

SRC_URI_append_x86 = "file://i486-defconfig"
SRC_URI_append_i586 = "file://i586-defconfig"
SRC_URI_append_i686 = "file://i686-defconfig"

S = "${WORKDIR}/linux-${PV}"

KERNEL_IMAGETYPE ?= "bzImage"

inherit kernel

COMPATIBLE_HOST = "i.86.*-linux"

do_configure_prepend_x86() {
	install -m 0644 ${WORKDIR}/i486-defconfig ${S}/.config
}

do_configure_prepend_i586-generic() {
	install -m 0644 ${WORKDIR}/i586-defconfig ${S}/.config
}

do_configure_prepend_i686() {
	install -m 0644 ${WORKDIR}/i686-defconfig ${S}/.config
}

SRC_URI[md5sum] = "34b0f354819217e6a345f48ebbd8f13e"
SRC_URI[sha256sum] = "2c14ada1ac7d272e03b430d3a530d60fc9ec69cc8252382aa049afba7d2b8558"
