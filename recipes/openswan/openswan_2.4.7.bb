SECTION = "console/network"
DESCRIPTION = "Openswan is an Open Source implementation of IPsec for the \
Linux operating system."
HOMEPAGE = "http://www.openswan.org"
LICENSE = "GPLv2"
DEPENDS = "gmp flex-native"
RRECOMMENDS = "kernel-module-ipsec"
RDEPENDS_append_nylon = "perl"
PR = "r1"

SRC_URI = "http://www.openswan.org/download/openswan-${PV}.tar.gz \
	   file://openswan-2.4.7-gentoo.patch;patch=1 \
           file://installflags.patch;patch=1 \
	   file://ld-library-path-breakage.patch;patch=1"
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

SRC_URI[md5sum] = "70f22e8adc39e07a165f75eccb7cd079"
SRC_URI[sha256sum] = "b839b024fd717a404711f5ec14ee9b9e412a080f23e90841cde14f20970dc5dd"
