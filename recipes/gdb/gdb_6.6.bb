require gdb.inc
LICENSE = "GPLv2"

PR = "r5"

SRC_URI += "file://early_debug_in_nptl.patch;striplevel=0"

SRC_URI[md5sum] = "18be4e7a1ac713bda93d00fee20bbef8"
SRC_URI[sha256sum] = "c119ce82f0e42d3412420c42c1ead75e0b9de67131a5c9dd3cdab509f65ddfdd"
