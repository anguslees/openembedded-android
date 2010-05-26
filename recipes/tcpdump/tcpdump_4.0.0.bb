DESCRIPTION = "A sophisticated network protocol analyzer"
HOMEPAGE = "http://www.tcpdump.org/"
LICENSE = "BSD"
SECTION = "console/network"
PRIORITY = "optional"
DEPENDS = "libpcap"
PR = "r3"

SRC_URI = " \
	http://www.tcpdump.org/release/tcpdump-${PV}.tar.gz \
	file://tcpdump_configure_no_-O2.patch \
	file://no-ipv6-tcpdump4.patch \
	file://0001-minimal-IEEE802.15.4-allowed.patch \
	file://ipv6-cross.patch \
	file://configure.patch \
"

inherit autotools
# ac_cv_linux_vers=${ac_cv_linux_vers=2}

EXTRA_OECONF = "--without-crypto \
		${@base_contains('DISTRO_FEATURES', 'ipv6', '--enable-ipv6', '--disable-ipv6', d)}"

do_configure() {
	gnu-configize
	autoconf
	oe_runconf
	sed -i 's:/usr/lib:${STAGING_LIBDIR}:' ./Makefile
	sed -i 's:/usr/include:${STAGING_INCDIR}:' ./Makefile
}

do_install_append() {
	# tcpdump 4.0.0 installs a copy to /usr/sbin/tcpdump.4.0.0
	rm -f ${D}${sbindir}/tcpdump.${PV}
}

SRC_URI[md5sum] = "b22ca72890df2301d922c9f2d17867f9"
SRC_URI[sha256sum] = "5fe99efe0274b5764c9974417c96a38b5f94e21096d8a94cc1fa172a42035284"
