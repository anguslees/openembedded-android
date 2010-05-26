SECTION = "console/network"
DESCRIPTION = "Openswan is an Open Source implementation of IPsec for the \
Linux operating system."
HOMEPAGE = "http://www.openswan.org"
LICENSE = "GPLv2"
DEPENDS = "gmp flex-native"
RRECOMMENDS = "kernel-module-ipsec"
RDEPENDS_append_nylon = "perl"
PR = "r6"

SRC_URI = "http://www.openswan.org/download/old/openswan-${PV}.tar.gz \
	   file://openswan-2.2.0-gentoo.patch \
           file://gcc4-fixes.patch \
           file://installflags.patch \
	   file://makefile-whitespace-fix.patch \
	   file://ld-library-path-breakage.patch"
S = "${WORKDIR}/openswan-${PV}"

PARALLEL_MAKE = ""
EXTRA_OEMAKE = "DESTDIR=${D} \
                USERCOMPILE="${CFLAGS}" \
                USERLINK="${LDFLAGS}" \
                FINALCONFDIR=${sysconfdir}/ipsec \
                INC_RCDEFAULT=${sysconfdir}/init.d \
                INC_USRLOCAL=${prefix} \
                INC_MANDIR=share/man WERROR=''"

do_compile () {
	oe_runmake programs
}

do_install () {
	oe_runmake install
}

FILES_${PN} = "${sysconfdir} ${libdir}/ipsec/* ${sbindir}/* ${libexecdir}/ipsec/*"
FILES_${PN}-dbg += "${libdir}/ipsec/.debug ${libexecdir}/ipsec/.debug"

CONFFILES_${PN} = "${sysconfdir}/ipsec/ipsec.conf"

SRC_URI[md5sum] = "f5f83204652627cf51d2567c53df5520"
SRC_URI[sha256sum] = "62d36b998b9ec21864263d17e83aeb30a300a8f0d336a057ca9610b447b9ce7a"
