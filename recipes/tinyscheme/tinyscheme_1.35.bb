DESCRIPTION = "TinyScheme is a lightweight Scheme interpreter that implements as large a subset of R5RS \
as was possible without getting very large and complicated."
SECTION = "devel"
LICENSE = "BSD"

SRC_URI = "http://tinyscheme.sourceforge.net/tinyscheme-${PV}.tar.gz"

EXTRA_OEMAKE = "-e scheme libtinyscheme.so"

export LD="${CCLD}"

do_install() {
	install -d ${D}${bindir}
	install -d ${D}${libdir}
	oe_libinstall -so libtinyscheme ${D}${libdir}
	install -m 0755 scheme ${D}${bindir}
}

PACKAGES =+ "libtinyscheme"
FILES_libtinyscheme = "${libdir}"
FILES_${PN} = "${bindir}"

SRC_URI[md5sum] = "2dcdbfdca5aa4b28a637429900dcf36c"
SRC_URI[sha256sum] = "7e45b6f6824b1342e598dee162368ed8d1c9c1bcee96d8e1eda9a4c63c599f08"
