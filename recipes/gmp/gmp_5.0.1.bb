INC_PR = "r0"
PR = "${INC_PR}.1"
SRC_URI[gmp.md5sum] = "6bac6df75c192a13419dfd71d19240a7"
SRC_URI[gmp.sha256sum] = "a2a610f01fd3298dc08c87bf30498c2402590e1bcb227fc40b15ee6d280939fb"
require gmp.inc
LICENSE = "GPLv3 LGPLv3"
NATIVE_INSTALL_WORKS = "1"
BBCLASSEXTEND = "native nativesdk"
