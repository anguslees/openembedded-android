DESCRIPTION = "A packet dissection and creation library"
HOMEPAGE = "http://www.packetfactory.net/libnet/"
SECTION = "libs"
PRIORITY = "optional"
LICENSE = "BSD"
DEPENDS = "libpcap"
# There are major API changes beween libnet v1.0 and libnet v1.1
PROVIDES = "libnet-1.1"
PR = "r4"

SRC_URI = "${DEBIAN_MIRROR}/main/libn/libnet/libnet_${PV}.orig.tar.gz \
           file://support-uclibc.patch \
           file://fix-endianess-test.patch \
           file://new-autotools.patch"

S = "${WORKDIR}/libnet"

inherit autotools binconfig

do_configure_prepend() {
        rm -f aclocal.m4 Makefile.am ltmain.sh
}

do_install_append () {
        oe_runmake -C src 'DESTDIR=${D}${libdir}/' 'libdir=' install-libLIBRARIES
        oe_runmake -C include 'DESTDIR=${D}${includedir}/' 'includedir=' install-includeHEADERS
        oe_runmake -C include/libnet 'DESTDIR=${D}${includedir}/' 'includedir=' install-libnetincludeHEADERS
        install -d ${D}${datadir}/man/man3/
        install -d ${D}${bindir}
        install -m 0644 ${S}/doc/man/man3/*.3 ${D}${datadir}/man/man3/
        install -m 0755 ${S}/libnet-config ${D}${bindir}
}

#static build
FILES_${PN} = ""
FILES_${PN}-dev += "${bindir}/libnet-config"

CPPFLAGS_prepend = "-I${S}/libnet/include "

SRC_URI[md5sum] = "be845c41170d72c7db524f3411b50256"
SRC_URI[sha256sum] = "ab01882a3d0556176018c09342cd0731f7cbc8e687795009894c3326942c76ff"
