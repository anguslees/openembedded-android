DESCRIPTION = "Flashrom is a simple utility for reading/writing BIOS chips"
HOMEPAGE = "http://www.coreboot.org/Flashrom"
LICENSE = "GPL"
DEPENDS = "zlib pciutils"

SRCREV = "3682"
PV = "0.0+svnr${SRCPV}"
PR = "r0"

SRC_URI = " \
	svn://coreboot.org/repos/trunk/util/;module=flashrom \
	file://makefile-disable-strip.patch \
	"

S = "${WORKDIR}/${PN}"

LDFLAGS += "-lpci -lz"

do_install () {
	install -d "${D}${sbindir}"
	oe_runmake 'PREFIX=${D}/${prefix}' install
}
