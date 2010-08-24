DESCRIPTION = "Simple ffmpeg-based player that uses the omapfb overlays"
DEPENDS = "bzip2 lame ffmpeg virtual/kernel"
LICENSE = "MIT"

PR = "r21"

PV = "0.0+${PR}+gitr${SRCREV}"

SRCREV = "190b44619bac188d86e8dcee74b0853e33fb7de0"
SRC_URI = "git://git.mansr.com/${PN};protocol=git \
"

S = "${WORKDIR}/git"

# We want a kernel header for armv7a, but we don't want to make mplayer machine specific for that
STAGING_KERNEL_DIR = "${STAGING_DIR}/${MACHINE_ARCH}${TARGET_VENDOR}-${TARGET_OS}/kernel"

CFLAGS += " -I. -I${STAGING_KERNEL_DIR}/include "

do_compile() {
	cp ${STAGING_KERNEL_DIR}/arch/arm/plat-omap/include/mach/omapfb.h ${S} || true
	cp ${STAGING_KERNEL_DIR}/include/asm-arm/arch-omap/omapfb.h ${S} || true
	cp ${STAGING_KERNEL_DIR}/include/linux/omapfb.h ${S} || true
	oe_runmake ${TARGET_ARCH}=y OMAPFB=y NETSYNC=y -e
}

do_install() {
	install -d ${D}/${bindir}
	install -m 0755 ${S}/omapfbplay ${D}/${bindir}/
}
