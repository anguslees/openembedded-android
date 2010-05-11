# libaio-oracle .bb build file
# Copyright (C) 2005-2006, Advanced Micro Devices, Inc.  All Rights Reserved
# Released under the MIT license (see /COPYING)

DESCRIPTION="This is a library for accessing the new AIO system calls (asynchronous i/o) for the Linux kernel."
HOMEPAGE="http://oss.oracle.com/projects/libaio-oracle"
LICENSE="LGPL"
PR = "r1"

SRC_URI="http://oss.oracle.com/projects/libaio-oracle/dist/files/${PN}-${PV}.tar.gz"

DEPENDS="libaio"
RDEPENDS="libaio"

inherit autotools

FILES_${PN}="/usr"

#We don't have a raw.h with uClibc, so copy in the one from glibc
SRC_URI_append_linux-uclibc=" file://raw.h"

do_configure_prepend_linux-uclibc () {
	install -D -m 0644 ${WORKDIR}/raw.h ${S}/sys/raw.h
}

do_stage() {

	rm -rf ${STAGE_TEMP}
	mkdir -p ${STAGE_TEMP}
	oe_runmake DESTDIR="${STAGE_TEMP}" install
	install -m 0644 ${STAGE_TEMP}/usr/include/libaio-oracle/libaio-oracle.h ${STAGING_INCDIR}
	rm -f ${STAGE_TEMP}/usr/lib/*.la
	oe_libinstall -a -so -C ${STAGE_TEMP}/usr/lib libaio-oracle ${STAGING_LIBDIR}
}

SRC_URI[md5sum] = "165bbd6c75b8c1916e4b03988bafb372"
SRC_URI[sha256sum] = "953e39b44b80bb89e52b00f7a7c4045fdd9c698e265bfa14a3d2291e64dffaf1"
