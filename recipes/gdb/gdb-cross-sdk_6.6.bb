require gdb-cross.inc
LICENSE = "GPLv2"

inherit sdk

DEPENDS = "ncurses-sdk zlib-sdk flex-native"

PR = "r3"

SRC_URI += "file://early_debug_in_nptl.patch;striplevel=0"

do_stage() {
	:
}

SRC_URI[md5sum] = "18be4e7a1ac713bda93d00fee20bbef8"
SRC_URI[sha256sum] = "c119ce82f0e42d3412420c42c1ead75e0b9de67131a5c9dd3cdab509f65ddfdd"
