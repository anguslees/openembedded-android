require gdb.inc
LICENSE = "GPLv3"

SRC_URI += "file://gcc-4.3-build-error.patch;striplevel=0 \
            file://gdb-6.8-fix-compile-karmic.patch"

PR = "r3"

SRC_URI[md5sum] = "925695333524108291dc4012c4c9cbb8"
SRC_URI[sha256sum] = "a3c5455f30ffb3e6eeafb9e54598b48f9fbf46e3e99f431120360c183ddcc889"
