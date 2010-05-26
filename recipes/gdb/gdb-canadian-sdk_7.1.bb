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

SRC_URI[md5sum] = "01a6ce13bab5307cfac5c405e4afd1cf"
SRC_URI[sha256sum] = "fc839b5226b94e58a5c7a526e67b7f8f93c7829d05dae1b6247f1bfe3829f291"
