DESCRIPTION = "Linux driver for 802.11a/b/g universal NIC cards using Atheros chip sets"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "GPL"
RDEPENDS_${PN} = "kernel (${KERNEL_VERSION})"
DEPENDS = "virtual/kernel"
PV = "1.0.0+cvs${SRCDATE}-bsd"
DEFAULT_PREFERENCE = "-1"
PR = "r1"

SRC_URI = "cvs://anonymous@madwifi.cvs.sourceforge.net/cvsroot/madwifi;module=madwifi;tag=BSD;date=${SRCDATE}"

S = "${WORKDIR}/madwifi"

inherit module-base

# Hack Alert :D
ARCH_mipsel = "mips"
EXTRA_OEMAKE_mtx-1 = "TARGET=mips-le-elf KERNELPATH=${STAGING_KERNEL_DIR} KERNELRELEASE=${KERNEL_VERSION} TOOLPREFIX=${TARGET_PREFIX} \
EXTRA_OEMAKE_mtx-2 = "TARGET=mips-le-elf KERNELPATH=${STAGING_KERNEL_DIR} KERNELRELEASE=${KERNEL_VERSION} TOOLPREFIX=${TARGET_PREFIX} \
COPTS='-G 0 -mno-abicalls -fno-pic -Wa,--trap -fno-strict-aliasing -fno-common -fomit-frame-pointer -mlong-calls -DATH_PCI'"

do_compile() {
	oe_runmake
	cd tools; oe_runmake
}

do_install() {
	oe_runmake DESTDIR=${D} install
	install -d ${D}${sbindir}
	cd tools;
	oe_runmake DESTDIR=${D} BINDIR=${sbindir} install
	install -m 755 athchans athctrl athkey ${D}${sbindir}
}

do_stage() {
	# hostapd needs these files
	install -d ${STAGING_INCDIR}/net80211
	install -m 0644 net80211/*.h ${STAGING_INCDIR}/net80211
	install -m 0644 include/compat.h ${STAGING_INCDIR}/net80211
}

pkg_postinst() {
if test "x$D" != "x"; then
       exit 1
else
	depmod -ae
fi
}

PACKAGES = "${PN}-dbg madwifi-tools ${PN}"
FILES_${PN} = "/lib/modules/"
FILES_madwifi-tools = "/usr/"
