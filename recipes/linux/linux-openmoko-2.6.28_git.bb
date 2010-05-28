require linux.inc
require linux-openmoko.inc

KERNEL_RELEASE = "2.6.28"
KERNEL_VERSION = "${KERNEL_RELEASE}"

SRCREV = "8c65792a5c83c76d662a617a7c4e1ae8104bb6a5"
OEV = "oe2"
PV = "${KERNEL_RELEASE}-${OEV}+gitr${SRCPV}"
PR = "r0"

SRC_URI = "\
  git://git.openmoko.org/git/kernel.git;protocol=git;branch=stable \
  file://defconfig-oe.patch;apply=no \
"
S = "${WORKDIR}/git"

CONFIG_NAME_om-gta01 = "gta01_moredrivers_defconfig"
CONFIG_NAME_om-gta02 = "gta02_packaging_defconfig"

do_configure_prepend() {
	install -m 644 ./arch/arm/configs/${CONFIG_NAME} ${WORKDIR}/defconfig-oe
	cat ${WORKDIR}/defconfig-oe.patch | patch -p0 -d ${WORKDIR}
}


