DESCRIPTION = 'The PCI Utilities package contains a library for portable access \
to PCI bus configuration space and several utilities based on this library.'
DESCRIPTION_pciutils-ids = 'The list of PCI IDs for pciutils'
SECTION = "console/utils"
HOMEPAGE = "http://atrey.karlin.mff.cuni.cz/~mj/pciutils.shtml"
LICENSE = "GPLv2"
DEPENDS = "zlib"

SRC_URI = "ftp://ftp.kernel.org/pub/software/utils/pciutils/pciutils-${PV}.tar.bz2 \
	   file://pciutils.patch "
SRC_URI_append_nylon = "file://gcc-3-compatibility.patch "

PARALLEL_MAKE = ""

PR = "r6"

EXTRA_OEMAKE += "'STRIP = ' PREFIX=${prefix} LIBDIR=${libdir}"
export SHARED=yes
export DESTDIR = "${D}"
export PREFIX = "${prefix}"
export SBINDIR = "${sbindir}"
export SHAREDIR = "${datadir}"
export MANDIR = "${mandir}"
export IDSDIR = "${datadir}"

do_configure () {
	export ZLIB=yes
	(cd lib && ./configure ${datadir} ${PV} ${TARGET_OS} 2.4.21 ${TARGET_ARCH})
}

do_install () {
	oe_runmake install install-lib

	# "make install" misses the debug file for the library
	oe_libinstall -so -C lib libpci ${D}/${libdir}

	# Some older versions of hal may need the uncompressed version.
	# We install it in a separate package, pciutils-ids-uncompressed.
	install -m 0644 ${S}/pci.ids ${D}/${datadir}
}

PACKAGES =+ "${PN}-ids ${PN}-ids-uncompressed"
FILES_${PN}-ids = "${datadir}/pci.ids.gz"
FILES_${PN}-ids-uncompressed = "${datadir}/pci.ids"

SRC_URI[md5sum] = "46387fd7a18c33fbb5311fdb3ab9ea12"
SRC_URI[sha256sum] = "f66e40b8df5a7b6a93c463b0fb734ea6451c5f0c2cc772f58640b6c7f0d69397"
