# cdparanoia OE build file
# Copyright (C) 2005, Advanced Micro Devices, Inc.  All Rights Reserved
# Released under the MIT license (see packages/COPYING)
LICENSE = "GPL"

PR = "r3"

SRC_URI = "http://downloads.xiph.org/releases/cdparanoia/cdparanoia-III-alpha9.8.src.tgz \
         file://fixes.patch \
         file://Makefile.patch"

S = "${WORKDIR}/cdparanoia-III-alpha9.8"

inherit autotools

PARALLEL_MAKE = ""

do_install() {
        oe_runmake BINDIR="${D}/usr/bin" MANDIR="${D}/usr/share/man/" \
                   INCLUDEDIR="${D}/usr/include/" LIBDIR="${D}/usr/lib" install
}
do_stage() {
        install -d ${STAGING_INCDIR} ${STAGING_LIBDIR}
        install -m 0644 paranoia/cdda_paranoia.h ${STAGING_INCDIR}
        install -m 0644 interface/cdda_interface.h ${STAGING_INCDIR}
        oe_libinstall -C interface libcdda_interface ${STAGING_LIBDIR}
        oe_libinstall -C paranoia libcdda_paranoia ${STAGING_LIBDIR}
}


SRC_URI[md5sum] = "7218e778b5970a86c958e597f952f193"
SRC_URI[sha256sum] = "1b79fae1aedc692f87d1344410f5c6b666961afccdc78bc5c4c257c450dfa008"
