# libaio .bb build file
# Copyright (C) 2005-2006, Advanced Micro Devices, Inc.  All Rights Reserved
# Released under the MIT license (see /COPYING)

DESCRIPTION="Asynchronous input/output library that uses the kernels native interface"
HOMEPAGE="https://rhn.redhat.com/errata/RHBA-2005-085.html"
LICENSE="GPL"

SRC_URI="http://search.belnet.be/packages/lineox/4.0/updates/SRPMS/${P}-3.src.rpm \
file://${P}-more-arches.patch"

S="${WORKDIR}/${P}"

FILES_${PN} = "/usr"

inherit kernel-arch

DEPENDS_append = " rpm2cpio-native"

do_unpack() {

	if ! test -f libaio-${PV}.tar.gz ; then
		rpm2cpio.pl ${DL_DIR}/${P}-3.src.rpm | cpio -i --make-directories
		tar xzvf libaio-${PV}.tar.gz
	fi
}

do_stage () {
	#make install prefix='${STAGING_DIR_TARGET}${layout_prefix}'
        install -D -m 644 src/libaio.h ${STAGING_INCDIR}/libaio.h
	oe_libinstall -so -C src libaio ${STAGING_LIBDIR}

}

do_install () {
	make install prefix='${D}/usr'
}

SRC_URI[md5sum] = "3d6ca28cb7cf73ca1d9fe902924bdc84"
SRC_URI[sha256sum] = "9f578643db9fff6ae088a492e6a4aca3cac4c2cbc51afce551687f4f0d36465f"
