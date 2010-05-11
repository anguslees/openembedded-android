DESCRIPTION = "Libcwd is a thread-safe, full-featured debugging support library for C++ developers. \
It includes ostream-based debug output with custom debug channels and devices, \
powerful memory allocation debugging support, as well as run-time support for \
printing source line number information and demangled type names."
SECTION = "devel/libs"
LICENSE = "QPL"
HOMEPAGE = "http://libcwd.sourceforge.net"
PR = "r0"

SRC_URI = "${SOURCEFORGE_MIRROR}/libcwd/libcwd-${PV}.tar.gz"

inherit autotools pkgconfig

do_configure() {
	gnu-configize
	oe_runconf
}

do_stage() {
	autotools_stage_all
}

PARALLEL_MAKE = ""

PACKAGES =+ "${PN}-config ${PN}-mt"
FILES_${PN} = "${libdir}/libcwd.so*"
FILES_${PN}-mt = "${libdir}/libcwd_r.so*"
FILES_${PN}-config = "${datadir}"
RRECOMMENDS_${PN} = "${PN}-config"
RRECOMMENDS_${PN}-mt = "${PN}-config"

SRC_URI[md5sum] = "9f734d279fa7a91f81d07fb9fd5de049"
SRC_URI[sha256sum] = "8e9bfe9838fce18bb55d41ec883be5b7330aeead265feec0347745e8e589822a"
