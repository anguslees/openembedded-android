require gdb-canadian-cross.inc
LICENSE = "GPLv3"

SRC_URI += "\
	    file://gdb-6.8-mingw-3.patch \
	    file://gdb-6.8-pr9638-ppc-canadian-configh.patch \
	    file://gdb-6.8-fix-compile-karmic.patch \
	    "
do_stage() {
	:
}

SRC_URI[md5sum] = "67b4144db385620d7b93f7b0c26800f7"
SRC_URI[sha256sum] = "ccff6cab0e847c486d30b70bdddac4e6646bafcecaa3b63134d6e3d4d5a4b91d"
